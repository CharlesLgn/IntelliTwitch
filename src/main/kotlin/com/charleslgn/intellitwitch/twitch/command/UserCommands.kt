package com.charleslgn.intellitwitch.twitch.command

import com.charleslgn.intellitwitch.twitch.command.type.ColorName

/**
 * This command will remove the colon that typically appears
 * after your chat name and italicize your message text.
 * Can be used to denote action in the third-person.
 */
internal data class MeCommand(val text: String) : Command {
    override val command: String
        get() = "/me $text"
}

/**
 * Allows you to change the color of your username.
 * Normal users can choose between Blue, Coral, DodgerBlue,
 * SpringGreen, YellowGreen, Green, OrangeRed, Red, GoldenRod,
 * HotPink, CadetBlue, SeaGreen, Chocolate, BlueViolet, and
 * Firebrick.
 * Twitch Turbo users can use any Hex value (i.e: #000000).
 */
internal class ColorCommand : Command {
    private val color: String

    constructor(color: ColorName) {
        this.color = color.toString()
    }

    constructor(colorAsHexa: String) {
        this.color = colorAsHexa
    }

    override val command: String
        get() = "/color $color"
}

/**
 * This command will allow you to target your message at a user,
 * or reply directly to a specific message they’ve posted in the chat.
 */
internal data class MentionCommand(val user: String, val message: String) : Command {
    override val command: String
        get() = "@$user $message"
}

/**
 * This command sends a private message to another user on Twitch.
 */
internal data class WhisperCommand(val user: String, val message: String) : Command {
    override val command: String
        get() = String.format("/w %s %s", user, message)
}

/**
 * This command will simply disconnect you from the chat server. To reconnect, simply refresh the page.
 */
internal class DisconnectCommand : Command {
    override val command: String
        get() = "/disconnect"
}

/**
 * Adds a stream marker (with an optional description, max 140 characters)
 * at the current timestamp.
 * You can use markers in the Highlighter for easier editing.
 */
internal data class MarkerCommand(val description: String) : Command {
    override val command: String
        get() {
            control()
            return "/marker $description"
        }

    @Throws(TooLongException::class)
    private fun control() {
        if (description.length > 140) {
            throw TooLongException("a description can only be 140 character max")
        }
    }

    private class TooLongException(s: String?) : Exception(s)
}
