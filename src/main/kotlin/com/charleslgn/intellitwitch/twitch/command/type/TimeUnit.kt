package com.charleslgn.intellitwitch.twitch.command.type

/**
 * represent the time a user need to follow a streamer to write in a follower command
 * @see com.charleslgn.intellitwitch.twitch.command.FollowersCommand
 */
@JvmRecord
data class TimeUnit(val time: Int, val unit: Unit) {

    override fun toString(): String {
        unit.control(time)
        return time.toString() + unit.toString()
    }

    /**
     * Represent the unit time that a user need to follow a streamer to write in a follower command
     */
    enum class Unit(private val format: String, private val max: Int) {
        /** time that a user need to follow a streamer to write in a follower command in seconds  */
        SECONDS("s", 7862400),

        /** time that a user need to follow a streamer to write in a follower command in minutes  */
        MINUTES("m", 131040),

        /** time that a user need to follow a streamer to write in a follower command in hours  */
        HOURS("h", 2184),

        /** time that a user need to follow a streamer to write in a follower command in days  */
        DAYS("d", 91),

        /** time that a user need to follow a streamer to write in a follower command in week  */
        WEEK("w", 13),

        /** time that a user need to follow a streamer to write in a follower command in months  */
        MOUNTH("mo", 3);

        override fun toString(): String {
            return format
        }

        /**
         * control time validity
         * @param time the unit time
         * @throws OutOfTimeException if you try to insert to much (like 4 months)
         */
        @Throws(OutOfTimeException::class)
        fun control(time: Int) {
            if (time > max) {
                throw OutOfTimeException(this.name + " has max " + max)
            }
        }

        class OutOfTimeException(s: String?) : Exception(s)
    }
}
