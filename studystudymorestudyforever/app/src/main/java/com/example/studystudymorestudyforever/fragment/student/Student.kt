package com.example.studystudymorestudyforever.fragment.student

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.afollestad.materialdialogs.MaterialDialog
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.student.ListFileStudentDialogAdapter
import com.example.studystudymorestudyforever.adapter.adapter.student.StudentAdapter
import com.example.studystudymorestudyforever.adapter.adapter.teacher.ListUsertoDialogAdapter
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.myinterface.ICallAddFriend
import com.example.studystudymorestudyforever.myinterface.ISetTeacher
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.example.studystudymorestudyforever.until.student.StudentofTeacher
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by VANKHANHPR on 10/8/2017.
 */

class Student: Fragment(), View.OnClickListener, ICallAddFriend, ISetTeacher {



    var call= OnEmitService.getIns()

    var recicleview_list_teacher:RecyclerView?= null
    var tab_add_student:LinearLayout?=null
    var dialog_add_student:Dialog?= null

    var tab_search: LinearLayout? = null
    var edt_search_friend: EditText? = null
    var progress_search_friend: ProgressBar? = null
    var mCountDownTimer: CountDownTimer? = null
    var tab_nodata: LinearLayout? = null
    var lv_find_user: ListView? = null
    var listUser: ArrayList<StudentofTeacher> = arrayListOf()



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view: View = inflater!!.inflate(R.layout.main_teacher_fragment_student, container, false)
        initview(view)
        tab_add_student!!.setOnClickListener(this)
        getUser()
        /*var user:StudentofTeacher= StudentofTeacher()
        user.setADDRESS("asfasdfsdf")
        listUser.add(user)
        listUser.add(user)
        listUser.add(user)
        showLayout()*/
        return  view
    }

    fun initview(view :View)
    {
        EventBus.getDefault().register(this)

        recicleview_list_teacher= view.findViewById(R.id.recicleview_list_teacher) as RecyclerView
        tab_add_student= view.findViewById(R.id.tab_add_student) as LinearLayout

    }
    override fun onClick(v: View?) {

        when (v!!.id)
        {
            R.id.tab_add_student ->{
                dialog_add_student = Dialog(context)
                dialog_add_student!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog_add_student!!.setContentView(R.layout.dialog_add_teacher_layout)
                tab_search = dialog_add_student!!.findViewById(R.id.tab_search) as LinearLayout
                edt_search_friend = dialog_add_student!!.findViewById(R.id.edt_search_friend) as EditText
                progress_search_friend = dialog_add_student!!.findViewById(R.id.progress_search_friend) as ProgressBar
                lv_find_user = dialog_add_student!!.findViewById(R.id.lv_find_user) as ListView
                tab_nodata = dialog_add_student!!.findViewById(R.id.tab_no_data) as LinearLayout


                var tv_add_student:TextView= dialog_add_student!!.findViewById(R.id.tv_add_student) as TextView

                tv_add_student.setText(R.string.add_student_parent);

                var tab_cancel = dialog_add_student!!.findViewById(R.id.tab_cancel)

                tab_cancel!!.setOnClickListener() //cancel dialog
                {
                    dialog_add_student!!.cancel()
                }

                dialog_add_student!!.show()

                tab_search!!.setOnClickListener()
                {
                    progress_search_friend!!.visibility = View.VISIBLE

                    var emailsearch = edt_search_friend!!.text.toString().trim()
                    var invalsearch: Array<String> = arrayOf(emailsearch, LocalData.user.getID().toString())

                    if(LocalData.usertype == 2 ) {//là giáo viên
                        call.Call_Service(Value.workername_searchfriend,
                                Value.servicename_searchstudent,
                                invalsearch, Value.key_searchfriend)
                    }
                    else
                    {//là phụ huuynh và học sinh
                        call.Call_Service(Value.workername_searchfriend,
                            Value.servicename_searchfriend,
                            invalsearch, Value.key_searchfriend)

                    }

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
        }
    }

    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        var gson = Gson()


        if (event.getKey() == Value.key_searchfriend) {
            tab_nodata!!.visibility = View.GONE
            lv_find_user!!.visibility = View.VISIBLE

            progress_search_friend!!.visibility = View.GONE
            if (event.getData()!!.getResult() == "1") {

                var gson = Gson()
                var list = event!!.getData()!!.getData()
                listUser.clear()

                for (i in 0..list!!.size - 1) {
                    var temp: StudentofTeacher = gson.fromJson(list[i].toString(), StudentofTeacher::class.java)
                    listUser.add(temp)
                }
                var adapter = ListFileStudentDialogAdapter(context, listUser, this)
                lv_find_user!!.adapter = adapter

            } else {
                var dialog = MaterialDialog.Builder(context)
                        .title("Lỗi")
                        .content("Không tìm thấy tài khoản người dùng")
                        .positiveText("Đồng ý")
                        .show()
            }
        }

        if (event.getKey() == Value.key_getlist_teacher) {
            if (event.getData()!!.getResult().toString() == "1")//không có dữ liệu
            {
                //recicleview_list_teacher!!.visibility = View.VISIBLE
                //tab_no_data_teacher.visibility = View.GONE

                if (event.getData()!!.getResult() == "0")//không có dữ liệu
                {
                    //khoong co data
                } else {
                    listUser.clear()
                    var list = event!!.getData()!!.getData()

                    for (i in 0..list!!.size - 1) {
                        var temp: StudentofTeacher = gson.fromJson(list[i].toString(), StudentofTeacher::class.java)
                        listUser.add(temp)
                    }
                    showLayout()
                }
            }
            else {
                //recicleview_list_teacher!!.visibility = View.GONE
                //tab_no_data_teacher.visibility = View.VISIBLE
            }
        }

    }

    //Hiển thị lên recycle
    fun showLayout() {
        var adapter = StudentAdapter(context, listUser,this)
        recicleview_list_teacher!!.layoutManager= LinearLayoutManager(context)
        recicleview_list_teacher!!.adapter= adapter
    }


    override fun calladdFriend(id: String) {
        super.calladdFriend(id)
        addFriend(id)
    }

    //Hủy kết bạn
    override fun calldisaddFriend(id: String) {
        super.calldisaddFriend(id)
        var invalunFriend: Array<String> = arrayOf(LocalData.user.getID().toString(), id)
        call.Call_Service(Value.workername_unfriend, Value.servicename_unfriend, invalunFriend,
                Value.key_call_unfriend)
    }

    //Kết bạn
    fun addFriend(id: String) {
        var ival2: Array<String> = arrayOf(LocalData.user.getID().toString(), id)
        call.Call_Service(Value.workername_addfriend, Value.servicename_addfriend, ival2, Value.key_addfriend)
    }

    //lấy danh sách bạn bè
    fun getUser() {
        var inval3: Array<String> = arrayOf(LocalData.user.getID().toString())
        call.Call_Service(Value.workername_getlist_teacher, Value.servicename_getlist_teacher,
                inval3, Value.key_getlist_teacher)
    }

    override fun setteacher(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
