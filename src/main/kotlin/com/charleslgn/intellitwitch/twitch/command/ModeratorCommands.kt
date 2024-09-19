package com.charleslgn.intellitwitch.twitch.command

/**
 * This command will display a list of all chat moderators for that specific channel.
 */
internal class ShowModeratorsCommand : Command {
    override val command: String
        get() = "/mods"
}

/**
 * This command will allow you to promote a viewer to moderator
 * status (giving moderator abilities).
 */
internal data class AddModeratorCommand(val user: String) : Command {
    override val command: String
        get() = "/mod $user"
}

/**
 * This command will allow you to demote an existing moderator back to viewer
 * status (removing their moderator abilities).
 */
internal data class RemoveModeratorCommand(val user: String) : Command {
    override val command: String
        get() = "/unmod $user"
}
