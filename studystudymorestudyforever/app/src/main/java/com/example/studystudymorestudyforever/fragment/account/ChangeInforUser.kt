package com.example.studystudymorestudyforever.fragment.account

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.Toast
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.example.studystudymorestudyforever.until.fragment_account.UserInfo
import com.google.gson.Gson
import kotlinx.android.synthetic.main.account_change_infor_user.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

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

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_change_infor_user)
        EventBus.getDefault().register(this)

        progress_changeinfouser= findViewById(R.id.progress_changeinfouser) as ProgressBar

        edt_email.setText(LocalData.email)
        refres_info_user.setOnRefreshListener(this)

        getUser()//lấy thông tin tài khoản

        tab_change_info_user.setOnClickListener()
        {
            name_user= edt_name.text.toString()
            age_user=edt_name.text.toString()
            phone_user= edt_phone.text.toString()
            address_user= edt_address.text.toString()

            dialog_change_info_user= Dialog(this)
            dialog_change_info_user!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_change_info_user!!.setContentView(R.layout.dialog_change_info_user)
            var btn_agree_dialogres= dialog_change_info_user!!.findViewById(R.id.btn_agree_dialogres)

            btn_agree_dialogres!!.setOnClickListener()
            {
                dialog_change_info_user!!.cancel()
            }

            var invalchangeuser:Array<String> = arrayOf(email!!,name_user!!,age_user!!,phone_user!!,address_user!!)
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

    }

    fun getUser()
    {
        var inval :Array<String> = arrayOf(email!!)
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
                var user: UserInfo= gson.fromJson(event.getData()!!.getData().toString(),UserInfo::class.java)
                edt_name.setText(user.getUsername())
                edt_address.setText(user.getUseraddress())
                edt_age.setText(user.getUserage())
                edt_phone.setText(user.getUserphone())
            }
            else
            {


            }
        }
        if (event.getKey()==Value.key_change_infor_user)
        {
            dialog_change_info_user!!.cancel()
            progress_changeinfouser!!.visibility= View.GONE
            Toast.makeText(applicationContext,"Cập nhật thông tin thành công!",Toast.LENGTH_SHORT).show()
            finish()
        }
    }





    public override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onRefresh() {

        Handler().postDelayed(Runnable {
            getUser()
            refres_info_user!!.setRefreshing(false)
        }, 2000)
    }


}