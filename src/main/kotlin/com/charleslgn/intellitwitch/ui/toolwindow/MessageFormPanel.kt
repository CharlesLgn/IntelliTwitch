package com.charleslgn.intellitwitch.ui.toolwindow

import com.charleslgn.intellitwitch.twitch.message.Message
import com.charleslgn.intellitwitch.twitch.message.Message.Companion.findBadges
import com.charleslgn.intellitwitch.twitch.message.Message.Companion.findColor
import com.charleslgn.intellitwitch.twitch.message.Message.Companion.findMessageContent
import com.charleslgn.intellitwitch.twitch.message.Message.Companion.findMessageId
import com.charleslgn.intellitwitch.twitch.message.Message.Companion.findStreamerName
import com.charleslgn.intellitwitch.twitch.message.Message.Companion.findUserName
import com.charleslgn.intellitwitch.twitch.message.MessageType
import com.charleslgn.intellitwitch.twitch.socket.ConnectedTwitchBot
import com.intellij.openapi.ui.addKeyboardAction
import java.awt.BorderLayout
import java.awt.event.KeyEvent.VK_ENTER
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.KeyStroke

class MessageFormPanel(private val twitchBot: ConnectedTwitchBot, private val messages: MessagePanel) {

    val panel: JPanel
        get() {
            val text = JTextField()
            val button = JButton("chat")
            button.addActionListener { sendChat(text) }
            text.addKeyboardAction(listOf(KeyStroke.getKeyStroke(VK_ENTER.toChar()))) { sendChat(text) }
            val panel = JPanel(BorderLayout())
            panel.add(text, BorderLayout.CENTER)
            panel.add(button, BorderLayout.EAST)
            return panel
        }

    private fun sendChat(text: JTextField) {
        if (text.text.isNotEmpty()) {
            val state = twitchBot.sendAndWaitAwnser(ChatTwitchToolWindowContent.connectedStreamer, text.text)
            messages.print(
                Message(
                    streamerName = ChatTwitchToolWindowContent.connectedStreamer,
                    messageContent = text.text,
                    messageType = MessageType.NORMAL,
                    id = findMessageId(state),
                    userName = findUserName(state),
                    color = findColor(state),
                    chatBadges = findBadges(state)
                )
            )
            text.text = ""
        }
    }
}