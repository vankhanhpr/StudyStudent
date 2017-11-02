package com.example.studystudymorestudyforever.changepasss

import android.app.Dialog
import android.content.Intent
import android.icu.text.LocaleDisplayNames
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.widget.ProgressBar

import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.encode.Encode
import com.example.studystudymorestudyforever.fragment.main.MainActivity
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.JsonLogin
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import kotlinx.android.synthetic.main.changepass_layout.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * Created by VANKHANHPR on 9/20/2017.
 */
class ChangePass:AppCompatActivity() {

    var email:String?=null
    var oldpass:String?=null
    var newpass:String?=null
    var call =OnEmitService.getIns()
    var dialog_changepass:Dialog?=null
    var dialog_restartpass:Dialog?=null

    var mCountDownTimer: CountDownTimer? = null
    var progress_changepass: ProgressBar?=null
    var tv_show_error:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.changepass_layout)

        if(LocalData.userlogin==0)
        {
            tab_back_changepasss.visibility= View.GONE
        }
        else{
            tab_back_changepasss.visibility=View.VISIBLE
        }
//
//        var inte: Intent = intent
//        var bundle:Bundle=inte.getBundleExtra(Value.bundle)
//        email= bundle.getString(Value.value)
        email= LocalData.email
        progress_changepass= findViewById(R.id.progress_changepass) as ProgressBar

        tab_button_changepass.setOnClickListener()
        {
            dialog_changepass= Dialog(this)
            dialog_changepass!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_changepass!!.setContentView(R.layout.dialog_changepass)
            var btn_agree_dialogres =dialog_changepass!!.findViewById(R.id.btn_agree_dialogres)
            tv_show_error=dialog_changepass!!.findViewById(R.id.tv_show_error) as TextView
            btn_agree_dialogres!!.setOnClickListener()
            {
                dialog_changepass!!.cancel()
            }



            var oldpass1= edt_oldpass.text.toString()
            var newpass1= edt_newpass.text.toString()
            var newpass2= edt_newpassagain.text.toString()

            /*if(!boolPass(oldpass!!,newpass1,newpass2))
            {
                dialog_changepass!!.show()
            }
            else
            {
               */
                oldpass= Encode().encryptString(oldpass1)
                newpass=Encode().encryptString(newpass1)
                progress_changepass!!.visibility=View.VISIBLE

                var data1:Array<String> = arrayOf(newpass1,email!!,oldpass1)
                call.Call_Service(Value.workername_changepasss,Value.servicename_changepass,data1,Value.key_changepass)

                //timeout
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
                            progress_changepass!!.visibility = View.GONE
                        }
                    }

                    override fun onFinish() {
                        //Do what you want
                        i++
                        try {
                            dialog_changepass!!.show()
                            progress_changepass!!.visibility = View.GONE
                        } catch (e: Exception) {
                        }
                    }
                }
                mCountDownTimer!!.start()
        }

        //lay lai mat khau
        btn_no_pass.setOnClickListener()
        {
            dialog_restartpass= Dialog(this)
            dialog_changepass!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_changepass!!.setContentView(R.layout.dialog_changepass_restart_again)
            var data2:Array<String> = arrayOf(email!!)
            call.Call_Service(Value.workername_restartpass,Value.servicename_restartpass,data2,"abc")
            dialog_changepass!!.show()
           // var bntok= dialog_changepass!!.findViewById(R.id.btn_agree_dialogres)
            /*bntok.setOnClickListener()
            {
                dialog_restartpass!!.cancel()
            }*/
        }
        tab_back_changepasss.setOnClickListener()
        {
            finish()
        }
    }
    //Nhận kết quả trả về
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getKey() == Value.key_import_code)
        {
            mCountDownTimer!!.cancel() //turn off timeout
            progress_changepass!!.visibility= View.GONE//turn off loading

            var json: ArrayList<JSONObject>? = event.getData()!!.getData()
            var temp=readJson(json!!)
            if (temp.getC0()=="Y")
            {
                if(LocalData.userlogin==0) {
                    var inte = Intent(applicationContext, MainActivity::class.java)
                    startActivity(inte)
                }
                else
                {
                    finish()
                }
            }
            else
            {
                dialog_changepass!!.show()
                tv_show_error!!.setText(event!!.getData()!!.getMessage())
            }
        }
    }
    //kiểm tra đúng đắn của mật khẩu
    fun boolPass(pas1:String,pas2:String,pas3:String):Boolean
    {
        if(pas1.length<6 || pas2.length<6||pas3.length<6|| !pas2.equals(pas3))
        {
            return false
        }
        return  true
    }


    // Đọc file Json để lấy kết quả
    fun readJson(json1: ArrayList<JSONObject>): JsonLogin
    {
        var jsonO: JSONObject?=null

        if(json1.size>0)
        {
            jsonO = json1[0]
        }
        var c0: String? =jsonO!!.getString("c0")
        var ser1 : JsonLogin = JsonLogin()
        ser1.setC0(c0!!)
        return ser1
    }
}