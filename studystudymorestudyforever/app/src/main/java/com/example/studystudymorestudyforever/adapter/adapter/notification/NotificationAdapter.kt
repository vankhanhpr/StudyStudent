package com.example.studystudymorestudyforever.adapter.adapter.notification

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.myinterface.ISetDetailNotif
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.example.studystudymorestudyforever.until.notification.Notifi
import java.util.*

/**
 * Created by VANKHANHPR on 10/1/2017.
 */
class NotificationAdapter (context: Context, notif: MutableList<Notifi>,gotoDetail: ISetDetailNotif):  RecyclerView.Adapter<NotificationAdapter.ViewHolder>(){

    private var notif :MutableList<Notifi>
    private var mInflater: LayoutInflater?=null
    private  var gotoDetail:ISetDetailNotif

    init {
        this.gotoDetail= gotoDetail
        this.mInflater = LayoutInflater.from(context)
        this.notif = notif
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = mInflater!!.inflate(R.layout.child_notification_layout, parent, false)
        var viewHolder = ViewHolder(view)
        return viewHolder
    }

    fun deleteNotif(id:String)
    {
        var tem= -1;
        for(i in 0..notif!!.size-1)
        {
            if(notif!![i].toString().equals(id))
            {
                notif.remove(notif[i])
                tem=i
            }
        }
        if(tem!== -1 ) {
            notifyItemRemoved(tem)
            notifyItemRangeChanged(tem,notif.size)
        }
    }

    // binds the data to the textview in each row

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val animal = notif!!.get(position)
        holder.tv_title_notif!!.setText(notif!![position].getNO_TITLE())
        holder.tv_content_notif!!.setText(if(notif!![position].getNO_MESSAGE()!!.length>10)
            notif!![position].getNO_MESSAGE()!!.substring(0,10) else notif!![position].getNO_MESSAGE()!!)

        Log.d("unre",notif!![position].getUNREAD()!!)
        if(notif!![position].getUNREAD() == "0")
        {
            holder.tab_status_notifi!!.setBackgroundColor(R.color.color_nochange_notifile_status)
        }
        else
        {
            holder.tab_status_notifi!!.setBackgroundColor(R.color.color_change_notifile_status)
        }
        holder.itemView.setOnClickListener()
        {
            gotoDetail.gotoNotif(notif!![position]!!.getNO_ID())
            LocalData.notifi = notif!![position]
            notif!![position].setUNREAD("0")
        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return notif!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_title_notif: TextView?=null
        var tv_content_notif:TextView?=null
        var tab_status_notifi:RelativeLayout?=null


        init {
            this.tv_title_notif = itemView.findViewById(R.id.tv_title_notif) as TextView
            this.tv_content_notif = itemView.findViewById(R.id.tv_content_notif) as TextView
            this.tab_status_notifi = itemView.findViewById(R.id.tab_status_notifi) as RelativeLayout

        }
    }
}