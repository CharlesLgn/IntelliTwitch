package com.charleslgn.intellitwitch.twitch.socket

import com.charleslgn.intellitwitch.twitch.action.ScheduleAction
import com.charleslgn.intellitwitch.twitch.action.TwitchSocketAction
import com.charleslgn.intellitwitch.twitch.socket.connection.ConnectionOption
import java.net.URISyntaxException

/**
 * Represent a twitch bot before it is connected.
 */
class TwitchBot(userAccount: String?, token: String?) {
    private val option = ConnectionOption(userAccount, token)
    private val messageActions: MutableList<TwitchSocketAction> = ArrayList()
    private val scheduleActions: MutableList<ScheduleAction> = ArrayList()

    /**
     * trigger the socket to connect to the SSL connection of twitch
     * @return the [TwitchBot] builder with a secure connection.
     */
    fun secureConnection(): TwitchBot {
        option.secureConnection()
        return this
    }

    /**
     * add an action triggered by a message
     * @param twitchSocketAction action triggered by a message
     * @return the [TwitchBot] builder
     */
    fun withActionOnMessage(twitchSocketAction: TwitchSocketAction): TwitchBot {
        messageActions.add(twitchSocketAction)
        return this
    }

    /**
     * add an action
     * @param periodInSecond time when you trigger an action
     * @param action the schedule action
     * @return the [TwitchBot] builder
     */
    fun withScheduleAction(periodInSecond: Int, action: ScheduleAction.Action): TwitchBot {
        scheduleActions.add(ScheduleAction(periodInSecond, action))
        return this
    }

    /**
     * connect the bot to the twitch socket.
     * @return the [ConnectedTwitchBot] with all de configured action.
     * @throws InterruptedException if the socket connection stop
     * @throws URISyntaxException   if the socket change address
     */
    @Throws(InterruptedException::class, URISyntaxException::class)
    fun connect(): ConnectedTwitchBot {
        return ConnectedTwitchBot(option, messageActions, scheduleActions)
    }
}
