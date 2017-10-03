package com.example.studystudymorestudyforever.fragment.account.file

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.filestudent.FileStudentAdapter
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.example.studystudymorestudyforever.until.fragment_account.Student
import kotlinx.android.synthetic.main.account_show_file_student.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * Created by VANKHANHPR on 9/30/2017.
 */
class FileStudent :AppCompatActivity(){

    var call= OnEmitService.getIns()

    var recicleview_file_student:RecyclerView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_show_file_student)
        EventBus.getDefault().register(this)

        recicleview_file_student= findViewById(R.id.recicleview_file_student) as RecyclerView


        var student:ArrayList<Student> ?= arrayListOf()
        var s1:Student ?= Student()
        s1!!.setName("Lê Trung Kiên")
        s1!!.setAge("22")

        student!!.add(s1)
        student!!.add(s1)
        student!!.add(s1)

        recicleview_file_student!!.setLayoutManager(LinearLayoutManager(this));
        var adapter= FileStudentAdapter(applicationContext,student)
        recicleview_file_student!!.adapter =adapter

        var inval:Array<String> = arrayOf(LocalData.email)
        call.Call_Service(Value.workername_getfile,Value.servicename_getfile,inval,Value.key_getfile)
    }

    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getKey() == Value.key_getfile)
        {
            var json: ArrayList<JSONObject>? = event.getData()!!.getData()
            if(event.getData()!!.getResult()=="1")
            {

            }
            else{

            }
        }
    }


}