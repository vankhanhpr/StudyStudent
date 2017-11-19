package com.example.studystudymorestudyforever.singin

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.encode.Encode
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.JsonLogin
import com.example.studystudymorestudyforever.until.Value
import kotlinx.android.synthetic.main.signin_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * Created by VANKHANHPR on 9/11/2017.
 */

class  SignIn:AppCompatActivity()
{
    var name:String= ""
    var email:String=""
    var pass:String=""

    var dialog_signin:Dialog?=null
    var mCountDownTimer: CountDownTimer? = null
    //dialog
    var btn_agree_dialogres: Button?=null
    var tv_show_error: TextView?=null
    var progress_signin:ProgressBar?=null
    var call= OnEmitService.getIns()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_layout)

        EventBus.getDefault().register(this)

        progress_signin=findViewById(R.id.progress_signin) as ProgressBar

        //lấy dữ liệu
        //var inte: Intent = intent
       // var bundle:Bundle=inte.getBundleExtra(Value.bundle)

        //email= bundle.getString(Value.value)
        //pass=bundle.getString(Value.value2)




        tab_sigin.setOnClickListener()
        {
            name= import_name_user.text.toString()
            email= import_email_singin.text.toString()
            var pass1= import_pass_signin.text.toString()
            var pass2= import_pass_again.text.toString()

            dialog_signin= Dialog(this)
            dialog_signin!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_signin!!.setContentView(R.layout.dialog_signin)
            btn_agree_dialogres=dialog_signin!!.findViewById(R.id.btn_agree_dialogres) as Button
            tv_show_error=dialog_signin!!.findViewById(R.id.tv_show_error) as TextView


            if (!pass1.equals(pass2) || pass1.length < 6 || boolPass(pass1, email!!) == false) {
                dialog_signin!!.show()
                btn_agree_dialogres!!.setOnClickListener()
                {
                    dialog_signin!!.cancel()
                }
            }
            else
            {
                progress_signin!!.visibility = View.VISIBLE
                pass= Encode().encryptString(pass1)
                var data: Array<String> = arrayOf(name!!,email!!, pass1!!)
                call.Call_Service(Value.workername_signin, Value.servicename_signin, data!!, Value.key_sigin)

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
                            progress_signin!!.visibility = View.GONE
                        }
                    }
                    override fun onFinish() {
                        //Do what you want
                        i++
                        try {
                            dialog_signin!!.show()
                            btn_agree_dialogres!!.setOnClickListener()
                            {
                                dialog_signin!!.cancel()
                            }
                            progress_signin!!.visibility = View.GONE
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
        if (event.getKey() == Value.key_sigin)
        {
            mCountDownTimer!!.cancel() //turn off timeout
            progress_signin!!.visibility= View.GONE//turn off loading

            var json: ArrayList<JSONObject>? = event.getData()!!.getData()
            var temp=readJson(json!!)
            if (temp.getC0()=="Y")
            {
                sendToActivityImportCode(email!!,pass!!)
            }
            else
            {
                dialog_signin!!.show()
                var btn_agree_dialogres= dialog_signin!!.findViewById(R.id.btn_agree_dialogres)
                tv_show_error!!.setText(event!!.getData()!!.getMessage())
                btn_agree_dialogres!!.setOnClickListener()
                {
                    dialog_signin!!.cancel()
                }
            }
        }
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

    fun boolPass(pass:String,email:String):Boolean
    {
        /*var s= ".*[^a-z^0-9].*+@+&+$"
        var y =s.toRegex()
        if(pass!!.matches(y))
        {
            return false
        }*/

        if(email.indexOf('@',0,false)==-1)
        {
            return false
        }
        var parts = email.split("@")

        if(parts.size>2 || parts[0]=="" || parts[1]==""||pass.length < 6)
        {
            return false
        }
        if(!(parts[1]=="gmail.com"||parts[1]=="yahoo.com.vn"||parts[1]=="outlook.com"))
        {
            return  false
        }
        return  true
    }
    //Hàm chuyển qua Login
    fun sendToActivityImportCode(value: String,value2:String) {

        var intent3 = Intent(applicationContext,ImportCode::class.java)
        var bundle = Bundle()
        bundle.putString(Value.value, value)
        bundle.putString(Value.value2, value2)
        intent3.putExtra(Value.bundle, bundle)
        startActivity(intent3)
        finish()
    }
}