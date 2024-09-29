package com.charleslgn.intellitwitch.twitch.api

import com.beust.klaxon.Json

data class BroadcasterData(
    @Json(name = "id")
    val id: String,
    @Json(name = "login")
    val login: String,
    @Json(name = "profile_image_url")
    val profileImageUrl: String,
) {
    val onScaleProfileImageUrl: String
       get() = profileImageUrl
}

data class ModeratedChannelData(
    @Json(name = "broadcaster_id")
    val id: String,
    @Json(name = "broadcaster_login")
    val login: String,
    @Json(name = "broadcaster_name")
    val name: String,
)