package com.charleslgn.intellitwitch.settings

import com.charleslgn.intellitwitch.ui.icons.IntelliTwitchIcons
import com.intellij.ui.JBColor
import com.intellij.util.ui.FormBuilder
import java.awt.Color
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel


/**
 * Supports creating and managing a [JPanel] for the Settings Dialog.
 */
class AppSettingsComponent {

    var status:TokenStatus = TokenStatus.UNMODIFIED

    private var myMainPanel: JPanel? = null
    private val connectToTwitchButton = JButton("Connect/Reconnect to Twitch", IntelliTwitchIcons.Twitch)

    init {
        connectToTwitchButton.addActionListener {
            TwitchOAuth2Action().connect()
            status = TokenStatus.GENERATED
        }
        myMainPanel = FormBuilder.createFormBuilder()
            .addComponent(connectToTwitchButton, 1)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    fun getPanel(): JPanel? {
        return myMainPanel
    }

    fun getPreferredFocusedComponent(): JComponent {
        return connectToTwitchButton
    }

    enum class TokenStatus {
        UNMODIFIED, GENERATED
    }
}