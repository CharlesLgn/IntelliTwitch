package com.charleslgn.intellitwitch.ui

import com.charleslgn.intellitwitch.twitch.api.TwitchApi
import com.charleslgn.intellitwitch.twitch.message.Message
import com.charleslgn.intellitwitch.twitch.message.Message.EmoteMessage
import java.net.URI
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

private fun Message.addEachWordAsALabel(begin: Int,
                                        end: Int,
                                        completeList: MutableList<DataDelimiter>
) {
    val messagesPart = messageContent.substring(begin, end)
    for (word in messagesPart.split(" ")) {
        completeList.add(DataDelimiter(begin, end, word))
    }
}

private data class DataDelimiter(val begin:Int, val end:Int, val data:Any) {
    fun jlabel(emoteMap:Map<String, String>): JLabel {
        if (data is String) {
            return JLabel(data)
        } else if (data is EmoteMessage) {
            return JLabel(ImageIcon(emoteMap[data.id]?.let { URI(it).toURL() }, data.id))
        }
        throw IllegalArgumentException()
    }
}