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
        //12/01/2017
        Log.d("ngaytrongthang",date)
        var sdf: SimpleDateFormat =  SimpleDateFormat("dd/MM/yyyy")
        try
        {
            var mDate: Date = sdf!!.parse(date)
            var timeInMilliseconds:Long = mDate.getTime()
            return timeInMilliseconds;
        }
        catch (e: ParseException)
        {
            e.printStackTrace();
        }

        return 0
    }


    fun converttomilisecondsTime(date:String):Long
    {
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
        try
        {
            /*var calendar = Calendar.getInstance()
            calendar.setTimeInMillis(timeInMilliseconds)

            var day= Calendar.DAY_OF_MONTH
            var daystring:String= ""+day
            if(day<=9)
            {
                daystring= "0"+ day
            }
           // Log.d("ngaythangnam",""+calendar.get(Calendar.DAY_OF_MONTH)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.YEAR))
            var date = "" + daystring+"/" + (if(calendar.get(Calendar.MONTH)>9)calendar.get(Calendar.MONTH) else "0"+calendar.get(Calendar.MONTH))+ "/" + calendar.get(Calendar.YEAR)
            return date*/
            //Log.d("longla:",""+currentTime)
            var currentTime = timeInMilliseconds

            val tz = TimeZone.getDefault()
            val cal = GregorianCalendar.getInstance(tz)
            val offsetInMillis = tz.getOffset(cal.timeInMillis)

            //currentTime -= offsetInMillis.toLong()
            val date:Date = Date(currentTime)

            var sdf: SimpleDateFormat =  SimpleDateFormat("dd/MM/yyyy")
            var mDate = sdf!!.format(date!!)

            Log.d("longla2:",""+mDate)
            return  mDate.toString()

        }
        catch (e: ParseException)
        {
            e.printStackTrace();
        }


        return ""
    }
}