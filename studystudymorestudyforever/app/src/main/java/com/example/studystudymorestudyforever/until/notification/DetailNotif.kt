package com.example.studystudymorestudyforever.until.notification

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.notification.NotificationAdapter
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.main_notification_fragment.*
import kotlinx.android.synthetic.main.notification_detail_show_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import android.content.Intent



/**
 * Created by VANKHANHPR on 11/9/2017.
 */
class DetailNotif:AppCompatActivity(), View.OnClickListener {


    var call = OnEmitService.getIns()
    var dialog_skip:Dialog? = null
    var ignoreaccept= 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_detail_show_layout)
        EventBus.getDefault().register(this)



        tv_skip.setOnClickListener(this)
        tv_agree.setOnClickListener(this)
        tab_back_detail_notif.setOnClickListener(this)

        tv_settitle.setText(LocalData.notifi.getNO_TITLE())
        tv_setstatus.setText(LocalData.notifi.getNO_MESSAGE())

        if(LocalData.notifi.getNOTIF_TYPE()=="0")
        {
            tab_send_notifilecation!!.visibility= View.GONE
        }
        else
        {
            tab_send_notifilecation!!.visibility= View.VISIBLE
        }
    }

    fun deleteNotificationServer()
    {
        var inval: Array<String> = arrayOf(LocalData.notifi.getNO_ID())
        call.Call_Service(Value.workername_disagreefriend,
                Value.servicename_disagreefriend,
                inval,
                Value.key_disagreeaddfriend)
    }

    override fun onClick(v: View?) {
        when (v!!.id!!)
        {
            R.id.tv_agree  ->{

                    var inval :Array<String> = arrayOf(LocalData.user.getID().toString(),
                            LocalData.notifi.getUSER_ID_SEND_NOTIFICATION().toString(),
                            LocalData.notifi.getNO_ID())

                    call.Call_Service(Value.workername_agreeaddfriend,
                            Value.servicename_agreeaddfriend,
                            inval,
                            Value.key_agreeaddfriend)
                deleteNotificationServer()

            }
            R.id.tv_skip->{

                /*if(LocalData.notifi.getNOTIF_TYPE()=="1"){*/
                    dialog_skip = Dialog(this)
                    dialog_skip!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog_skip!!.setContentView(R.layout.dialog_skip_friend)
                    dialog_skip!!.show()

                    var btn_cancel_dialogres =dialog_skip!!.findViewById(R.id.btn_cancel_dialogres)
                    var btn_agree_dialogres = dialog_skip!!.findViewById(R.id.btn_agree_dialogres)

                    btn_cancel_dialogres!!.setOnClickListener()
                    {
                        dialog_skip!!.cancel()
                    }

                    btn_agree_dialogres!!.setOnClickListener()
                    {
                        deleteNotificationServer()
                    }


            }
            R.id.tab_back_detail_notif ->{
                finish()
            }
        }
    }

    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getKey() == Value.key_agreeaddfriend)
        {
            //progress_addnotifi.visibility=View.GONE
            if(event.getData()!!.getResult()=="1")
            {
                var dialogaddfriend = Dialog(this)
                dialogaddfriend.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialogaddfriend.setContentView(R.layout.dialog_add_friend)
                var btn_agree_dialogres = dialogaddfriend.findViewById(R.id.btn_agree_dialogres)
                var tv_show_error = dialogaddfriend.findViewById(R.id.tv_show_error) as TextView

                dialogaddfriend.show()
                tv_show_error.setText("Kết bạn thành công")
                btn_agree_dialogres.setOnClickListener()
                {
                    dialogaddfriend.cancel()
                    sendToNotificaton(LocalData.notifi.getNO_ID(),ignoreaccept)
                }
            }
            else
            {
                MaterialDialog.Builder(this)
                        .title("Thông tin")
                        .content("Đã có lỗi xảy ra vui lòng kiểm tra lại")
                        .positiveText("Đồng ý")
                        .show()
            }
        }
        if(event.getKey()==Value.key_disagreeaddfriend)
        {
            dialog_skip!!.cancel()
            sendToNotificaton(LocalData.notifi.getNO_ID().toString(),ignoreaccept)
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    fun sendToNotificaton(value: String, resultcode: Int) {
        val intent = intent
        val bundle = Bundle()
        bundle.putString("noti_id", value)
        intent.putExtra("bundle", bundle)
        setResult(resultcode, intent) // phương thức này sẽ trả kết quả cho Activity1
        finish() // Đóng Activity hiện tại
    }
}