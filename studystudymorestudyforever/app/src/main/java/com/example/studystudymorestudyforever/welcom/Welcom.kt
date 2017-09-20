package com.example.studystudymorestudyforever.welcom

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.studystudymorestudyforever.R
import android.content.Intent
import android.os.Handler;
import com.example.studystudymorestudyforever.login.Login


/**
 * Created by VANKHANHPR on 9/9/2017.
 */

class Welcom:AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_welcom)

        var secondDelay:Long?=5
        Handler().postDelayed(Runnable {
            startActivity(Intent(this, Login::class.java))
            finish()
        }, secondDelay!! *1000)
    }
}