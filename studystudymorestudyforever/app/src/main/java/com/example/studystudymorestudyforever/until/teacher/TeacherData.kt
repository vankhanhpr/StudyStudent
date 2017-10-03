package com.example.studystudymorestudyforever.until.teacher

/**
 * Created by VANKHANHPR on 10/1/2017.
 */
class TeacherData {
    private var teacherID = 0
    private var teachername = ""
    private var teacheraddress = ""
    private var teachercourse = ""

    fun getTeacherID(): Int {
        return teacherID
    }

    fun setTeacherID(teacherID: Int) {
        this.teacherID = teacherID
    }

    fun getTeachername(): String {
        return teachername
    }

    fun setTeachername(teachername: String) {
        this.teachername = teachername
    }

    fun getTeacheraddress(): String {
        return teacheraddress
    }

    fun setTeacheraddress(teacheraddress: String) {
        this.teacheraddress = teacheraddress
    }

    fun getTeachercourse(): String {
        return teachercourse
    }

    fun setTeachercourse(teachercourse: String) {
        this.teachercourse = teachercourse
    }
}