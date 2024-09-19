package com.charleslgn.intellitwitch.twitch.action

import com.charleslgn.intellitwitch.twitch.message.Message
import com.charleslgn.intellitwitch.twitch.socket.ConnectedTwitchBot

/**
 * Represent what to do when you are trigger by the chat message. <br></br>
 * It is used when you build a [TwitchBot] with method [TwitchBot.withActionOnMessage]
 * <br></br>
 * You can for instance send `Hello world` on each message with : <br></br>
 * `
 * new TwitchBot(user, token).withActionOnMessage((bot, message) -> bot.send(message.getStreamerName(), "Hello World")
` *
 */
fun interface TwitchSocketAction {
    /**
     * execute something to do on the last message on the twitch chat.
     *
     * @param bot the bot when it is connected to the twitch socket
     * @param message the last message of the twitch chat
     */
    fun execute(bot: ConnectedTwitchBot, message: Message)
}
