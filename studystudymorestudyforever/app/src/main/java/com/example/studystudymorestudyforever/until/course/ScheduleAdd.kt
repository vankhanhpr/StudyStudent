package com.example.studystudymorestudyforever.until.course

/**
 * Created by VANKHANHPR on 10/31/2017.
 */
class ScheduleAdd {

    private var SCHE_ID: String = ""
    private var TEACHER_ID: String = ""
    private var START_TIME: String = ""
    private var END_TIME: String = ""
    private var FEE: String = ""
    private var LOCATION: String = ""
    private var SUB_NAME: String = ""
    private var SUB_ID: String = ""

    fun getSCHE_ID(): String {
        return SCHE_ID
    }
    fun setSCHE_ID(SCHE_ID: String) {
        this.SCHE_ID = SCHE_ID
    }
    fun getUSER_ID(): String {
        return TEACHER_ID
    }
    fun setUSER_ID(USER_ID: String) {
        this.TEACHER_ID = USER_ID
    }

    fun getSTART_TIME(): String {
        return START_TIME
    }
    fun setSTART_TIME(START_TIME: String) {
        this.START_TIME = START_TIME
    }
    fun getEND_TIME(): String {
        return END_TIME
    }
    fun setEND_TIME(END_TIME: String) {
        this.END_TIME = END_TIME
    }
    fun getFEE(): String {
        return FEE
    }
    fun setFEE(FEE: String) {
        this.FEE = FEE
    }
    fun getLOCATION(): String ?{
        return LOCATION
    }
    fun setLOCATION(LOCATION: String) {
        this.LOCATION = LOCATION
    }
    fun getSUB_NAME(): String ? {
        return SUB_NAME
    }
    fun setSUB_NAME(SUB_NAME: String) {
        this.SUB_NAME = SUB_NAME
    }
    fun getSUB_ID(): String {
        return SUB_ID
    }
    fun  setSUB_ID( SUB_ID:String) {
        this.SUB_ID = SUB_ID
    }
}