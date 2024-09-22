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
    @JvmField val Sword: Icon = IconLoader.getIcon("toolwindows/sword.svg", IntelliTwitchIcons::class.java.classLoader)
    @JvmField val Ban: Icon = IconLoader.getIcon("toolwindows/ban.svg", IntelliTwitchIcons::class.java.classLoader)
    @JvmField val Emote: Icon = IconLoader.getIcon("toolwindows/emote.svg", IntelliTwitchIcons::class.java.classLoader)
    @JvmField val Sub: Icon = IconLoader.getIcon("toolwindows/sub.svg", IntelliTwitchIcons::class.java.classLoader)
    @JvmField val Timeout: Icon = IconLoader.getIcon("toolwindows/timeout.svg", IntelliTwitchIcons::class.java.classLoader)
    @JvmField val Vip: Icon = IconLoader.getIcon("toolwindows/vip.svg", IntelliTwitchIcons::class.java.classLoader)
}
