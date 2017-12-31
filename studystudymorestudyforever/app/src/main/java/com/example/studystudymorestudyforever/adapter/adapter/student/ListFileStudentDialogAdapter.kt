package com.example.studystudymorestudyforever.adapter.adapter.student

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.myinterface.ICallAddFriend
import com.example.studystudymorestudyforever.until.student.StudentofTeacher

/**
 * Created by VANKHANHPR on 12/3/2017.
 */

class ListFileStudentDialogAdapter(context: Context, student:ArrayList<StudentofTeacher>, addFr: ICallAddFriend): BaseAdapter() {

    private var mInflator: LayoutInflater
    private var student: ArrayList<StudentofTeacher>? = null
    var addFr: ICallAddFriend
    var position1 = -1;

    init {
        this.mInflator = LayoutInflater.from(context)
        this.student = student
        this.addFr= addFr
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        var holder: ViewHolder
        val view: View?
        var course: StudentofTeacher = student!![position]

        if (convertView == null) {
            view = mInflator.inflate(R.layout.child_find_user_dialog, parent, false) //error in this line
            holder = ViewHolder(view)

            view!!.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }
        holder.tv_person_name!!.setText(student!![position].getNAME())
        holder.tv_course_name!!.setText(student!![position].getSUB_NAME())

        if(student!![position].getSTATUS() == 0)
        {
            holder.imv_add_friend!!.visibility= View.VISIBLE
            holder.imv_add_friend!!.setBackgroundResource(R.drawable.icon_add_friend2)
        }
        else{
            if(student!![position].getSTATUS() == 3)
            {
                holder.imv_add_friend!!.visibility= View.GONE
            }
            else{
                holder.imv_add_friend!!.visibility= View.VISIBLE
                holder.imv_add_friend!!.setBackgroundResource(R.drawable.icon_add_friend)
            }
        }
        holder.imv_add_friend!!.setOnClickListener()
        {
            position1= position
            if(student!![position].getSTATUS()== 0)
            {
                student!![position].setSTATUS(-5)
                holder.imv_add_friend!!.setBackgroundResource(R.drawable.icon_add_friend)
                addFr.calldisaddFriend(student!![position].getID().toString())
            }
            else{
                student!![position].setSTATUS(0)
                addFr.calladdFriend(student!![position].getID().toString())
                holder.imv_add_friend!!.setBackgroundResource(R.drawable.icon_add_friend2)
            }
        }
        return view
    }

    override fun getItem(position: Int): StudentofTeacher {
        return student!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return student!!.size
    }

    class ViewHolder(row: View?) {
        var tv_person_name: TextView? = null
        var tv_course_name: TextView? = null
        var imv_add_friend: ImageView?=null

        init {
            this.tv_person_name = row?.findViewById(R.id.tv_person_name) as TextView
            this.tv_course_name = row?.findViewById(R.id.tv_course_name) as TextView
            this.imv_add_friend= row?.findViewById(R.id.imv_add_friend) as ImageView
        }
    }

}