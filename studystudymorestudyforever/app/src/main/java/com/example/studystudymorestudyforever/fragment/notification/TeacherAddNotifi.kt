package com.example.studystudymorestudyforever.fragment.notification

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.notification.DialogAddNotifiAdapter
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.example.studystudymorestudyforever.until.user.User
import com.google.gson.Gson
import kotlinx.android.synthetic.main.notification_teachers_add_layout.*
import com.afollestad.materialdialogs.MaterialDialog
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.until.JsonLogin
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject


/**
 * Created by VANKHANHPR on 11/5/2017.
 */
class TeacherAddNotifi:AppCompatActivity(), View.OnClickListener {



    var dialogshowAccount:Dialog? = null
    var lv_select_account:ListView ? =null
    var adapte: DialogAddNotifiAdapter? = null
    var listcheckedUser:ArrayList<String> = arrayListOf()
    var tv_set_name_send:TextView?= null
    var mCountDownTimer: CountDownTimer? = null
    var tab_back:LinearLayout?=null



    var call =OnEmitService.getIns()


    fun initt()
    {
        EventBus.getDefault().register(this)
        tv_set_name_send = findViewById(R.id.tv_set_name_send) as TextView
        tab_back= findViewById(R.id.tab_back) as LinearLayout
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_teachers_add_layout)

        initt()

        tab_select_uer_chat.setOnClickListener(this)
        tab_send_notifilecation.setOnClickListener(this)
        tab_back!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id)
        {
            R.id.tab_select_uer_chat ->
            {
                dialogshowAccount= Dialog(this)
                dialogshowAccount!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialogshowAccount!!.setContentView(R.layout.dialog_list_account_sent_notifilecation)
                lv_select_account= dialogshowAccount!!.findViewById(R.id.lv_select_account) as ListView
                var tab_finish_select_account= dialogshowAccount!!.findViewById(R.id.tab_finish_select_account)
                var tv_OK= dialogshowAccount!!.findViewById(R.id.tv_OK)

                tv_OK!!.setOnClickListener()
                {
                    for(i in 0..adapte!!.listBoolean.size-1)
                    {
                        if(adapte!!.listBoolean[i]) {
                            listcheckedUser.add(LocalData.listUser[i].getID().toString())
                            tv_set_name_send!!.setText(LocalData.listUser[i].getNAME()+" ")
                            Log.d("listuser",LocalData.listUser[i].getNAME())
                        }
                    }
                    dialogshowAccount!!.cancel()
                }

                tab_finish_select_account!!.setOnClickListener()
                {
                    dialogshowAccount!!.cancel()
                }

                adapte = DialogAddNotifiAdapter(applicationContext,LocalData.listUser)
                lv_select_account!!.adapter= adapte
                dialogshowAccount!!.show()
            }
            R.id.tab_send_notifilecation ->{

                progress_addnotifi.visibility= View.VISIBLE
                var gson= Gson()
                var listUsersentNotif= gson.toJson(listcheckedUser)

                var title = edt_title_notifi.text.toString().trim()
                var status= edt_status_notif.text.toString().trim()
                var inval :Array<String> = arrayOf(LocalData.user.getID().toString(),
                        title,status,
                        listUsersentNotif.toString())
                //timeout
                mCountDownTimer = object : CountDownTimer(10000, 1000) {
                    var i = 0
                    override fun onTick(millisUntilFinished: Long) {
                        i++
                        if (i == 5) {
                            for (i in 0..OnEmitService.getIns().hasmap!!.size - 1) {
                            }
                        }
                        if (i == 10) {
                            progress_addnotifi!!.visibility = View.GONE
                        }
                    }

                    override fun onFinish() {
                        //Do what you want
                        i++
                        progress_addnotifi!!.visibility = View.GONE
                        try {
                            MaterialDialog.Builder(this@TeacherAddNotifi)
                                    .title("Lỗi")
                                    .content("Có lỗi xảy ra vui lòng thử lại!")
                                    .positiveText("Đồng ý")
                                    .show()

                        }
                        catch (e: Exception)
                        {
                        }
                    }
                }
                mCountDownTimer!!.start()

                call.Call_Service(Value.workername_sentnotifilecation,
                        Value.service_sentnotifilecation,inval,
                        Value.key_sendnotifile)
            }
            R.id.tab_back->{
                finish()
            }
        }
    }
    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getKey() == Value.key_sendnotifile) {

            progress_addnotifi.visibility= View.GONE
            if (event.getData()!!.getResult() == "1") {

                var tem= readJson(event.getData()!!.getData()!!)
                if(tem.getC0()=="Y")
                {
                    var x=MaterialDialog.Builder(this)
                            .title("Thông tin cập nhật")
                            .content("Gửi thông báo thành công")
                            .positiveText("Đồng ý")
                            .show()

                }

            }
            else{
                MaterialDialog.Builder(this)
                        .title("Thông tin cập nhật")
                        .content("Có lỗi xảy ra vui lòng kiểm tra lại")
                        .positiveText("Đồng ý")
                        .show()
            }
        }
    }

    public override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
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