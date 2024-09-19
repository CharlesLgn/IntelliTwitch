package com.charleslgn.intellitwitch.ui.action

import com.charleslgn.intellitwitch.ui.toolwindow.ChatTwitchToolWindowContent
import com.charleslgn.intellitwitch.ui.toolwindow.ChatTwitchToolWindowContent.Companion.twitchChatAction
import com.intellij.openapi.actionSystem.*

class TwitchChatAction(toolWindow: ChatTwitchToolWindowContent) {

    private val actionManager: ActionManager = ActionManager.getInstance()

    private val goToTwitchChannelAction = GoToTwitchChannelAction()
    private val connectAction = ConnectAction(toolWindow)
    private val clearChatAction = ClearChatAction(toolWindow.chat)

    val actionGroup: ActionGroup
        get() {
            val group = DefaultActionGroup()
            twitchChatAction.actions.forEach(group::add)
            return group
        }

    private val actions: Collection<AnAction>
        get() = mutableListOf(
            twitchChatAction.connectAction,
            twitchChatAction.clearChatAction,
            Separator(),
            twitchChatAction.goToTwitchChannelAction,
        )
}
