package com.example.studystudymorestudyforever.fragment.notification

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
import android.widget.Toast
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.notification.NotificationAdapter
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.example.studystudymorestudyforever.until.notification.Notifi
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import com.afollestad.materialdialogs.MaterialDialog
import com.example.studystudymorestudyforever.myinterface.ISetDetailNotif
import com.example.studystudymorestudyforever.until.notification.DetailNotif
import kotlinx.android.synthetic.main.main_notification_fragment.*


/**
 * Created by VANKHANHPR on 9/13/2017.
 */
class Notification: Fragment(),ISetDetailNotif, View.OnClickListener {


    var call= OnEmitService.getIns()
    var recicleview_list_notication:RecyclerView?=null
    var listNotif:ArrayList<Notifi> = arrayListOf()
    var tab_add_notifi:LinearLayout?=null

    var reFresh:SwipeRefreshLayout?= null
    var adapte :NotificationAdapter?=null



    fun initt(view: View)//Hàm khởi tạo
    {
        EventBus.getDefault().register(this)
        recicleview_list_notication= view.findViewById(R.id.recicleview_list_notication) as RecyclerView
        tab_add_notifi= view.findViewById(R.id.tab_add_notifi) as LinearLayout
        reFresh= view.findViewById(R.id.reFresh) as SwipeRefreshLayout
        tab_add_notifi!!.visibility=View.VISIBLE
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater!!.inflate(R.layout.main_notification_fragment, container, false)


        initt(view)
       // getNotif()

        if(LocalData.usertype == 2 )
        {
            tab_add_notifi!!.visibility= View.VISIBLE
        }
        else{
            tab_add_notifi!!.visibility= View.GONE
        }

        tab_add_notifi!!.setOnClickListener(this)

        //load refresh
        reFresh!!.setOnRefreshListener(object :SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {

                Handler().postDelayed({
                    //call.Sevecie()
                    getNotif()
                    reFresh!!.setRefreshing(false)
                }, 2000)
            }

        })
        recicleview_list_notication!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val topRowVerticalPosition = if (recyclerView == null || recyclerView.childCount == 0) 0 else recyclerView.getChildAt(0).top
                reFresh!!.setEnabled(topRowVerticalPosition >= 0)
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        return  view
    }

    //hàm bắt các sự kiện onClick
    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.tab_add_notifi->{
                var inten= Intent(context,TeacherAddNotifi::class.java)
                startActivity(inten)
            }
        }
    }
    //lấy danh sách thông báo
    fun getNotif()
    {
        if(LocalData.user.getID()!=null) {
            var inval: Array<String> = arrayOf(LocalData.user.getID().toString())
            call.Call_Service(Value.workername_getlistnotif,
                    Value.servicename_getlistnotif,
                    inval,
                    Value.key_getlistnotif)
        }
    }

    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getKey() == Value.key_getlistnotif)
        {
            var json: ArrayList<JSONObject>? = event.getData()!!.getData()
           if(event.getData()!!.getResult()=="1")
           {
               listNotif.clear()
                var gson = Gson()
               tab_nodata_notifi.visibility=View.GONE
               recicleview_list_notication!!.visibility = View.VISIBLE
               for(i in 0.. event.getData()!!.getData()!!.size-1) {
                   var noti: Notifi = gson.fromJson(event.getData()!!.getData()!![i].toString(),Notifi::class.java)
                   listNotif.add(noti)
               }

               adapte = NotificationAdapter(context,listNotif!!,this)
               recicleview_list_notication!!.setLayoutManager(LinearLayoutManager(context))
               recicleview_list_notication!!.adapter= adapte
           }
            else
           {
               tab_nodata_notifi.visibility=View.VISIBLE
               recicleview_list_notication!!.visibility = View.GONE
           }
        }
    }
    //Xem chi tiết thông tin thông báo
    override fun gotoNotif(id: String){
        gotoDetailNotif(id)
        var invalreadNotif:Array<String> = arrayOf(LocalData.user.getID().toString(),id)
        call.Call_Service(Value.workername_readnotif,Value.service_readnotif,invalreadNotif,Value.key_readnotifi)
    }
    //Hàm đi đến xem chi tiết thông tin thông báo
    fun gotoDetailNotif(value:String)
    {
        var bundle= Bundle()
        bundle.putString("bundle",value)
        var intent= Intent(context,DetailNotif::class.java)
        intent.putExtra("intent",bundle)
        startActivityForResult(intent,111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data==null)
        {
            return
        }
        else{
            if(requestCode == 111)
            {
                if(resultCode==1) {

                    var bundle = data.getBundleExtra("bundle")
                    var id_notif = bundle.getString("noti_id")
                    adapte!!.deleteNotif(id_notif)

                    for(i in 0.. listNotif.size-1)
                    {
                        if(listNotif[i].getNO_ID()==id_notif)
                        {
                            listNotif.remove(listNotif[i])
                            adapte = NotificationAdapter(context,listNotif!!,this)
                            recicleview_list_notication!!.setLayoutManager(LinearLayoutManager(context))
                            recicleview_list_notication!!.adapter= adapte
                            return;
                        }

                    }
                }
            }
        }
    }
}