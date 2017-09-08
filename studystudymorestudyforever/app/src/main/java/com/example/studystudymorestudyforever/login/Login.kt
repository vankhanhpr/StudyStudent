package com.example.studystudymorestudyforever.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.LinearLayout
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.model.OnEmitService

/**
 * Created by VANKHANHPR on 9/8/2017.
 */


class Login:AppCompatActivity()
{
    var user :String?=null
    var pass:String?=null
    var call= OnEmitService.getIns()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        call.Sevecie()

        var tab_button_login= findViewById(R.id.tab_button_login) as LinearLayout
        var  edt_user = findViewById(R.id.edt_user) as EditText
        var  edt_pass = findViewById(R.id.edt_pass) as EditText


        tab_button_login.setOnClickListener {
            user= edt_user.text.toString()
            pass=edt_pass.text.toString()
            var data :Array<String> = arrayOf(user!!,pass!!)

            call.Sevecie()
            call.Call_Service("login","login2",data!!,"khanh")
        }


    }
}