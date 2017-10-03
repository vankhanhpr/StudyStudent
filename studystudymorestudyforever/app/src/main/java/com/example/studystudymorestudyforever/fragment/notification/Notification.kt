package com.example.studystudymorestudyforever.fragment.notification

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.notification.NotificationAdapter
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.example.studystudymorestudyforever.until.notification.Notifi
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * Created by VANKHANHPR on 9/13/2017.
 */
class Notification: Fragment()
{
    var call= OnEmitService.getIns()
    var recicleview_list_notication:RecyclerView?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater!!.inflate(R.layout.main_notification_fragment, container, false)
        EventBus.getDefault().register(this)
        recicleview_list_notication= view.findViewById(R.id.recicleview_list_notication) as RecyclerView

        var listnoti:ArrayList<Notifi> = arrayListOf()
        var tem:Notifi = Notifi()
        tem.setNotifID(1)
        tem.setNotifstatus("cajsldfjsdjf")

        listnoti.add(tem)
        listnoti.add(tem)
        listnoti.add(tem)
        var adapte = NotificationAdapter(context,listnoti)
        recicleview_list_notication!!.setLayoutManager(LinearLayoutManager(context))
        recicleview_list_notication!!.adapter= adapte

        var inval:Array<String> = arrayOf(LocalData.email)
        call.Call_Service(Value.workername_getlistnotif,Value.servicename_getlistnotif,inval,Value.key_getlistnotif)
        return  view
    }

    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getKey() == Value.key_login)
        {
            var json: ArrayList<JSONObject>? = event.getData()!!.getData()
           if(event.getData()!!.getResult()=="1")
           {

           }
            else
           {

           }
        }
    }
}