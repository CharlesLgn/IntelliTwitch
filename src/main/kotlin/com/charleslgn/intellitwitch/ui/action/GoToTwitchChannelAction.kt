package com.charleslgn.intellitwitch.ui.action

import com.charleslgn.intellitwitch.ui.toolwindow.ChatTwitchToolWindowContent
import com.intellij.icons.AllIcons
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction


class GoToTwitchChannelAction(
    private val openInBrowser: (String) -> Unit = { url -> BrowserUtil.open(url) }
) : DumbAwareAction("Go To Twitch Channel", "Go to Twitch channel", AllIcons.General.Web) {

    override fun getActionUpdateThread() = ActionUpdateThread.EDT

    override fun actionPerformed(e: AnActionEvent) {
        openInBrowser("https://www.twitch.tv/${ChatTwitchToolWindowContent.connectedStreamer}")
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        e.presentation.isEnabled = ChatTwitchToolWindowContent.connectedStreamer.isNotEmpty()
    }
}
