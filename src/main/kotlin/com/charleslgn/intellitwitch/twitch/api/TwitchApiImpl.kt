package com.charleslgn.intellitwitch.twitch.api

import com.beust.klaxon.Converter
import com.beust.klaxon.Json
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import com.charleslgn.intellitwitch.settings.AppSettings
import com.jetbrains.rd.util.collections.SynchronizedMap
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair
import java.io.BufferedReader
import java.io.InputStreamReader

class TwitchApiImpl(
    private val jsonParser: Klaxon = Klaxon(),
    private val client: TwitchClient = TwitchClient.instance
) : TwitchApi {

    override var oauthToken: String = ""
    private var refreshToken: String = ""

    private var cachedBroadcasterData: BroadcasterData? = null
    private var cachedModeratedChannelData: Collection<ModeratedChannelData>? = null
    private var badgesGlobal: Collection<BadgeData>? = null
    private var broadcastersData: MutableMap<String, BroadcasterData> = SynchronizedMap()

    override fun connect(code: String, redirectUri: String) {
        val params: MutableList<NameValuePair> = ArrayList()
        params.add(BasicNameValuePair("code", code))
        params.add(BasicNameValuePair("grant_type", "authorization_code"))
        params.add(BasicNameValuePair("redirect_uri", redirectUri))
        authorize(params)
    }

    override fun refreshToken(refreshToken: String) {
        val params: MutableList<NameValuePair> = ArrayList()
        params.add(BasicNameValuePair("grant_type", "refresh_token"))
        params.add(BasicNameValuePair("refresh_token", refreshToken))
        authorize(params)
    }

    override val globalBadge: Collection<BadgeData>
        get() {
            if (badgesGlobal == null) {
                fetchBadgesGlobal()
            }
            return badgesGlobal!!
        }

    private fun fetchBadgesGlobal() {
        synchronized(this) {
            if (badgesGlobal == null) {
                badgesGlobal = fetchAll<BadgeData>("https://api.twitch.tv/helix/chat/badges/global")
            }
        }
    }

    override fun streamerBadge(streamer: String): Collection<BadgeData> {
        val data = fetchFirst<BroadcasterData>("https://api.twitch.tv/helix/users?login=$streamer")
        return fetchAll<BadgeData>("https://api.twitch.tv/helix/chat/badges?broadcaster_id=${data.id}")
    }

    override fun moderatedChannel(streamerName: String): Boolean {
        if (cachedModeratedChannelData == null) {
            synchronized(this) {
                cachedModeratedChannelData = fetchAll<ModeratedChannelData>("https://api.twitch.tv/helix/moderation/channels?user_id=${broadcasterData.id}")
            }
        }
        return streamerName == broadcasterData.login || (cachedModeratedChannelData?.any { streamerName == it.login } ?: false)
    }

    override val myStreamers: Collection<StreamerData>
        get() = fetchAll<StreamerData>("https://api.twitch.tv/helix/streams/followed?user_id=${broadcasterData.id}").sorted()

    override val broadcasterData: BroadcasterData
        get() {
            if (cachedBroadcasterData == null) {
                synchronized(this) {
                    cachedBroadcasterData = fetchFirst<BroadcasterData>("https://api.twitch.tv/helix/users")
                }
            }
            return cachedBroadcasterData!!
        }

    override fun broadcasterData(streamer: String): BroadcasterData {
        if (broadcastersData[streamer] == null) {
            broadcastersData[streamer] =
                fetchFirst<BroadcasterData>("https://api.twitch.tv/helix/users?login=$streamer")
        }
        return broadcastersData[streamer] ?: throw IllegalAccessError()
    }

    override fun emotes(ids: List<String>): Map<String, String> {
        if (ids.isEmpty()) {
            return mapOf()
        }
        val map: MutableMap<String, String> = HashMap()
        ids.forEach { emote -> map[emote] = toUrl(emote) }
        return map
    }

    private fun toUrl(emote: String) = "https://static-cdn.jtvnw.net/emoticons/v2/$emote/default/dark/1.0"

    private fun authorize(params: MutableList<NameValuePair>) {
        params.add(BasicNameValuePair("client_id", client.id))
        params.add(BasicNameValuePair("client_secret", client.secret))
        val client: HttpClient = HttpClientBuilder.create().build()
        val request = HttpPost("https://id.twitch.tv/oauth2/token")
        request.entity = UrlEncodedFormEntity(params)
        val response: HttpResponse = client.execute(request)
        val rd = BufferedReader(InputStreamReader(response.entity.content))
        val boby = rd.readLine()
        rd.close()
        val data = jsonParser.parse<TwitchAccessData>(boby)!!
        this.oauthToken = data.accessToken
        this.refreshToken = data.refreshToken
        AppSettings.instance.state.refreshTwitchToken = data.refreshToken
    }

    private inline fun <reified T> fetchFirst(url: String): T = fetchAll<T>(url).first()

    private inline fun <reified T> fetchAll(url: String): Collection<T> {
        while (oauthToken.isEmpty()) Thread.sleep(100)
        val client: HttpClient = HttpClientBuilder.create().build()
        val request = HttpGet(url)
        request.addHeader("Client-Id", TwitchClient.instance.id)
        request.addHeader("Authorization", "Bearer $oauthToken")
        val response: HttpResponse = client.execute(request)
        val rd = BufferedReader(InputStreamReader(response.entity.content))
        val boby = rd.readLine()
        rd.close()
        return jsonParser
            .converter(TwitchDataConverter(jsonParser))
            .parse<TwitchData>(boby)
            ?.parse<T>()!!
    }

    private data class TwitchData(val jsonParser: Klaxon, val data: String) {

        @Suppress("unused")
        inline fun <reified T> parse(): List<T> {
            return jsonParser.parseArray<T>(data)!!
        }
    }

    private class TwitchDataConverter(val jsonParser: Klaxon) : Converter {
        override fun canConvert(cls: Class<*>): Boolean = cls == TwitchData::class.java
        override fun toJson(value: Any): String = Klaxon().toJsonString(value)
        override fun fromJson(jv: JsonValue): TwitchData =
            TwitchData(jsonParser, jsonParser.toJsonString(jv.obj?.get("data")))
    }

    private data class TwitchAccessData(
        @Json(name = "access_token") val accessToken: String,
        @Json(name = "expires_in") val expiresIn: Int,
        @Json(name = "refresh_token") val refreshToken: String,
        @Json(name = "scope") val scope: List<String>,
        @Json(name = "token_type") val tokenType: String
        ?,
    )
}