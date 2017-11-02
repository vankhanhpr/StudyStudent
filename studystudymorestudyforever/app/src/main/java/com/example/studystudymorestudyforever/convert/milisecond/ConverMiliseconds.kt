package com.example.studystudymorestudyforever.convert.milisecond

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by VANKHANHPR on 10/15/2017.
 */
class ConverMiliseconds {

    fun converttomiliseconds(date:String):Long
    {
        //String date_ = date;
        var sdf: SimpleDateFormat =  SimpleDateFormat("dd/MM/yyyy")
        try
        {
            var mDate: Date = sdf!!.parse(date)
            var timeInMilliseconds:Long = mDate.getTime()
            return timeInMilliseconds;
        }
        catch (e: ParseException)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0
    }


    fun converttomilisecondsTime(date:String):Long
    {
        //String date_ = date;
        var sdf: SimpleDateFormat =  SimpleDateFormat("hh:mm a")

        try
        {
            var mDate: Date = sdf!!.parse(date)
            var timeInMilliseconds:Long = mDate.getTime()
            Log.d("khakha1",""+timeInMilliseconds)
            return timeInMilliseconds;
        }
        catch (e: ParseException)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0
    }



    fun  converttodate(timeInMilliseconds:Long):String
    {
        //var sdf: SimpleDateFormat =  SimpleDateFormat("dd/MM/yyyy")
        try
        {
            var calendar = Calendar.getInstance()
            calendar.setTimeInMillis(timeInMilliseconds)
            var date = "" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR)
            return date

        }
        catch (e: ParseException)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ""
    }



}