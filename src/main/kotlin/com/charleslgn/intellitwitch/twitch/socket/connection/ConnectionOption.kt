package com.charleslgn.intellitwitch.twitch.socket.connection

/**
 * Describe options to create a socket connection through the twitch IRC socket.
 */
class ConnectionOption(botAccount: String?, token: String?) {

    private var securityType: SecurityType = SecurityType.STANDARD_PROTOCOL
    private val credential: TwitchCredential = TwitchCredential(botAccount, token)

    /**
     * trigger the socket to connect to the SSL connection of twitch
     */
    fun secureConnection() {
        securityType = SecurityType.SECURE_PROTOCOL
    }

    val connectionUrl: String
        /** @return the formatted url connection */
        get() = String.format(URL, securityType.extention, securityType.port)

    val user: String?
        /** @return userName credential */
        get() = credential.user

    val token: String?
        /** @return the token to connection to the twitch IRC socket. */
        get() = credential.token

    /** The credential ton connect to the twitch IRC socket  */
    @JvmRecord
    private data class TwitchCredential(val user: String?, val token: String?)

    private enum class SecurityType(val extention: String, val port: Int) {
        STANDARD_PROTOCOL("ws", 80),
        SECURE_PROTOCOL("wss", 443)
    }

    companion object {
        private const val URL: String = "%s://irc-ws.chat.twitch.tv:%d/irc"
    }
}
