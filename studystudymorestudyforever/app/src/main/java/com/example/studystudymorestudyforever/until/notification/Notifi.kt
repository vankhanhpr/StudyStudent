package com.example.studystudymorestudyforever.until.notification

/**
 * Created by VANKHANHPR on 10/1/2017.
 */
class Notifi {
    private var NO_ID: String = ""
    private var NO_TITLE: String? = null
    private var NO_MESSAGE: String? = null
    private var UNREAD: String = ""
    private var NOTIF_TYPE: String = ""

    private var USER_ID_SEND_NOTIFICATION: Int = -1

    fun getUSER_ID_SEND_NOTIFICATION(): Int {
        return USER_ID_SEND_NOTIFICATION
    }

    fun setUSER_ID_SEND_NOTIFICATION(USER_ID_SEND_NOTIFICATION: Int) {
        this.USER_ID_SEND_NOTIFICATION = USER_ID_SEND_NOTIFICATION
    }

    fun getNO_ID(): String {
        return NO_ID
    }

    fun setNO_ID(NO_ID: String) {
        this.NO_ID = NO_ID
    }

    fun getNO_TITLE(): String ?{
        return NO_TITLE
    }

    fun setNO_TITLE(NO_TITLE: String) {
        this.NO_TITLE = NO_TITLE
    }

    fun getNO_MESSAGE(): String ? {
        return NO_MESSAGE
    }

    fun setNO_MESSAGE(NO_MESSAGE: String) {
        this.NO_MESSAGE = NO_MESSAGE
    }

    fun getUNREAD(): String {
        return UNREAD
    }

    fun setUNREAD(NO_STATUS: String) {
        this.UNREAD = NO_STATUS
    }

    fun getNOTIF_TYPE(): String {
        return NOTIF_TYPE
    }

    fun setNOTIF_TYPE(NOTIF_TYPE: String) {
        this.NOTIF_TYPE = NOTIF_TYPE
    }
}