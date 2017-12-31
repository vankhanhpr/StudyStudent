package com.example.studystudymorestudyforever.adapter.adapter.chat

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.myinterface.ISetMessage
import com.example.studystudymorestudyforever.until.chat.ChatData

import java.util.*
import android.widget.ProgressBar
import android.support.v7.widget.LinearLayoutManager
import com.example.studystudymorestudyforever.myinterface.IOnLoadMoreListener


/**
 * Created by VANKHANHPR on 10/1/2017.
 */
class ChatApdater(context: Context,chat:ArrayList<ChatData>,iner:ISetMessage):  RecyclerView.Adapter<ChatApdater.ViewHolder>() {


    private var chat :List<ChatData> ? = Collections.emptyList()
    private var mInflater: LayoutInflater?=null
    private  var iner:ISetMessage
    init {
        this.mInflater = LayoutInflater.from(context)
        this.chat = chat
        this.iner=iner
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        var view = mInflater!!.inflate(R.layout.child_message_layout, parent, false)
        var viewHolder = ViewHolder(view)
        return viewHolder
    }
    // binds the data to the textview in each row
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int){

        holder.tv_person_name!!.setText(chat!![position].getNAME().toString())


        if(chat!![position].getRECENTLY_ACTIVITY().toString().length<10) {
            holder.tv_status!!.setText(chat!![position].getRECENTLY_ACTIVITY())
        }
        else{
            holder.tv_status!!.setText(chat!![position].getRECENTLY_ACTIVITY()!!.substring(0,10)+"...")
        }

        holder.itemView.setOnClickListener()
        {
            iner.chat(chat!![position].getID().toString())
        }
    }
    // total number of rows
    override fun getItemCount(): Int {
        return chat!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tv_person_name: TextView?=null
        var tv_status:TextView ?= null
        var tab_child_chat:RelativeLayout?=null

        init {
            this.tv_person_name= itemView.findViewById(R.id.tv_person_name) as TextView
            this.tv_status= itemView.findViewById(R.id.tv_status) as TextView
            this.tab_child_chat = itemView.findViewById(R.id.tab_child_chat) as RelativeLayout

            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
        }
    }


    inner class LoadingViewHolder(view: View) :  RecyclerView.ViewHolder(view) {
        var progressBar: ProgressBar

        init {
            progressBar = view.findViewById(R.id.progressBar1) as ProgressBar
        }
    }
}