package com.charleslgn.intellitwitch.twitch.socket

import com.charleslgn.intellitwitch.twitch.action.TwitchSocketAction
import com.charleslgn.intellitwitch.twitch.message.Message
import com.charleslgn.intellitwitch.twitch.socket.connection.ConnectionOption
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Predicate

/**
 * Represent the twitch connection protocol.
 */
internal class TwitchSocket(
    private val connectionOption: ConnectionOption,
    private val messageActions: List<TwitchSocketAction>,
    private val bot: ConnectedTwitchBot
) : WebSocketClient(URI(connectionOption.connectionUrl)) {

    private var waitForAwnser: Boolean = false
    private var awnser: String = ""

    override fun onOpen(serverHandshake: ServerHandshake) {
        println("Websocket, Open ")
        send("PASS oauth:" + connectionOption.token)
        send("NICK " + connectionOption.user)
        send("CAP REQ :twitch.tv/membership")
        send("CAP REQ :twitch.tv/tags")
        send("CAP REQ :twitch.tv/commands")
    }

    override fun onMessage(message: String) {
        SocketMessage.process(this, message)
    }

    override fun onClose(i: Int, s: String, b: Boolean) {
        println("Websocket, Closed $s")
    }

    override fun onError(e: Exception) {
        println("Websocket, Error " + e.message)
    }

    private fun executeMessageActions(message: String) {
        println(message)
        messageActions.forEach(Consumer { twitchSocketAction: TwitchSocketAction ->
            twitchSocketAction.execute(bot, Message(message))
        })
    }

    fun sendAndWait(message: String?): String {
        send(message)
        do { Thread.sleep(10) } while (waitForAwnser)
        val awaitingAwnser = awnser
        awnser = ""
        return awaitingAwnser
    }

    private enum class SocketMessage(
        private val socketMessageType: Predicate<String>,
        private val actions: BiConsumer<TwitchSocket, String>
    ) {
        PING(
            Predicate { message -> message.startsWith("PING") },
            BiConsumer { socket, _ -> socket.send("PONG") }
        ),
        PRIVMSG(
            Predicate { message -> message.contains(" PRIVMSG ") },
            BiConsumer { obj, message -> obj.executeMessageActions(message) }
        ),
        USERSTATE (
            Predicate { message -> message.contains(" USERSTATE ") },
            BiConsumer { obj, message ->
                obj.awnser = message
                obj.waitForAwnser = false
            }
        ),
        WHISPER(
            Predicate { message -> message.contains(" WHISPER ") },
            BiConsumer { _, _ -> }
        ),
        ;

        companion object {
            fun process(socket: TwitchSocket, message: String) {
                SocketMessage.values()
                    .filter { socketMessage: SocketMessage -> socketMessage.socketMessageType.test(message) }
                    .forEach { socketMessage: SocketMessage -> socketMessage.actions.accept(socket, message) }
            }
        }
    }
}
