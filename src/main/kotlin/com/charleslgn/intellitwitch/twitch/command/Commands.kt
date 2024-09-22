package com.charleslgn.intellitwitch.twitch.command

import com.charleslgn.intellitwitch.twitch.command.type.ColorName
import com.charleslgn.intellitwitch.twitch.command.type.TimeUnit
import com.charleslgn.intellitwitch.twitch.command.type.When
import com.charleslgn.intellitwitch.twitch.message.Message

/**
 * Repository for all known twitch chat command
 */
object Commands {

    /**
     * This command will display a list of all chat moderators for that specific channel.
     * @return the command to show moderators
     */
    fun moderators(): Command = ShowModeratorsCommand()
    

    /**
     * This command will allow you to promote a user to a channel moderator
     * allowing them to have access to all the above commands and features.
     * @param user the user to grant privileges
     * @return the add to moderator command
     */
    fun addModerator(user: String): Command = AddModeratorCommand(user)

    /**
     * This command will allow you to demote an existing moderator back to viewer
     * status (removing their moderator abilities).
     * @param user the user to remove privileges
     * @return the remove to moderator command
     */
    fun removeModerator(user: String): Command = RemoveModeratorCommand(user)

    /**
     * This command will display a list of VIPs for that specific channel.
     * @return the command to show vips
     */
    fun vips(): Command = ShowVipsCommand()

    /**
     * @param user the user to grant privileges
     * @return the add to vip command
     */
    fun addVip(user: String): Command = AddVipCommand(user)

    /**
     * @param user the user to remove privileges
     * @return the remove to vip command
     */
    fun removeVip(user: String): Command = RemoveVipCommand(user)

    /**
     * Allows you to change the color of your username.
     * Normal users can choose between Blue, Coral, DodgerBlue,
     * SpringGreen, YellowGreen, Green, OrangeRed, Red, GoldenRod,
     * HotPink, CadetBlue, SeaGreen, Chocolate, BlueViolet, and
     * Firebrick.
     * @param colorName the new color of the bot in the chat
     * @return the command to change color
     */
    fun color(colorName: ColorName): Command = ColorCommand(colorName)

    /**
     * Allows you to change the color of your username.
     * Twitch Turbo users can use any Hex value (i.e: #000000).
     * @param hexa the new color of the bot in the chat as hexa (only for premium twitch)
     * @return the command to change color
     */
    fun color(hexa: String): Command = ColorCommand(hexa)

    /**
     * This command will allow you to block all messages from a specific
     * user in chat and whispers if you do not wish to see their comments
     * @param user the user to ban
     * @return the command to ban a user.
     */
    fun block(user: String): Command = BlockCommand(user)

    /**
     * This command will allow you to block all messages from a specific
     * user in chat and whispers if you do not wish to see their comments
     * @param user the user to unban
     * @return the command to unban a user.
     */
    fun unblock(user: String): Command = UnblockCommand(user)

    /**
     * This command will remove the colon that typically appears
     * after your chat name and italicize your message text.
     * Can be used to denote action in the third-person.
     * @param text the message to write on the chat
     * @return the "me" command
     */
    fun me(text: String): Command = MeCommand(text)

    /**
     * This command will simply disconnect you from the chat server.
     * To reconnect, simply refresh the page.
     * @return the command to disconnect the bot to a chat.
     */
    fun disconnect(): Command = DisconnectCommand()

    /**
     * This command sends a private message to another user on Twitch.
     * @param user the user to send the whisper
     * @param message the message to whisper
     * @return the whisper command
     */
    fun whisper(user: String, message: String): Command = WhisperCommand(user, message)

    /**
     * This command will allow you to target your message at a user,
     * or reply directly to a specific message they’ve posted in the chat.
     * @param user the user to mention
     * @param message the message to write to the mentioned user
     * @return the mention command
     */
    fun mention(user: String, message: String): Command = MentionCommand(user, message)

    /**
     * This command will delete the target message from the chat.
     * @param message the message to delete from the chat
     * @return the delete-message command
     */
    fun delete(message: Message): Command = delete(message.id)

    /**
     * This command will delete the target message from the chat.
     * @param messageId the message it from the message to delete from the chat
     * @return the delete-message command
     */
    fun delete(messageId: String): Command = DeleteCommand(messageId)

    /**
     * This command allows you to temporarily ban someone
     * from the chat room for 10 minutes by default.
     * This will be indicated to yourself and the temporarily
     * banned subject in chat on a successful temporary ban.
     * A new timeout command will overwrite an old one.
     *
     *
     * The command also supports banning for a specific set of
     * time via the optional [SECONDS] value.
     *
     *
     * To clear a timeout, either use the Unban command or overwrite the current timeout with a new, 1-second one.
     *
     * @param user the user to timeout
     * @param second the number of second of timeout
     * @return the timeout command
     */
    fun timeout(user: String, second: Int?): Command = TimeoutCommand(user, second)

    /**
     * This command will allow you to permanently ban
     * a user from the chat room.
     *
     * @param user the user to ban
     * @return the ban command
     */
    fun ban(user: String): Command = BanCommand(user)

    /**
     * This command allows you or your mods to restrict chat
     * to all or some of your followers, based on how long
     * they’ve followed — from 0 minutes (all followers) to 3 months.
     * @param timeUnit the time a follower need to follow a streamer to speak on the channel
     * @return the follower restrict chat command
     */
    fun followers(timeUnit: TimeUnit): Command = FollowersCommand(timeUnit)

    /**
     * This command will disable followers only mode
     * if it was previously enabled on the channel.
     * @return the follower restrict chat off command
     */
    fun followersOff(): Command = FollowersOffCommand()

    /**
     * This command will allow the Broadcaster
     * and chat moderators to completely wipe
     * the previous chat history.
     * @return the command to clear the chat
     */
    fun clear(): Command = ClearCommand()

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
     * @return the unique message in chat command
     */
    fun uniqueChat(): Command = UniqueChatCommand()

    /**
     * This command will disable Uniquechat mode if
     * it was previously enabled on the channel.
     * @return the unique message in chat off command
     */
    fun uniqueChatOff(): Command = UniqueChatOffCommand()

    /**
     * This command allows you to set your room so
     * only messages that are 100% emotes are allowed.
     * @return the emote-only in chat command
     */
    fun emoteOnly(): Command = EmoteOnlyCommand()

    /**
     * This command allows you to disable emote only mode
     * if you previously enabled it.
     * @return the emote-only in chat off command
     */
    fun emoteOnlyOff(): Command = EmoteOnlyOffCommand()

    /**
     * An Affiliate and Partner command that runs a commercial for all of your viewers.
     * @param when when the commercial advertising should be run (30|60|90|120|150|180)
     * @return the command to run a commercial advertising
     */
    fun commercial(`when`: When): Command = CommercialCommand(`when`)

    /**
     * This command will allow you to host another channel on yours (embedded video player).
     * @param channel the channel to host
     * @return the command to host someone
     */
    fun host(channel: String): Command = HostCommand(channel)

    /**
     * Using this command will revert the embedding from hosting a channel and return it to its normal state.
     * @return the command to unhost someone
     */
    fun unhost(): Command = UnhostCommand()

    /**
     * This command will send the viewer to another live channel.
     * @param channel the channel to raid
     * @return the command to raid someone
     */
    fun raid(channel: String): Command = RaidCommand(channel)

    /**
     * This command will cancel the raid.
     * @return the command to unraid someone
     */
    fun unraid(): Command = UnraidCommand()

    /**
     * Adds a stream marker (with an optional description, max 140 characters)
     * at the current timestamp.
     * You can use markers in the Highlighter for easier editing.
     * @param description the description of the marker
     * @return the command to add a stream marker
     */
    fun marker(description: String): Command = MarkerCommand(description)
}