package com.example.studystudymorestudyforever.fragment.schedule

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

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
    var lv_course_student: ListView? = null
    var listcourse: ArrayList<CourseStudent> = arrayListOf()
    var call = OnEmitService.getIns()
    var listEvent : ArrayList<CourseStudent> = arrayListOf()

    fun initt(view: View) {
        EventBus.getDefault().register(this)
        lv_course_student = view.findViewById(R.id.list_course_student) as ListView
        compactCalendarView = view.findViewById(R.id.compactcalendar_view) as CompactCalendarView
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?  {
        var view: View = inflater!!.inflate(R.layout.shedule_calender_layout, container, false)

        initt(view)
        getCourse()

        compactCalendarView!!.setUseThreeLetterAbbreviation(true)
        compactCalendarView!!.setFirstDayOfWeek(Calendar.MONDAY)

        var x = ConverMiliseconds().converttomiliseconds("02/12/2017")
        var ev1 = Event(Color.GREEN, x, "Some extra data that I want to store.")
        //var s = ConverMiliseconds().converttodate(x).toString()

       /* var st: ArrayList<String> = CalendarDayOfWeek().getDayofWeek("05/11/2017", "07/11/2017", "Thứ 2")
        for (i in 0..st.size - 1) {
            Log.d("dayofweok", st[i])
        }
*/
        var temp1:CourseStudent= CourseStudent()
        temp1.setLOCATION("số 1 Võ Văn Ngân")
        temp1.setSTART_TIME("05/11/2017")
        temp1.setEND_TIME("07/12/2017")
        temp1.setTHU("Thứ 2")
        listcourse.add(temp1)
        //listcourse.add(temp1)
        addEventtoCalendar()

        // Log.d("kiemtra", "ffff" +date+ " "+ tem)
        compactCalendarView!!.addEvent(ev1)

        // Added event 2 GMT: Sun, 07 Jun 2015 19:10:51 GMT
        //var ev2 = Event(Color.GREEN, 1433704251000L)
        var events: List<Event> = compactCalendarView!!.getEvents(x) // can also take a Date object

        compactCalendarView!!.setListener(object : CompactCalendarView.CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                listEvent.clear()
                tv_time.setText("" + dateClicked)
                var sdf: SimpleDateFormat =  SimpleDateFormat("dd/MM/yyyy")
                var mDate: String? = sdf!!.format(dateClicked)
                var mDaymili= ConverMiliseconds().converttomiliseconds(mDate.toString())

                var events: List<Event> = compactCalendarView!!.getEvents(mDaymili)
                for(k in 0..events.size-1)
                {

                    var x1:CourseStudent = CourseStudent()
                    x1.setLOCATION(events[k].data.toString())
                    listEvent.add(x1)
                }
                setAdapter(listEvent)
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {

                //Log.d("ádfsadf", "Month was scrolled to: " + events)
            }
        })

        return view
    }


    fun getCourse() {
        var inval: Array<String> = arrayOf(LocalData.user!!.getID()!!.toString())
        call.Call_Service(Value.workername_get_coursestudent, Value.servicename_get_coursestudent, inval, Value.key_getlistcoursestudent)
    }

    fun setAdapter(list:ArrayList<CourseStudent>) {
        var adapter = ListCourseAdapter(context, list)
        lv_course_student!!.adapter = adapter
    }

    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getKey() == Value.key_getlistcoursestudent)
        {
            if(event.getData()!!.getResult()=="1")
            {
                val gson = Gson()
                for(i in 0..event.getData()!!.getData()!!.size-1)
                {
                    var tem :CourseStudent= gson.fromJson(event.getData()!!.getData()!![i].toString(),CourseStudent::class.java)
                    listcourse!!.add(tem)
                }
                addEventtoCalendar()
            }
        }
    }

    fun addEventtoCalendar()
    {
        for(i in 0..listcourse.size-1)
        {
            var st: ArrayList<String> = CalendarDayOfWeek().getDayofWeek(listcourse[i].getSTART_TIME(),
                    listcourse[i].getEND_TIME(),listcourse[i].getTHU().toString())

            for(j in 0..st.size-1)
            {
                Log.d("kiemtra4",""+st[j])
                var x = ConverMiliseconds().converttomiliseconds(st[j])
                var event = Event(Color.GREEN, x,listcourse[i].getLOCATION().toString())
                compactCalendarView!!.addEvent(event)
            }

        }
    }


    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }
    fun getMondaysOfJanuary()
    {
        var cal = Calendar.getInstance()
        cal.set(Calendar.MONTH, Calendar.NOVEMBER)
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        cal.set(Calendar.DAY_OF_WEEK_IN_MONTH,1)
        val month = cal.get(Calendar.MONTH)
        var output = ""
        while (cal.get(Calendar.MONTH) == month) {
            output += cal.get(Calendar.DAY_OF_MONTH).toString() + "/" + (cal.get(Calendar.MONTH) + 1) +"/"+ (cal.get(Calendar.YEAR))+","
            cal.add(Calendar.DAY_OF_MONTH, 7)
        }
       // Log.d("dayofweek",""+output.substring(0, output.length-1))
    }
}