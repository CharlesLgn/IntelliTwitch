package com.charleslgn.intellitwitch.twitch.api

import com.beust.klaxon.Json

data class StreamerData(
    val id: String,
    @Json(name = "user_id")
    val userId: String,
    @Json(name = "user_login")
    val userLogin: String,
    @Json(name = "user_name")
    val userName: String,
    @Json(name = "game_id")
    val gameId: String?,
    @Json(name = "game_name")
    val gameName: String?,
    val type: String?,
    val title: String?,
    @Json(name = "viewer_count")
    val viewerCount: Int?,
    @Json(name = "started_at")
    val startedAt: String?,
    val language: String?,
    @Json(name = "thumbnail_url")
    val thumbnailUrl: String?,
    @Json(name = "tag_ids")
    val tagIds: List<String>,
    val tags: List<String>
) : Comparable<StreamerData> {

    override fun compareTo(other: StreamerData): Int {
        if (other == this) {
            return 0
        }
        if (other.type != "live") {
            return -1
        }
        if (this.type != "live") {
            return -1
        }
        return this.viewerCount?.compareTo(other.viewerCount!!)!!
    }
}