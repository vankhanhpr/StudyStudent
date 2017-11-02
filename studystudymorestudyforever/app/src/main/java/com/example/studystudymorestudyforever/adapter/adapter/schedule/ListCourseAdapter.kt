package com.example.studystudymorestudyforever.adapter.adapter.schedule

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
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
        /*this.mInflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater*/
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        var holder: ViewHolder
        val view: View?

        if(convertView == null){
            view = mInflator.inflate(R.layout.child_view_schedule_student_layout,parent,false) //error in this line
            holder = ViewHolder(view)
            view!!.tag=holder
        }
        else
        {
            view=convertView
            holder = convertView.tag as ViewHolder
        }
        return view
    }

    override fun getItem(position: Int): CourseStudent {
        return schedule!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return schedule!!.size
    }

    private class ViewHolder(row: View?) {
        /*var tv_time:TextView? = null
        var tv_doctor_name:TextView? = null
        var tv_mon_baby_name:TextView? = null
        var tv_status:TextView? = null*/

        /*init {
            this.tv_time = row?.findViewById(R.id.time_sche) as TextView
            this.tv_doctor_name = row?.findViewById(R.id.doctor_name) as TextView
            this.tv_mon_baby_name = row?.findViewById(R.id.mom_baby_name) as TextView
            this.tv_status = row?.findViewById(R.id.status) as TextView

        }*/
    }

}
