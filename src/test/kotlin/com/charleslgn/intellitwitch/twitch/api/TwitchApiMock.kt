package com.charleslgn.intellitwitch.twitch.api

/**
 * For testing usage
 */
class TwitchApiMock : TwitchApi {
    override fun connect(code: String, redirectUri: String) {
        // it's a mock. no need to connect
    }

    override fun refreshToken(refreshToken: String) {
        // it's a mock. no need to connect
    }

    override val globalBadge: Collection<BadgeData>
        get() = listOf(
            BadgeData(
                setId = "zevent-2024",
                versions = listOf(
                    BadgeVersion(
                        id = "1",
                        imageUrl1x = "https://static-cdn.jtvnw.net/mod.png",
                        imageUrl2x = "https://static-cdn.jtvnw.net/mod.png",
                        imageUrl4x = "https://static-cdn.jtvnw.net/mod.png",
                        title = "zevent-2024",
                        description = "zevent 2024 badge",
                        clickAction = "",
                        clickUrl = "",
                    )
                )
            ),
        )

    override val myStreamers: Collection<StreamerData>
        get() = listOf(
            StreamerData(
                id ="1",
                userId = "zerator",
                userLogin = "zerator",
                userName = "ZeratoR",
                gameId = "1234",
                gameName = "World of Warcraft",
                type = "MMORPG",
                title = "this is some weird title",
                viewerCount = 10548,
                startedAt = "102531",
                language = "fr",
                thumbnailUrl = "https://static-cdn.jtvnw.net/thumbnailUrl",
                tagIds = listOf(),
                tags = listOf(),
            ),
            StreamerData(
                id ="1",
                userId = "wingobear",
                userLogin = "wingo",
                userName = "Wingo",
                gameId = "1235",
                gameName = "Trackmania",
                type = "car game",
                title = "this is some other weird title",
                viewerCount = 759,
                startedAt = "100131",
                language = "fr",
                thumbnailUrl = "https://static-cdn.jtvnw.net/thumbnailUrl",
                tagIds = listOf(),
                tags = listOf(),
            )
        ).sorted()

    override val broadcasterData: BroadcasterData
        get() = BroadcasterData(
            profileImageUrl = "https://static-cdn.jtvnw.net/user/nyphew_/300x300/pict.png",
            id = "Nyphew_",
            login = "nyphew_"
        )

    override fun moderatedChannel(streamerName: String): Boolean = streamerName == "KametO"

    override fun broadcasterData(streamer: String): BroadcasterData {
        return when (streamer) {
            "wingo" -> BroadcasterData(
                id = "2",
                login = "Wingo",
                profileImageUrl = "https://static-cdn.jtvnw.net/user/nyphew_/300x300/wingo.png",
            )
            "zerator" -> BroadcasterData(
                id = "1",
                login = "ZeratoR",
                profileImageUrl = "https://static-cdn.jtvnw.net/user/nyphew_/300x300/zerator.png",
            )
            else -> BroadcasterData(
                id = "3",
                login = "KametO",
                profileImageUrl = "https://static-cdn.jtvnw.net/user/nyphew_/300x300/kameto.png",
            )
        }
    }

    override fun streamerBadge(streamer: String): Collection<BadgeData> = listOf(
        BadgeData(
            setId = "moderator",
            versions = listOf(
                BadgeVersion(
                    id = "1",
                    imageUrl1x = "https://static-cdn.jtvnw.net/mod.png",
                    imageUrl2x = "https://static-cdn.jtvnw.net/mod.png",
                    imageUrl4x = "https://static-cdn.jtvnw.net/mod.png",
                    title = "moderator",
                    description = "moderator badge",
                    clickAction = "",
                    clickUrl = "",
                )
            )
        ),
        BadgeData(
            setId = "subscriber",
            versions = listOf(
                BadgeVersion(
                    id = "0",
                    imageUrl1x = "https://static-cdn.jtvnw.net/sub.png",
                    imageUrl2x = "https://static-cdn.jtvnw.net/sub.png",
                    imageUrl4x = "https://static-cdn.jtvnw.net/sub.png",
                    title = "sub",
                    description = "sub 1 month badge",
                    clickAction = "",
                    clickUrl = "",
                ),
                BadgeVersion(
                    id = "1",
                    imageUrl1x = "https://static-cdn.jtvnw.net/sub.png",
                    imageUrl2x = "https://static-cdn.jtvnw.net/sub.png",
                    imageUrl4x = "https://static-cdn.jtvnw.net/sub.png",
                    title = "sub",
                    description = "sub 2 month badge",
                    clickAction = "",
                    clickUrl = "",
                ),
                BadgeVersion(
                    id = "42",
                    imageUrl1x = "https://static-cdn.jtvnw.net/sub.png",
                    imageUrl2x = "https://static-cdn.jtvnw.net/sub.png",
                    imageUrl4x = "https://static-cdn.jtvnw.net/sub.png",
                    title = "sub",
                    description = "sub 42 month badge",
                    clickAction = "",
                    clickUrl = "",
                ),
            )
        )
    )

    override val oauthToken: String
        get() = "thisIsMyToken_1234"

    override fun emotes(ids: List<String>): Map<String, String> = TwitchApiImpl().emotes(ids)
}