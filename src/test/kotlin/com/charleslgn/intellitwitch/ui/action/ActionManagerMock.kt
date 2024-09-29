package com.charleslgn.intellitwitch.ui.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.actionSystem.impl.ActionManagerImpl
import com.intellij.testFramework.MapDataContext
import java.awt.event.KeyEvent
import javax.swing.JPanel

fun newAnActionEvent(): AnActionEvent = AnActionEvent(
    KeyEvent(JPanel(), 1, 1, 1, 1, 'a', 1),
    MapDataContext(),
    "",
    Presentation(),
    ActionManagerMock(),
    1
)

class ActionManagerMock: ActionManagerImpl()