package com.example.studystudymorestudyforever.fragment.account.file

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.Window
import android.widget.*
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.filestudent.FileStudentAdapter
import com.example.studystudymorestudyforever.adapter.adapter.filestudent.ListUserFileDialogAdapter
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.myinterface.ICallAddFriend
import com.example.studystudymorestudyforever.myinterface.ISetScheduleFileStudent
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.example.studystudymorestudyforever.until.fragment_account.Student
import com.example.studystudymorestudyforever.until.teacher.TeacherofStudent

import com.google.gson.Gson
import kotlinx.android.synthetic.main.account_show_file_student.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.find
import org.json.JSONObject

/**
 * Created by VANKHANHPR on 9/30/2017.
 */
class FileStudent :AppCompatActivity(), View.OnClickListener,ICallAddFriend,ISetScheduleFileStudent {


    var call= OnEmitService.getIns()

    var recicleview_file_student:RecyclerView?=null
    var listFileST :ArrayList<Student> = arrayListOf()

    var listuser:ArrayList<TeacherofStudent> = arrayListOf()

    var tab_no_data:LinearLayout ?= null

    var lv_listUser:ListView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_show_file_student)
        EventBus.getDefault().register(this)

        recicleview_file_student= findViewById(R.id.recicleview_file_student) as RecyclerView
        getListFileStudent()

        tab_back.setOnClickListener(this)
        tab_add.setOnClickListener(this)
    }
    //Get list file student
    fun getListFileStudent()
    {
        var inval:Array<String> = arrayOf(LocalData.user.getID().toString())
        call.Call_Service(Value.workername_getfile,Value.servicename_getfile,inval,Value.key_getfile)
    }

    //Đổ dữ liệu vào các adapter
    fun resetLayout()
    {
        recicleview_file_student!!.setLayoutManager(LinearLayoutManager(this));
        var adapter= FileStudentAdapter(applicationContext,listFileST,this)
        recicleview_file_student!!.adapter =adapter
    }

    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        var gson:Gson =  Gson()

        if (event.getKey() == Value.key_getfile)//Lấy danh sách hồ sơ
        {
            var json: ArrayList<JSONObject>? = event.getData()!!.getData()
            if(event.getData()!!.getResult()=="1")
            {
                for(i in 0..event.getData()!!.getData()!!.size-1)
                {
                    var student: Student = gson.fromJson(json!![i].toString(),Student::class.java)
                    listFileST.add(student)
                }
                resetLayout()//Đổ dữ liệu vào adapter
            }
            else
            {

            }
        }
        if(event.getKey()== Value.key_search_file)
        {
            tab_no_data!!.visibility= View.GONE
            if(event.getData()!!.getResult()=="1")
            {
                listuser.clear()

                var json: ArrayList<JSONObject>? = event.getData()!!.getData()

                for(i in 0..json!!.size-1)
                {
                    var user = gson.fromJson(json!![i].toString(),TeacherofStudent::class.java)
                    listuser.add(user)
                }
                var adapter= ListUserFileDialogAdapter(applicationContext,listuser,this)
                lv_listUser!!.adapter= adapter
            }
        }
    }


    //Các sự kiện onclick
    override fun onClick(v: View?) {
        when (v!!.id)
        {
            R.id.tab_back ->{//Trở về activity trước đó
                finish()
            }
            R.id.tab_add ->{//Thêm hố sơ học sinh cho phụ huynh
                var dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.dialog_add_file_student)
                dialog.show()
                var edt_search_sudent= dialog.findViewById(R.id.edt_search_sudent) as EditText
                var tab_search= dialog.findViewById(R.id.tab_search)
                tab_no_data= dialog.findViewById(R.id.tab_no_data) as LinearLayout

                lv_listUser=dialog.findViewById(R.id.lv_listUser) as ListView
                var tab_cancel= dialog.findViewById(R.id.tab_cancel)

                tab_cancel.setOnClickListener()//Tắt dialog
                {
                    dialog.cancel()
                }

                tab_search.setOnClickListener()//Tìm kiếm hồ sơ
                {
                    var file = edt_search_sudent.text.toString().trim()
                    var inval:Array<String> = arrayOf(file,LocalData.user.getID().toString())

                    call.Call_Service(Value.workername_search_student_file,
                            Value.servicename_search_student_file,inval,Value.key_search_file)
                }
            }
        }
    }

    override fun calladdFriend(id: String)//Gọi service thêm hồ sơ
    {
        super.calladdFriend(id)

        var inval :Array<String> = arrayOf(LocalData.user.getID().toString(),id)
        call.Call_Service(Value.workername_addfilestudent,
                Value.servicename_addfilestudent,inval,
                Value.key_addfilestudent)
    }

    override fun calldisaddFriend(id: String)//GỌi service hủy thêm hồ sơ
    {
        super.calldisaddFriend(id)

        var inval :Array<String> = arrayOf(id,LocalData.user.getID().toString())
        call.Call_Service(Value.workername_unadddfileesstudent,
                Value.servicename_unaddfilestudent,inval,
                Value.key_unaddfilestudent)
    }
    override fun callSetSchedule(id: String) {
        super.callSetSchedule(id)

        var dialog= Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_setschedule_file_student)
        var btn_cancel_dialogres =dialog.findViewById(R.id.btn_cancel_dialogres)
        var btn_agree_dialogres= dialog.findViewById(R.id.btn_agree_dialogres)
        dialog.show()

        btn_agree_dialogres.setOnClickListener()
        {
            getCourse(id)
            dialog.cancel()
            finish()
        }
        btn_cancel_dialogres.setOnClickListener()
        {
            dialog.cancel()
        }


    }
    //Đổ lại thông tin lớp học
    fun getCourse(id:String) {
        var inval: Array<String> = arrayOf(id)
        call.Call_Service(Value.workername_setscheulestudentofparent,
                Value.servicename_setschedulestudentofparent,
                inval,
                Value.key_setlistcourse_studentoffteacher)
    }

}