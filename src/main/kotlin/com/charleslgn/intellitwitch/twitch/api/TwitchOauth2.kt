package com.charleslgn.intellitwitch.twitch.api

import com.intellij.ide.BrowserUtil
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress

class TwitchOauth2(private val twitchApi: TwitchApi = TwitchApi.instance) {

    fun connect(scopes: List<String>, clientId: String) {
        val responseType = "code"
        val redirectUri = "http://localhost:9856/oauth"
        val server: HttpServer = HttpServer.create(InetSocketAddress(9856), 0)
        server.createContext("/oauth", OauthHandler(server, twitchApi, redirectUri))
        server.start()
        val scope = scopes.joinToString(separator = "+") { it.replace(":", "%3A") }
        BrowserUtil.open("https://id.twitch.tv/oauth2/authorize?response_type=$responseType&client_id=$clientId&redirect_uri=$redirectUri&scope=$scope")
    }

    class OauthHandler(private val server: HttpServer,
                       private val twitchApi: TwitchApi,
                       private val redirectUri: String) : HttpHandler {
        override fun handle(exchange: HttpExchange?) {
            val result = toMap(exchange!!)
            if (result["code"] != null) {
                twitchApi.connect(result.getValue("code"), redirectUri)
            }
            server.stop(10)
        }

        private fun toMap(exchange: HttpExchange): Map<String, String> {
            val map = HashMap<String, String>()
            exchange.requestURI.query.split("&").forEach {
                val data = it.split("=")
                map[data[0]] = data[1]
            }
            return map
        }
    }
}
