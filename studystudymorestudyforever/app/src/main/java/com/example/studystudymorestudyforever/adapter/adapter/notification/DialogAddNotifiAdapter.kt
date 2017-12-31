package com.example.studystudymorestudyforever.adapter.adapter.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.until.user.User

import android.util.SparseBooleanArray






/**
 * Created by VANKHANHPR on 11/6/2017.
 */



class DialogAddNotifiAdapter(context: Context, user:ArrayList<User>): BaseAdapter()
{

    private var mInflator: LayoutInflater
    private  var user:ArrayList<User>?=null
    var listBoolean:ArrayList<Boolean> = arrayListOf()

    var listUserget:ArrayList<User> ? = arrayListOf()

    var mCheckStates: SparseBooleanArray? = null

    init
    {
        this.mInflator = LayoutInflater.from(context)
        this.user=user
        for(i in 0..user.size-1)
        {
            listBoolean.add(false)
        }
        mCheckStates =  SparseBooleanArray(listUserget!!.size);
        /*this.mInflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater*/
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        var holder: ViewHolder
        val view: View?
        var course: User = user!![position]

        if(convertView == null){
            view = mInflator.inflate(R.layout.child_notification_dialogshowlistfriend,parent,false) //error in this line
            holder = ViewHolder(view)
            view!!.tag=holder
        }
        else
        {
            view=convertView
            holder = convertView.tag as ViewHolder
        }
        holder.tv_namefriend!!.setText(user!![position].getNAME())
        holder.checkbox!!.setTag(position)
        holder.checkbox!!.isChecked =mCheckStates!!.get(position,false)

        holder.checkbox!!.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

                mCheckStates!!.put(buttonView!!.getTag() as Int, isChecked)
                listBoolean[position]=isChecked
            }
        })



        return view
    }

    override fun getItem(position: Int): User {
        return user!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return user!!.size
    }

    class ViewHolder(row: View?) {
        var tv_namefriend: TextView? = null
        var checkbox:CheckBox?=null

        init {
            this.tv_namefriend = row?.findViewById(R.id.tv_namefriend) as TextView
            this.checkbox= row?.findViewById(R.id.checkbox) as CheckBox
        }
    }

}