package com.example.studystudymorestudyforever.adapter.adapter.chat

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.myinterface.ISelectAccountChat
import com.example.studystudymorestudyforever.until.teacher.TeacherData
import com.example.studystudymorestudyforever.until.user.User
import java.util.*

/**
 * Created by VANKHANHPR on 10/5/2017.
 */

class SelectAcountChatAdapter(context: Context, teacher: ArrayList<User>,iner:ISelectAccountChat):  RecyclerView.Adapter<SelectAcountChatAdapter.ViewHolder>() {
    private var teacher: List<User>? = Collections.emptyList()
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
            iner.selectaccount()
        }

    }

    // total number of rows
    override fun getItemCount(): Int {
        return teacher!!.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

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