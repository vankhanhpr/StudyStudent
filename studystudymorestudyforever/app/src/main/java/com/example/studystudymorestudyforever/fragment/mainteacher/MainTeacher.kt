package com.example.studystudymorestudyforever.fragment.mainteacher

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.fragment.account.Account
import com.example.studystudymorestudyforever.fragment.chat.Chat
import com.example.studystudymorestudyforever.fragment.notification.Notification
import com.example.studystudymorestudyforever.fragment.scheduleteacher.ScheduleTeacher
import com.example.studystudymorestudyforever.fragment.student.Student
import com.example.studystudymorestudyforever.library_bottomnagivation.BottomNavigationViewEx
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.datalocal.LocalData

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_teacher)

        call.Sevecie()
        call.ListenEvent()

        //add editor luu trang thai dang nhap
        savingPreferences()

        bottomNavigationView = findViewById(R.id.bottom_navigation_main) as BottomNavigationViewEx
        fragment_main = findViewById(R.id.fragment_main) as LinearLayout


        addFragment()
        createAnimationBottom()
        menu = bottomNavigationView!!.menu
        bottomNavigationView!!.setOnNavigationItemSelectedListener(this)
    }

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
                fragmentTransaction!!.show(schedule)
            }
            R.id.mmenu2 -> {
                fragmentTransaction!!.show(chat)
            }
            R.id.mmenu3 -> {

                fragmentTransaction!!.show(notificatio)
            }
            R.id.mmenu4 -> {
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
        //tạo đối tượng Editor để lưu thay đổi
        editor = pre.edit()
        //lưu vào editor
        editor!!.putString("user", LocalData.email)
        editor!!.putString("pwd", LocalData.pass)
        editor!!.putInt("ustype", LocalData.usertype)
        //chấp nhận lưu xuống file
        editor!!.commit()
    }
}