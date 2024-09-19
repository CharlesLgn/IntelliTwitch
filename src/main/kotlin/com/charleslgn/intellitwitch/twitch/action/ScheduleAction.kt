package com.charleslgn.intellitwitch.twitch.action

import com.charleslgn.intellitwitch.twitch.socket.ConnectedTwitchBot
import java.util.*

/**
 * Represent what to on a specific time. <br></br>
 * It is used when you build a [TwitchBot] with method [TwitchBot.withScheduleAction].
 * <br></br>
 * You can for instance send `Hello world` all 5 minutes with : <br></br>
 * `
 * new TwitchBot(user, token).withScheduleAction(300, bot -> bot.send("streamerName", "Hello World")
` *
 */
@JvmRecord
data class ScheduleAction(val periodInSecond: Int, val action: Action) {
    /**
     * run a schedule action.
     * @param bot the bot when it is connected to the twitch socket
     */
    fun run(bot: ConnectedTwitchBot?) {
        val tasknew: TimerTask = object : TimerTask() {
            override fun run() {
                action.execute(bot)
            }
        }
        val timer = Timer()
        val milliseconds = periodInSecond * 1000L
        timer.schedule(tasknew, milliseconds, milliseconds)
    }

    /**
     * the action to be scheduled.
     */
    fun interface Action {
        /**
         * execute the action to be scheduled.
         * @param bot the bot when it is connected to the twitch socket
         */
        fun execute(bot: ConnectedTwitchBot?)
    }
}
