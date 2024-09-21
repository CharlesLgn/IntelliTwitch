package com.charleslgn.intellitwitch.ui.toolwindow

import com.charleslgn.intellitwitch.twitch.api.BadgeData
import com.charleslgn.intellitwitch.twitch.api.BadgeVersion
import com.charleslgn.intellitwitch.twitch.api.TwitchApi
import com.charleslgn.intellitwitch.twitch.message.Message
import com.charleslgn.intellitwitch.ui.MultipleImageIcon
import com.charleslgn.intellitwitch.ui.icons.IntelliTwitchIcons
import com.charleslgn.intellitwitch.ui.labels
import com.intellij.openapi.ui.JBMenuItem
import com.intellij.openapi.ui.JBPopupMenu
import com.intellij.vcs.log.ui.frame.WrappedFlowLayout
import com.jetbrains.rd.util.AtomicInteger
import java.awt.Color
import java.awt.Font
import java.net.URI
import javax.swing.*


class MessagePanel(val chat: JComponent,
                   private var messageStackLimit: Int = 500,
                   private val twitchApi: TwitchApi = TwitchApi.instance) {

    lateinit var form: MessageFormPanel

    private val messageStack = AtomicInteger(0)

    private var streamerBadges: Collection<BadgeData> = ArrayList()

    fun print(message: Message) {
        controlChatLimits()
        val iconLabel = iconLabel(message)
        val userLabel = JLabel("${message.userName}: ")
        if (message.color.isNotEmpty()) {
            userLabel.foreground = Color.decode(message.color)
        }
        userLabel.font = Font(userLabel.font.name, Font.BOLD, userLabel.font.size)

        val messageContent = message.labels(twitchApi)
        val mes = JPanel(WrappedFlowLayout(5, 0))
        mes.add(iconLabel)
        mes.add(userLabel)
        messageContent.forEach(mes::add)
        mes.componentPopupMenu = popupMenu(message)
        chat.add(mes)
        chat.repaint()
    }

    private fun iconLabel(message: Message): JLabel {
        if (message.chatBadges.isNotEmpty()) {
            val badges = ArrayList<BadgeVersion>()
            badges.addAll(findBadges(message, twitchApi.globalBadge))
            badges.addAll(findBadges(message, streamerBadges))
            if (badges.isNotEmpty()) {
                return JLabel(MultipleImageIcon(
                    badges.map { ImageIcon(URI(it.imageUrl1x).toURL(), it.title) }
                ))
            }
        }
        return JLabel("")
    }

    private fun popupMenu(message: Message): JPopupMenu {
        val popup = JBPopupMenu()
        val answer = JBMenuItem("Answer", IntelliTwitchIcons.Answer)
        answer.addActionListener { form.answerTo = message }
        popup.add(answer)
        return popup
    }

    private fun controlChatLimits() {
        if (messageStack.get() < messageStackLimit) {
            messageStack.incrementAndGet()
        } else {
            chat.remove(0)
        }
    }

    private fun findBadges(message: Message, badges: Collection<BadgeData>) = message.chatBadges.mapNotNull { badge ->
        badges.firstOrNull {
            badge.split("/")[0] == it.setId
        }?.versions?.firstOrNull {
            badge.split("/")[1] == it.id
        }
    }

    fun initStreamerBadges(streamer: String) {
        streamerBadges = twitchApi.streamerBadge(streamer)
    }
}