package com.example.studystudymorestudyforever.adapter.adapter.schedule

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.studystudymorestudyforever.R
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
        val animal = listcourse!!.get(position)
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
        private var title: TextView?=null
        private var author: TextView?=null
        private var price: TextView?=null

        fun BookViewHolder(itemView: View) {

            /*title = itemView.findViewById(R.id.title) as TextView
            author = itemView.findViewById(R.id.author) as TextView
            price = itemView.findViewById(R.id.price) as TextView*/
        }

        init {
            //myTextView = itemView.findViewById(R.id.person_photo) as TextView
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
        }
    }
}