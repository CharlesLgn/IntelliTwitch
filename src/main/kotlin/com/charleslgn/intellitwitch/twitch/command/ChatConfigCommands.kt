package com.charleslgn.intellitwitch.twitch.command

import com.charleslgn.intellitwitch.twitch.command.type.TimeUnit


/**
 * This command allows you to set a limit on how often
 * users in the chat room are allowed to send messages (rate limiting).
 */
internal data class SlowCommand(val seconds: Int) : Command {
    override val command: String
        get() = "/slow $seconds"
}

/**
 * This command allows you to disable subscribers
 * only chat room if you previously enabled it.
 */
internal class SlowOffCommand : Command {
    override val command: String
        get() = "/slowoff"
}

/**
 * This command allows you to set your room so
 * only messages that are 100% emotes are allowed.
 */
internal class EmoteOnlyCommand : Command {
    override val command: String
        get() = "/emoteonly"
}

/**
 * This command allows you to disable emote only mode
 * if you previously enabled it.
 */
internal class EmoteOnlyOffCommand : Command {
    override val command: String
        get() = "/emoteonlyoff"
}

/**
 * This command allows you or your mods to restrict chat
 * to all or some of your followers, based on how long
 * they’ve followed — from 0 minutes (all followers) to 3 months.
 */
data class FollowersCommand(val timeUnit: TimeUnit) : Command {
    override val command: String
        get() = "/followers $timeUnit"
}

internal class FollowersOffCommand : Command {
    override val command: String
        get() = "/followersoff"
}

/**
 * This command allows you to set your room so only
 * users subscribed to you can talk in the chat room.
 * If you don't have the subscription feature it will
 * only allow the Broadcaster and the channel moderators
 * to talk in the chat room.
 */
internal class SubscribersCommand : Command {
    override val command: String
        get() = "/subscribers"
}

internal class SubscribersOffCommand : Command {
    override val command: String
        get() = "/subscribersoff"
}

/**
 * This command disallows users from posting non-unique
 * messages to the channel.
 * It will check for a minimum of 9 characters that are
 * not symbol unicode characters and then purges any repetitive
 * chat lines beyond that.
 *
 * Uniquechat is a unique way of moderating, which essentially
 * allowing you to stop generic copy-pasted messages intended as
 * spam among over generally annoying content.
 */
internal class UniqueChatCommand : Command {
    override val command: String
        get() = "/uniquechat"
}

/**
 * This command will disable Uniquechat mode if
 * it was previously enabled on the channel.
 */
internal class UniqueChatOffCommand : Command {
    override val command: String
        get() = "/uniquechatoff"
}

/**
 * This command will allow the Broadcaster
 * and chat moderators to completely wipe
 * the previous chat history.
 */
internal class ClearCommand : Command {
    override val command: String
        get() = "/clear"
}