package com.example.studystudymorestudyforever.adapter.adapter.teacher

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studystudymorestudyforever.R
import java.util.*
import android.widget.TextView
import android.R.attr.author
import android.util.Log
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.studystudymorestudyforever.myinterface.ICallAddFriend
import com.example.studystudymorestudyforever.myinterface.ISetTeacher
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.example.studystudymorestudyforever.until.teacher.TeacherofStudent


/**
 * Created by VANKHANHPR on 10/1/2017.
 */
/*
class  TeacherAdapter(context: Context, teacher: ArrayList<TeacherofStudent> , iter:ISetTeacher):  RecyclerView.Adapter<TeacherAdapter.ViewHolder>()
{
    private var teacher :List<TeacherofStudent> ? = Collections.emptyList()
    private var mInflater: LayoutInflater?=null
    var iter: ISetTeacher

    init {
        this.mInflater = LayoutInflater.from(context)
        this.teacher = teacher
        this.iter=iter
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = mInflater!!.inflate(R.layout.child_teacher_layout, parent, false)
        var viewHolder = ViewHolder(view)
        return viewHolder
    }

    // binds the data to the textview in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.person_name!!.setText(teacher!![position].getNAME().toString())
        holder.person_subject!!.setText(teacher!![position].getSUB_NAME())

        holder.itemView.setOnClickListener()
        {
            LocalData.teacher= teacher!!.get(position)
            iter.setteacher(teacher!![position].getID().toString())
        }

    }

    // total number of rows
    override fun getItemCount(): Int {
        return teacher!!.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
         var person_name: TextView ?=null
         var person_subject: TextView ?=null

        init {
            this.person_name = itemView.findViewById(R.id.person_name) as TextView
            this.person_subject = itemView.findViewById(R.id.person_subject) as TextView
            itemView.setOnClickListener(this)
        }
        override fun onClick(view: View) {
        }
    }

}
*/


class TeacherAdapter(context: Context, teacher:ArrayList<TeacherofStudent>): BaseAdapter() {

    private var mInflator: LayoutInflater
    private var teacher: ArrayList<TeacherofStudent>? = null

    init {
        this.mInflator = LayoutInflater.from(context)
        this.teacher = teacher
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        var holder: ViewHolder
        val view: View?
        var course: TeacherofStudent = teacher!![position]

        if (convertView == null) {
            view = mInflator!!.inflate(R.layout.child_teacher_layout, parent, false)
            holder = ViewHolder(view)

            view!!.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }
        holder.person_name!!.setText(teacher!![position].getNAME().toString())
        holder.person_subject!!.setText(teacher!![position].getSUB_NAME())

        /*holder.imv_add_friend!!.setOnClickListener()
        {

        }*/
        return view
    }

    override fun getItem(position: Int): TeacherofStudent {
        return teacher!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return teacher!!.size
    }

    class ViewHolder(itemView: View?) {
        var person_name: TextView ?=null
        var person_subject: TextView ?=null

        init {
            this.person_name = itemView!!.findViewById(R.id.person_name) as TextView
            this.person_subject = itemView!!.findViewById(R.id.person_subject) as TextView
        }
    }

}