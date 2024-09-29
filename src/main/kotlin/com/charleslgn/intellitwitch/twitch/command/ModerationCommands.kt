package com.charleslgn.intellitwitch.twitch.command

import com.charleslgn.intellitwitch.twitch.command.type.When

/**
 * This command will allow you to block all messages from a specific
 * user in chat and whispers if you do not wish to see their comments
 */
internal data class BlockCommand(val user: String) : Command {
    override val command: String
        get() = "/block $user"
}

/**
 * This command will allow you to block all messages from a specific
 * user in chat and whispers if you do not wish to see their comments
 */
internal data class UnblockCommand(val user: String) : Command {
    override val command: String
        get() = "/unblock $user"
}

/**
 * This command will allow you to permanently
 * ban a user from the chat room.
 */
internal data class BanCommand(val user: String) : Command {
    override val command: String
        get() = "/ban $user"
}

/**
 * This command will allow you to lift a permanent ban
 * on a user from the chat room.
 * You can also use this command to end a ban early;
 * this also applies to timeouts.
 */
internal data class UnbanCommand(val user: String) : Command {
    override val command: String
        get() = "/unban $user"
}

/**
 * This command allows you to temporarily ban someone
 * from the chat room for 10 minutes by default.
 * This will be indicated to yourself and the temporarily
 * banned subject in chat on a successful temporary ban.
 * A new timeout command will overwrite an old one.
 *
 * The command also supports banning for a specific set of
 * time via the optional [second] value.
 *
 * To clear a timeout, either use the Unban command or overwrite the current timeout with a new, 1-second one.
 */
internal data class TimeoutCommand(val user: String, val second: Int?) : Command {

    override val command: String
        get() = "/timeout $user ${second ?: ""}"
}

/**
 * This command will delete the target message from the chat.
 */
internal data class DeleteCommand(val messageId: String) : Command {
    override val command: String
        get() = "/delete $messageId"
}

/**
 * An Affiliate and Partner command that runs a commercial for all of your viewers.
 */
internal data class CommercialCommand(val `when`: When) : Command {
    override val command: String
        get() = "/commercial $`when`"
}