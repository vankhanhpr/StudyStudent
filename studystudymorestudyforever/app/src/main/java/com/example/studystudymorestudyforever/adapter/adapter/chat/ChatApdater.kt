package com.example.studystudymorestudyforever.adapter.adapter.chat

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.until.chat.ChatData

import java.util.*

/**
 * Created by VANKHANHPR on 10/1/2017.
 */
class ChatApdater(context: Context, chat:ArrayList<ChatData>):  RecyclerView.Adapter<ChatApdater.ViewHolder>() {


    private var chat :List<ChatData> ? = Collections.emptyList()
    private var mInflater: LayoutInflater?=null

    init {
        this.mInflater = LayoutInflater.from(context)
        this.chat = chat
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = mInflater!!.inflate(R.layout.child_message_layout, parent, false)
        var viewHolder = ViewHolder(view)
        return viewHolder
    }

    // binds the data to the textview in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animal = chat!!.get(position)
        //holder.myTextView.setText(animal)
    }

    // total number of rows
    override fun getItemCount(): Int {
        return chat!!.size
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        //var myTextView: TextView?=null

        init {
            //myTextView = itemView.findViewById(R.id.person_photo) as TextView
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
        }
    }

}