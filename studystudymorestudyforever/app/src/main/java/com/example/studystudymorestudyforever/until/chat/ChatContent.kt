package com.example.studystudymorestudyforever.until.chat

/**
 * Created by VANKHANHPR on 11/20/2017.
 */
class ChatContent {
    private var ID: Int = 0
    private var SENDERID: Int? = 0
    private var RECEIVERID: Int? = 0
    private var MESSAGE_TYPE: String? = null
    private var MESSAGE: String? = null

    fun getID(): Int {
        return ID
    }

    fun setID(ID: Int) {
        this.ID = ID
    }

    fun getSENDERID(): Int? {
        return SENDERID
    }

    fun setSENDERID(SENDERID: Int) {
        this.SENDERID = SENDERID
    }

    fun getRECEIVERID(): Int ?{
        return RECEIVERID
    }

    fun setRECEIVERID(RECEIVERID: Int) {
        this.RECEIVERID = RECEIVERID
    }

    fun getMESSAGE_TYPE(): String ? {
        return MESSAGE_TYPE
    }

    fun setMESSAGE_TYPE(MESSAGE_TYPE: String) {
        this.MESSAGE_TYPE = MESSAGE_TYPE
    }

    fun getMESSAGE(): String ? {
        return MESSAGE
    }

    fun setMESSAGE(MESSAGE: String) {
        this.MESSAGE = MESSAGE
    }
}