package com.charleslgn.intellitwitch.twitch.command

import com.charleslgn.intellitwitch.twitch.command.type.ColorName
import com.charleslgn.intellitwitch.twitch.command.type.TimeUnit
import com.charleslgn.intellitwitch.twitch.command.type.When
import com.charleslgn.intellitwitch.twitch.message.Message
import com.charleslgn.intellitwitch.twitch.message.MessageType
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class TestCommands {

    @Test
    fun testModeratorCommands() {
        assertEquals("/mods", Commands.moderators().command)
        assertEquals("/mod wingo", Commands.addModerator("wingo").command)
        assertEquals("/mod Nyphew_", Commands.addModerator("Nyphew_").command)
        assertEquals("/unmod wingo", Commands.removeModerator("wingo").command)
        assertEquals("/unmod Nyphew_", Commands.removeModerator("Nyphew_").command)
    }

    @Test
    fun testVipCommands() {
        assertEquals("/vips", Commands.vips().command)
        assertEquals("/vip wingo", Commands.addVip("wingo").command)
        assertEquals("/vip Nyphew_", Commands.addVip("Nyphew_").command)
        assertEquals("/unvip wingo", Commands.removeVip("wingo").command)
        assertEquals("/unvip Nyphew_", Commands.removeVip("Nyphew_").command)
    }

    @Test
    fun testColorCommands() {
        assertEquals("/color #FF00A1", Commands.color("#FF00A1").command)
        assertEquals("/color Blue", Commands.color(ColorName.BLUE).command)
        assertEquals("/color Coral", Commands.color(ColorName.CORAL).command)
        assertEquals("/color DodgerBlue", Commands.color(ColorName.DODGER_BLUE).command)
        assertEquals("/color SpringGreen", Commands.color(ColorName.SPRING_GREEN).command)
        assertEquals("/color YellowGreen", Commands.color(ColorName.YELLOW_GREEN).command)
        assertEquals("/color Green", Commands.color(ColorName.GREEN).command)
        assertEquals("/color OrangeRed", Commands.color(ColorName.ORANGE_RED).command)
        assertEquals("/color Red", Commands.color(ColorName.RED).command)
        assertEquals("/color GoldenRod", Commands.color(ColorName.GOLDEN_ROD).command)
        assertEquals("/color HotPink", Commands.color(ColorName.HOT_PINK).command)
        assertEquals("/color CadetBlue", Commands.color(ColorName.CADET_BLUE).command)
        assertEquals("/color SeaGreen", Commands.color(ColorName.SEA_GREEN).command)
        assertEquals("/color Chocolate", Commands.color(ColorName.CHOCOLATE).command)
        assertEquals("/color BlueViolet", Commands.color(ColorName.BLUE_VIOLET).command)
        assertEquals("/color Firebrick", Commands.color(ColorName.FIREBRICK).command)
    }

    @Test
    fun testModerationCommands() {
        assertEquals("/block wingo", Commands.block("wingo").command)
        assertEquals("/block Nyphew_", Commands.block("Nyphew_").command)
        assertEquals("/unblock wingo", Commands.unblock("wingo").command)
        assertEquals("/unblock Nyphew_", Commands.unblock("Nyphew_").command)
        assertEquals("/ban wingo", Commands.ban("wingo").command)
        assertEquals("/ban Nyphew_", Commands.ban("Nyphew_").command)
        assertEquals("/unban wingo", Commands.unban("wingo").command)
        assertEquals("/unban Nyphew_", Commands.unban("Nyphew_").command)
        assertEquals("/timeout Nyphew_ ", Commands.timeout("Nyphew_", null).command)
        assertEquals("/timeout wingo 30", Commands.timeout("wingo", 30).command)
        assertEquals("/delete 1234", Commands.delete("1234").command)
        assertEquals("/delete 12345", Commands.delete(Message(id="12345",
            moderator = false,
            vip = true,
            color = "",
            emotes = listOf(),
            userName = "Nyphew_",
            streamerName = "wingo",
            chatBadges = listOf(),
            messageType = MessageType.NORMAL,
            messageContent = "test"
        )).command)
        assertEquals("/commercial 30", Commands.commercial(When.HALF_MINUTE).command)
        assertEquals("/commercial 60", Commands.commercial(When.ONE_MINUTE).command)
        assertEquals("/commercial 90", Commands.commercial(When.ONE_MINUTE_AND_A_HALF).command)
        assertEquals("/commercial 120", Commands.commercial(When.TWO_MINUTE).command)
        assertEquals("/commercial 150", Commands.commercial(When.TWO_MINUTE_AND_A_HALF).command)
        assertEquals("/commercial 180", Commands.commercial(When.THREE_MINUTE).command)
    }

    @Test
    fun testOtherChannerCommands() {
        assertEquals("/host wingo", Commands.host("wingo").command)
        assertEquals("/unhost", Commands.unhost().command)
        assertEquals("/raid Nyphew_", Commands.raid("Nyphew_").command)
        assertEquals("/unraid", Commands.unraid().command)
    }

    @Test
    fun testUserCommands() {
        assertEquals("/disconnect", Commands.disconnect().command)
        assertEquals("/me test", Commands.me("test").command)
        assertEquals("@Nyphew_ test", Commands.mention("Nyphew_", "test").command)
        assertEquals("/w Nyphew_ test", Commands.whisper("Nyphew_", "test").command)
        assertEquals("/marker test", Commands.marker("test").command)
        assertThrows<MarkerCommand.TooLongException> {Commands.marker("this is a very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very long marker").command}
    }

    @Test
    fun testChatConfigCommands() {
        assertEquals("/slow 10", Commands.slow(10).command)
        assertEquals("/slow 11", Commands.slow(11).command)
        assertEquals("/slowoff", Commands.slowOff().command)
        assertEquals("/emoteonly", Commands.emoteOnly().command)
        assertEquals("/emoteonlyoff", Commands.emoteOnlyOff().command)
        assertEquals("/followers 30s", Commands.followers(TimeUnit(30, TimeUnit.Unit.SECONDS)).command)
        assertEquals("/followers 60m", Commands.followers(TimeUnit(60, TimeUnit.Unit.MINUTES)).command)
        assertEquals("/followers 12h", Commands.followers(TimeUnit(12, TimeUnit.Unit.HOURS)).command)
        assertEquals("/followers 30d", Commands.followers(TimeUnit(30, TimeUnit.Unit.DAYS)).command)
        assertEquals("/followers 4w", Commands.followers(TimeUnit(4, TimeUnit.Unit.WEEK)).command)
        assertEquals("/followers 1mo", Commands.followers(TimeUnit(1, TimeUnit.Unit.MOUNTH)).command)
        assertThrows<TimeUnit.Unit.OutOfTimeException> {Commands.followers(TimeUnit(4, TimeUnit.Unit.MOUNTH)).command}
        assertEquals("/followersoff", Commands.followersOff().command)
        assertEquals("/subscribers", Commands.subOnly().command)
        assertEquals("/subscribersoff", Commands.subOnlyOff().command)
        assertEquals("/uniquechat", Commands.uniqueChat().command)
        assertEquals("/uniquechatoff", Commands.uniqueChatOff().command)
        assertEquals("/clear", Commands.clear().command)

    }
}