package com.example.studystudymorestudyforever.fragment.account

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.example.studystudymorestudyforever.until.fragment_account.UserInfo
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import android.app.DatePickerDialog

import android.app.DatePickerDialog.OnDateSetListener

import android.util.Log
import android.widget.*
import com.example.studystudymorestudyforever.convert.milisecond.ConverMiliseconds
import com.example.studystudymorestudyforever.until.JsonLogin
import kotlinx.android.synthetic.main.account_change_infor_user.*

import java.util.*
import com.afollestad.materialdialogs.MaterialDialog
import com.example.studystudymorestudyforever.sqlite.dao.DatabaseHandler
import com.example.studystudymorestudyforever.until.user.User


/**
 * Created by VANKHANHPR on 9/28/2017.
 */

class ChangeInforUser: AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener{

    var call= OnEmitService.getIns()
    var email:String?=""
    var name_user:String?=""
    var age_user:String?=""
    var phone_user:String?=""
    var address_user:String?=""
    var mCountDownTimer: CountDownTimer? = null
    var progress_changeinfouser:ProgressBar?=null
    var dialog_change_info_user:Dialog?=null

    var tv_age:TextView?=null
    var edt_name:EditText?=null
    var edt_phone:EditText?=null
    var edt_address:EditText?=null
    var edt_email:EditText?=null
    internal var helper = DatabaseHandler(this)


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_change_infor_user)
        EventBus.getDefault().register(this)
        init1()//khởi tạo các layout

        getInforUserLocal()

        edt_email!!.setText(LocalData.email)
        refres_info_user.setOnRefreshListener(this)

        getUser()//lấy thông tin tài khoản


        tab_back.setOnClickListener()
        {
            finish()
        }

        tab_change_info_user.setOnClickListener()
        {
            name_user= edt_name!!.text.toString()
            age_user=tv_age!!.text.toString()
            phone_user= edt_phone!!.text.toString()
            address_user= edt_address!!.text.toString()

            dialog_change_info_user= Dialog(this)
            dialog_change_info_user!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_change_info_user!!.setContentView(R.layout.dialog_change_info_user)
            var btn_agree_dialogres= dialog_change_info_user!!.findViewById(R.id.btn_agree_dialogres)

            btn_agree_dialogres!!.setOnClickListener()
            {
                dialog_change_info_user!!.cancel()
            }

            var invalchangeuser:Array<String> = arrayOf(name_user!!,
                    phone_user!!,address_user!!,
                    ConverMiliseconds().converttomiliseconds(age_user!!).toString(),LocalData.user.getID().toString())

            call.Call_Service(Value.workername_changeinfouser,Value.service_changeinfouser,invalchangeuser,Value.key_change_infor_user)

            progress_changeinfouser!!.visibility=View.VISIBLE
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
                        progress_changeinfouser!!.visibility = View.GONE
                    }
                }
                override fun onFinish() {
                    //Do what you want
                    i++
                    try {
                        dialog_change_info_user!!.show()
                        btn_agree_dialogres!!.setOnClickListener()
                        {
                            dialog_change_info_user!!.cancel()
                        }
                        progress_changeinfouser!!.visibility = View.GONE
                    } catch (e: Exception) {
                    }
                }
            }
            mCountDownTimer!!.start()

        }
        tab_set_birthday.setOnClickListener()
        {
            showDatePickerDialog()
        }
    }


    fun init1()
    {
        tv_age= findViewById(R.id.tv_age) as TextView
        progress_changeinfouser= findViewById(R.id.progress_changeinfouser) as ProgressBar
        edt_name=findViewById(R.id.edt_name) as EditText
        edt_phone= findViewById(R.id.edt_phone) as EditText
        edt_address=findViewById(R.id.edt_address) as EditText
        edt_email= findViewById(R.id.edt_email) as EditText
    }

    fun getUser()
    {
        var inval :Array<String> = arrayOf(LocalData.user.getEMAIL().toString()  )
        call.Call_Service(Value.workername_getuser,Value.servicename_getuser,inval,Value.key_getuser)
    }

    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getKey() == Value.key_getuser)
        {
            var json: ArrayList<JSONObject>? = event.getData()!!.getData()
            if (event.getData()!!.getResult()=="1")
            {
                val gson = Gson()
                var user: User= gson.fromJson(event.getData()!!.getData()!![0].toString(),User::class.java)

                var userlocal=helper.getData(user.getID())


                edt_name!!.setText(user.getNAME())
                edt_phone!!.setText(user.getPHONENUMBER().toString())
                edt_address!!.setText(user.getADDRESS())
                var age1= ConverMiliseconds()!!.converttodate(user.getBIRTHDAY())
                tv_age!!.setText(age1)

                if(userlocal!=null)
                {
                    //helper.deleteData(user.getID().toString())

                    helper.updateData(user.getID(), user.getNAME().toString(), user.getEMAIL().toString(), user.getPHONENUMBER().toString(),
                            user.getADDRESS().toString(), user.getBIRTHDAY().toInt(),
                            user.getHASHCODE().toString(), user.getACTIVE().toString(), Integer.parseInt(user.getUSER_TYPE().toString()))

                }
                else {
                    helper.insertData(user.getID(), user.getNAME().toString(), user.getEMAIL().toString(), user.getPHONENUMBER().toString(),
                            user.getADDRESS().toString(), Integer.parseInt(user.getBIRTHDAY().toString()),
                            user.getHASHCODE().toString(), user.getACTIVE().toString(), Integer.parseInt(user.getUSER_TYPE().toString()))
                }
                getInforUserLocal()
            }
            else
            {
            }
        }
        if (event.getKey()==Value.key_change_infor_user)
        {
            var json: ArrayList<JSONObject>? = event.getData()!!.getData()
            var temp=readJson(json!!)
            if (temp.getC0()=="Y")
            {
                dialog_change_info_user!!.cancel()
                progress_changeinfouser!!.visibility= View.GONE
                Toast.makeText(applicationContext,"Cập nhật thông tin thành công!",Toast.LENGTH_SHORT).show()
                finish()
            }
            else
            {
                MaterialDialog.Builder(this)
                        .title("Lỗi ")
                        .content("Cập nhật thông tin không thành công")
                        .positiveText("OK")
                        .show()
            }
        }
    }
    /**
     * Hàm hiển thị DatePicker dialog
     */
    fun showDatePickerDialog(){
        val callback = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
            var day=dayOfMonth
            var month= monthOfYear
            var day1:String=""
            var month1:String=""

            if(day<10)
            {
                day1="0"+day
            }
            else{
                day1=day.toString()
            }
            if(month+1 < 10)
            {
                month1="0"+(month+1).toString()
            }
            else
            {
                month1=(month+1).toString()
            }
            tv_age!!.setText(day1.toString() + "/" + (month1) + "/" + year)
        }

        var s = tv_age!!.getText()

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
    //Lấy thông tin dữ liệu Local
    fun getInforUserLocal()
    {
        //helper.insertData(2,"vanKhanh2","donkihote2305@gmail.com","0989898989","Số 1 VVN",121,"ádfasdf","ádfsad",1)
        val res = helper.allData
        if (res.count ==0)
        {
            Log.d("ChangeInforUser", "Không có dữ liệu")
        }
        else
        {
            var x:String
            var y:String
            var z:String
            while (res.moveToNext()){
               //Log.d("datasqlite", "lần1:" + res.getString(0) +";lần 2:"+ res.getString(1)+"lần3:" + res.getString(2)+"lần 4:"+res.getString(3)+"lần 5"+res.getString(4)+res.getString(5)+res.getString(6))
                edt_name!!.setText(res.getString(1))
                edt_phone!!.setText(res.getString(3))
                edt_address!!.setText(res.getString(4))
                var age= ConverMiliseconds().converttodate(res.getLong(5))
                tv_age!!.setText(age)
            }
        }
    }

    public override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onRefresh() {

        Handler().postDelayed(Runnable {
            getUser()
            getInforUserLocal()
            refres_info_user!!.setRefreshing(false)
        }, 2000)
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

}