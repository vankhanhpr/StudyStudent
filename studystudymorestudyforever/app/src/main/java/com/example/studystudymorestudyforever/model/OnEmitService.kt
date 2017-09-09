package com.example.studystudymorestudyforever.model

import android.util.JsonWriter
import android.util.Log
import com.example.myapplication.value.Hasmap
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.until.EmitService
import com.example.studystudymorestudyforever.until.OnService
import com.example.studystudymorestudyforever.until.Value
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import org.greenrobot.eventbus.EventBus
import com.github.nkzawa.socketio.client.Socket
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.StringWriter
import java.io.Writer
import kotlin.concurrent.thread

/**
 * Created by VANKHANHPR on 9/8/2017.
 */

class OnEmitService()
{
    //singleton
    companion object {
        private var instance : OnEmitService? = null
        fun getIns(): OnEmitService {
            if (instance == null) {
                Log.d("Call_Receive_Server", "getIns")
                instance = OnEmitService()
            }
            return instance!!
        }
    }

    var mSocket: Socket?= IO.socket(Value.address)
    var hasmap:ArrayList<Hasmap>?= ArrayList()
    var stnumber:Int?= 0
    var output : StringWriter?=null
    var temp2: EmitService?= null

    fun Sevecie()
    {
        Log.d("connect","Connect")
        var mSocket: Socket?= IO.socket(Value.address)
        mSocket!!.connect()
    }

    fun Disconnect()
    {
        mSocket!!.disconnect()
        mSocket!!.connect()
    }

    fun ListenEvent()
    {
        mSocket!!.on("RES_MSG",onNewMessage)
        mSocket!!.on("error",systemError)

        mSocket!!.on("connect",onConnect)
        mSocket!!.on("disconnect",onDisconnect)
    }

    var onNewMessage  =
            object : Emitter.Listener {
                override fun call(vararg args: Any) {
                    var json: JSONObject = args[0] as JSONObject
                    thread{
                        var x: OnService
                        Log.d("Call_Receive_Server","onNewMessage result: "+json.toString())
                        x= readJson(json)
                        var message: MessageEvent = MessageEvent()
                        var tm:Int?= x.getClientSeq()
                        var ttt:String?=null

                        for (i in 0..getIns().hasmap!!.size-1)
                        {
                            if(getIns().hasmap!![i].getKeySystem()== tm.toString())
                            {
                                Log.d("hass",hasmap!!.size!!.toString()+" "+ getIns().hasmap!![i].getKeyString()+x.getMessage())
                                ttt= getIns().hasmap!![i].getKeyString()
                                getIns().hasmap!![i].setStatus(0)
                            }
                        }
                        message.setKey(ttt!!)
                        message.setData(x)
                        //truyền data đi
                        EventBus.getDefault().post(message)
                    }
                }
            }

    var onConnect  =
            object : Emitter.Listener {
                override fun call(vararg args: Any) {
                    thread{
                        Log.d("onConnect","onConnect")
                        var connect: MessageEvent = MessageEvent()
                        /*connect.setData(AllValue.connect!!)
                        //truyền data đi
                        EventBus.getDefault().post(connect)*/
                    }
                }
            }

    //connect fail
    var systemError  =
            object : Emitter.Listener {
                override fun call(vararg args: Any) {
                    // var json: JSONObject = args[0] as JSONObject
                    thread{
                        Log.d("SystemError","SystemError")
                        OnEmitService.getIns()!!.Sevecie()
                    }
                }
            }

    //disconnect
    var onDisconnect  =
            object : Emitter.Listener {
                override fun call(vararg args: Any) {
                    // var json: JSONObject = args[0] as JSONObject
                    thread {
                        Log.d("Disconnect","Disconnect")
                        var error: MessageEvent = MessageEvent()
                        error.setKey(Value.disconnect!!)
                        EventBus.getDefault().post(error)
                        if(getIns().hasmap!!.size > 0) {
                            for (i in 0..getIns().hasmap!!.size-1)
                            {
                                hasmap!![i].setStatus(0)
                            }
                        }
                    }
                }
            }

    //call service

    fun Call_Service(workerName:String,serviceName:String,input:Array<String>,key:String)
    {
        //khóa vòng đời của 1 service
        if(getIns().hasmap!!.size > 0)
        {
            for (i in 0..getIns().hasmap!!.size-1)
            {
                if(getIns().hasmap!![i].getKeyString()==key && getIns().hasmap!![i].getStatus()==1)
                {
                    Log.d("hass","Da gui mot lan vui long cho")
                    return
                }
            }
        }
        //map 1 key String với 1 số Int
        getIns().stnumber = getIns().stnumber!! + 1
        var tem:Hasmap= Hasmap()
        tem.setKeySystem(getIns().stnumber!!.toString())
        tem.setKeyString(key)
        tem.setStatus(0)//set trang thai cua yeu cau gui di
        Log.d("Call_Receive_Server",tem.getKeyString()+tem.getKeySystem()+tem.getStatus())
        hasmap!!.add(tem)
        output=null
        output= StringWriter()
        temp2= writeSV(stnumber!!,workerName,serviceName,input)

        try
        {
            writeJsonStream(output!!,temp2!!)
            mSocket!!.emit(Value.service,output)
            Log.d("Call_Receive_Server",output.toString())
        }
        catch (e:Exception)
        {
            Log.d("Error",e.toString())
        }
    }

    fun writeSV(clientSeq:Int,workerName:String,serviceName:String,input:Array<String>):EmitService
    {
        var x: EmitService = EmitService()

        x.setClientSeq(clientSeq)
        x.setWorkerName(workerName)
        x.setServiceName(serviceName)
        x.setTimeOut(15)
        x.setOperation("Q")
        x.setInVal(input)
        x.setTotInVal(input.size)
        return x
    }

    //doc file Json
    fun readJson(json: JSONObject): OnService
    {
        var TransId: String? =json.getString("TransId")
        var ClientSeq:Int? =json.getInt("ClientSeq")
        //jsonArray
        var jsonOj:String?= json.getString("Data")
        var s:String ="{c0:N}"
        var js2 = JSONObject(s)
        var list2:ArrayList<JSONObject> = ArrayList()
        list2.add(js2)
        if(jsonOj!="") {
            var js2 = JSONArray(jsonOj)
            list2.clear()
            for (i in 0..js2!!.length() - 1)
            {
                list2.add(js2.getJSONObject(i))
            }
        }
        var Code :String? =json.getString("Code")
        var Message:String? =json.getString("Message")
        var Result :String? =json.getString("Result")

        var ser : OnService = OnService()

        ser.setTransId(TransId!!)
        ser.setClientSeq(ClientSeq!!)
        ser.setData(list2)
        ser.setCode(Code!!)
        ser.setMessage(Message!!)
        ser.setResult(Result!!)
        return ser
    }

    @Throws(IOException::class)
    fun writeJsonStream(output1: Writer, json: EmitService)
    {
        var jsonWriter = JsonWriter(output1)
        jsonWriter.beginObject()// begin root

        jsonWriter.name("ClientSeq").value(json.getClientSeq())
        jsonWriter.name("WorkerName").value(json.getWorkerName())
        jsonWriter.name("ServiceName").value(json.getServiceName())
        jsonWriter.name("TimeOut").value(json.getTimeOut())
        jsonWriter.name("Operation").value(json.getOperation())

        var inval = json.getInVal()
        jsonWriter.name("InVal").beginArray()
        for (iv in inval) {
            jsonWriter.value(iv)
        }
        jsonWriter.endArray()
        jsonWriter.name("TotInVal").value(json.getTotInVal())
        jsonWriter.endObject()// end address
    }
}