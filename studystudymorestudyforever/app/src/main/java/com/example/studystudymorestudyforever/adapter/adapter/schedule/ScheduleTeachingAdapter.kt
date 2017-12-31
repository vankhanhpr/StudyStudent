package com.example.studystudymorestudyforever.adapter.adapter.schedule

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.convert.milisecond.ConverMiliseconds
import com.example.studystudymorestudyforever.myinterface.ISelectCourse
import com.example.studystudymorestudyforever.until.course.ScheduleAdd
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import java.util.*

/**
 * Created by VANKHANHPR on 10/8/2017.
 */
class ScheduleTeachingAdapter (context: Context, listcourse: ArrayList<ScheduleAdd>, iter: ISelectCourse):  RecyclerView.Adapter<ScheduleTeachingAdapter.ViewHolder>(){
    private var listcourse :List<ScheduleAdd> ? = Collections.emptyList()
    private var mInflater: LayoutInflater?=null
    var iter: ISelectCourse

    init {
        this.mInflater = LayoutInflater.from(context)
        this.listcourse = listcourse
        this.iter=iter
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = mInflater!!.inflate(R.layout.child_teacher_schedule_teaching, parent, false)
        var viewHolder = ViewHolder(view)
        return viewHolder
    }

    // binds the data to the textview in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder!!.tv_namecourse!!.setText(listcourse!![position]!!.getSUB_NAME().toString())
        var starttime= ConverMiliseconds().converttodate(listcourse!![position]!!.getSTART_TIME().toLong())
        var endtime= ConverMiliseconds().converttodate(listcourse!![position]!!.getEND_TIME().toLong())
        holder!!.tv_time!!.setText(starttime+" đến " +endtime);

        holder.itemView.setOnClickListener()
        {
            iter.selectCourse()
            LocalData.course= listcourse!![position]
        }

    }

    // total number of rows
    override fun getItemCount(): Int {
        return listcourse!!.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
         var tv_namecourse: TextView?=null
         var tv_time: TextView?=null

        init {
            this.tv_namecourse = itemView.findViewById(R.id.tv_namecourse) as TextView
            this.tv_time = itemView.findViewById(R.id.tv_time) as TextView

            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
        }
    }
}