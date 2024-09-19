package com.charleslgn.intellitwitch.ui.toolwindow

import com.charleslgn.intellitwitch.settings.AppSettings
import com.charleslgn.intellitwitch.ui.action.TwitchChatAction
import com.charleslgn.intellitwitch.settings.TwitchOAuth2Action
import com.charleslgn.intellitwitch.twitch.api.TwitchApi
import com.charleslgn.intellitwitch.twitch.message.Message
import com.charleslgn.intellitwitch.twitch.socket.ConnectedTwitchBot
import com.charleslgn.intellitwitch.twitch.socket.TwitchBot
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DataProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.ui.VerticalFlowLayout
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.components.JBScrollPane
import java.awt.BorderLayout
import javax.swing.Box
import javax.swing.JPanel
import javax.swing.ScrollPaneConstants

class ChatTwitchToolWindowContent(val project: Project,
                                  private val toolWindow: ToolWindow,
                                  twitchApi: TwitchApi = TwitchApi.instance) : SimpleToolWindowPanel(false, true),
    DataProvider {
    val chat: JPanel = JPanel(VerticalFlowLayout())
    private val twitchBot: ConnectedTwitchBot
    private val messages = MessagePanel(chat)

    init {
        if (AppSettings.instance.refreshTwitchToken != null) {
            twitchApi.refreshToken(AppSettings.instance.refreshTwitchToken!!)
        } else {
            TwitchOAuth2Action().connect()
        }
        user = twitchApi.broadcasterData.login
        twitchChatAction = TwitchChatAction(this)
        val scroll = JBScrollPane(chat, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)
        twitchBot = TwitchBot(user, AppSettings.instance.tmiOAuthToken)
            .secureConnection()
            .withActionOnMessage { _: ConnectedTwitchBot, message: Message ->
                messages.print(message)
                val bar = scroll.verticalScrollBar
                bar.value = bar.maximum + 15
            }
            .connect()
        val form = MessageFormPanel(twitchBot, messages)
        val c = JPanel(BorderLayout())
        c.add(scroll, BorderLayout.CENTER)
        c.add(form.panel, BorderLayout.SOUTH)
        connect(user)
        setContent(c)
        createToolBar()
    }

    private fun createToolBar() {
        val toolBar = ActionManager.getInstance().createActionToolbar(TOOL_WINDOW_ID, twitchChatAction.actionGroup, true)
        toolBar.targetComponent = this
        val toolBarBox = Box.createVerticalBox()
        toolBarBox.add(toolBar.component)
        toolBar.component.isVisible = true
        isVertical = true
        super.setToolbar(toolBarBox)
    }

    fun connect(streamer: String) {
        twitchBot.leave(connectedStreamer)
        connectedStreamer = streamer
        chat.removeAll()
        twitchBot.join(connectedStreamer)
        messages.initStreamerBadges(connectedStreamer)
        toolWindow.stripeTitle = "Chat Twitch - $connectedStreamer"
    }

    companion object {
        const val TOOL_WINDOW_ID = "TwitchChat"
        lateinit var twitchChatAction: TwitchChatAction
        lateinit var user: String
        var connectedStreamer: String = ""
    }
}