package com.example.studystudymorestudyforever.until.notification

/**
 * Created by VANKHANHPR on 10/1/2017.
 */
class Notifi {
    private var notifID = 0
    private var notiftitle = ""
    private var notifstatus = ""

    fun Notification(notifID: Int, notiftitle: String, notifstatus: String) {
        this.notifID = notifID
        this.notiftitle = notiftitle
        this.notifstatus = notifstatus
    }

    fun getNotifID(): Int {
        return notifID
    }

    fun setNotifID(notifID: Int) {
        this.notifID = notifID
    }

    fun getNotiftitle(): String {
        return notiftitle
    }

    fun setNotiftitle(notiftitle: String) {
        this.notiftitle = notiftitle
    }

    fun getNotifstatus(): String {
        return notifstatus
    }

    fun setNotifstatus(notifstatus: String) {
        this.notifstatus = notifstatus
    }
}