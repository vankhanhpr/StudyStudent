package com.example.studystudymorestudyforever.model

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by VANKHANHPR on 11/2/2017.
 */
class CalendarDayOfWeek
{

    fun getDayofWeek(startTime:String,endTime:String):ArrayList<String>
    {
        var listDay:ArrayList<String> = arrayListOf()
        var startDay:String= startTime.substring(0,3)
        var startMonth:Int= Integer.parseInt(startTime.substring(3,5))
        var startYear:Int = Integer.parseInt(startTime.substring(6))

        var endDay:String= endTime.substring(0,3)
        var endMonth:Int= Integer.parseInt(endTime.substring(3,5))
        var endYear:Int = Integer.parseInt(endTime.substring(6))

        if(startMonth>endMonth){
            return listDay
        }
        if(startMonth == endMonth)
        {
            //var list1:ArrayList<String> = getMondaysOfJanuary(startMonth!!.toString(),)
        }

        return  listDay

    }

    fun getMondaysOfJanuary(monthh:String,dayofweek:String,year:Int):ArrayList<String>
    {
        var cal = Calendar.getInstance()
        cal.set(Calendar.YEAR,year)
        var listday:ArrayList<String> = arrayListOf()
        when (monthh)
        {
            "01"-> cal.set(Calendar.MONTH, Calendar.JANUARY)
            "02"-> cal.set(Calendar.MONTH, Calendar.FEBRUARY)
            "03"-> cal.set(Calendar.MONTH, Calendar.MARCH)
            "04"-> cal.set(Calendar.MONTH, Calendar.APRIL)
            "05"-> cal.set(Calendar.MONTH, Calendar.MAY)
            "06"-> cal.set(Calendar.MONTH, Calendar.JUNE)
            "07"-> cal.set(Calendar.MONTH, Calendar.JULY)
            "08"-> cal.set(Calendar.MONTH, Calendar.AUGUST)
            "09"-> cal.set(Calendar.MONTH, Calendar.SEPTEMBER)
            "10"-> cal.set(Calendar.MONTH, Calendar.OCTOBER)
            "11"-> cal.set(Calendar.MONTH, Calendar.NOVEMBER)
            "12"-> cal.set(Calendar.MONTH, Calendar.DECEMBER)
        }

        when(dayofweek)
        {
            "Thứ 2" ->cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            "Thứ 3" ->cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
            "Thứ 4" -> cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
            "Thứ 5" ->cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
            "Thứ 6" ->cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
            "Thứ 7"->cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
            "Chủ nhật"->cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        }

        cal.set(Calendar.DAY_OF_WEEK_IN_MONTH,1)

        val month = cal.get(Calendar.MONTH)
        var output = ""
        while (cal.get(Calendar.MONTH) == month) {
            output += cal.get(Calendar.DAY_OF_MONTH).toString() + "/" + (cal.get(Calendar.MONTH) + 1) +"/"+ (cal.get(Calendar.YEAR))+","
            cal.add(Calendar.DAY_OF_MONTH, 7)
            listday!!.add(cal.get(Calendar.DAY_OF_MONTH).toString() + "/" + (cal.get(Calendar.MONTH) + 1) +"/"+ (cal.get(Calendar.YEAR)))
        }
        Log.d("dayofweek",""+output.substring(0, output.length-1))
        return listday
    }
}