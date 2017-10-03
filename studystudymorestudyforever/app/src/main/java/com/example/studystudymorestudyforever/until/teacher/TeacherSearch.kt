package com.example.studystudymorestudyforever.until.teacher

/**
 * Created by VANKHANHPR on 10/1/2017.
 */
class TeacherSearch {

    private var email = ""
    private var teachername = ""
    private var course = ""

    fun getEmail(): String {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getTeachername(): String {
        return teachername
    }

    fun setTeachername(teachername: String) {
        this.teachername = teachername
    }

    fun getCourse(): String {
        return course
    }

    fun setCourse(course: String) {
        this.course = course
    }
}