package com.charleslgn.intellitwitch.ui.action

import com.intellij.openapi.actionSystem.AnActionEvent
import org.mockito.Mockito
import java.awt.Component
import javax.swing.JPanel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/** [ClearChatAction] */
class TestClearChatAction {

    @Test
    fun test() {
        val pan = JPanelMock()
        pan.add(JPanel())
        assertEquals(1, pan.datas.size)
        assertFalse { pan.repainted }
        ClearChatAction(pan).actionPerformed(Mockito.mock(AnActionEvent::class.java))
        assertEquals(0, pan.datas.size)
        assertTrue { pan.repainted }
    }

    private class JPanelMock(val datas: MutableList<Component> = mutableListOf(),
        var repainted: Boolean = false): JPanel() {

        override fun add(comp: Component?): Component {
            datas.add(comp!!)
            return comp
        }

        override fun removeAll() {
            datas.clear()
        }

        override fun repaint() {
            repainted = true
        }
    }
}