package com.charleslgn.intellitwitch.ui.toolwindow

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory


class ChatTwitchToolWindowFactory : ToolWindowFactory, DumbAware {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val toolWindowContent = ChatTwitchToolWindowContent(project, toolWindow)
        val content = ContentFactory.getInstance().createContent(toolWindowContent, "", true)
        toolWindow.contentManager.addDataProvider(toolWindowContent)
        toolWindow.contentManager.addContent(content)
    }
}
