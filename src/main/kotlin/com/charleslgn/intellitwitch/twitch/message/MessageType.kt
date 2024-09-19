package com.charleslgn.intellitwitch.twitch.message

/**
 * Represent a type of message. <br></br>
 * for instance, [MessageType.SUB] represent a message writing by a user for his sub.
 */
enum class MessageType(private val type: String) {
    /** a message written when you sub for the first time  */
    SUB("sub"),

    /** a message written when you resub to a channel  */
    RESUB("resub"),

    /** a message written when you offer someone a sub  */
    SUB_GIFT("subgift"),

    /** a message written when you offer someone a sub (anonymous)  */
    A_NON_SUB_GIFT("anonsubgift"),

    /** a message written when you offer randomly a sub  */
    SUB_MYSTERY_GIFT("submysterygift"),

    /** a message written when you turn a gift sub into a real sub  */
    GIFT_PAID_UPGRADE("giftpaidupgrade"),

    /** message is a viewer is reward by the streamer  */
    REWARD_GIFT("rewardgift"),

    /** a message written when you turn a gift sub into a real sub (anonymous)  */
    A_NON_GIFT_PAID_UPGRADE("anongiftpaidupgrade"),

    /** a message written when you use your channel point to write a highlight message   */
    HIGHLIGHT("highlighted-message"),

    /** a message written when you use your channel point to write in a sub only channel  */
    SKIP_SUBS_MODE_MESSAGE("skip-subs-mode-message"),

    /** a message written when you raid someone   */
    RAID("raid"),

    /** a message written when you unraid someone   */
    UNRAID("unraid"),

    /** a ritual message  */
    RITUAL("ritual"),

    /** a message written when you paid some bits   */
    BITS_BADGE_TIER("bitsbadgetier"),

    /** a normal message  */
    NORMAL("");

    companion object {
        @JvmStatic
        fun of(type: String?): MessageType {
            return values().firstOrNull { typeMessage: MessageType -> typeMessage.type == type } ?: NORMAL
        }
    }
}
