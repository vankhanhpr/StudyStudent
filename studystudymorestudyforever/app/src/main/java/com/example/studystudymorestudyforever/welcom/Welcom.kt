package com.example.studystudymorestudyforever.welcom

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.studystudymorestudyforever.R
import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler;
import android.renderscript.Sampler
import com.example.studystudymorestudyforever.fragment.main.MainActivity
import com.example.studystudymorestudyforever.login.Login
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.datalocal.LocalData


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

        var secondDelay:Long?=5
        Handler().postDelayed(Runnable {

            if(email!="")//tiến hành đăng nhập khi chưa có email
            {
                LocalData.email= email
                LocalData.usertype= usertype
                startActivity(Intent(this, SelectAccount::class.java))
                finish()
            }
            else
            {
                call.Sevecie()
                call.ListenEvent()
                var inval :Array<String> = arrayOf(email,password)
                call.Call_Service(Value.workername_login,Value.servicename_login,inval,Value.key_login_welcom)
                var inten= Intent(this,MainActivity::class.java)
                startActivity(inten)
            }


        },secondDelay!! *1000)
    }
    //ham luu trang thai dang nhap
    fun getingPreferences()
    {
        //tạo đối tượng getSharedPreferences
        var pre: SharedPreferences = getSharedPreferences("status", Context.MODE_PRIVATE)
        //tạo đối tượng Editor để lưu thay đổi
        email= pre.getString("user","")
        password= pre.getString("pwd","")
        usertype=pre.getInt("usertype",-1)
    }
}