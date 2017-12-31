package com.example.studystudymorestudyforever.adapter.adapter.filestudent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.myinterface.ICallAddFriend
import com.example.studystudymorestudyforever.until.teacher.TeacherofStudent

/**
 * Created by VANKHANHPR on 11/27/2017.
 */

class ListUserFileDialogAdapter(context: Context, teacher:ArrayList<TeacherofStudent>, addFr: ICallAddFriend): BaseAdapter() {

    private var mInflator: LayoutInflater
    private var teacher: ArrayList<TeacherofStudent>? = null
    var addFr: ICallAddFriend

    init {
        this.mInflator = LayoutInflater.from(context)
        this.teacher = teacher
        this.addFr= addFr
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        var holder: ViewHolder
        val view: View?
        var course: TeacherofStudent = teacher!![position]

        if (convertView == null) {
            view = mInflator.inflate(R.layout.child_find_user_dialog, parent, false) //error in this line
            holder = ViewHolder(view)

            view!!.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }
        holder.tv_person_name!!.setText(teacher!![position].getNAME())
        holder.tv_course_name!!.setText(teacher!![position].getSUB_NAME())

        if(teacher!![position].getSTATUS() == 0)
        {
            holder.imv_add_friend!!.visibility= View.VISIBLE
            holder.imv_add_friend!!.setBackgroundResource(R.drawable.icon_add_friend2)
        }
        else{
            if(teacher!![position].getSTATUS() == 2)
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
            if(teacher!![position].getSTATUS()== 0)
            {
                teacher!![position].setSTATUS(-5)
                holder.imv_add_friend!!.setBackgroundResource(R.drawable.icon_add_friend)
                addFr.calldisaddFriend(teacher!![position].getID().toString())
            }
            else{
                teacher!![position].setSTATUS(0)
                addFr.calladdFriend(teacher!![position].getID().toString())
                holder.imv_add_friend!!.setBackgroundResource(R.drawable.icon_add_friend2)
            }
        }
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