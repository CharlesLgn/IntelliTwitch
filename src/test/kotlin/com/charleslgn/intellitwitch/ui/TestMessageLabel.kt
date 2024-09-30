package com.charleslgn.intellitwitch.ui

import com.charleslgn.intellitwitch.twitch.api.TwitchApiMock
import com.charleslgn.intellitwitch.twitch.message.Message
import org.assertj.core.api.Assertions
import java.awt.Font
import javax.swing.JLabel
import kotlin.test.Test

class TestMessageLabel{

    @Test
    fun test() {
        // Given
        val message = Message("@badge-info=subscriber/47;badges=subscriber/42,zevent-2024/1;client-nonce=44ec63aa0de6a325ff00dac093623971;color=#1E90FF;display-name=DavidTaroz;emotes=25:35-39;first-msg=0;flags=;id=4367a259-0ea0-42a0-9401-8d6064a718b9;mod=0;reply-parent-display-name=Ascaanor;reply-parent-msg-body=@maridececilefilsdesylvain\\sOn\\ss'est\\slevé\\sdu\\spied\\sgauche\\s?;reply-parent-msg-id=4bf9f9a3-003d-430f-b236-08019625c403;reply-parent-user-id=68177037;reply-parent-user-login=ascaanor;reply-thread-parent-display-name=maridececilefilsdesylvain;reply-thread-parent-msg-id=99502f87-38ed-46ca-a809-15dc7c3e2b24;reply-thread-parent-user-id=1096544092;reply-thread-parent-user-login=maridececilefilsdesylvain;returning-chatter=0;room-id=41719107;subscriber=1;tmi-sent-ts=1727624408513;turbo=0;user-id=36998043;user-type= :davidtaroz!davidtaroz@davidtaroz.tmi.twitch.tv PRIVMSG #zerator :@Ascaanor nn du pied droit du coup Kappa")
        // When
        val result = message.labels(TwitchApiMock())
        // Then
        Assertions.assertThat(result).hasSize(8)
        assertLabel(result, 0, "@Ascaanor")
        Assertions.assertThat(result[0].font.style).isEqualTo(Font.BOLD)
        assertLabel(result, 1, "nn")
        assertLabel(result, 2, "du")
        assertLabel(result, 3, "pied")
        assertLabel(result, 4, "droit")
        assertLabel(result, 5, "du")
        assertLabel(result, 6, "coup")
        Assertions.assertThat(result[7].icon).isNotNull()
    }

    private fun assertLabel(labels: List<JLabel>, i: Int, value: String) {
        Assertions.assertThat(labels[i].text).isEqualTo(value)
    }
}