package com.charleslgn.intellitwitch.twitch.command

/**
 * This command will allow you to host another channel on yours (embedded video player).
 */
internal data class HostCommand(val channel: String) : Command {
    override val command: String
        get() = "/host $channel"
}

/**
 * Using this command will revert the embedding from hosting a channel and return it to its normal state.
 */
internal class UnhostCommand : Command {
    override val command: String
        get() = "/unhost"
}


/**
 * This command will send the viewer to another live channel.
 */
internal data class RaidCommand(val channel: String) : Command {
    override val command: String
        get() = "/raid $channel"
}

/**
 * This command will cancel the raid.
 */
internal class UnraidCommand : Command {
    override val command: String
        get() = "/unraid"
}