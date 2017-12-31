package com.example.studystudymorestudyforever.model

import android.util.JsonWriter
import android.util.Log
import com.example.myapplication.value.Hasmap
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.until.EX
import com.example.studystudymorestudyforever.until.EmitService
import com.example.studystudymorestudyforever.until.OnService
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import org.greenrobot.eventbus.EventBus
import com.github.nkzawa.socketio.client.Socket
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.StringWriter
import java.io.Writer
import javax.xml.validation.Validator
import kotlin.concurrent.thread

/**
 * Created by VANKHANHPR on 9/8/2017.
 */

class OnEmitService()
{
    var boolx:String= ""
    var clientSeq:Int= -3
    var fla:Boolean= true

    //singleton
    companion object {
        private var instance : OnEmitService? = null
        var i:Int =1
        fun getIns(): OnEmitService {

            if (instance === null) {
                Log.d("Call_Receive_Server", "getIns")
                instance = OnEmitService()
                i++
            }
            //Log.d("Call_Receive","khoi"+i)
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
        try {
            if (!mSocket!!.connected()) {
                mSocket!!.connect()
                Log.d("onConnect","onConnect")
            }
        }
        catch (e:Exception){}

    }


    fun Disconnect()
    {
        mSocket!!.disconnect()
        //mSocket!!.connect()
    }

    fun ListenEvent()
    {
        try {
            mSocket!!.on("RES_MSG", onNewMessage)
            mSocket!!.on("error", systemError)

            mSocket!!.on("connect", onConnect)
            mSocket!!.on("disconnect", onDisconnect)
        }
        catch (e:Exception){}
    }
    fun removeListener()
    {
        /*mSocket!!.off("RES_MSG",onNewMessage)
        mSocket!!.off("error",systemError)

        mSocket!!.off("connect",onConnect)
        mSocket!!.off("disconnect",onDisconnect)*/
    }

    var onNewMessage  =
            object : Emitter.Listener {
                override fun call(vararg args: Any) {
                    thread{
                        var json: JSONObject =  JSONObject(args[0].toString())
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
                                //Log.d("hass",hasmap!!.size!!.toString()+" "+ getIns().hasmap!![i].getKeyString()+x.getMessage())
                                if(getIns().hasmap!![i].getKeySystem()==boolx && clientSeq == x.getClientSeq())
                                {
                                    fla= false
                                }
                                else {
                                    fla=true
                                    ttt = getIns().hasmap!![i].getKeyString()
                                    boolx = ttt!!

                                    clientSeq = x.getClientSeq()!!

                                    getIns().hasmap!![i].setStatus(0)
                                }
                            }
                        }
                        if(x.getClientSeq()==-1)
                        {
                            ttt= "message"
                        }
                        else
                            if(x.getClientSeq()==-2)
                            {
                                ttt="notification"
                            }
                        //fla= false
                        if(fla) {
                            message.setKey(ttt!!)
                            message.setData(x)
                            //truyền data đi
                            Log.d("Call_Receive_Server112",boolx+ "  "+clientSeq)
                            EventBus.getDefault().post(message)
                        }
                        else{}
                    }
                }
            }

    var onConnect  =
            object : Emitter.Listener {
                override fun call(vararg args: Any) {
                    thread{
                        Log.d("onConnect","onConnect")
                        var connect: MessageEvent = MessageEvent()
                        connect.setKey(Value.connect!!)
                        //truyền data đi
                        EventBus.getDefault().post(connect)
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
        x.setUserType(LocalData.usertype)
        return x
    }

    //doc file Json
    fun readJson(json: JSONObject): OnService
    {
        var TransId: String? =json.getString("TransId")
        var ClientSeq:Int? =json.getInt("ClientSeq")
        //jsonArray
        var jsonArry:String?= json.getJSONArray("Data").toString()/*.substring(1,json.getJSONArray("Data").toString().length-1)*/

        var s:String ="{c0:N}"
        var js2 = JSONObject(s)
        var list2:ArrayList<JSONObject> = ArrayList()
        list2.add(js2)


        if(jsonArry!="") {
            var js2 = JSONArray(jsonArry)

            list2.clear()
            for (i in 0..js2!!.length() - 1)
            {
                list2.add(js2.getJSONObject(i))
            }
        }
        var Code :String? =json.getString("Code")
        var Message:String? =json.getString("Message")
        var Result :String? =json.getString("Result")
        var UserType:Int?= json.getInt("UserType")


        var ser : OnService = OnService()

        ser.setTransId(TransId!!)
        ser.setClientSeq(ClientSeq!!)
        ser.setData(list2)

        ser.setCode(Code!!)
        ser.setMessage(Message!!)
        ser.setResult(Result!!)
        ser.setUserType(UserType.toString())
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
        jsonWriter.name("UserType").value(json.getUserType())
        jsonWriter.endObject()// end address
    }
}