package com.charleslgn.intellitwitch.twitch.message

import java.net.URL

enum class StaticTwitchBadge(override val icon: URL?): TwitchBadge{
    STAFF({}.javaClass.getResource("/twitch/icon/staff.png")),
    ADMIN({}.javaClass.getResource("/twitch/icon/admin.png")),
    BROADCASTER({}.javaClass.getResource("/twitch/icon/broadcaster.png")),
    MODERATOR({}.javaClass.getResource("/twitch/icon/moderator.png")),
    VERIFIED({}.javaClass.getResource("/twitch/icon/verified.png")),
    VIP({}.javaClass.getResource("/twitch/icon/vip.png")),
    ARTIST({}.javaClass.getResource("/twitch/icon/artist.png")),
    GAME_DEVELOPER({}.javaClass.getResource("/twitch/icon/game_developer.png")),
    WATCHING_WITHOUT_VIDEO({}.javaClass.getResource("/twitch/icon/watching_without_video.png")),
    WATCHING_WITHOUT_AUDIO({}.javaClass.getResource("/twitch/icon/watching_without_audio.png")),
    TURBO_USER({}.javaClass.getResource("/twitch/icon/turbo_user.png")),
    PRIME_GAMING_USER({}.javaClass.getResource("/twitch/icon/prime_gaming_user.png")),
    ;
}