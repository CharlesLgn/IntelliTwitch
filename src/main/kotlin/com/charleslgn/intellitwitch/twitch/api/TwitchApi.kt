package com.charleslgn.intellitwitch.twitch.api


interface TwitchApi {

    fun connect(code: String, redirectUri: String)
    fun refreshToken(refreshToken: String)

    val globalBadge: Collection<BadgeData>
    val myStreamers: Collection<StreamerData>
    val broadcasterData: BroadcasterData
    fun moderatedChannel(streamerName:String): Boolean

    fun broadcasterData(streamer: String): BroadcasterData
    fun streamerBadge(streamer: String): Collection<BadgeData>

    val oauthToken: String

    fun emotes(ids:List<String>): Map<String, String>

    companion object {
        private var impl: TwitchApi? = null

        val instance: TwitchApi
            get() {
                if (impl == null) {
                    impl = TwitchApiImpl()
                }
                return impl!!
            }
    }
}