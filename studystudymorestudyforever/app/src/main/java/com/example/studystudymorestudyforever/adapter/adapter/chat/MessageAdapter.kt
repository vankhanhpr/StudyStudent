package com.example.studystudymorestudyforever.adapter.adapter.chat

import android.widget.TextView
import android.support.design.widget.CoordinatorLayout.Behavior.setTag
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.until.chat.ChatMessage


/**
 * Created by VANKHANHPR on 10/5/2017.
 */
class MessageAdapter(val activity: Activity, resource: Int,messages:ArrayList<ChatMessage>) : ArrayAdapter<ChatMessage>(activity, resource, messages){
    private val TYPE_LEFT = 0
    private val TYPE_RIGHT = 1
    private var messages:ArrayList<ChatMessage>
    init {
        this.messages = messages
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        val inflater = activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var layoutResource = 0 // determined by view type
        var chatMessage = getItem(position)
        var viewType = getItemViewType(position)

        if(viewType===TYPE_RIGHT){
            layoutResource = R.layout.chat_item_right
        }else if(viewType===TYPE_LEFT){
            layoutResource = R.layout.chat_item_left
        }

        /*if (chatMessage!!.isMine()) {
            layoutResource = R.layout.chat_item_right
        } else {
            layoutResource = R.layout.chat_item_left
        }*/

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
        if(messages.size!==0){
            if(this.messages[position].isMine()){
                return TYPE_RIGHT
            }else if(!this.messages[position].isMine()){
                return TYPE_LEFT
            }else{
                return -1;
            }
        }else{
            return -1;
        }
    }

    override fun getItem(position: Int): ChatMessage? {
        return this.messages.get(position)
    }

    private inner class ViewHolder(v: View) {
        var msg: TextView
        init {
            msg = v.findViewById(R.id.txt_msg) as TextView
        }
    }
}