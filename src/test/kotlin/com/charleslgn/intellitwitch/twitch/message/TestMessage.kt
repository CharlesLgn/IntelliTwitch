package com.charleslgn.intellitwitch.twitch.message

import com.charleslgn.intellitwitch.twitch.message.Message.EmoteDelimiter
import kotlin.test.*

class TestMessage {

    @Test
    fun test_as_sub_message() {
        // Given
        val value = "@badge-info=;badges=broadcaster/1,premium/1;client-nonce=1b2ad004e5399c018d17657259439e4e;color=#16A085;msg-id=highlighted-message;display-name=Nyphew_;emotes=;flags=;id=24db27fb-6160-4c79-8f82-f687dcde4456;mod=0;vip=1;room-id=57076120;subscriber=0;tmi-sent-ts=1627225532874;turbo=0;user-id=57076120;user-type= :nyphew_!nyphew_@nyphew_.tmi.twitch.tv PRIVMSG #nyphew_ :GG"
        // When
        val result = Message(value)
        // Then
        assertEquals("24db27fb-6160-4c79-8f82-f687dcde4456", result.id)
        assertEquals("nyphew_", result.streamerName)
        assertEquals("Nyphew_", result.userName)
        assertEquals("GG", result.messageContent)
        assertEquals(MessageType.HIGHLIGHT, result.messageType)
        assertTrue(result.vip)
    }

    @Test
    fun test_as_resub_message() {
        // Given
        val value = "@badge-info=subscriber/2;badges=subscriber/0;client-nonce=84f7b701459c15e444c825298fb6afb6;color=;display-name=aelter;emotes=;flags=;msg-id=resub;id=7eeea85f-10ae-49de-8674-c8eb5b50eaa2;mod=0;reply-parent-display-name=Matthelot;reply-parent-msg-body=Imagine,\\st'es\\slà,\\stu\\sfais\\sune\\slose\\sstreak,\\scertes\\stu\\sjoues\\smal\\smais\\sça\\sarrive\\sd'avoir\\sde\\smauvaises\\spériodes,\\set\\slà\\st'as\\sun\\sstreamer\\squi\\st'insulte\\set\\sveut\\ste\\sfaire\\sperma\\sdu\\sjeu\\s:/;reply-parent-msg-id=bb651ba1-7fbd-474e-8184-9e46d799e85e;reply-parent-user-id=128337168;reply-parent-user-login=matthelot;room-id=50795214;subscriber=1;tmi-sent-ts=1627224406163;turbo=0;user-id=480357104;user-type= :aelter!aelter@aelter.tmi.twitch.tv PRIVMSG #zerator :@Matthelot Tais-toi je t'en conjure\n"
        // When
        val result = Message(value)
        // Then
        assertEquals("7eeea85f-10ae-49de-8674-c8eb5b50eaa2", result.id)
        assertEquals("zerator", result.streamerName)
        assertEquals("aelter", result.userName)
        assertEquals("@Matthelot Tais-toi je t'en conjure\n", result.messageContent)
        assertEquals(MessageType.RESUB, result.messageType)
        assertFalse(result.moderator)
        assertFalse(result.vip)
    }

    @Test
    fun test_with_no_specific_typeMessage() {
        // Given
        val value = "@badge-info=subscriber/2;badges=subscriber/0;client-nonce=84f7b701459c15e444c825298fb6afb6;color=;display-name=aelter;emotes=;flags=;id=7eeea85f-10ae-49de-8674-c8eb5b50eaa2;mod=1;reply-parent-display-name=Matthelot;reply-parent-msg-body=Imagine,\\st'es\\slà,\\stu\\sfais\\sune\\slose\\sstreak,\\scertes\\stu\\sjoues\\smal\\smais\\sça\\sarrive\\sd'avoir\\sde\\smauvaises\\spériodes,\\set\\slà\\st'as\\sun\\sstreamer\\squi\\st'insulte\\set\\sveut\\ste\\sfaire\\sperma\\sdu\\sjeu\\s:/;reply-parent-msg-id=bb651ba1-7fbd-474e-8184-9e46d799e85e;reply-parent-user-id=128337168;reply-parent-user-login=matthelot;room-id=50795214;subscriber=1;tmi-sent-ts=1627224406163;turbo=0;user-id=480357104;user-type= :aelter!aelter@aelter.tmi.twitch.tv PRIVMSG #zerator :@Matthelot Tais-toi je t'en conjure\n"
        // When
        val result = Message(value)
        // Then
        assertEquals("7eeea85f-10ae-49de-8674-c8eb5b50eaa2", result.id)
        assertEquals("zerator", result.streamerName)
        assertEquals("aelter", result.userName)
        assertEquals("@Matthelot Tais-toi je t'en conjure\n", result.messageContent)
        assertEquals(MessageType.NORMAL, result.messageType)
        assertContentEquals(listOf("subscriber/0"), result.chatBadges)
        assertTrue(result.moderator)
    }

    @Test
    fun test_with_specific_user_name() {
        // Given
        val value = "@badge-info=;badges=broadcaster/1,premium/1;client-nonce=1b2ad004e5399c018d17657259439e4e;color=#16A085;msg-id=highlighted-message;display-name=estBot!!test;emotes=;flags=;id=24db27fb-6160-4c79-8f82-f687dcde4456;mod=0;room-id=57076120;subscriber=0;tmi-sent-ts=1627225532874;turbo=0;user-id=57076120;user-type= :estBot!!test!estBot!!test@estBot!!test.tmi.twitch.tv PRIVMSG #nyphew_ :GG"
        // When
        val result = Message(value)
        // Then
        assertEquals("24db27fb-6160-4c79-8f82-f687dcde4456", result.id)
        assertEquals("nyphew_", result.streamerName)
        assertEquals("estBot!!test", result.userName)
        assertEquals("GG", result.messageContent)
        assertEquals(MessageType.HIGHLIGHT, result.messageType)
        assertEquals("#16A085", result.color)
        assertContentEquals(listOf("broadcaster/1", "premium/1"), result.chatBadges)
    }

    @Test
    fun test_with_emote() {
        // Given
        val value = "@badge-info=subscriber/47;badges=subscriber/42,zevent-2024/1;client-nonce=44ec63aa0de6a325ff00dac093623971;color=#1E90FF;display-name=DavidTaroz;emotes=25:35-39;first-msg=0;flags=;id=4367a259-0ea0-42a0-9401-8d6064a718b9;mod=0;reply-parent-display-name=Ascaanor;reply-parent-msg-body=@maridececilefilsdesylvain\\sOn\\ss'est\\slevé\\sdu\\spied\\sgauche\\s?;reply-parent-msg-id=4bf9f9a3-003d-430f-b236-08019625c403;reply-parent-user-id=68177037;reply-parent-user-login=ascaanor;reply-thread-parent-display-name=maridececilefilsdesylvain;reply-thread-parent-msg-id=99502f87-38ed-46ca-a809-15dc7c3e2b24;reply-thread-parent-user-id=1096544092;reply-thread-parent-user-login=maridececilefilsdesylvain;returning-chatter=0;room-id=41719107;subscriber=1;tmi-sent-ts=1727624408513;turbo=0;user-id=36998043;user-type= :davidtaroz!davidtaroz@davidtaroz.tmi.twitch.tv PRIVMSG #zerator :@Ascaanor nn du pied droit du coup Kappa"
        // When
        val result = Message(value)
        // Then
        assertEquals(1, result.emotes.size)
        val emote = result.emotes.first()
        assertEquals("25", emote.id)
        assertContentEquals(listOf(EmoteDelimiter(35, 39)), emote.delimiters)
    }
}