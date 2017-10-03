package com.example.studystudymorestudyforever.restartpass

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.renderscript.Sampler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.changepasss.ChangePass
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.Value
import kotlinx.android.synthetic.main.restart_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * Created by VANKHANHPR on 9/11/2017.
 */

class RestartPass :AppCompatActivity()
{
    var email:String?=null
    var call= OnEmitService.getIns()
    var dialog_restartpass:Dialog?=null
    var mCountDownTimer: CountDownTimer? = null
    var progress_restart:ProgressBar?=null
    var tv_show_error:TextView?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restart_layout)
        EventBus.getDefault().register(this)

        progress_restart= findViewById(R.id.progress_restart) as ProgressBar

        id_tab_restart_pass.setOnClickListener()
        {
            dialog_restartpass= Dialog(this)
            dialog_restartpass!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_restartpass!!.setContentView(R.layout.dialog_restart_passs)
            tv_show_error= dialog_restartpass!!.findViewById(R.id.tv_show_error) as TextView
            var btn_agree_dialogres= dialog_restartpass!!.findViewById(R.id.btn_agree_dialogres)
            btn_agree_dialogres!!.setOnClickListener()
            {
                dialog_restartpass!!.cancel()
            }

            if(!boolEmail(email!!))
            {
                dialog_restartpass!!.show()
            }
            else {

                email = edt_email_restart.text.toString()
                var data: Array<String> = arrayOf(email!!)
                call.Call_Service(Value.workername_restartpass, Value.workername_restartpass, data, Value.key_restartpass)

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
                            progress_restart!!.visibility = View.GONE
                        }
                    }

                    override fun onFinish() {
                        //Do what you want
                        i++
                        try {
                            dialog_restartpass!!.show()
                            progress_restart!!.visibility = View.GONE
                        } catch (e: Exception) {
                        }
                    }
                }
                mCountDownTimer!!.start()
            }
        }
    }
    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getKey() == Value.key_login)
        {
            mCountDownTimer!!.cancel() //turn off timeout
            progress_restart!!.visibility= View.GONE//turn off loading
            if (event.getData()!!.getResult()=="1")
            {
                sendToActivityChangePass(email!!)
            }
            else
            {
                dialog_restartpass!!.show()
                tv_show_error!!.text =event!!.getData()!!.getMessage().toString()
            }
        }
    }

    fun boolEmail(email:String):Boolean
    {
        if(email.indexOf('@',0,false)==-1)
        {
            return false
        }
        var parts = email.split("@")

        if(parts.size>2 || parts[0]=="" || parts[1]=="")
        {
            return false
        }
        if(!(parts[1]=="gmail.com"||parts[1]=="yahoo.com.vn"||parts[1]=="outlook.com"))
        {
            return  false
        }
        return  true
    }

    fun sendToActivityChangePass(value:String)
    {
        var inten= Intent(applicationContext,ChangePass::class.java)
        var bundle = Bundle()
        bundle.putString(Value.value,value)
        inten.putExtra(Value.bundle, bundle)
        startActivity(inten)
        finish()
    }

}