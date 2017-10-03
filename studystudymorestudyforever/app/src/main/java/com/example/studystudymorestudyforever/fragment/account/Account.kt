package com.example.studystudymorestudyforever.fragment.account

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.changepasss.ChangePass
import com.example.studystudymorestudyforever.fragment.account.file.FileStudent
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.datalocal.LocalData

/**
 * Created by VANKHANHPR on 9/13/2017.
 */

class Account : Fragment()
{
    var gotochangepass:LinearLayout?=null
    var goto_update_user:LinearLayout?=null
    var goto_file_student:LinearLayout?=null

    var tab_logout:LinearLayout?=null


    var call = OnEmitService.getIns()
    var editor : SharedPreferences.Editor?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view: View = inflater!!.inflate(R.layout.main_account_fragment, container, false)
        gotochangepass= view.findViewById(R.id.gotochangepass) as LinearLayout
        tab_logout=view.findViewById(R.id.tab_logout) as LinearLayout
        goto_update_user=view.findViewById(R.id.goto_update_user) as LinearLayout
        goto_file_student= view.findViewById(R.id.goto_file_student) as LinearLayout

        goto_update_user!!.setOnClickListener()
        {
            var inten= Intent(context,ChangeInforUser::class.java)
            startActivity(inten)
        }
        gotochangepass!!.setOnClickListener()
        {
            var inten= Intent(context,ChangePass::class.java)
            LocalData.userlogin=1
            startActivity(inten)
        }
        tab_logout!!.setOnClickListener()
        {
            call.Disconnect()
            var pre: SharedPreferences = activity.getSharedPreferences("status", Context.MODE_PRIVATE)
            editor= pre.edit()
            editor!!.clear()
            editor!!.commit()
        }

        goto_file_student!!.setOnClickListener()//quản lý hồ sơ HS
        {
            var intestudent= Intent(context,FileStudent::class.java)
            startActivity(intestudent)
        }



        return  view
    }
}
