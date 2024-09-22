package com.charleslgn.intellitwitch.ui.toolwindow

import com.charleslgn.intellitwitch.twitch.api.BadgeData
import com.charleslgn.intellitwitch.twitch.api.BadgeVersion
import com.charleslgn.intellitwitch.twitch.api.TwitchApi
import com.charleslgn.intellitwitch.twitch.command.Commands
import com.charleslgn.intellitwitch.twitch.command.type.TimeUnit
import com.charleslgn.intellitwitch.twitch.message.Message
import com.charleslgn.intellitwitch.twitch.socket.ConnectedTwitchBot
import com.charleslgn.intellitwitch.ui.MultipleImageIcon
import com.charleslgn.intellitwitch.ui.icons.IntelliTwitchIcons
import com.charleslgn.intellitwitch.ui.labels
import com.intellij.icons.AllIcons
import com.intellij.openapi.ui.JBMenuItem
import com.intellij.openapi.ui.JBPopupMenu
import com.intellij.ui.components.JBMenu
import com.intellij.vcs.log.ui.frame.WrappedFlowLayout
import com.jetbrains.rd.util.AtomicInteger
import java.awt.Color
import java.awt.Font
import java.awt.event.ActionListener
import java.net.URI
import javax.swing.*


class MessagePanel(
    val chat: JComponent,
    private var messageStackLimit: Int = 500,
    private val twitchApi: TwitchApi = TwitchApi.instance
) {

    lateinit var form: MessageFormPanel
    lateinit var twitchBot: ConnectedTwitchBot

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
        val mes = JPanel(WrappedFlowLayout(3, 0))
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
        moderationMenu(message)?.let { popup.add(it) }
        chatConfigurationMenu(message)?.let { popup.add(it) }
        return popup
    }

    private fun moderationMenu(message: Message): JMenu? {
        if (message.userName.lowercase() != message.streamerName.lowercase() && twitchApi.moderatedChannel(message.streamerName)) {
            val moderation = JBMenu()
            moderation.text = "Moderation"
            moderation.icon = IntelliTwitchIcons.Sword
            moderation.add(menuItem("Delete message", AllIcons.Actions.Cancel) {
                twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.delete(message))
            })
            moderation.add(menuItem("Ban user", IntelliTwitchIcons.Ban) {
                twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.ban(message.userName))
            })
            moderation.add(menuItem("Block user") {
                twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.block(message.userName))
            })
            moderation.add(menuItem("Timeout user", IntelliTwitchIcons.Timeout) {
                twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.timeout(message.userName, 60))
            })
            if (message.vip) {
                moderation.add(menuItem("Remove as vip", IntelliTwitchIcons.Vip) {
                    twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.removeVip(message.userName))
                })
            } else {
                moderation.add(menuItem("Add as vip", IntelliTwitchIcons.Vip) {
                    twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.addVip(message.userName))
                })
            }
            if (message.moderator) {
                moderation.add(menuItem("Add as moderator", IntelliTwitchIcons.Sword) {
                    twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.addModerator(message.userName))
                })
            } else {
                moderation.add(menuItem("Remove as moderator", IntelliTwitchIcons.Sword) {
                    twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.removeModerator(message.userName))
                })
            }
            return moderation
        }
        return null
    }

    private fun chatConfigurationMenu(message: Message): JMenu? {
        if (twitchApi.moderatedChannel(message.streamerName)) {
            val moderation = JBMenu()
            moderation.text = "Configure Chat"
            moderation.icon = AllIcons.General.Settings
            moderation.add(menuItem("Enable emote only", IntelliTwitchIcons.Emote) {
                twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.emoteOnly())
            })
            moderation.add(menuItem("Disable emote only", IntelliTwitchIcons.Emote) {
                twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.emoteOnlyOff())
            })
            moderation.add(menuItem("Enable follower only") {
                twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.followers(TimeUnit(30, TimeUnit.Unit.DAYS)))
            })
            moderation.add(menuItem("Disable follower only") {
                twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.followersOff())
            })
            moderation.add(menuItem("Enable sub only", IntelliTwitchIcons.Sub) {
                twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.subOnly())
            })
            moderation.add(menuItem("Disable sub only", IntelliTwitchIcons.Sub) {
                twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.subOnlyOff())
            })
            moderation.add(menuItem("Enable slow mode") {
                twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.slow(30))
            })
            moderation.add(menuItem("Disable slow mode") {
                twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.slowOff())
            })
            moderation.add(menuItem("Enable unique chat") {
                twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.uniqueChat())
            })
            moderation.add(menuItem("Disable unique chat") {
                twitchBot.command(ChatTwitchToolWindowContent.connectedStreamer, Commands.uniqueChatOff())
            })
            return moderation
        }
        return null
    }

    private fun menuItem(title: String, icon: Icon? = null, l: ActionListener): JMenuItem {
        val item = JBMenuItem(title)
        item.icon = icon
        item.addActionListener(l)
        return item
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