package com.charleslgn.intellitwitch.settings

import com.charleslgn.intellitwitch.twitch.api.TwitchClient
import com.charleslgn.intellitwitch.twitch.api.TwitchOauth2
import com.charleslgn.intellitwitch.twitch.api.TwitchScope

class TwitchOAuth2Action(
    private val twitchOauth2: TwitchOauth2 = TwitchOauth2(),
    private val client: TwitchClient = TwitchClient.instance,
    private val scope: TwitchScope = TwitchScope.instance,
) {

    fun connect() {
        twitchOauth2.connect(scope.scope, client.id)
    }
}