package com.example.studystudymorestudyforever.fragment.scheduleteacher


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.schedule.ScheduleTeachingAdapter
import com.example.studystudymorestudyforever.fragment.scheduleteacher.addcourse.AddCourse
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.myinterface.ISelectCourse
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.course.ScheduleAdd
import com.example.studystudymorestudyforever.until.course.TeacherSchedule
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.main_teacher_fagment_shedule.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by VANKHANHPR on 10/8/2017.
 */
class ScheduleTeacher : Fragment(),ISelectCourse, View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {



    var call= OnEmitService.getIns()
    var recycle_list_teaching:RecyclerView?=null
    var tab_add_course: LinearLayout?=null
    var listcourse :ArrayList<ScheduleAdd> = arrayListOf()
    var srlLayout:SwipeRefreshLayout ?= null




    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view: View = inflater!!.inflate(R.layout.main_teacher_fagment_shedule, container, false)

        initt(view)
        tab_add_course!!.setOnClickListener(this)
        srlLayout!!.setOnRefreshListener(this)

        callGetListCourse()
        //load refresh
        srlLayout!!.setOnRefreshListener(this)
        return view
    }

    //Khởi tạo các giá trị
    fun initt(view:View)
    {
        tab_add_course= view.findViewById(R.id.tab_add_course) as LinearLayout
        recycle_list_teaching= view.findViewById(R.id.recycle_list_teaching) as RecyclerView
        srlLayout =  view.findViewById(R.id.srlLayout) as SwipeRefreshLayout
        EventBus.getDefault().register(this)
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

    //Đổ các lớp học đã tạo vào trong recycleview
    fun showlistSchedule()
    {
        var adapter= ScheduleTeachingAdapter(context,listcourse,this)
        recycle_list_teaching!!.setLayoutManager(LinearLayoutManager(context))
        recycle_list_teaching!!.adapter= adapter
    }

    override fun onClick(v: View?) {

        when(v!!.id)
        {
            R.id.tab_add_course ->
            {
                var inten= Intent(context,AddCourse::class.java)
                startActivity(inten)
            }
        }
    }
    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getKey() == Value.key_addcourse)
        {
            callGetListCourse()
        }
        if (event.getKey()== Value.key_getlistteachershedule)
        {
            listcourse.clear()

            if(event.getData()!!.getResult().toString()=="1")
            {
                tab_nodata_main.visibility= View.GONE
                recycle_list_teaching!!.visibility= View.VISIBLE

                for(i in 0..event.getData()!!.getData()!!.size-1)
                {
                    try{
                        Log.d("day la dau","size"+event.getData()!!.getData()!![0].toString())
                        var tempcourse: ScheduleAdd = Gson().fromJson(event.getData()!!.getData()!![i].toString(), ScheduleAdd::class.java)
                        listcourse.add(tempcourse)
                    }
                    catch (e:Exception){
                        Log.e("myerr",e.printStackTrace().toString())
                    }
                }
                showlistSchedule()
            }
            else
            {
                tab_nodata_main.visibility= View.VISIBLE
                recycle_list_teaching!!.visibility= View.GONE
            }

        }
    }

   /* override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }*/

    //Bắt sự kiện khi click vào các item trong recycleview
    override fun selectCourse() {
        super.selectCourse()
        var inten= Intent(context, ScheduleTeacherUpdateCourse::class.java)
        startActivity(inten)
    }

    //load lai trang
    override fun onRefresh() {

        callGetListCourse()
        call.Sevecie()
        var secondDelay: Long? = 5
        Handler().postDelayed({
            srlLayout!!.setRefreshing(false)
        }, 2000)
    }
}

