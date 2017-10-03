package com.example.studystudymorestudyforever.adapter.adapter.teacher

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.until.teacher.TeacherData
import java.util.*
import android.widget.TextView
import android.R.attr.author
import android.util.Log
import com.example.studystudymorestudyforever.myinterface.ISetTeacher
import com.example.studystudymorestudyforever.until.datalocal.LocalData


/**
 * Created by VANKHANHPR on 10/1/2017.
 */
class  TeacherAdapter(context: Context, teacher: ArrayList<TeacherData> , iter:ISetTeacher):  RecyclerView.Adapter<TeacherAdapter.ViewHolder>()
{
    private var teacher :List<TeacherData> ? = Collections.emptyList()
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
        val animal = teacher!!.get(position)
        holder.itemView.setOnClickListener()
        {
            LocalData.teacher= teacher!!.get(position)
            iter.setteacher()
            Log.d("vankahnh12","vitri"+position)
        }

    }

    // total number of rows
    override fun getItemCount(): Int {
        return teacher!!.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var title: TextView ?=null
        private var author: TextView ?=null
        private var price: TextView ?=null

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
