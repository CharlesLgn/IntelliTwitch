package com.charleslgn.intellitwitch.settings

import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel


/**
 * Supports creating and managing a [JPanel] for the Settings Dialog.
 */
class AppSettingsComponent {

    private var myMainPanel: JPanel? = null
    private val tmiOauthTokenField = JBTextField()
    private val connectToTwitchButton = JButton("Connect/Reconnect to Twitch")

    init {
        connectToTwitchButton.addActionListener { TwitchOAuth2Action().connect() }
        myMainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent("TMI OAuth token:", tmiOauthTokenField, 1, false)
            .addComponent(connectToTwitchButton, 1)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    fun getPanel(): JPanel? {
        return myMainPanel
    }

    fun getPreferredFocusedComponent(): JComponent {
        return tmiOauthTokenField
    }

    var tmiOauthTokenText: String?
        get() = tmiOauthTokenField.text
        set(value) {
            this.tmiOauthTokenField.text = value
        }
}