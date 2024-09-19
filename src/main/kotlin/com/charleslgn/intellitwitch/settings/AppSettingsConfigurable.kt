package com.charleslgn.intellitwitch.settings

import com.intellij.openapi.options.Configurable
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.Nullable
import java.util.Objects
import javax.swing.JComponent


/**
 * Provides controller functionality for application settings.
 */
class AppSettingsConfigurable: Configurable {

    private var mySettingsComponent: AppSettingsComponent? = null

    // A default constructor with no arguments is required because
    // this implementation is registered as an applicationConfigurable
    override fun getDisplayName(): @Nls(capitalization = Nls.Capitalization.Title) String? {
        return "IntelliTwitch Settings"
    }

    override fun getPreferredFocusedComponent(): JComponent {
        return mySettingsComponent!!.getPreferredFocusedComponent()
    }

    @Nullable
    override fun createComponent(): JComponent? {
        mySettingsComponent = AppSettingsComponent()
        return mySettingsComponent!!.getPanel()
    }

    override fun isModified(): Boolean {
        val state: AppSettings.State =
            Objects.requireNonNull(AppSettings.instance.getState())
        return mySettingsComponent?.tmiOauthTokenText !== state.tmiOAuthToken
    }

    override fun apply() {
        val state: AppSettings.State = AppSettings.instance.getState()
        state.tmiOAuthToken = mySettingsComponent?.tmiOauthTokenText
    }

    override fun reset() {
        val state: AppSettings.State = AppSettings.instance.getState()
        mySettingsComponent?.tmiOauthTokenText = state.tmiOAuthToken
    }

    override fun disposeUIResources() {
        mySettingsComponent = null
    }
}