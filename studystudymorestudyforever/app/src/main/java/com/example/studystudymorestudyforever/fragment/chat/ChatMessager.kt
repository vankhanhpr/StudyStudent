package com.example.studystudymorestudyforever.fragment.chat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.chat.MessageAdapter
import com.example.studystudymorestudyforever.until.chat.ChatMessage
import kotlinx.android.synthetic.main.chat_message_main_layout.*
import android.widget.Toast
import com.example.studystudymorestudyforever.fragment.main.MainActivity



/**
 * Created by VANKHANHPR on 10/5/2017.
 */
class ChatMessager :AppCompatActivity(){


    var isMine = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_message_main_layout)
        var chatMessages :ArrayList<ChatMessage> = arrayListOf()
        var message :ChatMessage= ChatMessage("",false)
        //chatMessages.add(message)
        var adapter= MessageAdapter(this,R.layout.chat_item_left,chatMessages)
        list_msg.adapter= adapter

        tab_send_message.setOnClickListener {
                if (msg_type.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "Please input some text...", Toast.LENGTH_SHORT).show()
                } else {
                    //add message to list
                    var chatMessage = ChatMessage(msg_type.getText().toString(),isMine)
                    chatMessages.add(chatMessage)
                    adapter.notifyDataSetChanged()
                    msg_type.setText("")
                    if (isMine)
                    {
                        isMine = false
                    }
                    else
                    {
                        isMine = true
                    }
                }
        }
        tab_back.setOnClickListener()
        {
            finish()
        }
    }


}