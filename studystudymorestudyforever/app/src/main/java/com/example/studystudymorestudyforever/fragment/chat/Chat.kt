package com.example.studystudymorestudyforever.fragment.chat

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.chat.ChatApdater
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.chat.ChatData

/**
 * Created by VANKHANHPR on 9/13/2017.
 */

class  Chat: Fragment()
{

    var call= OnEmitService.getIns()
    var recicleview_list_message:RecyclerView ?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater!!.inflate(R.layout.main_chat_fragment, container, false)

        recicleview_list_message= view.findViewById(R.id.recicleview_list_message) as RecyclerView

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



        var adapter = ChatApdater(context,listchat)
        recicleview_list_message!!.layoutManager=LinearLayoutManager(context)
        recicleview_list_message!!.adapter= adapter

        return  view
    }
}
