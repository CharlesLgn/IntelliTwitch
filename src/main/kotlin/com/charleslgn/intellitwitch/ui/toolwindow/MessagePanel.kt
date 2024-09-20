package com.charleslgn.intellitwitch.ui.toolwindow

import com.charleslgn.intellitwitch.twitch.api.BadgeData
import com.charleslgn.intellitwitch.twitch.api.BadgeVersion
import com.charleslgn.intellitwitch.twitch.api.TwitchApi
import com.charleslgn.intellitwitch.twitch.message.Message
import com.charleslgn.intellitwitch.ui.MultipleImageIcon
import com.charleslgn.intellitwitch.ui.icons.IntelliTwitchIcons
import com.intellij.openapi.ui.JBMenuItem
import com.intellij.openapi.ui.JBPopupMenu
import com.jetbrains.rd.util.AtomicInteger
import java.net.URI
import javax.swing.ImageIcon
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPopupMenu


class MessagePanel(val chat: JComponent,
                   private var messageStackLimit: Int = 500,
                   private val twitchApi: TwitchApi = TwitchApi.instance) {

    lateinit var form: MessageFormPanel

    private val messageStack = AtomicInteger(0)

    private var streamerBadges: Collection<BadgeData> = ArrayList()

    fun print(message: Message) {
        controlChatLimits()
        val userLabel = """<span style="color:${message.color}; font-weight:bold;">${message.userName}:&nbsp;</span>"""
        val messageContent = "<span>${message.messageContent}</span>"
        val mes = JLabel("<html>$userLabel$messageContent</html>")
        setIconLabel(mes, message)
        mes.componentPopupMenu = popupMenu(message)
        chat.add(mes)
        chat.repaint()
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

    private fun setIconLabel(label: JLabel, message: Message) {
        if (message.chatBadges.isNotEmpty()) {
            val badges = ArrayList<BadgeVersion>()
            badges.addAll(findBadges(message, twitchApi.globalBadge))
            badges.addAll(findBadges(message, streamerBadges))
            if (badges.isNotEmpty()) {
                label.icon = MultipleImageIcon(
                    badges.map { ImageIcon(URI(it.imageUrl1x).toURL(), it.title) }
                )
            }
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