package com.charleslgn.intellitwitch.ui.toolwindow

import com.charleslgn.intellitwitch.twitch.message.Message
import com.charleslgn.intellitwitch.twitch.message.Message.Companion.findBadges
import com.charleslgn.intellitwitch.twitch.message.Message.Companion.findColor
import com.charleslgn.intellitwitch.twitch.message.Message.Companion.findMessageId
import com.charleslgn.intellitwitch.twitch.message.Message.Companion.findUserName
import com.charleslgn.intellitwitch.twitch.message.MessageType
import com.charleslgn.intellitwitch.twitch.socket.ConnectedTwitchBot
import com.intellij.icons.AllIcons
import com.intellij.openapi.ui.addKeyboardAction
import com.intellij.ui.components.JBLabel
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.KeyEvent.VK_ENTER
import javax.swing.*

class MessageFormPanel(private val twitchBot: ConnectedTwitchBot, private val messages: MessagePanel) {

    val panel: JPanel = JPanel(BorderLayout())
    private var answerPanel: JPanel? = null

    init {
        messages.form = this
        val text = JTextField()
        val button = JButton("chat")
        button.addActionListener { sendChat(text) }
        text.addKeyboardAction(listOf(KeyStroke.getKeyStroke(VK_ENTER.toChar()))) { sendChat(text) }
        panel.add(text, BorderLayout.CENTER)
        panel.add(button, BorderLayout.EAST)
    }

    var answerTo: Message? = null
        set(value) {
            createAnswerPanel(value)
            field = value
        }

    private fun createAnswerPanel(value: Message?) {
        if (value == null) {
            panel.remove(answerPanel)
            answerPanel = null
        } else {
            val p = JPanel(BorderLayout())
            val button = JButton(AllIcons.Actions.Cancel)
            button.preferredSize = Dimension(24, 24)
            button.addActionListener { answerTo = null }
            p.add(JBLabel("answer to @${value.userName}"), BorderLayout.WEST)
            p.add(button, BorderLayout.EAST)
            panel.add(p, BorderLayout.NORTH)
            answerPanel = p
        }
    }

    private fun sendChat(text: JTextField) {
        if (text.text.isNotEmpty()) {
            val state = if (answerTo != null) {
                twitchBot.answerAndWaitAnswer(answerTo!!, text.text)
            } else {
                twitchBot.sendAndWaitAnswer(ChatTwitchToolWindowContent.connectedStreamer, text.text)
            }
            messages.print(
                Message(
                    streamerName = ChatTwitchToolWindowContent.connectedStreamer,
                    messageContent = if (answerTo != null) "@${answerTo?.userName} ${text.text}" else text.text,
                    messageType = MessageType.NORMAL,
                    id = findMessageId(state),
                    userName = findUserName(state),
                    color = findColor(state),
                    chatBadges = findBadges(state)
                )
            )
            answerTo?.let { answerTo = null }
            text.text = ""
        }
    }
}