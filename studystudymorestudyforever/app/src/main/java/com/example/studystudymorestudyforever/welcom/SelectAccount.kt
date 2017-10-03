package com.example.studystudymorestudyforever.welcom

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.welcom.ImageAdapter
import com.example.studystudymorestudyforever.login.Login
import com.example.studystudymorestudyforever.myinterface.ISelectAccount

/**
 * Created by VANKHANHPR on 9/22/2017.
 */
class SelectAccount :AppCompatActivity(),ISelectAccount{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcom_select_account_layout)

        var mviewpager = findViewById(R.id.mviewpager) as ViewPager
        var adapter= ImageAdapter(applicationContext,this)
        mviewpager.adapter= adapter

    }

    override fun call() {
        super.call()
        var int= Intent(applicationContext,Login::class.java)
        startActivity(int)
        finish()
    }
}