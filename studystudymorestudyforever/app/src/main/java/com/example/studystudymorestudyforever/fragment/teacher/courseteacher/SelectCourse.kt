package com.example.studystudymorestudyforever.fragment.teacher.courseteacher

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.teacher.DetailCourseStudentAdapter
import com.example.studystudymorestudyforever.convert.milisecond.ConverMiliseconds
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.course.CourseStudent
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.teacher_select_course_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * Created by VANKHANHPR on 10/3/2017.
 */
class SelectCourse:AppCompatActivity() {

    var call = OnEmitService.getIns()
    var progress_sigin_course:ProgressBar ?= null
    var mCountDownTimer: CountDownTimer? = null
    var courseID:String= ""
    var dialog:Dialog?=null
    var lv_time_course:ListView?= null

    var tv_namecourse:TextView?= null
    var tv_starttime:TextView ?=null
    var tv_endtime:TextView?=null
    var tv_tuition:TextView?=null
    var tv_address:TextView?=null
    var nameteacherTextview:TextView?=null


    fun initt()
    {
        EventBus.getDefault().register(this)
        progress_sigin_course= findViewById(R.id.progress_sigin_course) as ProgressBar
        lv_time_course= findViewById(R.id.lv_time_course) as ListView


        val header = layoutInflater.inflate(R.layout.header_listview_detail_course_student_layout, null)
        lv_time_course!!.addHeaderView(header)
        tv_namecourse= header.findViewById(R.id.tv_namecourse) as TextView
        tv_starttime= header.findViewById(R.id.tv_starttime) as TextView
        tv_endtime= header.findViewById(R.id.tv_endtime) as TextView
        tv_tuition= header.findViewById(R.id.tv_tuition) as TextView
        tv_address= header.findViewById(R.id.tv_address) as TextView
        nameteacherTextview = header.findViewById(R.id.nameteacherTextview) as TextView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_select_course_layout)

        initt()

        var invalsetdetailCourse:Array<String> = arrayOf(LocalData.selectCourseSt.getSCHE_ID())
        val bundle = intent.extras
        nameteacherTextview!!.setText(bundle.getString("nameteacher"))
        call.Call_Service(Value.workername_getdetailteacher,
                Value.servicename_getdetailteacher,
                invalsetdetailCourse,
                Value.key_getdetailcourseteacher)

        tab_back_teacher.setOnClickListener()
        {
            finish()
        }
        tab_singin_course.setOnClickListener()
        {
            dialog = MaterialDialog.Builder(this)
                    .title("Đang đăng kí")
                    .content("Please wait")
                    .progress(true, 0)
                    .show()
            var dialog2= MaterialDialog.Builder(this)
                    .title(R.string.dialog_title)
                    .content("Có lỗi xảy ra vui lòng kiểm tra lại")
                    .positiveText("OK")

            var inval :Array<String> = arrayOf(LocalData.user.getID().toString(),
                    LocalData.selectCourseSt.getSCHE_ID())
            call.Call_Service(Value.workername_signin_course,
                    Value.servicename_signin_course,
                    inval,Value.key_call_signin_course)

            mCountDownTimer = object : CountDownTimer(15000, 1000) {
                var i = 0
                override fun onTick(millisUntilFinished: Long) {
                    i++
                    OnEmitService.getIns().Sevecie()
                    if (i == 5) {

                        for (i in 0..OnEmitService.getIns().hasmap!!.size - 1) {
                            OnEmitService.getIns().hasmap!![i].setStatus(0)
                        }
                    }
                    //mProgressBar.progress = i
                    if (i == 15) {
                        OnEmitService.getIns().Sevecie()
                        /*progress_sigin_course!!.visibility = View.GONE*/
                        dialog2.show()
                    }
                }

                override fun onFinish() {
                    //Do what you want
                    i++
                    try {
                        dialog!!.cancel()
                        dialog2.show()
                        /*progress_sigin_course!!.visibility = View.GONE*/
                    } catch (e: Exception) {
                    }
                }
            }
            mCountDownTimer!!.start()
        }
    }

    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        val gson = Gson()

        if (event.getKey() == Value.key_call_signin_course)
        {
            mCountDownTimer!!.cancel()
            dialog!!.cancel()

            if(event.getData()!!.getResult()=="0")
            {
                    MaterialDialog.Builder(this)
                        .title("Lỗi")
                        .content(event.getData()!!.getMessage().toString())
                        .positiveText("Đồng ý").show()
            }
            else{
                var dialogsiginsuccess =Dialog(this)
                dialogsiginsuccess.setContentView(R.layout.dialog_login)
                var tv_title = dialogsiginsuccess.findViewById(R.id.tv_title) as TextView
                var tv_show_error= dialogsiginsuccess.findViewById(R.id.tv_show_error) as TextView
                var btn_agree_dialogres = dialogsiginsuccess.findViewById(R.id.btn_agree_dialogres) as Button
                tv_title.setText("Đăng kí môn học")
                tv_show_error.setText("Đăng kí môn học thành công")
                dialogsiginsuccess.show()
                btn_agree_dialogres.setOnClickListener()
                {
                    var inval: Array<String> = arrayOf(LocalData.user!!.getID()!!.toString())
                    call.Call_Service(Value.workername_get_coursestudent,
                            Value.servicename_get_coursestudent,
                            inval,
                            Value.key_getlistcoursestudent)
                    finish()
                }
            }
        }
        if(event.getKey()== Value.key_getdetailcourseteacher)
        {
            if(event.getData()!!.getResult()=="1")
            {
                var listCourse :ArrayList<CourseStudent> = arrayListOf()
                for(i in 0.. event.getData()!!.getData()!!.size-1)
                {
                    var course:CourseStudent= gson.fromJson(event.getData()!!.getData()!![i].toString(),CourseStudent::class.java)
                    listCourse.add(course)
                }

                tv_namecourse!!.setText("")
                var starttime= ConverMiliseconds().converttodate(listCourse[0].getSTART_TIME().toLong())
                var endtime= ConverMiliseconds().converttodate(listCourse[0].getEND_TIME().toLong())
                tv_starttime!!.setText(starttime)
                tv_endtime!!.setText(endtime)
                tv_tuition!!.setText(listCourse[0].getFEE())
                tv_address!!.setText(listCourse[0].getLOCATION())

                var adapter= DetailCourseStudentAdapter(this,listCourse)
                lv_time_course!!.adapter= adapter
                lv_time_course!!.setTranscriptMode(0);
            }
        }
    }
    public override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }
}