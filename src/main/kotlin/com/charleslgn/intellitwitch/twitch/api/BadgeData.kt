package com.charleslgn.intellitwitch.twitch.api

import com.beust.klaxon.Json


data class BadgeData(
    @Json(name = "set_id")
    val setId: String,
    @Json(name = "versions")
    val versions: List<BadgeVersion>
)

data class BadgeVersion(
    @Json(name = "id")
    val id: String?,
    @Json(name = "image_url_1x")
    val imageUrl1x: String,
    @Json(name = "image_url_2x")
    val imageUrl2x: String?,
    @Json(name = "image_url_4x")
    val imageUrl4x: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "click_action")
    val clickAction: String?,
    @Json(name = "click_url")
    val clickUrl: String?
)