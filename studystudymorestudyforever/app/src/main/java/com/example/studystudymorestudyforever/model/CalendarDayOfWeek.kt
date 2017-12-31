package com.example.studystudymorestudyforever.model

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by VANKHANHPR on 11/2/2017.
 */
class CalendarDayOfWeek
{

    // lọc tất cả các ngày từ ngày bắt đầu đế ngày kết thúc  có thứ là dayofweek

    fun getDayofWeek(startTime:String,endTime:String,dayofweek:String):ArrayList<String>
    {
        var listDay:ArrayList<String> = arrayListOf()

        var liststarttime= startTime.split("/")
        var listendtime= endTime.split("/")


        var startDay:Int= Integer.parseInt(liststarttime[0])
        var startMonth:Int= Integer.parseInt(liststarttime[1])
        var startYear:Int = Integer.parseInt(liststarttime[2])

        var endDay:Int= Integer.parseInt(listendtime[0])
        var endMonth:Int= Integer.parseInt(listendtime[1])
        var endYear:Int = Integer.parseInt(listendtime[2])

        try {
            if (startYear == endYear) {
                if (startMonth == endMonth) {
                    var listDayEx: ArrayList<String> = getMondaysOfMounth(startMonth, dayofweek, endYear!!)
                    for (i in 0..listDayEx.size - 1) {

                        var s = listDayEx[i].split("/")

                        if (Integer.parseInt(s[0]) > startDay && Integer.parseInt(s[0]) <= endDay)
                        {
                            listDay.add(listDayEx[i])
                        }
                    }
                } else {
                    for (j in startMonth..endMonth) {

                        var listDayEx: ArrayList<String> = getMondaysOfMounth(j, dayofweek, endYear!!)

                        if (j == startMonth) {

                            for (i in 0..listDayEx.size - 1) {
                                //Log.d("dayofwe1",""+listDayEx[i] )
                                var s = listDayEx[i].split("/")
                                if (Integer.parseInt(s[0]) >= startDay) {
                                    listDay.add(listDayEx[i])
                                }
                            }
                        } else {
                            if (j == endMonth) {
                                for (i in 0..listDayEx.size - 1) {
                                    var s = listDayEx[i].split("/")
                                    if (Integer.parseInt(s[0]) <= endDay) {
                                        listDay.add(listDayEx[i])
                                    }
                                }
                            } else {
                                for (i in 0..listDayEx.size - 1) {
                                    listDay.add(listDayEx[i])
                                }
                            }
                        }
                    }
                }
            } else {
                for (k in startYear..endYear) {
                    if (k == startYear) {
                        for (j in startMonth..12) {
                            var listDayEx: ArrayList<String> = getMondaysOfMounth(j, dayofweek, k)

                            if (j == startMonth) {
                                for (i in 0..listDayEx.size - 1) {
                                    var s = listDayEx[i].split("/")
                                    if (Integer.parseInt(s[0]) >= startDay) {
                                        listDay.add(listDayEx[i])
                                    }
                                }
                            } else {
                                for (i in 0..listDayEx.size - 1) {
                                    listDay.add(listDayEx[i])
                                }
                            }

                        }
                    } else {
                        if (k == endYear) {
                            for (j in 1..endMonth) {
                                var listDayEx: ArrayList<String> = getMondaysOfMounth(j, dayofweek, k)
                                if (j == endMonth) {
                                    for (i in 0..listDayEx.size - 1) {
                                        var s = listDayEx[i].split("/")
                                        if (Integer.parseInt(s[0]) <= endDay) {
                                            listDay.add(listDayEx[i])
                                        }
                                    }
                                } else {
                                    for (i in 0..listDayEx.size - 1) {
                                        listDay.add(listDayEx[i])
                                    }
                                }
                            }
                        } else {
                            for (j in 1..12) {
                                var listDayEx: ArrayList<String> = getMondaysOfMounth(j, dayofweek, k)
                                for (i in 0..listDayEx.size - 1) {
                                    listDay.add(listDayEx[i])
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (e:Exception){}
        return  listDay
    }

    //Lọc tất cả các ngày có tháng là monnth có thứu là dayofweek, có năm là year
    fun getMondaysOfMounth(monthh:Int, dayofweek:String, year:Int):ArrayList<String>
    {
        var cal = Calendar.getInstance()
        cal.set(Calendar.YEAR,year)
        var listday:ArrayList<String> = arrayListOf()
        when (monthh)
        {
            1-> cal.set(Calendar.MONTH, Calendar.JANUARY)
            2-> cal.set(Calendar.MONTH, Calendar.FEBRUARY)
            3-> cal.set(Calendar.MONTH, Calendar.MARCH)
            4-> cal.set(Calendar.MONTH, Calendar.APRIL)
            5-> cal.set(Calendar.MONTH, Calendar.MAY)
            6-> cal.set(Calendar.MONTH, Calendar.JUNE)
            7-> cal.set(Calendar.MONTH, Calendar.JULY)
            8-> cal.set(Calendar.MONTH, Calendar.AUGUST)
            9-> cal.set(Calendar.MONTH, Calendar.SEPTEMBER)
            10-> cal.set(Calendar.MONTH, Calendar.OCTOBER)
            11-> cal.set(Calendar.MONTH, Calendar.NOVEMBER)
            12-> cal.set(Calendar.MONTH, Calendar.DECEMBER)
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
            listday!!.add(cal.get(Calendar.DAY_OF_MONTH).toString() + "/" + (cal.get(Calendar.MONTH) + 1) +"/"+ (cal.get(Calendar.YEAR)))
            cal.add(Calendar.DAY_OF_MONTH, 7)
        }
        return listday
    }
}