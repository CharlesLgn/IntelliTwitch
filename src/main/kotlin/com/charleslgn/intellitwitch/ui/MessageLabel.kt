package com.charleslgn.intellitwitch.ui

import com.charleslgn.intellitwitch.twitch.api.TwitchApi
import com.charleslgn.intellitwitch.twitch.message.Message
import com.charleslgn.intellitwitch.twitch.message.Message.EmoteMessage
import com.intellij.ide.BrowserUtil
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBLabel
import java.awt.Font
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.font.TextAttribute
import java.net.URI
import java.util.HashMap
import javax.swing.ImageIcon
import javax.swing.JLabel

fun Message.labels(twitchApi: TwitchApi): Collection<JLabel> {
    val emoteMap = twitchApi.emotes(emotes.map { it.id })
    val list: MutableList<DataDelimiter> = ArrayList()
    emotes.forEach { emote -> emote.delimiters.forEach { d -> list.add(DataDelimiter(d.begin, d.end, emote)) } }
    val completeList: MutableList<DataDelimiter> = ArrayList()
    var i = 0
    for (data in list.sortedBy { it.begin }) {
        if (data.begin > i) {
            addEachWordAsALabel(i, data.begin - 1, completeList)
        }
        completeList.add(data)
        i = data.end + 1
    }
    if (i < messageContent.length - 1) {
        addEachWordAsALabel(i, messageContent.length, completeList)
    }
    return completeList.map { it.jlabel(emoteMap) }
}

private fun Message.addEachWordAsALabel(
    begin: Int,
    end: Int,
    completeList: MutableList<DataDelimiter>
) {
    val messagesPart = messageContent.substring(begin, end)
    for (word in messagesPart.split(" ")) {
        completeList.add(DataDelimiter(begin, end, word))
    }
}

private data class DataDelimiter(val begin: Int, val end: Int, val data: Any) {
    fun jlabel(emoteMap: Map<String, String>): JLabel {
        if (data is String) {
            return data.jlabel()
        } else if (data is EmoteMessage) {
            JBLabel(ImageIcon(emoteMap[data.id]?.let { URI(it).toURL() }, data.id))
        }
        throw IllegalArgumentException()
    }

    private fun String.jlabel(): JLabel {
        val label = JBLabel(this)
        if (isUserAt()) {
            label.font = Font(label.font.name, Font.BOLD, label.font.size)
            label.background = JBColor.MAGENTA
        } else if (isHtmlLink()) {
            label.font = Font(label.font.name, Font.ITALIC, label.font.size)
            val attributes: MutableMap<TextAttribute, Any> = HashMap(label.font.attributes)
            attributes[TextAttribute.UNDERLINE] = TextAttribute.UNDERLINE_ON
            label.font = label.font.deriveFont(attributes)
            label.foreground = JBColor.BLUE
            label.addMouseListener(LinkClickListener(this))
        }
        return label
    }

    private class LinkClickListener(val link: String) : MouseAdapter() {
        override fun mousePressed(e: MouseEvent?) {
            val fullLink = link.replace("https://", "")
                .replace("http://", "")
                .replace("www.", "")
            BrowserUtil.open("https://www.$fullLink")
        }
    }

    private fun String.isUserAt(): Boolean = "@.*".toRegex().matches(trim())

    private fun String.isHtmlLink(): Boolean =
        "(?:https?://.)?(?:www\\.)?[-a-zA-Z0-9@%._+~#=]{2,256}\\.[a-z]{2,6}\\b[-a-zA-Z0-9@:%_+.~#?&/=]*".toRegex()
            .matches(trim())
}