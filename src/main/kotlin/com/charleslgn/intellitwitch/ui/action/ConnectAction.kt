package com.charleslgn.intellitwitch.ui.action

import com.charleslgn.intellitwitch.ui.dialog.ConnectToChannelDialog
import com.charleslgn.intellitwitch.ui.toolwindow.ChatTwitchToolWindowContent
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction


class ConnectAction(private val toolWindow: ChatTwitchToolWindowContent) : DumbAwareAction("Connect To Stream", "Connect to a streamer channel", AllIcons.General.OpenInToolWindow) {

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }

    override fun actionPerformed(event: AnActionEvent) {
        val dialog = ConnectToChannelDialog()
        dialog.show()
        if (dialog.streamToGo.isNotEmpty()) toolWindow.connect(dialog.streamToGo)
    }

    override fun update(e: AnActionEvent) {
        // Set the availability based on whether a project is open
        val project = e.project
        e.presentation.isEnabledAndVisible = project != null
    }


}