package com.example.studystudymorestudyforever.fragment.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.studystudymorestudyforever.R
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.example.studystudymorestudyforever.fragment.account.Account
import com.example.studystudymorestudyforever.fragment.chat.Chat
import com.example.studystudymorestudyforever.fragment.notification.Notification

import com.example.studystudymorestudyforever.fragment.teacher.Teacher
import com.example.studystudymorestudyforever.library_bottomnagivation.BottomNavigationViewEx
import android.support.design.widget.BottomNavigationView;
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.encode.Encode
import com.example.studystudymorestudyforever.fragment.schedule.SchedulerCalendar
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.JsonLogin
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.example.studystudymorestudyforever.until.user.User
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject


class MainActivity : AppCompatActivity(),BottomNavigationView.OnNavigationItemSelectedListener {

    var account: Account? = null
    var chat: Chat? = null
    var notificatio: Notification? = null
    var schedule: SchedulerCalendar? = null
    var teacher: Teacher? = null
    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null

    var menu: Menu? = null

    var fragment_main: LinearLayout? = null
    var bottomNavigationView: BottomNavigationViewEx? = null

    var email:String?=""
    var pass:String?=""
    var editor : SharedPreferences.Editor?=null
    var call= OnEmitService.getIns()

    var listUser:ArrayList<User> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)

        if(LocalData.login)
        {
            tab_no_connect.visibility= View.GONE
        }
        //Lấy thông tin cá nhân
        getInforUser()
        //add editor luu trang thai dang nhap
        savingPreferences()

        bottomNavigationView = findViewById(R.id.bottom_navigation_main) as BottomNavigationViewEx
        fragment_main = findViewById(R.id.fragment_main) as LinearLayout
        addFragment()
        createAnimationBottom()
        menu = bottomNavigationView!!.menu
        bottomNavigationView!!.setOnNavigationItemSelectedListener(this)//click button nagivation view
    }

    //Lấy thông tin cá nhân
    fun getInforUser()
    {
        var inval3 :Array<String> = arrayOf(LocalData!!.email)
        call.Call_Service(Value.workername_getuser,Value.servicename_getuser,inval3,Value.key_getuser)
    }

    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {

        if(event.getKey()==Value.key_getuser)//Lấy thông tin cá nhân
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

                tab_no_connect.visibility= View.VISIBLE
            }
        }
        if(event.getKey()== Value.key_loginshape)//Tự động đăng nhập
        {

            var json: ArrayList<JSONObject>? = event.getData()!!.getData()
            var temp=readJson(json!!)

            if (temp.getC0()=="Y")
            {
                Log.d("coveday","ádfasdfasd")
                tab_no_connect.visibility= View.GONE
                getInforUser()
            }
            else {
                if (temp.getC0() == "N") {
                    tab_no_connect.visibility = View.GONE
                    var pre: SharedPreferences = getSharedPreferences("status", Context.MODE_PRIVATE)
                    editor = pre.edit()
                    editor!!.clear()
                    editor!!.commit()
                } else{
                    tab_no_connect.visibility = View.VISIBLE
                }
            }
        }

        if(event.getKey()==Value.disconnect)//Xử lí disconect
        {
            tab_no_connect.visibility= View.VISIBLE

        }
        if(event.getKey()==Value.connect)//Có connect lại
        {
            tab_no_connect.visibility= View.GONE
        }
    }

    fun addFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager!!.beginTransaction()

        account = Account()
        schedule = SchedulerCalendar()
        notificatio = Notification()
        teacher = Teacher()
        chat = Chat()

        fragmentTransaction!!.add(R.id.fragment_main, schedule)
        fragmentTransaction!!.add(R.id.fragment_main, chat)
        fragmentTransaction!!.add(R.id.fragment_main, notificatio)
        fragmentTransaction!!.add(R.id.fragment_main, teacher)
        fragmentTransaction!!.add(R.id.fragment_main, account)


        fragmentTransaction!!.hide(schedule)
        fragmentTransaction!!.hide(chat)
        fragmentTransaction!!.hide(notificatio)
        fragmentTransaction!!.hide(teacher)
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

    //Click button nagivationView
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.hide(schedule)
        fragmentTransaction.hide(chat)
        fragmentTransaction.hide(notificatio)
        fragmentTransaction.hide(teacher)
        fragmentTransaction.hide(account)

        when (item.itemId) {
            R.id.mmenu1 -> {
                setTab(0)
                fragmentTransaction!!.show(schedule)
                getCourse()
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
                fragmentTransaction!!.show(teacher)
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
        var pre:SharedPreferences = getSharedPreferences("status",Context.MODE_PRIVATE)

        editor = pre.edit()
        editor!!.clear()
        editor!!.putString("user",LocalData.email)
        editor!!.putString("pwd",LocalData.pass)
        editor!!.putInt("usertype",LocalData.usertype)
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

    //Lấy lịch học của học sinh
    fun getCourse() {
        var inval: Array<String> = arrayOf(LocalData.user!!.getID()!!.toString())
        call.Call_Service(Value.workername_get_coursestudent,
                Value.servicename_get_coursestudent,
                inval,
                Value.key_getlistcoursestudent)
    }

    //lay danh sach lich su chat
    fun getListMessage()
    {
        var inval2: Array<String> = arrayOf(LocalData.user.getID().toString())
        call.Call_Service(Value.workername_getlistmessage,Value.servicename_getlistmessage,inval2,Value.key_getlistmessage)
    }

    //Lấy danh sách thông báo
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

}
