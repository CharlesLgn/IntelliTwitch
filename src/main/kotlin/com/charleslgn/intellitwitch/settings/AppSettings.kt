package com.charleslgn.intellitwitch.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(
    name = "com.charleslgn.intellitwitch.settings.AppSettings",
    storages = [Storage("IntelliTwitch.xml")],
)
class AppSettings: PersistentStateComponent<AppSettings.State> {

    private var state = State()

    override fun getState(): State = state

    override fun loadState(state: State) {
        this.state = state
    }

    val refreshTwitchToken: String? get() = state.refreshTwitchToken
    val tmiOAuthToken: String? get() = state.tmiOAuthToken

    companion object {
        val instance: AppSettings
            get () = ApplicationManager.getApplication().getService(AppSettings::class.java)
    }

    class State {
        var refreshTwitchToken: String? = null
        var tmiOAuthToken: String? = null
    }
}
