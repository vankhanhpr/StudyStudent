package com.example.studystudymorestudyforever.fragment.mainteacher

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.encode.Encode
import com.example.studystudymorestudyforever.fragment.account.Account
import com.example.studystudymorestudyforever.fragment.chat.Chat
import com.example.studystudymorestudyforever.fragment.notification.Notification
import com.example.studystudymorestudyforever.fragment.scheduleteacher.ScheduleTeacher
import com.example.studystudymorestudyforever.fragment.student.Student
import com.example.studystudymorestudyforever.library_bottomnagivation.BottomNavigationViewEx
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.JsonLogin
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.example.studystudymorestudyforever.until.user.User
import com.example.studystudymorestudyforever.welcom.SelectAccount
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main_teacher.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * Created by VANKHANHPR on 10/8/2017.
 */
class MainTeacher :AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{

    var call= OnEmitService.getIns()
    var account: Account? = null
    var chat: Chat? = null
    var notificatio: Notification? = null
    var schedule: ScheduleTeacher? = null
    var student: Student? = null
    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null

    var menu: Menu? = null

    var fragment_main: LinearLayout? = null
    var bottomNavigationView: BottomNavigationViewEx? = null

    var email:String?=""
    var pass:String?=""
    var editor : SharedPreferences.Editor?=null

    var listUser:ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_teacher)
        EventBus.getDefault().register(this)


        if(LocalData.login)
        {
            tab_no_connect.visibility= View.GONE
        }
        else
        {
            tab_no_connect.visibility= View.VISIBLE
        }

        getInfoUser()

        //add editor luu trang thai dang nhap
        savingPreferences()

        bottomNavigationView = findViewById(R.id.bottom_navigation_main) as BottomNavigationViewEx
        fragment_main = findViewById(R.id.fragment_main) as LinearLayout
        addFragment()
        createAnimationBottom()
        menu = bottomNavigationView!!.menu
        bottomNavigationView!!.setOnNavigationItemSelectedListener(this)
    }


    //Lây thông tin cá nhân
    fun getInfoUser()
    {
        var inval3 :Array<String> = arrayOf(LocalData!!.email)
        call.Call_Service(Value.workername_getuser, Value.servicename_getuser,inval3, Value.key_getuser)
    }

    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {

        if(event.getKey()==Value.key_getuser)//Lấy thông tin tài khoản
        {
            if(event.getData()!!.getResult().toString()=="1")//không có dữ liệu
            {
                var gson= Gson()
                var list= event!!.getData()!!.getData()

                for(i in 0..list!!.size-1)
                {
                    var temp: User = gson.fromJson(list[i].toString(), User::class.java)
                    listUser.add(temp)
                }
                LocalData.user= listUser[0]
            }
            else{

            }
        }
        if(event.getKey()== Value.key_loginshape)//Tự động đăng nhập
        {
            var json: ArrayList<JSONObject>? = event.getData()!!.getData()
            var temp=readJson(json!!)

            if (temp.getC0()=="Y")
            {
                tab_no_connect.visibility= View.GONE
            }
            else
            {
                if (temp.getC0() == "N")
                {
                    tab_no_connect.visibility = View.GONE
                    var pre: SharedPreferences = getSharedPreferences("status", Context.MODE_PRIVATE)
                    editor = pre.edit()
                    editor!!.clear()
                    editor!!.commit()
                    var intenlogout= Intent(applicationContext,SelectAccount::class.java)
                    startActivity(intenlogout)
                    finish()

                } else
                {
                    tab_no_connect.visibility = View.VISIBLE
                }
            }
        }

        if(event.getKey()==Value.disconnect)//Xử lí disconnect
        {
            tab_no_connect.visibility= View.VISIBLE
        }
        if(event.getKey()==Value.connect)//Khi có connect lại
        {
            tab_no_connect.visibility= View.GONE
        }
    }
   /* override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
        call.removeListener()
    }*/
    fun addFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager!!.beginTransaction()

        account = Account()
        schedule = ScheduleTeacher()
        notificatio = Notification()
        student = Student()
        chat = Chat()

        fragmentTransaction!!.add(R.id.fragment_main, schedule)
        fragmentTransaction!!.add(R.id.fragment_main, chat)
        fragmentTransaction!!.add(R.id.fragment_main, notificatio)
        fragmentTransaction!!.add(R.id.fragment_main, student)
        fragmentTransaction!!.add(R.id.fragment_main, account)


        fragmentTransaction!!.hide(schedule)
        fragmentTransaction!!.hide(chat)
        fragmentTransaction!!.hide(notificatio)
        fragmentTransaction!!.hide(student)
        fragmentTransaction!!.hide(account)

        fragmentTransaction!!.show(schedule)
        fragmentTransaction!!.commit()
    }

    fun createAnimationBottom() {
        bottomNavigationView!!.setTextVisibility(true)     //Ẩn chữ
        bottomNavigationView!!.enableAnimation(true)       //Ẩn đi hiệu ứng chuyện tab
        bottomNavigationView!!.enableShiftingMode(false)     //Ẩn đi hiệu hứng chuyển tab
        bottomNavigationView!!.enableItemShiftingMode(false)
        bottomNavigationView!!.setIconVisibility(true)

        bottomNavigationView!!.setIconTintList(0, null)
        bottomNavigationView!!.setIconTintList(1, null)
        bottomNavigationView!!.setIconTintList(2, null)
        bottomNavigationView!!.setIconTintList(3, null)
        bottomNavigationView!!.setIconTintList(4, null)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.hide(schedule)
        fragmentTransaction.hide(chat)
        fragmentTransaction.hide(notificatio)
        fragmentTransaction.hide(student)
        fragmentTransaction.hide(account)

        when (item.itemId) {
            R.id.mmenu1 -> {
                setTab(0)
                callGetListCourse()
                fragmentTransaction!!.show(schedule)
            }
            R.id.mmenu2 -> {
                fragmentTransaction!!.show(chat)
                getListMessage()
            }
            R.id.mmenu3 -> {

                getNotif()
                fragmentTransaction!!.show(notificatio)
            }
            R.id.mmenu4 -> {
                getUser()
                fragmentTransaction!!.show(student)
            }
            R.id.mmenu5 -> {
                fragmentTransaction!!.show(account)
            }
        }
        true

        fragmentTransaction!!.commitAllowingStateLoss()
        return true
    }

    fun setTab(currentTabindex: Int) {

        when (currentTabindex) {
            0 -> {
                menu!!.getItem(0).setIcon(R.drawable.icon_schedule)
                menu!!.getItem(1).setIcon(R.drawable.icon_chat)
                menu!!.getItem(2).setIcon(R.drawable.icon_notification)
                menu!!.getItem(3).setIcon(R.drawable.icon_teacher)
                menu!!.getItem(4).setIcon(R.drawable.icon_account)
            }
        }
    }

    //ham luu trang thai dang nhap
    fun savingPreferences()
    {
        //tạo đối tượng getSharedPreferences
        var pre: SharedPreferences = getSharedPreferences("status", Context.MODE_PRIVATE)
        editor = pre.edit()

        var encode= Encode()
        editor!!.putString("user",LocalData.email)
        editor!!.putString("pwd",LocalData.pass)
        editor!!.putInt("usertype", LocalData.usertype)
        editor!!.commit()
    }

    // Đọc file Json để lấy kết quả
    fun readJson(json1: ArrayList<JSONObject>): JsonLogin
    {
        var jsonO: JSONObject?=null
        if(json1.size>0)
        {
            jsonO = json1[0]
        }
        var c0: String? =jsonO!!.getString("c0")
        var ser1 : JsonLogin = JsonLogin()
        ser1.setC0(c0!!)
        return ser1
    }


    //lấy danh sách thông báo
    fun getNotif()
    {
        if(LocalData.user.getID()!=null) {

            var inval: Array<String> = arrayOf(LocalData.user.getID().toString())
            call.Call_Service(Value.workername_getlistnotif, Value.servicename_getlistnotif, inval, Value.key_getlistnotif)
        }
    }

    //lấy danh sách bạn bè
    fun getUser() {
        var inval3: Array<String> = arrayOf(LocalData.user.getID().toString())
        call.Call_Service(Value.workername_getlist_teacher, Value.servicename_getlist_teacher,
                inval3, Value.key_getlist_teacher)
    }

    //lay danh sach lich su chat
    fun getListMessage()
    {
        var inval2: Array<String> = arrayOf(LocalData.user.getID().toString())
        call.Call_Service(Value.workername_getlistmessage,Value.servicename_getlistmessage,inval2,Value.key_getlistmessage)
    }

    //goi service lay danh sach lop hoc
    fun callGetListCourse()
    {
        var inval : Array<String> = arrayOf(LocalData.user.getID().toString())

        call.Call_Service(Value.workername_getlisttechershedule,
                Value.servicename_getlistteachershedule,
                inval!!,
                Value.key_getlistteachershedule)
    }
}