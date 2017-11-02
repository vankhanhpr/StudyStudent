package com.example.studystudymorestudyforever.fragment.scheduleteacher.addcourse


import android.app.TimePickerDialog
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.convert.milisecond.ConverMiliseconds
import com.example.studystudymorestudyforever.myinterface.ISetTimeDialog
import java.util.*

/**
 * Created by VANKHANHPR on 10/27/2017.
 */
class ViewHolder (v:View,iner:ISetTimeDialog)
{
     var rootview:View?=null
     var textviewnumberview:TextView? = null
     var spiner:Spinner?=null
     var textviewtime: TextView? = null
     var tab_settime:LinearLayout ? =null
     var iner:ISetTimeDialog

    var s:String = ""

    init {
        this.rootview = v
        this.iner= iner
        textviewnumberview= rootview!!.findViewById(R.id.tv_count_view) as TextView
        spiner =rootview!!.findViewById(R.id.sp_setdayofweek) as Spinner
        textviewtime= rootview!!.findViewById(R.id.tv_course_time_child_view) as TextView
        tab_settime= rootview!!.findViewById(R.id.tab_set_time_row ) as LinearLayout

        //Xét thời gian các child view
        spiner!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, i: Int, l: Long)
            {
                s= spiner!!.getSelectedItem().toString()
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        })
        //set thời gian cho các child view
        tab_settime!!.setOnClickListener()
        {
            iner.callSetTime(textviewtime!!)
        }
    }

    fun getNumber():String
    {
        return ""+textviewnumberview!!.text.toString()

    }
    fun getSpiner():String
    {
        return ""+s
    }
    fun getTime():String {
        var s= textviewtime!!.text.toString()

        return "" + s.toString()

    }

}