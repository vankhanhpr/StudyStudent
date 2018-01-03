package com.example.studystudymorestudyforever.fragment.scheduleteacher

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.Value
import kotlinx.android.synthetic.main.teacher_update_course.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import com.example.studystudymorestudyforever.convert.milisecond.ConverMiliseconds
import com.example.studystudymorestudyforever.fragment.scheduleteacher.addcourse.ViewHolder
import com.example.studystudymorestudyforever.myinterface.ISetTimeDialog

import com.example.studystudymorestudyforever.until.course.DetailCourse
import com.example.studystudymorestudyforever.until.course.ScheduleAdd
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.google.gson.Gson
import org.jetbrains.anko.find
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by VANKHANHPR on 10/8/2017.
 */
class ScheduleTeacherUpdateCourse :AppCompatActivity(), View.OnClickListener,ISetTimeDialog {


    var call = OnEmitService.getIns()

    var edt_course_address :EditText?=null
    var tv_course_starttime :TextView?=null
    var tv_course_endtime :TextView?=null
    var edt_course_tuition :EditText?=null
    var mCountDownTimer: CountDownTimer? = null
    var dialogwwait :MaterialDialog?=null
    var dialogerror :MaterialDialog?=null
    var parentLinearLayout:LinearLayout?=null
    var tv_stime:TextView?= null
    var spinner_course_time:Spinner ? =null

    var dataspin:String= ""


    var listViewHolder:ArrayList<ViewHolder> = arrayListOf()
    var listnNumberView:ArrayList<TextView> ?= arrayListOf()
    var listdetailcourse :ArrayList<DetailCourse> = arrayListOf()

    var indexOfView=0

    var listdataspiner :ArrayList<String> = arrayListOf("Thứ 2","Thứ 3","Thứ 4","Thứ 5","Thứ 6","Thứ 7","Chủ nhật")
    var adapter:ArrayAdapter<String>? =null
    var course : ScheduleAdd = LocalData.course
    var cal: Calendar? = null

    fun initt()
    {
        EventBus.getDefault().register(this)
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,listdataspiner)
        adapter!!.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
        parentLinearLayout= findViewById(R.id.parent_linear_layout_update) as LinearLayout

        edt_course_address= findViewById(R.id.edt_course_address) as EditText
        tv_course_starttime= findViewById(R.id.tv_course_starttime) as TextView
        tv_course_endtime= findViewById(R.id.tv_course_endtime) as TextView
        edt_course_tuition= findViewById(R.id.edt_course_tuition) as EditText
        spinner_course_time= findViewById(R.id.spinner_course_time) as Spinner

        tv_stime= findViewById(R.id.tv_stime) as  TextView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_update_course)

        initt()
        //cập nhật môn học
        card_update_course.setOnClickListener(this)
        //xóa môn học
        card_delete_course.setOnClickListener(this)
        tab_sethours.setOnClickListener(this)
        tab_setstart_time.setOnClickListener(this)
        tab_back_updatecourse.setOnClickListener(this)
        tab_setendtime.setOnClickListener(this)
        tab_sethours.setOnClickListener(this)

        tab_add_course_update.setOnClickListener(this)
        card_update_course.setOnClickListener(this)
        getDetailCourse()
        //resetLayout()

        //Xét thời gian các child view
        spinner_course_time!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, i: Int, l: Long)
            {
                dataspin= spinner_course_time!!.getSelectedItem().toString()
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        })

    }


    //Lấy chi tiết lớp học
    fun getDetailCourse()
    {
        var inval :Array<String> = arrayOf(course!!.getSCHE_ID()!!.toString())
        call.Call_Service(Value.workername_get_detailcourse,
                Value.servicename_get_listdetailcourse,inval,
                Value.key_getdetailcourse)
    }


    //Xóa buổi của môn học
    fun onDelete(v:View)
    {
        parentLinearLayout!!.removeView(v.parent.parent as View)
        listViewHolder!!.removeAt(v.getTag() as Int)
        resetTag()
    }


    //Đổ dữ liệu vào trong combobox
    fun setSpinner(spn:Spinner,dayofweek:String)//set buổi học bằng combobox
    {
        spn!!.setAdapter(adapter)
        if(dayofweek!="") {
            spn.setSelection(listdataspiner.indexOf(dayofweek))
        }
    }

    //lấy giờ dạy
    override fun callSetTime(tv: TextView) {
        super.callSetTime(tv)
    }

    //cập nhật lại tên buổi học
    fun updatedayofweek()
    {
        var x=2
        for (i in 0.. listnNumberView!!.size-1)
        {
            listnNumberView!![i].setText("Buổi "+x)
            x++
        }
    }

    override fun onClick(v: View?) {

        when (v!!.id)
        {
            /*R.id.card_update_course ->
            {
                //var inval:Array<String> = arrayOf(edt_course_address!!.text.toString(),edt_course_time!!.text.toString(),edt_course_starttime!!.text.toString(),edt_course_endtime!!.text.toString(),edt_course_tuition!!.text.toString())
                //call.Call_Service(Value.workername_updatecourse,Value.servicename_updatecourse,inval,Value.key_updatecourse)
                dialogwwait=MaterialDialog.Builder(this)
                        .title("Đang cập nhật")
                        .content("Vui lòng chờ")
                        .progress(true, 0)
                        .show()
                dialogerror = MaterialDialog.Builder(this)
                        .title("Lỗi")
                        .content("Có lỗi xảy ra vui lòng thử lại!!!")
                        .positiveText("OK")
                        .show()
                dialogerror!!.cancel()

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
                            dialogwwait!!.cancel()
                            dialogerror!!.show()

                        }
                    }

                    override fun onFinish() {
                        //Do what you want
                        i++
                        try {
                            dialogwwait!!.cancel()

                            dialogerror!!.show()

                        } catch (e: Exception) {
                        }
                    }
                }
                mCountDownTimer!!.start()
            }*/
            R.id.card_delete_course ->{


            }
            R.id.tab_setstart_time ->{
                showDatePickerDialog(tv_course_starttime!!)
            }
            R.id.tab_back_updatecourse ->
            {
                finish()
            }
            R.id.tab_setendtime ->{
                showDatePickerDialog(tv_course_endtime!!)
            }
            R.id.tab_sethours ->
            {
                showTimePickerDialog(tv_stime!!)
            }
            R.id.tab_add_course_update ->
            {
                addCourse("",getDefaultInforView())
            }
            R.id.card_update_course ->
            {

                var starttime1= ConverMiliseconds().converttomiliseconds(tv_course_starttime!!.text.toString())
                var endtime1= ConverMiliseconds().converttomiliseconds(tv_course_endtime!!.text.toString())

                var listDayofWeek:ArrayList<String> = arrayListOf()
                var listTime:ArrayList<String> = arrayListOf()
                listDayofWeek.add(dataspin!!)
                listTime.add(tv_stime!!.text.toString())

                for(i in 0..listViewHolder!!.size-1) {
                    listDayofWeek!!.add(listViewHolder!![i].getSpiner())
                    listTime!!.add((listViewHolder!![i].getTime()))
                }

                var gson= Gson()
                var listdayofw :String= gson.toJson(listDayofWeek)
                var listtimest :String= gson.toJson(listTime)


                var format :SimpleDateFormat=  SimpleDateFormat("dd/MM/yyyy")
                var stday= format.parse(tv_course_starttime!!.text.toString())
                var edday =format.parse(tv_course_endtime!!.text.toString())

                if(stday.after(edday))
                {
                    Toast.makeText(this,"Ngày bắt đầu phải nhỏ hơn ngày kết thúc!",Toast.LENGTH_SHORT).show()
                }
                else {

                    var inval: Array<String> = arrayOf(
                            edt_course_address!!.text.toString(),
                            edt_course_tuition!!.text.toString(),
                            starttime1.toString(),
                            endtime1.toString(),
                            LocalData.course.getSCHE_ID(),
                            listdayofw.toString(), listtimest!!.toString())
                    call.Call_Service(Value.workername_updatecourse, Value.servicename_updatecourse, inval, Value.key_updatecourse)


                    dialogwwait = MaterialDialog.Builder(this)
                            .title("Đang cập nhật")
                            .content("Vui lòng chờ")
                            .progress(true, 0)
                            .show()
                }
                dialogerror = MaterialDialog.Builder(this)
                        .title("Lỗi")
                        .content("Có lỗi xảy ra vui lòng thử lại!!!")
                        .positiveText("OK")
                        .show()
                dialogerror!!.cancel()
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
                            dialogwwait!!.cancel()
                            dialogerror!!.show()

                        }
                    }

                    override fun onFinish() {
                        //Do what you want
                        i++
                        try {
                            dialogwwait!!.cancel()

                            dialogerror!!.show()

                        } catch (e: Exception) {
                        }
                    }
                }
                mCountDownTimer!!.start()
            }
        }
    }




    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getKey() == Value.key_updatecourse)
        {

            mCountDownTimer!!.cancel() //turn off timeout
            dialogwwait!!.cancel()
            if(event.getData()!!.getResult()=="1")
            {
                var dialog= Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.dialog_login)
                dialog.show()
                var tv_title= dialog.findViewById(R.id.tv_title) as TextView
                var tv_show_notif= dialog.findViewById(R.id.tv_show_error) as TextView
                var btn_agree_dialogres= dialog.findViewById(R.id.btn_agree_dialogres)
                tv_title.setText("Cập nhật lớp học")
                tv_show_notif.setText("Cập nhật lớp học thành công")
                btn_agree_dialogres.setOnClickListener()
                {
                    dialog.cancel()
                    finish()
                }
            }
            else{
                MaterialDialog.Builder(this)
                        .title("Lỗi")
                        .content(event.getData()!!.getMessage()!!)
                        .positiveText("OK")
                        .show()
            }
        }
        if(event.getKey()== Value.key_getdetailcourse)
        {
            for(i in 0..event.getData()!!.getData()!!.size-1)
            {
                var detailcourse = Gson().fromJson(event.getData()!!.getData()!![i].toString(),DetailCourse::class.java)
                listdetailcourse!!.add(detailcourse)
            }
            dataspin=listdetailcourse!![0].getTHU().toString()
            tv_stime!!.setText(listdetailcourse[0].getTIME().toString())
            resetLayout()
        }
    }

    fun resetLayout()
    {
        edt_course_address!!.setText(listdetailcourse[0].getLOCATION())

        var sttime:String= ConverMiliseconds().converttodate(listdetailcourse[0].getSTART_TIME().toLong())
        var entime:String= ConverMiliseconds().converttodate(listdetailcourse[0].getEND_TIME().toLong())

        Log.d("thoigian2",""+listdetailcourse[0].getSTART_TIME().toLong())

        tv_course_starttime!!.setText(sttime)
        tv_course_endtime!!.setText(entime)
        edt_course_tuition!!.setText(listdetailcourse[0].getFEE())
        tv_stime!!.setText(listdetailcourse[0].getTIME())

        for(i in 1..listdetailcourse.size-1)
        {
            addCourse(listdetailcourse!![i].getTHU().toString(),listdetailcourse!![i].getTIME().toString())
        }

        setSpinner(spinner_course_time!!,listdetailcourse!![0].getTHU().toString())
        spinner_course_time!!.setPrompt("Thứ 6")
    }

    //gắn cờ cho mỗi view
    fun resetTag(){
        for(i  in 0 ..parentLinearLayout!!.childCount-1){
            parentLinearLayout!!.getChildAt(i).findViewById(R.id.tab_remove_view).tag = i
            (parentLinearLayout!!.getChildAt(i).findViewById(R.id.tv_count_view) as TextView).setText("Buổi "+(i+2) )
        }
    }

    fun addCourse(dayofw:String,time:String)
    {
        Log.d("addview","addview")
        var inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var rowView = inflater.inflate(R.layout.teacher_addview_row_bucking_schedule, null)
        var tv_course_time_child_view= rowView.findViewById(R.id.tv_course_time_child_view) as TextView
        var tab_set_time_row = rowView.findViewById(R.id.tab_set_time_row) as LinearLayout

        var spinner = rowView.findViewById(R.id.sp_setdayofweek) as Spinner

        var viewholder : ViewHolder = ViewHolder(rowView,this)
        listViewHolder!!.add(viewholder)
        parentLinearLayout!!.addView(rowView )
        resetTag()
        indexOfView++

        tv_course_time_child_view!!.setText(time)
        var tem :ArrayList<String> = arrayListOf(dayofw)
        setSpinner(spinner!!,dayofw!!)
        updatedayofweek()

        tab_set_time_row.setOnClickListener()
        {
            showTimePickerDialog(tv_course_time_child_view!!)
        }
    }


    fun getDefaultInforView():String {
        //lấy ngày hiện tại của hệ thống
        cal = Calendar.getInstance()
        var dft: SimpleDateFormat? = null
        //Định dạng giờ phút am/pm
        dft = SimpleDateFormat("hh:mm a", Locale.getDefault())
        var strTime = dft!!.format(cal!!.getTime())
        //đưa lên giao diện
        return strTime.toString()

    }



    public override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    /**
     * Hàm hiển thị DatePicker dialog
     */
    fun showDatePickerDialog(tv:TextView) {
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


            tv!!.setText(day1.toString() + "/" + (month1) + "/" + year)

        }
        var s = tv!!.getText()

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
          /*  cal!!.set(Calendar.HOUR_OF_DAY, hourOfDay)
            cal!!.set(Calendar.MINUTE, minute)*/
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

}