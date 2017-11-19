package com.example.studystudymorestudyforever.fragment.chat

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.chat.MessageAdapter
import com.example.studystudymorestudyforever.until.chat.ChatMessage
import kotlinx.android.synthetic.main.chat_message_main_layout.*
import android.widget.Toast
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.fragment.main.MainActivity
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * Created by VANKHANHPR on 10/5/2017.
 */
class ChatMessager :AppCompatActivity(){


    var isMine = false
    var userchat:String= ""
    var call= OnEmitService.getIns()
    var chatMessages :ArrayList<ChatMessage> = arrayListOf()
    var adapter:MessageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_message_main_layout)
        initt()

        /*var intent :Intent = intent
        var bundle= intent.getBundleExtra("intent")
        userchat= bundle.getString("bundle")*/

        adapter= MessageAdapter(this,R.layout.chat_item_left,chatMessages)
        list_msg.adapter= adapter

        tab_send_message.setOnClickListener{
                if (msg_type.getText().toString().trim().equals(""))
                {
                    Toast.makeText(this, "Please input some text...", Toast.LENGTH_SHORT).show()
                }
                else {

                    var chatMessage = ChatMessage(msg_type.getText().toString(),isMine)
                    sentMessage(msg_type.getText().toString())
                    chatMessages.add(chatMessage)
                    adapter!!.notifyDataSetChanged()
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
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if(event.getKey()== "message")
        {
            var chatMessage = ChatMessage(msg_type.getText().toString(),true)
            chatMessages.add(chatMessage)
            adapter!!.notifyDataSetChanged()
        }
    }
    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }
    fun sentMessage(tem:String)
    {
        var inval:Array<String> =  arrayOf(LocalData.user.getID().toString(),userchat!!,tem)
        call.Call_Service(Value.workername_sendmessager,Value.servicename_sendmessage,inval,Value.key_sentmessage)
    }
}