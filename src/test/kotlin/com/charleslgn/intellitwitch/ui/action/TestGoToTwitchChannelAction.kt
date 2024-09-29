package com.charleslgn.intellitwitch.ui.action

import com.charleslgn.intellitwitch.ui.toolwindow.ChatTwitchToolWindowContent
import com.intellij.openapi.actionSystem.ActionUpdateThread
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/** [GoToTwitchChannelAction] */
class TestGoToTwitchChannelAction {

    @Test
    fun test() {
        var result = ""
        val action = GoToTwitchChannelAction { url -> result = url }
        val e = newAnActionEvent()
        ChatTwitchToolWindowContent.connectedStreamer = ""
        assertEquals(ActionUpdateThread.EDT, action.actionUpdateThread)
        action.update(e)
        assertFalse { e.presentation.isEnabled }

        ChatTwitchToolWindowContent.connectedStreamer = "zerator"
        action.actionPerformed(e)
        assertEquals("https://www.twitch.tv/zerator", result)

        ChatTwitchToolWindowContent.connectedStreamer = "rex_woof"
        action.actionPerformed(e)
        assertEquals("https://www.twitch.tv/rex_woof", result)

        action.update(e)
        assertTrue { e.presentation.isEnabled }

    }
}