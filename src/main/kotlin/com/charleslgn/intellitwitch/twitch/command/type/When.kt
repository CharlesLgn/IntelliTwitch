package com.charleslgn.intellitwitch.twitch.command.type

/**
 * Represent when you need to throw a commercial advertising.
 * it can be used in a [com.charleslgn.intellitwitch.twitch.action.ScheduleAction] to say that every hour, you throw a commercial.
 */
enum class When(private val seconds: Int) {
    /** throw a commercial advertising in 30 seconds  */
    HALF_MINUTE(30),

    /** throw a commercial advertising in 1 minute  */
    ONE_MINUTE(60),

    /** throw a commercial advertising in 1 minute and 30 seconds  */
    ONE_MINUTE_AND_A_HALF(90),

    /** throw a commercial advertising in 2 minutes  */
    TWO_MINUTE(120),

    /** throw a commercial advertising in 2 minutes and 30 seconds  */
    TWO_MINUTE_AND_A_HALF(150),

    /** throw a commercial advertising in 3 minutes  */
    THREE_MINUTE(180),
    ;

    override fun toString(): String {
        return seconds.toString()
    }
}
