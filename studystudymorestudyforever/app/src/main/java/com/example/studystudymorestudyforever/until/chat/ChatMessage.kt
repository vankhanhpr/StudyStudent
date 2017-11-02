package com.example.studystudymorestudyforever.until.chat

/**
 * Created by VANKHANHPR on 10/5/2017.
 */
class ChatMessage(content: String, isMine: Boolean) {

    private var content: String
    private var isMine: Boolean

    init {
        this.content = content
        this.isMine = isMine
    }

    fun getContent(): String {
        return content
    }

    fun isMine(): Boolean {
        return isMine
    }
}