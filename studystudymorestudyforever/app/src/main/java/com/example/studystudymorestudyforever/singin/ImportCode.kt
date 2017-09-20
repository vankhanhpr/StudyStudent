package com.example.studystudymorestudyforever.singin

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.encode.Encode
import com.example.studystudymorestudyforever.login.Login
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.JsonLogin
import com.example.studystudymorestudyforever.until.Value
import kotlinx.android.synthetic.main.import_code_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * Created by VANKHANHPR on 9/11/2017.
 */
class ImportCode:AppCompatActivity()
{
    var email:String?=null
    var code:String?=null
    var dialog_import_code:Dialog?=null
    var btn_agree_dialogres:LinearLayout?=null
    var tv_show_error:TextView?=null

    var mCountDownTimer: CountDownTimer? = null
    var call= OnEmitService.getIns()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.import_code_layout)

        EventBus.getDefault().register(this)
        //lấy dữ liệu
        var inte: Intent = intent
        var bundle:Bundle=inte.getBundleExtra(Value.bundle)
        email= bundle.getString(Value.value)
        tab_import_code.setOnClickListener()
        {
            dialog_import_code =Dialog(this)
            dialog_import_code!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_import_code!!.setContentView(R.layout.dialog_import_code)
            btn_agree_dialogres=dialog_import_code!!.findViewById(R.id.btn_agree_dialogres) as LinearLayout
            btn_agree_dialogres!!.setOnClickListener()
            {
                dialog_import_code!!.cancel()
            }

            code= edt_import_code.text.toString()
            if(code!!.length!=4)
            {
                dialog_import_code!!.show()
            }
            else
            {
                progress_importcode!!.visibility = View.VISIBLE
                var data: Array<String> = arrayOf(email!!,code!!)
                call.Call_Service(Value.workername_import_code, Value.servicename_import_code, data!!, Value.key_import_code)

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
                            progress_importcode!!.visibility = View.GONE
                        }
                    }

                    override fun onFinish() {
                        //Do what you want
                        i++
                        try {
                            dialog_import_code!!.show()
                            progress_importcode!!.visibility = View.GONE
                        } catch (e: Exception) {
                        }
                    }
                }
                mCountDownTimer!!.start()
            }
        }

    }

    //Nhận kết quả trả về
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getKey() == Value.key_import_code)
        {
            mCountDownTimer!!.cancel() //turn off timeout
            progress_importcode!!.visibility= View.GONE//turn off loading

            var json: ArrayList<JSONObject>? = event.getData()!!.getData()
            var temp=readJson(json!!)
            if (temp.getC0()=="Y")
            {
                Toast.makeText(applicationContext,"Xác thực thành công!",Toast.LENGTH_SHORT).show()
                var inte= Intent(applicationContext,Login::class.java)
                startActivity(inte)
            }
            else
            {
                dialog_import_code!!.show()
                tv_show_error!!.setText(event!!.getData()!!.getMessage())
            }
        }
    }
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