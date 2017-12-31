package com.example.studystudymorestudyforever.until.chat

/**
 * Created by VANKHANHPR on 10/1/2017.
 */
class ChatData {

    private var CREATED_DATE: Long = 0
    private var NAME: String? = null
    private var RECENTLY_ACTIVITY: String? = null
    private var ID: Int = 0

    fun getID(): Int {
        return ID
    }

    fun setID(ID: Int) {
        this.ID = ID
    }


    fun getCREATED_DATE(): Long {
        return CREATED_DATE
    }

    fun setCREATED_DATE(CREATED_DATE: Long) {
        this.CREATED_DATE = CREATED_DATE
    }

    fun getNAME(): String ?{
        return NAME
    }

    fun setNAME(NAME: String) {
        this.NAME = NAME
    }

    fun getRECENTLY_ACTIVITY(): String ?{
        return RECENTLY_ACTIVITY
    }

    fun setRECENTLY_ACTIVITY(RECENTLY_ACTIVITY: String) {
        this.RECENTLY_ACTIVITY = RECENTLY_ACTIVITY
    }
}