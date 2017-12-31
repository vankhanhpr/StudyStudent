package com.example.studystudymorestudyforever.adapter.adapter.teacher

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.convert.milisecond.ConverMiliseconds
import com.example.studystudymorestudyforever.myinterface.ISetCourseStudent

import com.example.studystudymorestudyforever.until.course.CourseStudent
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.example.studystudymorestudyforever.until.user.User
import java.util.*

/**
 * Created by VANKHANHPR on 11/13/2017.
 */

class  CourseTeacherDialogAdapter(context: Context, course: ArrayList<CourseStudent>, iter: ISetCourseStudent):  RecyclerView.Adapter<CourseTeacherDialogAdapter.ViewHolder>()
{
    private var course :List<CourseStudent>
    private var mInflater: LayoutInflater?=null
    var iter: ISetCourseStudent

    init {
        this.mInflater = LayoutInflater.from(context)
        this.course = course
        this.iter=iter
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = mInflater!!.inflate(R.layout.child_schedule_layout, parent, false)
        var viewHolder = ViewHolder(view)
        return viewHolder
    }

    // binds the data to the textview in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_name_couser!!.setText(course!![position].getLOCATION())
        var starttime= ConverMiliseconds().converttodate(course!![position].getSTART_TIME().toLong())
        var endtime= ConverMiliseconds().converttodate(course!![position].getEND_TIME().toLong())
        holder.tv_time_course!!.setText(starttime+ " đến "+endtime)

        holder.itemView.setOnClickListener()
        {
            LocalData.selectCourseSt= course!!.get(position)
            iter.gotoDetailCourse()
        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return course!!.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tv_name_couser: TextView?=null
        var tv_time_course: TextView?=null

        init {
            this.tv_name_couser = itemView.findViewById(R.id.tv_name_couser) as TextView
            this.tv_time_course = itemView.findViewById(R.id.tv_time_course) as TextView
            itemView.setOnClickListener(this)
        }
        override fun onClick(view: View) {
        }
    }

}