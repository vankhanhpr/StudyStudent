package com.example.studystudymorestudyforever.fragment.teacher

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.teacher.TeacherAdapter
import com.example.studystudymorestudyforever.model.OnEmitService

import android.widget.*
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.teacher.TeacherSearch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import com.example.studystudymorestudyforever.fragment.teacher.courseteacher.SelectCourse
import com.example.studystudymorestudyforever.myinterface.ISetTeacher
import com.afollestad.materialdialogs.MaterialDialog
import com.example.studystudymorestudyforever.adapter.adapter.teacher.CourseTeacherDialogAdapter
import com.example.studystudymorestudyforever.adapter.adapter.teacher.ListUsertoDialogAdapter
import com.example.studystudymorestudyforever.myinterface.ICallAddFriend
import com.example.studystudymorestudyforever.myinterface.ISetCourseStudent
import com.example.studystudymorestudyforever.until.course.CourseStudent
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.example.studystudymorestudyforever.until.teacher.TeacherofStudent
import com.google.gson.Gson
import com.tubb.smrv.SwipeMenuRecyclerView
import kotlinx.android.synthetic.main.main_teacher_fragment.*
import android.support.v7.widget.helper.ItemTouchHelper
import com.baoyz.swipemenulistview.SwipeMenu
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView


/**
 * Created by VANKHANHPR on 9/13/2017.

 */
class  Teacher: Fragment(),ISetTeacher, ISetCourseStudent,ICallAddFriend {


    var call = OnEmitService.getIns()
    var email: String = ""
   // var recicleview_list_teacher: RecyclerView? = null
    var lv_list_teacher: SwipeMenuListView?=null

    var tab_add_teacher: LinearLayout? = null
    var dialog_add_teacher: Dialog? = null
    var tab_search: LinearLayout? = null
    var edt_search_friend: EditText? = null
    var progress_search_friend: ProgressBar? = null
    var mCountDownTimer: CountDownTimer? = null

    var listUser: ArrayList<TeacherofStudent> = arrayListOf()
    var swp_refresh: SwipeRefreshLayout? = null
    var dialogshowCourseTeacher: Dialog? = null


    var recycle_select_account: RecyclerView? = null

    var tab_nodata: LinearLayout? = null

    var lv_find_user: ListView? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater!!.inflate(R.layout.main_teacher_fragment, container, false)
        EventBus.getDefault().register(this)

        //recicleview_list_teacher = view.findViewById(R.id.recicleview_list_teacher) as RecyclerView
        lv_list_teacher= view.findViewById(R.id.lv_list_teacher) as SwipeMenuListView

        tab_add_teacher = view.findViewById(R.id.tab_add_teacher) as LinearLayout
        swp_refresh = view.findViewById(R.id.swp_refresh) as SwipeRefreshLayout

        listUser = LocalData.listTeacher




        /*var creator = SwipeMenuCreator { menu ->
            // create "open" item
            var openItem = SwipeMenuItem(context)
            // set item background
            openItem.background = ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25))
            // set item width
            openItem.width=200
            // set item title
            openItem.title = "Hủy"
            // set item title fontsize
            openItem.titleSize = 10
            // set item title font color
            openItem.titleColor = Color.WHITE
            // add to menu
            menu.addMenuItem(openItem)
        }*/


        //lv_list_teacher!!.setMenuCreator(creator)//set tab khi vuốt ngang

        lv_list_teacher!!.setOnItemClickListener{
            parent, view, position, id ->
            callgetDetail(listUser[position].getID().toString())
        }

       /* lv_list_teacher!!.setOnMenuItemClickListener(object : SwipeMenuListView.OnMenuItemClickListener{
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onMenuItemClick(position: Int, menu: SwipeMenu, index: Int): Boolean {

                when (index) {
                    0 -> {
                        var dialog_cancel_schedule = Dialog(context)

                        dialog_cancel_schedule!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog_cancel_schedule!!.setContentView(R.layout.dialog_cancel_course)
                        var btn_cancel_dialogres=  dialog_cancel_schedule.findViewById(R.id.btn_cancel_dialogres)
                        var btn_agree_dialogres= dialog_cancel_schedule.findViewById(R.id.btn_agree_dialogres)

                        btn_cancel_dialogres.setOnClickListener()
                        {
                            dialog_cancel_schedule.cancel()
                        }
                        btn_agree_dialogres.setOnClickListener()
                        {
                            var inval : Array<String> = arrayOf()
                            call.Call_Service(Value.workername_cancel_friend,Value.servicename_cancel_friend,inval,Value.key_cancelfriend)
                        }
                        dialog_cancel_schedule.show()
                    }
                    1 -> {
                       // callgetDetail(listUser[position].getID().toString())
                    }
                }
                return false
            }
        })*/



        if (listUser.size > 0) {
            //recicleview_list_teacher!!.visibility = View.VISIBLE
            //tab_no_data_teacher.visibility= View.GONE
            showLayout()
        }

        tab_add_teacher!!.setOnClickListener()
        {
            dialog_add_teacher = Dialog(context)
            dialog_add_teacher!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_add_teacher!!.setContentView(R.layout.dialog_add_teacher_layout)
            tab_search = dialog_add_teacher!!.findViewById(R.id.tab_search) as LinearLayout
            edt_search_friend = dialog_add_teacher!!.findViewById(R.id.edt_search_friend) as EditText
            progress_search_friend = dialog_add_teacher!!.findViewById(R.id.progress_search_friend) as ProgressBar
            lv_find_user = dialog_add_teacher!!.findViewById(R.id.lv_find_user) as ListView
            tab_nodata = dialog_add_teacher!!.findViewById(R.id.tab_no_data) as LinearLayout

            var tab_cancel = dialog_add_teacher!!.findViewById(R.id.tab_cancel)

            tab_cancel!!.setOnClickListener() //cancel dialog
            {
                dialog_add_teacher!!.cancel()
            }

            dialog_add_teacher!!.show()

            tab_search!!.setOnClickListener()
            {
                progress_search_friend!!.visibility = View.VISIBLE

                var emailsearch = edt_search_friend!!.text.toString().trim()
                var invalsearch: Array<String> = arrayOf(emailsearch, LocalData.user.getID().toString())
                call.Call_Service(Value.workername_searchfriend,
                        Value.servicename_searchfriend,
                        invalsearch, Value.key_searchfriend)

                //timeout
                mCountDownTimer = object : CountDownTimer(15000, 1000) {
                    var i = 0
                    override fun onTick(millisUntilFinished: Long) {
                        i++
                        OnEmitService.getIns().Sevecie()
                        if (i == 5) {

                            for (i in 0..OnEmitService.getIns().hasmap!!.size - 1) {
                                OnEmitService.getIns().hasmap!![i].setStatus(0)
                            }
                        }
                        //mProgressBar.progress = i
                        if (i == 15) {
                            OnEmitService.getIns().Sevecie()
                            progress_search_friend!!.visibility = View.GONE
                        }
                    }

                    override fun onFinish() {
                        //Do what you want
                        i++
                        try {
                            progress_search_friend!!.visibility = View.GONE
                        }
                        catch (e: Exception){
                        }
                    }
                }
                mCountDownTimer!!.start()

            }
        }
        //load refresh load lại danh sách user
        swp_refresh!!.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {

                Handler().postDelayed({
                    getUser()
                    swp_refresh!!.setRefreshing(false)
                }, 2000)
            }
        })

        //Chặn scroll của listview
        /*recicleview_list_teacher!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val topRowVerticalPosition = if (recyclerView == null || recyclerView.childCount == 0) 0 else recyclerView.getChildAt(0).top
                swp_refresh!!.setEnabled(topRowVerticalPosition >= 0)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        recicleview_list_teacher!!.visibility = View.VISIBLE*/

        return view
    }

    fun addFriend(id: String) {
        var ival2: Array<String> = arrayOf(LocalData.user.getID().toString(), id)
        call.Call_Service(Value.workername_addfriend, Value.servicename_addfriend, ival2, Value.key_addfriend)
    }

    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        var gson = Gson()

        if (event.getKey() == Value.key_getlistfriendofstudent) {
            if (event.getData()!!.getResult().toString() == "1")//không có dữ liệu
            {
                //recicleview_list_teacher!!.visibility = View.VISIBLE
                tab_no_data_teacher.visibility = View.GONE

                if (event.getData()!!.getResult() == "0")//không có dữ liệu
                {
                    //khoong co data
                } else {
                    listUser.clear()
                    var list = event!!.getData()!!.getData()

                    for (i in 0..list!!.size - 1) {
                        var temp: TeacherofStudent = gson.fromJson(list[i].toString(), TeacherofStudent::class.java)
                        listUser.add(temp)
                    }
                    LocalData.listTeacher = listUser
                    showLayout()
                }
            } else {
                //recicleview_list_teacher!!.visibility = View.GONE
                tab_no_data_teacher.visibility = View.VISIBLE
            }
        }
        if (event.getKey() == Value.key_searchfriend) {
            tab_nodata!!.visibility = View.GONE
            lv_find_user!!.visibility = View.VISIBLE

            progress_search_friend!!.visibility = View.GONE
            if (event.getData()!!.getResult() == "1") {

                var gson = Gson()
                var list = event!!.getData()!!.getData()
                listUser.clear()

                for (i in 0..list!!.size - 1) {
                    var temp: TeacherofStudent = gson.fromJson(list[i].toString(), TeacherofStudent::class.java)
                    listUser.add(temp)
                }
                var adapter = ListUsertoDialogAdapter(context, listUser, this)
                lv_find_user!!.adapter = adapter

            } else {
                var dialog = MaterialDialog.Builder(context)
                        .title("Lỗi")
                        .content("Không tìm thấy tài khoản người dùng")
                        .positiveText("Đồng ý")
                        .show()
            }
        }
        if (event.getKey() == Value.key_addfriend) {

        }
        if (event.getKey() == Value.key_getlistcoursestudentbuck) {
            if (event.getData()!!.getResult() == "1") {

                var listCourse: ArrayList<CourseStudent> = arrayListOf()
                for (i in 0..event.getData()!!.getData()!!.size-1) {
                    var course = gson.fromJson(event!!.getData()!!.getData()!![i].toString(), CourseStudent::class.java)
                    listCourse.add(course)
                }
                var adapter = CourseTeacherDialogAdapter(context, listCourse!!, this)
                recycle_select_account!!.layoutManager = LinearLayoutManager(context)
                recycle_select_account!!.adapter = adapter
            }
        }
    }


    fun showLayout() {
        var adapter = TeacherAdapter(context, listUser)
        lv_list_teacher!!.adapter= adapter
    }


    //lấy danh sách bạn bè
    fun getUser() {
        var inval3: Array<String> = arrayOf(LocalData.user.getID().toString())

        if(LocalData.usertype == 1)
        {
            call.Call_Service(Value.workername_getlistteacher,
                    Value.servicename_getlistteacher,
                    inval3,
                    Value.key_getlistfriendofstudent)
        }
        else {
            call.Call_Service(Value.workername_getlistfriendofstudent,
                    Value.servicename_getlistfriendofstudent,
                    inval3,
                    Value.key_getlistfriendofstudent)
        }
    }

    // Đọc file Json để lấy kết quả
    fun readJson(json1: ArrayList<JSONObject>): TeacherSearch {
        var jsonO: JSONObject? = null
        if (json1.size > 0) {
            jsonO = json1[0]
        }
        var tname: String? = jsonO!!.getString("teachername")
        var temail: String? = jsonO!!.getString("teacheremail")
        var course: String? = jsonO!!.getString("course")
        email = jsonO!!.getString("teacheremail")
        var teacher: TeacherSearch = TeacherSearch()
        teacher.setEmail(temail!!)
        teacher.setTeachername(tname!!)
        teacher.setCourse(course!!)
        return teacher
    }

    fun callgetDetail(id:String)
    {

        dialogshowCourseTeacher = Dialog(context)
        dialogshowCourseTeacher!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogshowCourseTeacher!!.setContentView(R.layout.dialog_list_account_chat)
        var tv_title_dialog = dialogshowCourseTeacher!!.findViewById(R.id.tv_title_dialog) as TextView
        recycle_select_account = dialogshowCourseTeacher!!.findViewById(R.id.recycle_select_account) as RecyclerView
        var tab_cancel_account = dialogshowCourseTeacher!!.findViewById(R.id.tab_cancel_account)

        tab_cancel_account.setOnClickListener()
        {
            dialogshowCourseTeacher!!.cancel()
        }

        tv_title_dialog!!.setText("Danh sách các buổi học")

        var invallistCourse: Array<String> = arrayOf(id)
        call.Call_Service(Value.workername_getlisstcoursestudent,
                Value.servicename_getlisstcoursestudent, invallistCourse,
                Value.key_getlistcoursestudentbuck)
        dialogshowCourseTeacher!!.show()
    }

    override fun setteacher(id: String) {

        dialogshowCourseTeacher = Dialog(context)
        dialogshowCourseTeacher!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogshowCourseTeacher!!.setContentView(R.layout.dialog_list_account_chat)
        var tv_title_dialog = dialogshowCourseTeacher!!.findViewById(R.id.tv_title_dialog) as TextView
        recycle_select_account = dialogshowCourseTeacher!!.findViewById(R.id.recycle_select_account) as RecyclerView
        var tab_cancel_account = dialogshowCourseTeacher!!.findViewById(R.id.tab_cancel_account)

        tab_cancel_account.setOnClickListener()
        {
            dialogshowCourseTeacher!!.cancel()
        }

        tv_title_dialog!!.setText("Danh sách các buổi học")

        var invallistCourse: Array<String> = arrayOf(id)
        call.Call_Service(Value.workername_getlisstcoursestudent,
                Value.servicename_getlisstcoursestudent, invallistCourse,
                Value.key_getlistcoursestudentbuck)
        dialogshowCourseTeacher!!.show()


    }

    override fun gotoDetailCourse() {
        super.gotoDetailCourse()
        var intent = Intent(context, SelectCourse::class.java)
        dialogshowCourseTeacher!!.cancel()
        startActivity(intent)
    }


    override fun calladdFriend(id: String) {
        super.calladdFriend(id)
        addFriend(id)
    }

    override fun calldisaddFriend(id: String) {
        super.calldisaddFriend(id)
        var invalunFriend: Array<String> = arrayOf(LocalData.user.getID().toString(), id)
        call.Call_Service(Value.workername_unfriend, Value.servicename_unfriend, invalunFriend,
                Value.key_call_unfriend)
    }



}
