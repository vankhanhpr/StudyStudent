package com.example.studystudymorestudyforever.adapter.adapter.chat

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.myinterface.ISelectAccountChat
import com.example.studystudymorestudyforever.until.teacher.TeacherData
import com.example.studystudymorestudyforever.until.teacher.TeacherofStudent
import java.util.*

/**
 * Created by VANKHANHPR on 10/5/2017.
 */

class SelectAcountChatAdapter(context: Context, teacher: ArrayList<TeacherofStudent>, iner:ISelectAccountChat):  RecyclerView.Adapter<SelectAcountChatAdapter.ViewHolder>() {
    private var teacher: List<TeacherofStudent>? = Collections.emptyList()
    private var mInflater: LayoutInflater? = null
    private var iner:ISelectAccountChat


    init {
        this.mInflater = LayoutInflater.from(context)
        this.teacher = teacher
        this.iner=iner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = mInflater!!.inflate(R.layout.child_teacher_layout, parent, false)
        var viewHolder = ViewHolder(view)
        return viewHolder
    }

    // binds the data to the textview in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animal = teacher!!.get(position)
        holder.itemView.setOnClickListener()
        {
            //lay data
            iner.selectaccount(teacher!![position].getID().toString())
            Log.d("idchat",teacher!![position].getID().toString())
        }
        holder.person_name!!.setText(teacher!![position].getNAME())
        holder.person_subject!!.setText(teacher!![position].getEMAIL())
    }

    // total number of rows
    override fun getItemCount(): Int {
        return teacher!!.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var person_name:TextView ? =null
        var person_subject:TextView?= null

        init {
            //myTextView = itemView.findViewById(R.id.person_photo) as TextView
            person_name = itemView.findViewById(R.id.person_name) as TextView
            person_subject = itemView.findViewById(R.id.person_subject) as TextView
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
        }
    }
}