package com.example.studystudymorestudyforever.until.datalocal

import com.example.studystudymorestudyforever.fragment.scheduleteacher.addcourse.AddCourse
import com.example.studystudymorestudyforever.until.course.ScheduleAdd
import com.example.studystudymorestudyforever.until.course.TeacherSchedule
import com.example.studystudymorestudyforever.until.teacher.TeacherData
import com.example.studystudymorestudyforever.until.user.User

/**
 * Created by VANKHANHPR on 9/26/2017.
 */
object LocalData {

    //json emit
    var   usertype:Int= 2

    var email= ""
    var pass=""
    var user:User= User()
    //data
    var userlogin= 0
    //teacher
    var teacher:TeacherData=TeacherData()
    //course
    var course: ScheduleAdd = ScheduleAdd()

}