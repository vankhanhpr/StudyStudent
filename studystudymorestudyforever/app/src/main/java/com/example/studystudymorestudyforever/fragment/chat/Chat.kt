package com.example.studystudymorestudyforever.fragment.chat

import android.app.Dialog
import android.app.DialogFragment
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.renderscript.Sampler
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AbsListView
import android.widget.LinearLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.chat.ChatApdater
import com.example.studystudymorestudyforever.adapter.adapter.chat.SelectAcountChatAdapter
import com.example.studystudymorestudyforever.adapter.adapter.teacher.TeacherAdapter
import com.example.studystudymorestudyforever.fragment.main.MainActivity
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.myinterface.ISelectAccountChat
import com.example.studystudymorestudyforever.myinterface.ISetMessage
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.chat.ChatData
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.example.studystudymorestudyforever.until.teacher.TeacherData
import com.example.studystudymorestudyforever.until.teacher.TeacherSearch
import com.example.studystudymorestudyforever.until.user.User
import com.google.gson.Gson
import kotlinx.android.synthetic.main.main_chat_fragment.*
import kotlinx.android.synthetic.main.main_teacher_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener



/**
 * Created by VANKHANHPR on 9/13/2017.
 */

class  Chat: Fragment(),ISetMessage,ISelectAccountChat
{

    var call= OnEmitService.getIns()
    var recicleview_list_message:RecyclerView ?=null
    var tab_add_message:LinearLayout ? =null
    var dialog_show_list_account: Dialog?=null
    var recycle_select_account:RecyclerView?=null
    var listUser:ArrayList<User> = arrayListOf()
    var tab_nodata_message :LinearLayout?=null
    var sw_refresh:SwipeRefreshLayout?=null



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater!!.inflate(R.layout.main_chat_fragment, container, false)

        EventBus.getDefault().register(this)
        getListMessage()//laay danh sach tin nhan

        recicleview_list_message= view.findViewById(R.id.recicleview_list_message) as RecyclerView
        tab_add_message= view.findViewById(R.id.tab_add_message) as LinearLayout
        tab_nodata_message= view.findViewById(R.id.tab_nodata_message) as LinearLayout
        sw_refresh= view.findViewById(R.id.sw_refresh) as SwipeRefreshLayout



        var listchat:ArrayList<ChatData> = arrayListOf()
        var chat :ChatData= ChatData()
        chat.setChatID(1)
        chat.setChatstatus("khoongsfasdf")

        listchat.add(chat)
        listchat.add(chat)
        listchat.add(chat)
        listchat.add(chat)
        listchat.add(chat)
        listchat.add(chat)
        listchat.add(chat)







        var adapter = ChatApdater(context,listchat,this)
        recicleview_list_message!!.layoutManager=LinearLayoutManager(context)
        recicleview_list_message!!.adapter= adapter

        tab_add_message!!.setOnClickListener()
        {
            dialog_show_list_account= Dialog(context)
            dialog_show_list_account!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_show_list_account!!.setContentView(R.layout.dialog_list_account_chat)
/*
            var listaccount :ArrayList<TeacherData> = arrayListOf()
            var tem :TeacherData = TeacherData()
            tem.setTeacherID(1)
            tem.setTeacheraddress("sdfsdaf")
            tem.setTeachercourse("sàd")
            tem.setTeachername("khanh")
            listaccount.add(tem)
            listaccount.add(tem)
            listaccount.add(tem)
            listaccount.add(tem)*/



            recycle_select_account = dialog_show_list_account!!.findViewById(R.id.recycle_select_account) as RecyclerView


            getListFriend()

            var tab_cancel_dialog_select = dialog_show_list_account!!.findViewById(R.id.tab_cancel_dialog_select) as LinearLayout
            tab_cancel_dialog_select!!.setOnClickListener()
            {
                dialog_show_list_account!!.cancel()
            }
            dialog_show_list_account!!.show()
        }



        //load refresh
        sw_refresh!!.setOnRefreshListener(object :SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {

                Handler().postDelayed({
                    getListMessage()
                    sw_refresh!!.setRefreshing(false)
                }, 2000)
            }

        })

        return  view
    }

    //lay danh sach lich su chat
    fun getListMessage() {
        var inval2: Array<String> = arrayOf(LocalData.user.getID().toString())
        call.Call_Service(Value.workername_getlistmessage,Value.servicename_getlistmessage,inval2,Value.key_getlistmessage)
    }


    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {

        if(event.getKey()==Value.key_getlist_tdialog)
        {
            if(event.getData()!!.getResult()=="0")//không có dữ liệu
            {
            //khoong co data
            }
            else{
                var gson= Gson()
                var list= event!!.getData()!!.getData()
                for(i in 0..list!!.size-1)
                {
                    var temp: User = gson.fromJson(list[i].toString(), User::class.java)
                    listUser.add(temp)
                }
                var adapterw= SelectAcountChatAdapter(context,listUser,this)
                recycle_select_account!!.layoutManager=LinearLayoutManager(context)
                recycle_select_account!!.adapter = adapterw
            }
        }
        if(event.getKey()==Value.key_getlistmessage)
        {
            tab_nodata_message!!.visibility = View.GONE
            recicleview_list_message!!.visibility =View.VISIBLE
        }


        recicleview_list_message!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val topRowVerticalPosition = if (recyclerView == null || recyclerView.childCount == 0) 0 else recyclerView.getChildAt(0).top
                sw_refresh!!.setEnabled(topRowVerticalPosition >= 0)

            }
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }


    fun getListFriend()
    {
        var inval:Array<String> =  arrayOf(LocalData.email)
        call.Call_Service(Value.workername_getlist_teacher,Value.workername_getlist_teacher,inval,Value.key_getlist_tdialog)
    }

     override fun chat() {
        super.chat()
         var inten =Intent(context,ChatMessager::class.java)
         startActivity(inten)
    }

    override fun selectaccount() {
        super.selectaccount()
        var iten2= Intent(context,ChatMessager::class.java)
        startActivity(iten2)
        dialog_show_list_account!!.cancel()
    }


}
