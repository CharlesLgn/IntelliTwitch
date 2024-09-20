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
        return mySettingsComponent?.status == AppSettingsComponent.TokenStatus.GENERATED
    }

    override fun apply() {
        // nothing there the generated token will be use even if you clik on cancel
    }

    override fun reset() {
        mySettingsComponent?.status = AppSettingsComponent.TokenStatus.UNMODIFIED
    }

    override fun disposeUIResources() {
        mySettingsComponent = null
    }
}