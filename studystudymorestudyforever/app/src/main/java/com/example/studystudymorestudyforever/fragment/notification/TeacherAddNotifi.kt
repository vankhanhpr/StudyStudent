package com.example.studystudymorestudyforever.fragment.notification

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.studystudymorestudyforever.R
import kotlinx.android.synthetic.main.notification_teachers_add_layout.*

/**
 * Created by VANKHANHPR on 11/5/2017.
 */
class TeacherAddNotifi:AppCompatActivity(), View.OnClickListener {



    var dialogshowAccount:Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_teachers_add_layout)


        tab_select_uer_chat.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id)
        {
            R.id.tab_select_uer_chat ->
            {

            }
        }
    }
}