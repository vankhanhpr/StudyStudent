package com.example.studystudymorestudyforever.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.encode.Encode
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.Value
import android.graphics.drawable.AnimationDrawable
import android.app.Dialog
import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.fragment.main.MainActivity
import com.example.studystudymorestudyforever.restartpass.RestartPass
import com.example.studystudymorestudyforever.singin.SignIn
import com.example.studystudymorestudyforever.until.JsonLogin
import kotlinx.android.synthetic.main.login_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import com.afollestad.materialdialogs.MaterialDialog
import com.example.studystudymorestudyforever.fragment.mainteacher.MainTeacher
import com.example.studystudymorestudyforever.until.datalocal.LocalData


/**
 * Created by VANKHANHPR on 9/8/2017.
 */


class Login:AppCompatActivity()
{
    var user :String?=null
    var pass:String?=null
    var call= OnEmitService.getIns()
    var progress_login:ProgressBar ?= null
    var dialog_login:Dialog?=null
    var mCountDownTimer: CountDownTimer? = null
    //dialog
    var btn_agree_dialogres:Button?=null
    var tv_show_error:TextView?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        EventBus.getDefault().register(this)

        if(LocalData.usertype==2)//không cho giáo viên đăng kí
        {
            goto_signin.visibility=View.GONE
        }
        else//cho phép phụ huynh và học sinh đăng kí
        {
            goto_signin.visibility=View.VISIBLE
        }
        var container = findViewById(R.id.container) as RelativeLayout
        var anim = container.background as AnimationDrawable
        progress_login= findViewById(R.id.progress_login)  as ProgressBar

        anim.setEnterFadeDuration(6000)
        anim.setExitFadeDuration(2000)

        if (anim != null && !anim.isRunning())
            anim.start();


        var tab_button_login= findViewById(R.id.tab_button_login) as LinearLayout
        var  edt_user = findViewById(R.id.edt_user) as EditText
        var  edt_pass = findViewById(R.id.edt_pass) as EditText

        tab_button_login.setOnClickListener {

            dialog_login = Dialog(this)
            dialog_login!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_login!!.setContentView(R.layout.dialog_login)
            btn_agree_dialogres=dialog_login!!.findViewById(R.id.btn_agree_dialogres) as Button
            tv_show_error=dialog_login!!.findViewById(R.id.tv_show_error) as TextView

            user = edt_user.text.trim().toString()
            pass = edt_pass.text.trim().toString()
            var pass2 = Encode().encryptString(pass!!)//endcode password

            if ( boolPass(pass!!, user!!)== false || pass!!.length<6 || user==""||pass=="")
            {
                dialog_login!!.show()
                tv_show_error!!.setText(applicationContext.resources.getText(R.string.dialog_status))
                btn_agree_dialogres!!.setOnClickListener()
                {
                    dialog_login!!.cancel()
                }
            }
            else {
                progress_login!!.visibility = View.VISIBLE
                var data: Array<String> = arrayOf(user!!, pass!!)
                call.Sevecie()
                call.Call_Service(Value.workername_login, Value.servicename_login, data!!, Value.key_login)

                //timeout
                mCountDownTimer = object : CountDownTimer(15000, 1000) {
                    var i = 0
                    override fun onTick(millisUntilFinished: Long) {
                        i++
                       OnEmitService.getIns().Sevecie()
                        if (i == 5){

                            call.Sevecie()
                            for (i in 0..OnEmitService.getIns().hasmap!!.size - 1) {
                                call.hasmap!![i].setStatus(0)
                            }
                        }
                        //mProgressBar.progress = i
                        if (i == 15) {
                            OnEmitService.getIns().Sevecie()
                            progress_login!!.visibility = View.GONE
                        }
                    }

                    override fun onFinish() {
                        //Do what you want
                        i++
                        try {
                            dialog_login!!.show()
                            btn_agree_dialogres!!.setOnClickListener()
                            {
                                dialog_login!!.cancel()
                            }
                            progress_login!!.visibility = View.GONE
                        } catch (e: Exception) {
                        }
                    }
                }
                mCountDownTimer!!.start()
            }
        }

        ///sign in
        goto_signin.setOnClickListener()
        {
            var inten= Intent(this,SignIn::class.java)
            startActivity(inten)
            finish()
        }
        //restart password
        goto_restart_password.setOnClickListener()
        {
            var inten2= Intent(this,RestartPass::class.java)
            startActivity(inten2)
            finish()
        }
    }
    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getKey() == Value.key_login)
        {
            mCountDownTimer!!.cancel() //turn off timeout
            progress_login!!.visibility= View.GONE//turn off loading

            //LocalData.email=user!!
            var json: ArrayList<JSONObject>? = event.getData()!!.getData()
            var temp=readJson(json!!)
            if (temp.getC0()=="Y")
            {
                LocalData.email= user!!
                LocalData.login=true//đăng nhập vào bình thường

                LocalData.pass= pass!!
                Log.d("passsnew",LocalData.pass)

                if(LocalData.usertype==2)
                {
                    sendToActivityMainTeacher(user!!)
                }
                else
                {
                    sendToActivityMain(user!!)
                }

            }
            else
            {
                dialog_login!!.show()
                var btn_agree_dialogres= dialog_login!!.findViewById(R.id.btn_agree_dialogres)
                tv_show_error!!.text =event!!.getData()!!.getMessage().toString()
                btn_agree_dialogres!!.setOnClickListener()
                {
                    dialog_login!!.cancel()
                }
            }
        }
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
        call.removeListener()
    }

    fun boolPass(pass:String,email:String):Boolean
    {
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

    //Hàm chuyển qua Login
    fun sendToActivityMain(value: String) {

        var intent3 = Intent(applicationContext,MainActivity::class.java)
        var bundle = Bundle()
        bundle.putString(Value.value, value)
        intent3.putExtra(Value.bundle, bundle)
        startActivity(intent3)
        finish()
    }

    //Hàm chuyển qua Login
    fun sendToActivityMainTeacher(value: String) {

        var intent3 = Intent(applicationContext,MainTeacher::class.java)
        var bundle = Bundle()
        bundle.putString(Value.value, value)
        intent3.putExtra(Value.bundle, bundle)
        startActivity(intent3)
        finish()
    }
}