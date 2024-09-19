package com.charleslgn.intellitwitch.ui

import java.awt.Component
import java.awt.Graphics
import javax.swing.Icon
import javax.swing.ImageIcon

/**
 * Take multiple [ImageIcon] and put it as a unique one
 */
class MultipleImageIcon(private val icons: List<ImageIcon>): Icon {

    override fun paintIcon(c: Component?, g: Graphics?, x: Int, y: Int) {
        var iconX = x
        icons.forEach {
            it.paintIcon(c, g, iconX, y)
            iconX += 5 + it.iconWidth
        }
    }

    override fun getIconWidth() = icons.sumOf { 5 + it.iconWidth }

    override fun getIconHeight() = icons.maxOfOrNull { it.iconHeight } ?:0
}