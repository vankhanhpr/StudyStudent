package com.example.studystudymorestudyforever.fragment.schedule

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AbsListView
import android.widget.ListView
import com.baoyz.swipemenulistview.SwipeMenu
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView

import com.example.myapplication.value.MessageEvent

import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.schedule.ListCourseAdapter
import com.example.studystudymorestudyforever.convert.milisecond.ConverMiliseconds
import com.example.studystudymorestudyforever.model.CalendarDayOfWeek
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.course.CourseStudent
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.github.sundeepk.compactcalendarview.domain.Event
import com.google.gson.Gson
import kotlinx.android.synthetic.main.shedule_calender_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList




/**
 * Created by VANKHANHPR on 10/6/2017.
 */
class SchedulerCalendar : Fragment() {

    var compactCalendarView: CompactCalendarView? = null
    var lv_course_student: SwipeMenuListView? = null
    var listcourse: ArrayList<CourseStudent> = arrayListOf()
    var call = OnEmitService.getIns()
    var listEvent : ArrayList<CourseStudent> = arrayListOf()
    var sw_refresh:SwipeRefreshLayout? =null


    fun initt(view: View) {
        EventBus.getDefault().register(this)
        lv_course_student = view.findViewById(R.id.list_course_student) as SwipeMenuListView
        compactCalendarView = view.findViewById(R.id.compactcalendar_view) as CompactCalendarView
        sw_refresh = view.findViewById(R.id.sw_refresh) as SwipeRefreshLayout
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?  {
        var view: View = inflater!!.inflate(R.layout.shedule_calender_layout, container, false)

        initt(view)

        compactCalendarView!!.setUseThreeLetterAbbreviation(true)
        compactCalendarView!!.setFirstDayOfWeek(Calendar.MONDAY)

        var x = ConverMiliseconds().converttomiliseconds("02/12/2017")
        //load refresh
        sw_refresh!!.setOnRefreshListener(object :SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {

                Handler().postDelayed({
                    call.Sevecie()
                    getCourse()
                    sw_refresh!!.setRefreshing(false)
                }, 3000)
            }
        })


        getCourse()

        compactCalendarView!!.setListener(object : CompactCalendarView.CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date)
            {
                setViewListSchedule(dateClicked)
            }
            override fun onMonthScroll(firstDayOfNewMonth: Date) {

                var sdf: SimpleDateFormat =  SimpleDateFormat("dd/MM/yyyy")
                var sdfmoth:SimpleDateFormat= SimpleDateFormat("MM/yyyy")
                var mDate: String? = sdf!!.format(firstDayOfNewMonth)
                var month= sdfmoth!!.format(firstDayOfNewMonth)

                tv_time.setText("" + mDate)
                tv_month.setText("Tháng "+month)
            }
        })
        return view
    }


    //Hiển thị các lớp học lên lịch
    fun setViewListSchedule(dateClick:Date)
    {
        listEvent.clear()

        var sdf: SimpleDateFormat =  SimpleDateFormat("dd/MM/yyyy")
        var sdfmoth:SimpleDateFormat= SimpleDateFormat("MM/yyyy")
        var mDate: String? = sdf!!.format(dateClick)
        var month= sdfmoth!!.format(dateClick)

        tv_time.setText("" + mDate)
        tv_month.setText("Tháng " + month)

        var mDaymili= ConverMiliseconds().converttomiliseconds(mDate.toString())
        var events: List<Event> = compactCalendarView!!.getEvents(mDaymili)
        for(k in 0..events.size-1)
        {
            var eventt:CourseStudent = CourseStudent()
            var arr= events[k].data.toString().split("/")
            eventt.setSUB_NAME(arr[0])
            eventt.setTIME(arr[1])
            listEvent.add(eventt)
        }
        setAdapter(listEvent)
    }

    //Lấy danh sách lớp học
    fun getCourse() {
        var inval: Array<String> = arrayOf(LocalData.user!!.getID()!!.toString())
        call.Call_Service(Value.workername_get_coursestudent,
                Value.servicename_get_coursestudent,
                inval,
                Value.key_getlistcoursestudent)
    }

    fun setAdapter(list:ArrayList<CourseStudent>) {

        var adapter = ListCourseAdapter(context, list)
        lv_course_student!!.adapter = adapter

        var creator = SwipeMenuCreator { menu ->
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
        }
        lv_course_student!!.setMenuCreator(creator)//set tab khi vuốt ngang

        lv_course_student!!.setOnMenuItemClickListener(object : SwipeMenuListView.OnMenuItemClickListener{
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onMenuItemClick(position: Int, menu: SwipeMenu, index: Int): Boolean {

                when (index) {
                    0 -> {
                        var dialog_cancel_schedule = Dialog(context)

                        dialog_cancel_schedule!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog_cancel_schedule!!.setContentView(R.layout.dialog_cancel_course)
                        var btn_cancel_dialogres=  dialog_cancel_schedule.findViewById(R.id.btn_cancel_dialogres)
                        var btn_agree_dialogres= dialog_cancel_schedule.findViewById(R.id.btn_agree_dialogres)
                        dialog_cancel_schedule.show()
                        btn_cancel_dialogres.setOnClickListener()
                        {
                            dialog_cancel_schedule.cancel()
                        }
                        btn_agree_dialogres.setOnClickListener()
                        {
                            var inval : Array<String> = arrayOf(LocalData.user.getID().toString(),listcourse[position].getSCHE_ID())
                            call.Call_Service(Value.workername_cancelcourse,
                                    Value.servicename_cancelcourse,inval,Value.key_cancelcourse)
                            dialog_cancel_schedule.cancel()
                        }

                    }
                }
                return false
            }
        })

        //Chặn scroll của refresh khi scroll listview
        lv_course_student!!.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {

            }
            override fun onScroll(listView: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                val topRowVerticalPosition = if (listView == null || listView.childCount === 0)
                    0
                else
                    lv_course_student!!.getChildAt(0).getTop()
                sw_refresh!!.setEnabled(topRowVerticalPosition >= 0)
            }
        })


    }

    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getKey() == Value.key_getlistcoursestudent)
        {
            if(event.getData()!!.getResult()=="1")
            {
                val gson = Gson()
                listcourse.clear()
                for(i in 0..event.getData()!!.getData()!!.size-1)
                {
                    var tem2 :CourseStudent= gson.fromJson(event.getData()!!.getData()!![i].toString(),CourseStudent::class.java)
                    tem2.setSTART_TIME(ConverMiliseconds().converttodate(tem2!!.getSTART_TIME()!!.toLong()))
                    tem2.setEND_TIME(ConverMiliseconds().converttodate(tem2!!.getEND_TIME()!!.toLong()))
                    listcourse!!.add(tem2)
                }
                addEventtoCalendar()
                setViewListSchedule(Calendar.getInstance().time)
            }
        }
        if(event.getKey() == Value.key_cancelcourse)
        {
            if(event.getData()!!.getResult()=="1")
            {
                getCourse()
            }
        }
    }



    fun addEventtoCalendar()
    {
        compactCalendarView!!.removeAllEvents()
        try {
            for (i in 0..listcourse.size - 1)
            {
                var st: ArrayList<String> = CalendarDayOfWeek().getDayofWeek(listcourse[i].getSTART_TIME(),
                        listcourse[i].getEND_TIME(), listcourse[i].getTHU().toString())

                for (j in 0..st.size - 1)
                {
                    var x = ConverMiliseconds().converttomiliseconds(st[j])
                    var event = Event(Color.GREEN, x, listcourse[i].getSUB_NAME().toString()+"/"+listcourse[i].getTIME().toString())
                    compactCalendarView!!.addEvent(event)
                }
            }
        }
        catch (e:Exception){}
    }

}