package com.charleslgn.intellitwitch.twitch.command

/**
 * This command will display a list of VIPs for that specific channel.
 */
internal class ShowVipsCommand : Command {
    override val command: String
        get() = "/vips"
}

/**
 * This command will grant VIP status to a user.
 */
internal data class AddVipCommand(val user: String) : Command {
    override val command: String
        get() = "/vip $user"
}

/**
 * This command willl revoke VIP status from a user.
 */
internal data class RemoveVipCommand(val user: String) : Command {
    override val command: String
        get() = "/unvip $user"
}
