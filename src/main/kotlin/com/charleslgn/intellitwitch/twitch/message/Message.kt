package com.charleslgn.intellitwitch.twitch.message

import java.util.stream.Stream

private const val TMI_TWITCH_TV_PRIVMSG = ".tmi.twitch.tv PRIVMSG #"

/**
 * Represent a message sent by a user on twitch
 * to create an instance of it, you need to pass the twitch socket message version of it.
 */
class Message(
    val id: String,
    val userName: String,
    val streamerName: String,
    val messageContent: String,
    val messageType: MessageType,
    val color: String,
    val chatBadges: List<String>
) {

    /**
     * @param socketMessage the message sent by twitch by socket.
     */
    constructor (socketMessage: String) : this(
        id              = findMessageId(socketMessage),
        userName        = findUserName(socketMessage),
        streamerName    = findStreamerName(socketMessage),
        messageContent  = findMessageContent(socketMessage),
        messageType     = findTypeMessage(socketMessage),
        color           = findColor(socketMessage),
        chatBadges      = findBadges(socketMessage)
    )

    companion object {
        fun findTypeMessage(socketMessage: String): MessageType {
            return MessageType.of(Stream.of(*socketMessage.split(";".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray())
                .filter { m: String -> m.startsWith("msg-id=") }
                .findFirst()
                .map { m: String -> m.substring(7) }
                .orElse(null))
        }

        fun findMessageId(socketMessage: String): String {
            return Stream.of(*socketMessage.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                .filter { m: String -> m.startsWith("id=") }
                .findFirst()
                .map { m: String -> m.substring(3) }
                .orElse("")
        }

        fun findColor(socketMessage: String) = socketMessage.split(";".toRegex())
            .first { m: String -> m.startsWith("color=") }
            .replace("color=", "")

        fun findMessageContent(message: String): String {
            val userPart = message.substring(message.indexOf(TMI_TWITCH_TV_PRIVMSG) + TMI_TWITCH_TV_PRIVMSG.length)
            return userPart.substring(userPart.indexOf(":") + 1)
        }

        fun findStreamerName(message: String) =
            message.substring(message.indexOf(TMI_TWITCH_TV_PRIVMSG) + TMI_TWITCH_TV_PRIVMSG.length)
                .split(" ")
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()[0]

        fun findUserName(socketMessage: String): String = socketMessage.split(";".toRegex())
            .first { m: String -> m.startsWith("display-name=") }
            .replace("display-name=", "")

        fun findBadges(socketMessage: String): List<String> = socketMessage.split(";")
            .first { m: String -> m.startsWith("badges=") }
            .replace("badges=", "")
            .split(",")
    }

    override fun toString(): String {
        return "$id | $streamerName | $userName | $messageContent"
    }
}
