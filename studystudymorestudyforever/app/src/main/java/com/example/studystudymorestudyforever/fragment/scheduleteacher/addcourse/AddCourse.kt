package com.example.studystudymorestudyforever.fragment.scheduleteacher.addcourse

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.studystudymorestudyforever.R
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.teacher_add_course.*

import java.text.SimpleDateFormat
import java.util.*
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.teacher_addview_row_bucking_schedule.view.*
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.convert.milisecond.ConverMiliseconds
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.myinterface.ISetTimeDialog
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.dialog_login.*
import kotlinx.android.synthetic.main.teacher_addview_row_bucking_schedule.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONArray
import org.json.JSONObject
import kotlin.collections.ArrayList
import java.util.Arrays.asList




/**
 * Created by VANKHANHPR on 10/21/2017.
 */
class AddCourse:AppCompatActivity(),View.OnClickListener,ISetTimeDialog{



    var call =OnEmitService.getIns()
    var imv_add_course:ImageView?=null
    private  var parentLinearLayout: LinearLayout? = null
    var tab_remove_view:LinearLayout?=null
    var tv_course_starttime :TextView?=null
    var tv_course_endtime:TextView?=null
    var tv_course_time_1:TextView?=null
    var psn_select_dayofweek:Spinner?=null
    var dayweek:Array<String> = arrayOf("Thứ 2","Thứ 3","Thứ 4","Thứ 5","Thứ 6","Thứ 7","Chủ Nhật")
    var indexOfView=0;


    var listDayofWeek :ArrayList<String> ? = arrayListOf()
    var listTime : ArrayList<String> ? = arrayListOf()

    var cal: Calendar? = null

    var listnNumberView : ArrayList<TextView> ?= arrayListOf()

    var listViewHolder :ArrayList<ViewHolder> ? = arrayListOf()

    var temp_spiner=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_add_course)

        call.Sevecie()
        call.ListenEvent()

        initt()

        getDefaultInfor()

        imv_add_course!!.setOnClickListener(this)
        imv_set_time_start!!.setOnClickListener(this)
        imv_set_endtime.setOnClickListener(this)
        tab_ste_time1.setOnClickListener(this)
        tab_backaddcourse.setOnClickListener(this)

        setSpinner()
        

        if(parentLinearLayout!!.getChildAt(0)!=null) {
            Toast.makeText(applicationContext,"afasdfasdfas ",Toast.LENGTH_SHORT).show()


            var tv: TextView = parentLinearLayout!!.getChildAt(0).findViewById(R.id.tv_course_time_child_view) as TextView

            var tab_set_time_row = parentLinearLayout!!.tab_set_time_row


            tab_set_time_row!!.setOnClickListener()
            {
                Toast.makeText(applicationContext,"afasdfasdfas ",Toast.LENGTH_SHORT).show()
            }
        }
        tab_add_course.setOnClickListener(this)

        psn_select_dayofweek!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, i: Int, l: Long)
            {
               temp_spiner=psn_select_dayofweek!!.getSelectedItem().toString()
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        })

    }


    //Đổ dữ liệu vào trong combobox
    fun setSpinner()//set buổi học bằng combobox
    {
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,dayweek)
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
        psn_select_dayofweek!!.setAdapter(adapter)
    }

    //khởi tạo các giá trị
    fun initt()
    {
        EventBus.getDefault().register(this)
        imv_add_course=findViewById(R.id.imv_add_course) as ImageView
        tv_course_starttime=findViewById(R.id.tv_course_starttime) as TextView
        tv_course_endtime= findViewById(R.id.tv_course_endtime) as TextView
        tv_course_time_1=findViewById(R.id.tv_course_time_1) as TextView
        psn_select_dayofweek= findViewById(R.id.psn_select_dayofweek) as Spinner

        parentLinearLayout =  findViewById(R.id.parent_linear_layout) as LinearLayout

        psn_select_dayofweek!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, i: Int, l: Long)
            {
            Toast.makeText(applicationContext, psn_select_dayofweek!!.getSelectedItem().toString(), Toast.LENGTH_SHORT).show()
        }
            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        })

    }

    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getKey() == Value.key_addcourse)
        {
            if(event.getData()!!.getResult()=="1")
            {
                 var matrix = MaterialDialog.Builder(this)
                 .title("Thêm lớp học")
                 .content("Thêm lớp học thành công!")
                 .positiveText("Thoát")
                 .show()


            }
            if(event.getData()!!.getResult()=="0")
            {
                MaterialDialog.Builder(this)
                        .title("Lỗi")
                        .content("Thêm lớp học không thành công")
                        .positiveText("OK")
                        .show()
            }
        }
    }
    public override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    /**
     * Các sự kiện onClick
     */
    override fun onClick(v: View?) {
        when(v!!.id)
        {
            //Thêm buổi của môn học
            R.id.imv_add_course ->
            {

                var inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                var rowView = inflater.inflate(R.layout.teacher_addview_row_bucking_schedule, null)

                var viewholder :ViewHolder = ViewHolder(rowView,this)
                listViewHolder!!.add(viewholder)
                parentLinearLayout!!.addView(rowView )
                resetTag()

                indexOfView++;


                var textview= rowView.findViewById(R.id.tv_course_time_child_view) as TextView
                var spinerview= rowView.findViewById(R.id.sp_setdayofweek) as Spinner

                var adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item,dayweek)
                adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
                spinerview!!.setAdapter(adapter1)


                var tv_countview= rowView.findViewById(R.id.tv_count_view) as TextView

                getDefaultInforView(textview!!)


                listnNumberView!!.add(tv_countview)

                updatedayofweek()
            }
            R.id.imv_set_time_start ->
            {
                showDatePickerDialog()
            }
            R.id.imv_set_endtime ->
            {
                showDatePickerDialog2()
            }
            R.id.tab_ste_time1 ->
            {
                showTimePickerDialog(tv_course_time_1!!)
            }
            R.id.tab_backaddcourse->
            {
                finish()
            }

            R.id.tab_add_course-> {


                listTime!!.clear()
                listDayofWeek!!.clear()
                listDayofWeek!!.add(temp_spiner!!)

               // var ls= ConverMiliseconds().converttomilisecondsTime(tv_course_time_1!!.text.toString())
                listTime!!.add(tv_course_time_1!!.text.toString())

                for(i in 0..listViewHolder!!.size-1) {

                    listDayofWeek!!.add(listViewHolder!![i].getSpiner())
                    listTime!!.add((listViewHolder!![i].getTime()))
                }

                var daystart= ConverMiliseconds().converttomiliseconds(tv_course_starttime!!.text.toString())
                var dayend= ConverMiliseconds().converttomiliseconds(tv_course_endtime!!.text.toString())

                var gson= Gson()
                var listdayofw= gson.toJson(listDayofWeek)
                var listtimest= gson.toJson(listTime)

                var address= edt_course_address.text.toString()
                var tuitionmoney = edt_course_tuition.text.toString()
                if(tuitionmoney == "")
                {
                    tuitionmoney= "0"
                }

                var inval: Array<String> = arrayOf("39".toString(),
                        address,
                        tuitionmoney,
                        daystart!!.toString(),
                        dayend!!.toString(),
                        listdayofw.toString(),
                        listtimest.toString())
                call.Call_Service(Value.workername_adđcourse,Value.servicename_addcourse,inval,Value.key_addcourse)
            }

        }
    }

    //cập nhật lại tên buổi học
    fun updatedayofweek()
    {
        Toast.makeText(applicationContext,"jsfhaskdhf"+listnNumberView!!.size,Toast.LENGTH_SHORT).show()
        var x=2
        for (i in 0.. listnNumberView!!.size-1)
        {
            listnNumberView!![i].setText("Buổi "+x)
            x++
        }
    }


    //Xóa buổi của môn học
    fun onDelete(v:View)
    {
        parentLinearLayout!!.removeView(v.parent.parent as View)
        listViewHolder!!.removeAt(v.getTag() as Int)
        resetTag()
    }

    //gắn cờ cho mỗi view
    fun resetTag(){
        for(i  in 0 ..parentLinearLayout!!.childCount-1){

            parentLinearLayout!!.getChildAt(i).findViewById(R.id.tab_remove_view).tag = i
            (parentLinearLayout!!.getChildAt(i).findViewById(R.id.tv_count_view) as TextView).setText("Buổi "+(i+2) )
        }
    }

    /**
     * Hàm hiển thị DatePicker dialog
     */
    fun showDatePickerDialog() {
        val callback = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
            var day = dayOfMonth
            var month = monthOfYear
            var day1: String = ""
            var month1: String = ""

            if (day < 10) {
                day1 = "0" + day
            } else {
                day1 = day.toString()
            }
            if (month + 1 < 10) {
                month1 = "0" + (month + 1).toString()
            } else {
                month1 = (month + 1).toString()
            }


            tv_course_starttime!!.setText(day1.toString() + "/" + (month1) + "/" + year)

        }
        var s = tv_course_starttime!!.getText()

        if(s== "")
        {
            s="01/01/2000"
        }

        var strArrtmp = s.split("/".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        var ngay = Integer.parseInt(strArrtmp[0])
        var thang = Integer.parseInt(strArrtmp[1]) - 1
        var nam = Integer.parseInt(strArrtmp[2])
        var pic = DatePickerDialog(this, callback, nam, thang, ngay)
        pic.setTitle("Chọn ngày hoàn thành")
        pic.show()
    }

    /**
     * Hàm hiển thị DatePicker dialog
     */
    fun showDatePickerDialog2() {
        val callback = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
            var day = dayOfMonth
            var month = monthOfYear
            var day1: String = ""
            var month1: String = ""

            if (day < 10) {
                day1 = "0" + day
            } else {
                day1 = day.toString()
            }
            if (month + 1 < 10) {
                month1 = "0" + (month + 1).toString()
            } else {
                month1 = (month + 1).toString()
            }


            tv_course_endtime!!.setText(day1.toString() + "/" + (month1) + "/" + year)

        }
        var s = tv_course_endtime!!.getText()

        if(s== "")
        {
            s="01/01/2000"
        }

        var strArrtmp = s.split("/".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        var ngay = Integer.parseInt(strArrtmp[0])
        var thang = Integer.parseInt(strArrtmp[1]) - 1
        var nam = Integer.parseInt(strArrtmp[2])
        var pic = DatePickerDialog(this, callback, nam, thang, ngay)
        pic.setTitle("Chọn ngày hoàn thành")
        pic.show()
    }
    /**
     * Hàm lấy các thông số mặc định khi lần đầu tiền chạy ứng dụng
     */
    fun getDefaultInfor() {
        //lấy ngày hiện tại của hệ thống
        cal = Calendar.getInstance()
        var dft: SimpleDateFormat? = null
        //Định dạng ngày / tháng /năm
        dft = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        var strDate = dft!!.format(cal!!.getTime())
        //hiển thị lên giao diện
        tv_course_starttime!!.setText(strDate)
        tv_course_endtime!!.setText(strDate)
        //Định dạng giờ phút am/pm
        dft = SimpleDateFormat("hh:mm a", Locale.getDefault())
        /*var strTime = dft!!.format(cal!!.getTime())*/
        //đưa lên giao diện

        //lấy giờ theo 24 để lập trình theo Tag
        /*dft = SimpleDateFormat("HH:mm", Locale.getDefault())*/
        var strTime = dft!!.format(cal!!.getTime())
        //txtTime.setTag(dft!!.format(cal!!.getTime()))

        tv_course_time_1!!.setText(strTime)

    }

    /**
     * Hàm hiển thị TimePickerDialog
     */
    fun showTimePickerDialog(tv1:TextView) {
        var callback = OnTimeSetListener { view, hourOfDay, minute ->
            //Xử lý lưu giờ và AM,PM
            //var s = hourOfDay.toString() + ":" + minute
            var hourTam:Int = hourOfDay
            if (hourTam > 12)
                hourTam = hourTam - 12

            var hourse1 = if(hourTam>9)hourTam else "0"+hourTam
            var minute1=  if(minute>9) minute else "0"+ minute

            tv1!!.setText("" + hourse1 + ":" +minute1+ if (hourOfDay > 12)" CH" else " SA")
            //lưu vết lại giờ vào hourFinish
            cal!!.set(Calendar.HOUR_OF_DAY, hourOfDay)
            cal!!.set(Calendar.MINUTE, minute)
            // hourFinish = cal!!.getTime()
        }
        //các lệnh dưới này xử lý ngày giờ trong TimePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        var s = tv1!!.text.substring(0,5)
        var aft= tv1!!.text.substring(6)
        var strArr = s.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()

        var gio = Integer.parseInt(strArr[0])


        if(aft=="CH" || aft=="PM")
        {
            gio= gio+12
        }
        var phut = Integer.parseInt(strArr[1])
        var time = TimePickerDialog(
                this,
                callback, gio, phut, true)
        time.setTitle("Chọn giờ học")
        time.show()
    }

    fun getDefaultInforView(tv:TextView) {
        //lấy ngày hiện tại của hệ thống
        cal = Calendar.getInstance()
        var dft: SimpleDateFormat? = null
        //Định dạng giờ phút am/pm
        dft = SimpleDateFormat("hh:mm a", Locale.getDefault())
        var strTime = dft!!.format(cal!!.getTime())
        //đưa lên giao diện
        tv!!.setText(strTime)

    }

    override fun callSetTime(tv: TextView) {
        super.callSetTime(tv)
        showTimePickerDialog(tv)

    }

}