package com.charleslgn.intellitwitch.ui.dialog

import com.charleslgn.intellitwitch.twitch.api.StreamerData
import com.charleslgn.intellitwitch.twitch.api.TwitchApi
import com.intellij.icons.AllIcons
import com.intellij.openapi.ui.Messages.InputDialog
import com.intellij.openapi.ui.VerticalFlowLayout
import com.intellij.openapi.ui.addKeyboardAction
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.panels.HorizontalLayout
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Image
import java.awt.event.KeyEvent.VK_ENTER
import java.net.URI
import javax.swing.*


class ConnectToChannelDialog : InputDialog(
    "Connect to a streamer channel",
    "Streamer Channel",
    AllIcons.General.OpenInToolWindow,
    null,
    null
) {

    var streamToGo: String = ""

    override fun init() {
        super.init()
        myField = JTextField()
    }

    override fun createMessagePanel(): JPanel {
        val stream = JPanel(VerticalFlowLayout())
        TwitchApi.instance.myStreamers.map { data -> streamerPanel(data) }.forEach { stream.add(it) }
        val scroll = JBScrollPane(
            stream,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        )
        val c = JPanel(BorderLayout())
        c.add(scroll, BorderLayout.CENTER)
        c.add(customStream(), BorderLayout.SOUTH)
        return c
    }

    private fun streamerPanel(data: StreamerData): JComponent {
        val imageUrl = URI(TwitchApi.instance.broadcasterData(data.userName).onScaleProfileImageUrl).toURL()
        val image = ImageIcon(imageUrl).image.getScaledInstance(32, 32, Image.SCALE_DEFAULT)
        val imageIcon = ImageIcon(image)
        val button = JButton(data.userName)
        button.addActionListener { connect(data.userName) }
        val viewerNumber = JPanel(HorizontalLayout(5))
        val liveLabel = JBLabel(" ● ")
        liveLabel.foreground = JBColor.RED
        viewerNumber.add(liveLabel)
        viewerNumber.add(JBLabel(data.viewerCount.toString()))
        viewerNumber.size = Dimension(200, viewerNumber.height)
        val panel = JPanel(BorderLayout())
        panel.add(JBLabel(imageIcon), BorderLayout.WEST)
        panel.add(button, BorderLayout.CENTER)
        panel.add(viewerNumber, BorderLayout.EAST)
        return panel
    }

    private fun customStream(): JComponent {
        val text = JTextField()
        val button = JButton("connect")
        button.addActionListener { connect(text.text) }
        text.addKeyboardAction(listOf(KeyStroke.getKeyStroke(VK_ENTER.toChar()))) { connect(text.text) }
        val panel = JPanel(BorderLayout())
        panel.add(text, BorderLayout.CENTER)
        panel.add(button, BorderLayout.EAST)
        return panel
    }

    private fun connect(streamToGo: String) {
        this.streamToGo = streamToGo
        close(0)
    }

    override fun createActions(): Array<Action> {
        return arrayOf()
    }

    override fun createCenterPanel(): JComponent? = null
}