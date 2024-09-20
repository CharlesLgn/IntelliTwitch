package com.charleslgn.intellitwitch.ui.icons

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

/**
 * All [Icon] for the IntelliTwitch plugin
 */
@Suppress("unused")
object IntelliTwitchIcons {
    @JvmField val Twitch: Icon = IconLoader.getIcon("toolwindows/TwitchIcon.svg", IntelliTwitchIcons::class.java.classLoader)
    @JvmField val Answer: Icon = IconLoader.getIcon("toolwindows/answer.svg", IntelliTwitchIcons::class.java.classLoader)
}
