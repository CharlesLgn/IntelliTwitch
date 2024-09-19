package com.charleslgn.intellitwitch.ui.action

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import javax.swing.JPanel

class ClearChatAction(val chat: JPanel) : DumbAwareAction("Clear Chat", "Clear chat", AllIcons.Actions.GC) {

    override fun actionPerformed(e: AnActionEvent) {
        chat.removeAll()
        chat.repaint()
    }
}