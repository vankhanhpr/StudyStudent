package com.example.studystudymorestudyforever.adapter.adapter.filestudent

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.myinterface.ISetScheduleFileStudent
import com.example.studystudymorestudyforever.until.fragment_account.Student
import java.util.*


/**
 * Created by VANKHANHPR on 9/30/2017.
 */

class FileStudentAdapter(context: Context, student:ArrayList<Student>,setSchedu:ISetScheduleFileStudent):  RecyclerView.Adapter<FileStudentAdapter.ViewHolder>() {

    private var student :List<Student> ? = Collections.emptyList()
    private var mInflater: LayoutInflater?=null
    private  var setSchedu:ISetScheduleFileStudent

    init {
        this.mInflater = LayoutInflater.from(context)
        this.student = student
        this.setSchedu= setSchedu
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = mInflater!!.inflate(R.layout.child_file_student_layout, parent, false)
        var viewHolder = ViewHolder(view)
        return viewHolder
    }

    // binds the data to the textview in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animal = student!!.get(position)

        holder.person_name!!.setText(student!![position].getNAME().toString())
        holder.person_age!!.setText(student!![position].getEMAIL().toString())

        holder.itemView.setOnClickListener()
        {
            setSchedu.callSetSchedule(student!![position].getID().toString())
        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return student!!.size
    }

     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var person_name: TextView?=null
        var person_age:TextView?=null

        init {
            this.person_name = itemView.findViewById(R.id.person_name) as TextView
            this.person_age = itemView.findViewById(R.id.person_age) as TextView

        }
    }

}