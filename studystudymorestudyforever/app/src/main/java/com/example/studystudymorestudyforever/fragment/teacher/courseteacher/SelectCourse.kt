package com.example.studystudymorestudyforever.fragment.teacher.courseteacher

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import com.afollestad.materialdialogs.MaterialDialog
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.Value
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_select_course_layout)
        progress_sigin_course= findViewById(R.id.progress_sigin_course) as ProgressBar
        var dialog:Dialog?=null


        tab_back_teacher.setOnClickListener()
        {
            finish()
        }
        tab_singin_course.setOnClickListener()
        {
            //progress_sigin_course!!.visibility =View.VISIBLE


            dialog = MaterialDialog.Builder(this)
                    .title("Đang đăng kí")
                    .content("Please wait")
                    .progress(true, 0)
                    .show()
            var dialog2= MaterialDialog.Builder(this)
                    .title(R.string.dialog_title)
                    .content("Có lỗi xảy ra vui lòng kiểm tra lại")
                    .positiveText("OK")

            var inval :Array<String> = arrayOf()
            call.Call_Service(Value.workername_signin_course,Value.servicename_signin_course,inval,Value.key_call_signin_course)

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
        if (event.getKey() == Value.key_call_signin_course)
        {
            mCountDownTimer!!.cancel()
        }
    }
    public override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

}