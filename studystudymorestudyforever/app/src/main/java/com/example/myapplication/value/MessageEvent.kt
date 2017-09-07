package com.example.myapplication.value

import android.accessibilityservice.GestureDescription

/**
 * Created by VANKHANHPR on 9/6/2017.
 */

class MessageEvent()
{
    private  var key:String?=null
    private var data:On_Service?= On_Service()
     fun  MessageEvent(key:String,data:On_Service)
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
    fun getData(): On_Service? {
        return data
    }

    fun setData(service: On_Service) {
        this.data = data
    }
}