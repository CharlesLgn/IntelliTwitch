package com.charleslgn.intellitwitch.twitch.socket

import com.charleslgn.intellitwitch.twitch.action.ScheduleAction
import com.charleslgn.intellitwitch.twitch.action.TwitchSocketAction
import com.charleslgn.intellitwitch.twitch.command.Command
import com.charleslgn.intellitwitch.twitch.command.Commands
import com.charleslgn.intellitwitch.twitch.message.Message
import com.charleslgn.intellitwitch.twitch.socket.connection.ConnectionOption
import java.util.function.Consumer

/**
 * Represent a Twitch bos when it is connected.
 * Before it is connected, you can't join a channel or send a message.<br></br>
 * To create a connection you need to use [TwitchBot.connect].
 *
 * @param connectionOption      the credentials
 * @param scheduleActions       all the schedule action when the bot is on
 */
class ConnectedTwitchBot(
    connectionOption: ConnectionOption,
    messageActions: List<TwitchSocketAction>,
    scheduleActions: List<ScheduleAction>
) {
    private val twitchSocket = TwitchSocket(connectionOption, messageActions, this)

    init {
        twitchSocket.connectBlocking()
        scheduleActions.forEach(Consumer { scheduleAction: ScheduleAction -> scheduleAction.run(this) })
    }

    /**
     * Join a streamer channel.
     * @param streamer the channel to join
     * @return itself
     */
    fun join(streamer: String): ConnectedTwitchBot {
        sendRawSocketMessage("JOIN #$streamer")
        return this
    }

    /**
     * Leave a streamer channel
     * @param streamer the channel to quit
     * @return itself
     */
    fun leave(streamer: String): ConnectedTwitchBot {
        sendRawSocketMessage("PART #$streamer")
        return this
    }

    /**
     * send a message to a specific channel.
     * @param streamer the channel to send a message
     * @param message the message to send
     */
    fun send(streamer: String, message: String) {
        sendRawSocketMessage("PRIVMSG #$streamer :$message")
    }

    /**
     * send a message to a specific channel.
     * @param streamer the channel to send a message
     * @param message the message to send
     */
    fun sendAndWaitAnswer(streamer: String, message: String) = sendRawSocketMessageAndWait("PRIVMSG #$streamer :$message")

    /**
     * reply to a message.
     * @param messageToAnswer the targeted message to answer
     * @param message the message to send
     */
    fun answer(messageToAnswer: Message, message: String) = sendRawSocketMessage(answerMessage(messageToAnswer, message))

    /**
     * send a message to a specific channel.
     * @param streamer the channel to send a message
     * @param message the message to send
     */
    fun answerAndWaitAnswer(messageToAnswer: Message, message: String) = sendRawSocketMessageAndWait(answerMessage(messageToAnswer, message))

    private fun answerMessage(messageToAnswer: Message, message: String) = "@reply-parent-msg-id=${messageToAnswer.id} PRIVMSG #${messageToAnswer.streamerName} :$message"

    /**
     * This command sends a private message to another user on Twitch.
     * @param user the targeted user to whisper
     * @param message the message to whisper
     */
    fun whisper(user: String, message: String) {
        command("jtv", Commands.whisper(user, message))
    }

    /**
     * Mention a specific user on a specific channel.
     * @param user the user to mention
     * @param streamer the channel where to mention it
     * @param message the message
     */
    fun mention(user: String, streamer: String, message: String) {
        command(streamer, Commands.mention(user, message))
    }

    /**
     * send command to a specific channel.
     * You need to use [Command] repository.
     * @param streamer the channel where to send the command
     * @param command the command to send. it can be find by [Command]
     */
    fun command(streamer: String, command: Command) {
        send(streamer, command.command)
    }

    /**
     * delete a message.
     * @param message the targeted message to delete
     */
    fun delete(message: Message) {
        command(message.streamerName, Commands.delete(message))
    }

    /**
     * send a raw command to the socket. <br></br>
     * **Warning : Use this command only if a specific command does not exist in the wrapper.**
     * @param message the raw socket message
     */
    private fun sendRawSocketMessage(message: String?) {
        twitchSocket.send(message)
    }

    /**
     * send a raw command to the socket. <br></br>
     * **Warning : Use this command only if a specific command does not exist in the wrapper.**
     * @param message the raw socket message
     */
    private fun sendRawSocketMessageAndWait(message: String?) = twitchSocket.sendAndWait(message)
}
