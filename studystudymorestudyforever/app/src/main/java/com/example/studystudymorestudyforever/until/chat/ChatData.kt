package com.example.studystudymorestudyforever.until.chat

/**
 * Created by VANKHANHPR on 10/1/2017.
 */
class ChatData {

    private var chatID = 0
    private var chatstatus = ""

    fun ChatData(chatstatus: String) {
        this.chatstatus = chatstatus
    }

    fun getChatID(): Int {
        return chatID
    }

    fun setChatID(chatID: Int) {
        this.chatID = chatID
    }

    fun getChatstatus(): String {
        return chatstatus
    }

    fun setChatstatus(chatstatus: String) {
        this.chatstatus = chatstatus
    }
}