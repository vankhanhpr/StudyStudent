package com.example.studystudymorestudyforever.adapter.adapter.chat

import android.widget.TextView
import android.support.design.widget.CoordinatorLayout.Behavior.setTag
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.until.chat.ChatMessage


/**
 * Created by VANKHANHPR on 10/5/2017.
 */
class MessageAdapter(val activity: Activity, resource: Int,var messages: ArrayList<ChatMessage>) : ArrayAdapter<ChatMessage>(activity, resource, messages){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        val inflater = activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var layoutResource = 0 // determined by view type
        var chatMessage = getItem(position)
        var viewType = getItemViewType(position)

        if (chatMessage!!.isMine()) {
            layoutResource = R.layout.chat_item_left
        } else {
            layoutResource = R.layout.chat_item_right
        }

        if (convertView != null) {
            holder = convertView!!.getTag() as ViewHolder
        } else {
            convertView = inflater.inflate(layoutResource, parent, false)
            holder = ViewHolder(convertView)
            convertView!!.setTag(holder)
        }

        //set message content
        holder!!.msg.setText(chatMessage!!.getContent())

        return convertView
    }

    override fun getViewTypeCount(): Int {
        // return the total number of view types. this value should never change
        // at runtime
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2
    }

    private inner class ViewHolder(v: View) {
        var msg: TextView
        init {
            msg = v.findViewById(R.id.txt_msg) as TextView
        }
    }
}