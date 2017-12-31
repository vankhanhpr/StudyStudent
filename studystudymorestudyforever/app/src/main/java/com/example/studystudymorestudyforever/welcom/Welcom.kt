package com.example.studystudymorestudyforever.welcom

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.studystudymorestudyforever.R
import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler;
import android.renderscript.Sampler
import android.util.Log
import android.view.View
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.encode.Encode
import com.example.studystudymorestudyforever.fragment.main.MainActivity
import com.example.studystudymorestudyforever.fragment.mainteacher.MainTeacher
import com.example.studystudymorestudyforever.login.Login
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject


/**
 * Created by VANKHANHPR on 9/9/2017.
 */

class Welcom:AppCompatActivity()
{

    var email:String =""
    var password =""
    var usertype:Int=0
    var call= OnEmitService.getIns()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_welcom)

        getingPreferences()//kiểm tra đã đang nhập chưa
        call.Sevecie()
        call.ListenEvent()
        var secondDelay:Long?=5

        if(email != "")
        {
            LocalData.login=false//Tự động đăng nhập
            LocalData.email= email
            LocalData.pass= password
            LocalData.usertype= usertype
            var inval:Array<String> = arrayOf(email,password)
            if(usertype == 2)
            {
                startActivity(Intent(this, MainTeacher::class.java))
                call.Call_Service(Value.workername_login,Value.servicename_login,inval,Value.key_loginshape)
            }
            else
                if(usertype == 1 || usertype == 0)
                {
                    startActivity(Intent(this, MainActivity::class.java))

                    call.Call_Service(Value.workername_login,Value.servicename_login,inval,Value.key_loginshape)
                }
            finish()
        }
        else
        {
            var inten= Intent(this,SelectAccount::class.java)
            startActivity(inten)
            Handler().postDelayed(Runnable
            {

            },secondDelay!! *1000)

            finish()
        }

    }

    //ham luu trang thai dang nhap
    fun getingPreferences()
    {
        var pre: SharedPreferences = getSharedPreferences("status", Context.MODE_PRIVATE)
        email = pre.getString("user", "")
        password = pre.getString("pwd", "")
        usertype = pre.getInt("usertype", 0)
        Log.d("kieu đăng nhập là ", "là: " + usertype + " " + email + " " + password)

    }

    override fun onStop() {
        super.onStop()
        call.removeListener()
    }

}