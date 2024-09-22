package com.charleslgn.intellitwitch.settings

import com.intellij.openapi.options.Configurable
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.Nullable
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

    override fun getPreferredFocusedComponent(): JComponent = mySettingsComponent!!.getPreferredFocusedComponent()

    @Nullable
    override fun createComponent(): JComponent? {
        mySettingsComponent = AppSettingsComponent()
        return mySettingsComponent!!.getPanel()
    }

    override fun isModified(): Boolean {
        val state: AppSettings.State = AppSettings.instance.getState()
        return mySettingsComponent?.status == AppSettingsComponent.TokenStatus.GENERATED
                || mySettingsComponent?.chatMessagesLimits != state.chatMessagesLimits
    }

    override fun apply() {
        val state: AppSettings.State = AppSettings.instance.getState()
        state.chatMessagesLimits = mySettingsComponent?.chatMessagesLimits!!
    }

    override fun reset() {
        val state: AppSettings.State = AppSettings.instance.getState()
        mySettingsComponent?.status = AppSettingsComponent.TokenStatus.UNMODIFIED
        mySettingsComponent?.chatMessagesLimits = state.chatMessagesLimits
    }

    override fun disposeUIResources() {
        mySettingsComponent = null
    }
}