package com.example.studystudymorestudyforever.until.course

/**
 * Created by VANKHANHPR on 10/8/2017.
 */
class TeacherSchedule {

    fun TeacherSchedule(SCHE_ID: Int, USER_ID: Int, START_TIME: Long, END_TIME: Long, FEE: Int, LOCATION: String, SUB_ID: Long, SUB_NAME: String) {
        this.SCHE_ID = SCHE_ID
        this.START_TIME = START_TIME
        this.END_TIME = END_TIME
        this.FEE = FEE
        this.LOCATION = LOCATION
        this.SUB_ID = SUB_ID
        this.SUB_NAME = SUB_NAME
    }
    private var SCHE_ID: Int = 0
    private var TEACHER_ID: Int = 0
    private var START_TIME: Long = 0
    private var END_TIME: Long = 0
    private var FEE: Int = 0
    private var LOCATION: String? = ""
    private var SUB_NAME: String? = null
    private var SUB_ID: Long = 0

    fun getSCHE_ID(): Int {
        return SCHE_ID
    }
    fun setSCHE_ID(SCHE_ID: Int) {
        this.SCHE_ID = SCHE_ID
    }
    fun getUSER_ID(): Int {
        return TEACHER_ID
    }
    fun setUSER_ID(USER_ID: Int) {
        this.TEACHER_ID = USER_ID
    }

    fun getSTART_TIME(): Long {
        return START_TIME
    }
    fun setSTART_TIME(START_TIME: Long) {
        this.START_TIME = START_TIME
    }
    fun getEND_TIME(): Long {
        return END_TIME
    }
    fun setEND_TIME(END_TIME: Long) {
        this.END_TIME = END_TIME
    }
    fun getFEE(): Int {
        return FEE
    }
    fun setFEE(FEE: Int) {
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
    fun getSUB_ID(): Long {
        return SUB_ID
    }
    fun  setSUB_ID( SUB_ID:Long) {
        this.SUB_ID = SUB_ID
    }

}