package com.charleslgn.intellitwitch.twitch.command

/**
 * Broadcasters and channel moderators appointed by the broadcaster
 * are equipped with a set of commands and features that will allow
 * them to closely monitor and moderate the chat.
 * These features and commands range from giving a user a quick
 * timeout to built in anti-spam.
 */
interface Command {
    /**
     * @return the command of the chat (like /ban or /info)
     */
    val command: String
}
