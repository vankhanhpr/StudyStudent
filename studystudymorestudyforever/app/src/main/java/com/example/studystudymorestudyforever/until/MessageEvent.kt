package com.example.myapplication.value

import android.accessibilityservice.GestureDescription
import com.example.studystudymorestudyforever.until.OnService

/**
 * Created by VANKHANHPR on 9/6/2017.
 */

class MessageEvent()
{
    private  var key:String?=null
    private var data:OnService?= OnService()
     fun  MessageEvent(key:String,data:OnService)
     {
         this.key=key
         this.data=data
     }
    fun getKey(): String? {
        return key
    }

    fun setKey(key: String) {
        this.key = key
    }
    fun getData(): OnService? {
        return data
    }

    fun setData(data: OnService) {
        this.data = data
    }
}