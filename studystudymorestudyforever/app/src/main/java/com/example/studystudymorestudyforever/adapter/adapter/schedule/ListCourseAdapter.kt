package com.example.studystudymorestudyforever.adapter.adapter.schedule

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.until.course.CourseStudent


/**
 * Created by VANKHANHPR on 11/1/2017.
 */


class ListCourseAdapter(context: Context, schedule:ArrayList<CourseStudent>): BaseAdapter(){

    private var mInflator: LayoutInflater
    private  var schedule:ArrayList<CourseStudent>?=null
    init
    {
        this.mInflator = LayoutInflater.from(context)
        this.schedule=schedule
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        var holder: ViewHolder
        val view: View?
        var course:CourseStudent = schedule!![position]

        if(convertView == null)
        {
            view = mInflator.inflate(R.layout.child_view_schedule_student_layout,parent,false) //error in this line
            holder = ViewHolder(view)
            view!!.tag=holder
        }
        else
        {
            view=convertView
            holder = convertView.tag as ViewHolder
        }
        holder.tv_child_course!!.setText("Môn học: "+course.getSUB_NAME())
        holder.tv_child_time!!.setText("Thời gian học: "+course.getTIME())
        return view
    }

    override fun getItem(position: Int): CourseStudent {
        return schedule!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int{
        return schedule!!.size
    }

    class ViewHolder(row: View?) {
        var tv_child_course:TextView? = null
        var tv_child_time:TextView? = null
        init {
            this.tv_child_course = row?.findViewById(R.id.tv_child_course) as TextView
            this.tv_child_time = row?.findViewById(R.id.tv_child_time) as TextView
        }
    }
}
