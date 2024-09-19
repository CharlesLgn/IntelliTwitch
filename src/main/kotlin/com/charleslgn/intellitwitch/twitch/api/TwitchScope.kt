package com.charleslgn.intellitwitch.twitch.api

data class TwitchScope(val scope:List<String>) {

    companion object {
        val instance: TwitchScope
            get() = TwitchScope::class.java.classLoader.getResourceAsStream("twitchscope.txt").use { input ->
                return TwitchScope(input?.reader()?.useLines { it.toList() } ?: mutableListOf())
            }
    }
}
