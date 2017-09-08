package com.example.studystudymorestudyforever.until

import org.json.JSONObject

/**
 * Created by VANKHANHPR on 9/8/2017.
 */

class OnService()
{
    private var TransId: String? = null
    private var ClientSeq: Int? = 0
    private var Code :String? =null
    private  var Message: String? = null
    private var Result :String? =null
    private var Data: ArrayList<JSONObject>?=  null

    fun getData(): ArrayList<JSONObject>?{
        return Data
    }

    fun setData(data: ArrayList<JSONObject>) {
        Data = data
    }

    fun getTransId(): String? {
        return TransId
    }

    fun setTransId(transId: String) {
        TransId = transId
    }

    fun getClientSeq():Int?{
        return ClientSeq
    }
    fun setClientSeq(clientSeq: Int) {
        ClientSeq = clientSeq
    }

    fun getCode(): String? {
        return Code
    }

    fun setCode(code: String) {
        Code = code
    }

    fun getMessage(): String? {
        return Message
    }

    fun setMessage(message: String) {
        Message = message
    }

    fun getResult(): String? {
        return Result
    }

    fun setResult(result: String) {
        Result = result
    }
}