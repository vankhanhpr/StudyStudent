package com.example.studystudymorestudyforever.until.teacher

/**
 * Created by VANKHANHPR on 11/13/2017.
 */
class TeacherofStudent {

    private var ID: Int = 0
    private var NAME: String? = ""
    private var EMAIL: String? = ""
    private var PASSWORD: String? = ""
    private var PHONENUMBER: Int = 0
    private var ADDRESS: String? = ""
    private var BIRTHDAY: Long = 0
    private var HASHCODE: String? = ""
    private var ACTIVE: String? = ""
    private var USER_TYPE: Int = 0
    private var IMAGEPATH: String? = ""

    private var STATUS: Int = -5

    fun getSTATUS(): Int {
        return STATUS
    }

    fun setSTATUS(STATUS: Int) {
        this.STATUS = STATUS
    }


    private var SUB_NAME: String? = null

    fun getSUB_NAME(): String? {
        return SUB_NAME
    }

    fun setSUB_NAME(SUB_NAME: String) {
        this.SUB_NAME = SUB_NAME
    }

    fun getID(): Int {
        return ID
    }

    fun setID(ID: Int) {
        this.ID = ID
    }

    fun getNAME(): String? {
        return NAME
    }

    fun setNAME(NAME: String) {
        this.NAME = NAME
    }

    fun getEMAIL(): String ? {
        return EMAIL
    }

    fun setEMAIL(EMAIL: String) {
        this.EMAIL = EMAIL
    }

    fun getPASSWORD(): String? {
        return PASSWORD
    }

    fun setPASSWORD(PASSWORD: String) {
        this.PASSWORD = PASSWORD
    }

    fun getPHONENUMBER(): Int {
        return PHONENUMBER
    }

    fun setPHONENUMBER(PHONENUMBER: Int) {
        this.PHONENUMBER = PHONENUMBER
    }

    fun getADDRESS(): String ?{
        return ADDRESS
    }

    fun setADDRESS(ADDRESS: String) {
        this.ADDRESS = ADDRESS
    }

    fun getBIRTHDAY(): Long {
        return BIRTHDAY
    }

    fun setBIRTHDAY(BIRTHDAY: Long) {
        this.BIRTHDAY = BIRTHDAY
    }

    fun getHASHCODE(): String ? {
        return HASHCODE
    }

    fun setHASHCODE(HASHCODE: String) {
        this.HASHCODE = HASHCODE
    }

    fun getACTIVE(): String ? {
        return ACTIVE
    }

    fun setACTIVE(ACTIVE: String) {
        this.ACTIVE = ACTIVE
    }

    fun getUSER_TYPE(): Int {
        return USER_TYPE
    }

    fun setUSER_TYPE(USER_TYPE: Int) {
        this.USER_TYPE = USER_TYPE
    }

    fun getIMAGEPATH(): String ? {
        return IMAGEPATH
    }

    fun setIMAGEPATH(IMAGEPATH: String) {
        this.IMAGEPATH = IMAGEPATH
    }
}