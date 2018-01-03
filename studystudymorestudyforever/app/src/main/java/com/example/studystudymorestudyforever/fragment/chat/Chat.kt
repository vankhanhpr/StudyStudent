package com.example.studystudymorestudyforever.fragment.chat

import android.app.Dialog
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
import android.view.Window
import android.widget.LinearLayout
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.chat.ChatApdater
import com.example.studystudymorestudyforever.adapter.adapter.chat.SelectAcountChatAdapter
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.myinterface.ISelectAccountChat
import com.example.studystudymorestudyforever.myinterface.ISetMessage
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.chat.ChatData
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.example.studystudymorestudyforever.until.teacher.TeacherData
import com.example.studystudymorestudyforever.until.teacher.TeacherofStudent
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import android.widget.ProgressBar
import android.widget.Toast


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
    var listUser:ArrayList<TeacherofStudent> = arrayListOf()
    var tab_nodata_message :LinearLayout?=null
    var sw_refresh:SwipeRefreshLayout?=null
    var listMessage:ArrayList<ChatData> = arrayListOf()

    private var visibleThreshold = 5
    private var lastVisibleItem: Int = 0
    var totalItemCount: Int = 0
    private var isLoading: Boolean = false






    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater!!.inflate(R.layout.main_chat_fragment, container, false)

        EventBus.getDefault().register(this)
        //getListMessage()//laay danh sach tin nhan

        recicleview_list_message= view.findViewById(R.id.recicleview_list_message) as RecyclerView
        tab_add_message= view.findViewById(R.id.tab_add_message) as LinearLayout
        tab_nodata_message= view.findViewById(R.id.tab_nodata_message) as LinearLayout
        sw_refresh= view.findViewById(R.id.sw_refresh) as SwipeRefreshLayout

        getListFriend()

        tab_add_message!!.setOnClickListener()
        {
            dialog_show_list_account= Dialog(context)
            dialog_show_list_account!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_show_list_account!!.setContentView(R.layout.dialog_list_account_chat)

            getListFriend()

            recycle_select_account = dialog_show_list_account!!.findViewById(R.id.recycle_select_account) as RecyclerView

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
                    call.Sevecie()
                    getListMessage()
                    sw_refresh!!.setRefreshing(false)
                }, 2000)
            }
        })

        recicleview_list_message!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val topRowVerticalPosition = if (recyclerView == null || recyclerView.childCount == 0) 0 else recyclerView.getChildAt(0).top
                sw_refresh!!.setEnabled(topRowVerticalPosition >= 0)


                val linearLayoutManager = recyclerView!!.getLayoutManager() as LinearLayoutManager

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {

                    isLoading = true;
                }
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        return  view
    }
    //lay danh sach lich su chat
    fun getListMessage()
    {
        var inval2: Array<String> = arrayOf(LocalData.user.getID().toString())
        call.Call_Service(Value.workername_getlistmessage,
                Value.servicename_getlistmessage,
                inval2,
                Value.key_getlistmessage)
    }
    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {

        val gson= Gson()
        if(event.getKey()==Value.key_getlist_tdialog)
        {
            if(event.getData()!!.getResult()=="0")//không có dữ liệu
            {
            //khoong co data
            }
            else{
                listUser.clear()

                var list= event!!.getData()!!.getData()
                for(i in 0..list!!.size-1)
                {
                    var temp: TeacherofStudent = gson.fromJson(list[i].toString(), TeacherofStudent::class.java)
                    if(boolUser(temp.getID().toString())) {
                        listUser.add(temp)
                    }
                }
                var adapterw= SelectAcountChatAdapter(context,listUser,this)
                recycle_select_account!!.layoutManager=LinearLayoutManager(context)
                recycle_select_account!!.adapter = adapterw

                LocalData.listTeacher= listUser //Lấy user một lần
            }
        }
        if(event.getKey()==Value.key_getlistmessage)
        {
            listMessage.clear()
            if(event.getData()!!.getResult()=="1") {

                var list= event!!.getData()!!.getData()
                tab_nodata_message!!.visibility = View.GONE
                recicleview_list_message!!.visibility = View.VISIBLE

                for(i in 0..list!!.size-1)
                {
                    var temp: ChatData = gson.fromJson(list[i].toString(), ChatData::class.java)
                    listMessage.add(temp)
                }
                showListMessage()
            }
        }
    }

    //Kiểm tra tính đúng đắn của User

    fun boolUser(tem:String):Boolean
    {

        for(i in 0..listUser!!.size-1)
        {
            if(listUser!![i].getID().toString() == tem)
            {
                return false
            }
        }
        return true
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    //Hiển thị lịch sử chat ra màn hình
    fun showListMessage()
    {
        var adapter = ChatApdater(context,listMessage,this)
        recicleview_list_message!!.layoutManager=LinearLayoutManager(context)
        recicleview_list_message!!.adapter= adapter
    }

    //Lấy danh sách bạn bè
    fun getListFriend()
    {
        var inval:Array<String> =  arrayOf(LocalData.user.getID().toString())
        if(LocalData.usertype == 2) {
            call.Call_Service(Value.workername_getlist_teacher,
                    Value.servicename_getlist_teacher,
                    inval, Value.key_getlist_tdialog)
        }
        else{
            call.Call_Service(Value.workername_getlistfriendofstudent,
                    Value.servicename_getlistfriendofstudent, inval,
                    Value.key_getlist_tdialog)
        }
    }
    //xem lịch sử chat và chat tiếp
    override fun chat(tem: ChatData) {
        super.chat(tem)
       /* var inten = Intent(context,ChatMessager::class.java)
        var bundle= Bundle()
        bundle.putString("bundle",tem!!)
        inten.putExtra("intent",bundle)
        //startActivity(inten)*/
        var iten2= Intent(context,ChatMessager::class.java)
        iten2.putExtra("id_chat",tem.getID().toString())
        iten2.putExtra("name",tem.getNAME())
        startActivity(iten2)
    }

    override fun selectaccount(id: TeacherofStudent) {
        super.selectaccount(id)
        var iten2= Intent(context,ChatMessager::class.java)
        iten2.putExtra("id_chat",id.getID().toString())
        iten2.putExtra("name",id.getNAME())
        startActivity(iten2)
        dialog_show_list_account!!.cancel()
    }




    private inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var progressBar: ProgressBar

        init {
            progressBar = view.findViewById(R.id.progressBar1) as ProgressBar
        }
    }

}

