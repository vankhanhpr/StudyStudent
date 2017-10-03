package com.example.studystudymorestudyforever.adapter.adapter.filestudent

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.until.fragment_account.Student
import java.util.*


/**
 * Created by VANKHANHPR on 9/30/2017.
 */

class FileStudentAdapter(context: Context, student:ArrayList<Student>):  RecyclerView.Adapter<FileStudentAdapter.ViewHolder>() {

    private var student :List<Student> ? = Collections.emptyList()
    private var mInflater: LayoutInflater?=null

    init {
        this.mInflater = LayoutInflater.from(context)
        this.student = student
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = mInflater!!.inflate(R.layout.child_file_student_layout, parent, false)
        var viewHolder = ViewHolder(view)
        return viewHolder
    }

    // binds the data to the textview in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animal = student!!.get(position)
        //holder.myTextView.setText(animal)
    }

    // total number of rows
    override fun getItemCount(): Int {
        return student!!.size
    }



     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        //var myTextView: TextView?=null

        init {
            //myTextView = itemView.findViewById(R.id.person_photo) as TextView
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
        }
    }

}