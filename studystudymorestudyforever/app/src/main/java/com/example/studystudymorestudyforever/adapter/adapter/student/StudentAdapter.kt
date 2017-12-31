package com.example.studystudymorestudyforever.adapter.adapter.student

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.convert.milisecond.ConverMiliseconds
import com.example.studystudymorestudyforever.myinterface.ISetTeacher
import com.example.studystudymorestudyforever.until.student.StudentofTeacher
import com.example.studystudymorestudyforever.until.teacher.TeacherofStudent
import java.util.*
import com.ramotion.foldingcell.FoldingCell
import org.jetbrains.anko.find


/**
 * Created by VANKHANHPR on 12/3/2017.
 */


class  StudentAdapter(context: Context, student: ArrayList<StudentofTeacher>, iter: ISetTeacher):  RecyclerView.Adapter<StudentAdapter.ViewHolder>()
{
    private var student :List<StudentofTeacher> ? = Collections.emptyList()
    private var mInflater: LayoutInflater?=null
    var iter: ISetTeacher

    init {
        this.mInflater = LayoutInflater.from(context)
        this.student = student
        this.iter=iter
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = mInflater!!.inflate(R.layout.child_folding_cell_list_student, parent, false)
        var viewHolder = ViewHolder(view)
        return viewHolder
    }

    // binds the data to the textview in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.person_name!!.setText(""+student!![position].getNAME().toString())
        holder.tv_telephone!!.setText(""+student!![position].getPHONENUMBER())

        holder.tv_name2!!.setText(""+student!![position].getNAME())
        holder.tv_phone2!!.setText(""+student!![position].getPHONENUMBER())

        holder.tv_birthday2!!.setText(""+ConverMiliseconds().converttodate(student!![position].getBIRTHDAY()))
        holder.tv_address2!!.setText(""+student!![position].getADDRESS())
        holder.tv_email2!!.setText(""+student!![position].getEMAIL())
        holder.itemView.setOnClickListener()
        {
            holder.fc.toggle(false)
        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return student!!.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var person_name: TextView?=null
        var tv_telephone: TextView ?=null
        val fc :FoldingCell
        var tv_name2:TextView?=null
        var  tv_phone2:TextView?=null
        var tv_birthday2:TextView?=null
        var tv_address2:TextView?=null
        var tv_email2:TextView?=null


        init {
            this.fc= itemView.findViewById(R.id.folding_cell) as FoldingCell
            this.person_name = itemView.findViewById(R.id.person_name) as TextView
            this.tv_telephone = itemView.findViewById(R.id.tv_telephone) as TextView

            this.tv_name2= itemView.findViewById(R.id.tv_name2) as  TextView
            this.tv_phone2= itemView.findViewById(R.id.tv_phone2) as  TextView
            this.tv_birthday2= itemView.findViewById(R.id.tv_birthday2) as  TextView
            this.tv_address2= itemView.findViewById(R.id.tv_address2) as  TextView
            this.tv_email2= itemView.findViewById(R.id.tv_email2) as TextView

            itemView.setOnClickListener(this)
        }
        override fun onClick(view: View) {
        }
    }

}