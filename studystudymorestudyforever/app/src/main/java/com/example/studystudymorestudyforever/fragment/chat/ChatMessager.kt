package com.example.studystudymorestudyforever.fragment.chat

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.chat.MessageAdapter
import com.example.studystudymorestudyforever.until.chat.ChatMessage
import kotlinx.android.synthetic.main.chat_message_main_layout.*
import android.widget.Toast
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.JsonLogin
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.chat.ChatContent

import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject


/**
 * Created by VANKHANHPR on 10/5/2017.
 */
class ChatMessager :AppCompatActivity(){
    var isMine = false
    var userchat:String= ""
    var call= OnEmitService.getIns()
    var chatMessages :ArrayList<ChatMessage> = arrayListOf()
    var adapter:MessageAdapter? = null
    var status_res:String = ""

    var tv_name_user_chat:TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_message_main_layout)
        initt()
        var bundle = intent.extras;
        var name= bundle.getString("name")
        userchat= bundle.getString("id_chat")

        tv_name_user_chat!!.setText(""+name)

        getContentMessage()

        adapter= MessageAdapter(this,R.layout.chat_item_left,chatMessages)
        list_msg.adapter= adapter

        tab_send_message.setOnClickListener{
                if (msg_type.getText().toString().trim().equals(""))
                {
                    Toast.makeText(this, "Please input some text...", Toast.LENGTH_SHORT).show()
                }
                else {
                    //var chatMessage = ChatMessage(msg_type.getText().toString(),false)
                    sentMessage(msg_type.getText().toString())
                   // chatMessages.add(chatMessage)
                    status_res= msg_type.getText().toString()

                    msg_type.setText("")
                }
        }
        tab_back.setOnClickListener()
        {
            finish()
        }
    }
    fun initt()
    {
        EventBus.getDefault().register(this)
        tv_name_user_chat = findViewById(R.id.tv_name_user_chat) as TextView
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {

        val gson = Gson()

        if(event.getKey()== "message")
        {
            isMine = false
            var mess = readJson(event.getData()!!.getData()!!)
            var chatMessage = ChatMessage(mess.getC0().toString(), isMine)
            chatMessages.add(chatMessage)
            adapter!!.notifyDataSetChanged()
        }
        if(event.getKey()==Value.key_sentmessage)
        {
            if(event.getData()!!.getResult()=="1") {
                    isMine= true
                    var chatMessage = ChatMessage(status_res,isMine)
                    chatMessages.add(chatMessage)
                    adapter!!.notifyDataSetChanged()

            }
            else{
            }
        }

        if(event.getKey()==Value.key_getcontent_message)//Lấy nội dung tin nhắn đã chat
        {
            if(event.getData()!!.getResult()=="1")
            {
                Log.d("cotoiday","csadfsadf")
                //var lisChatContent :ArrayList<ChatContent> = arrayListOf()
                for(i in 0..event.getData()!!.getData()!!.size-1) {

                    var chat = gson.fromJson(event.getData()!!.getData()!![i].toString(), ChatContent::class.java)
                    if(chat.getSENDERID().toString()== userchat)
                    {
                        isMine = false
                        var chatMessage = ChatMessage(chat.getMESSAGE().toString(), isMine)
                        chatMessages.add(chatMessage)
                        adapter!!.notifyDataSetChanged()
                    }
                    else{
                        if(chat.getSENDERID() == LocalData.user.getID()){

                            isMine = true
                            var chatMessage = ChatMessage(chat.getMESSAGE().toString(), isMine)
                            chatMessages.add(chatMessage)
                            adapter!!.notifyDataSetChanged()
                        }
                    }
                }
            }
            else
            {

            }
        }
    }

    fun sentMessage(tem:String)
    {
        var inval:Array<String> =  arrayOf(LocalData.user.getID().toString(),userchat!!,tem)
        call.Call_Service(Value.workername_sendmessager,Value.servicename_sendmessage,inval,Value.key_sentmessage)
    }


    //Lấy nội dung tin nhắn cũ
    fun getContentMessage()
    {
        var invalconten : Array<String> = arrayOf(LocalData.user.getID().toString(),userchat.toString())
        call.Call_Service(Value.workername_getcontentmessage,
                Value.service_getcontentmessage,invalconten,
                Value.key_getcontent_message)

    }
    // Đọc file Json để lấy kết quả
    fun readJson(json1: ArrayList<JSONObject>): JsonLogin
    {
        var jsonO: JSONObject?=null

        if(json1.size>0)
        {
            jsonO = json1[0]
        }
        var c0: String? =jsonO!!.getString("message")
        var ser1 : JsonLogin = JsonLogin()
        ser1.setC0(c0!!)
        return ser1
    }

    /*fun getNewChat()
    {
        var inval :Array<String> = arrayOf(LocalData.user.getID().toString())
        call.Call_Service(Value.wokername_getnewmessage,
                Value.service_getnewmessage,
                inval,Value.key_getnewmessage)
    }*/
}