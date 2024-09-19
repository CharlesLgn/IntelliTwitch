package com.charleslgn.intellitwitch.twitch.api

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon

data class TwitchClient(
    @Json(name = "CLIENT_ID")
    val id:String,
    @Json(name = "CLIENT_SECRET")
    val secret:String,
) {

    companion object {
        val instance: TwitchClient
            get() = TwitchClient::class.java.classLoader.getResourceAsStream("twitchClient.json")
                .use { input -> return input?.let { Klaxon().parse<TwitchClient>(it) }!! }
    }
}
