package com.example.myapplication.value

/**
 * Created by VANKHANHPR on 9/6/2017.
 */
class  Emit_Service()
{
    private var ClientSeq : Int = 0            //Client seq
    private var WorkerName: String? = null    //Worker name values
    private var ServiceName: String? = null    //Service name values
    private var TimeOut: Int = 0            //TimeOut values
          //Approve status
    private var Operation: String? = "Q"        //Q: Query, I: Insert, U: Update, D: Delete, E: Export, P: Print
    private var InVal: Array<String>? = null  //List of input values
    private  var TotInVal: Int = 0//Total input values

    fun getClientSeq(): Int {
        return ClientSeq
    }

    fun setClientSeq(clientSeq: Int) {
        ClientSeq = clientSeq
    }

    fun getWorkerName(): String {
        return WorkerName!!
    }

    fun setWorkerName(workerName: String) {
        WorkerName = workerName
    }

    fun getServiceName(): String {
        return ServiceName!!
    }

    fun setServiceName(serviceName: String) {
        ServiceName = serviceName
    }

    fun getTimeOut(): Int {
        return TimeOut
    }

    fun setTimeOut(timeOut: Int) {
        TimeOut = timeOut
    }


    fun getOperation(): String {
        return Operation!!
    }

    fun setOperation(operation: String) {
        Operation = operation
    }

    fun getInVal(): Array<String> {
        return InVal!!
    }

    fun setInVal(inVal: Array<String>) {
        InVal = inVal
    }

    fun getTotInVal(): Int {
        return TotInVal
    }

    fun setTotInVal(totInVal: Int) {
        TotInVal = totInVal
    }
}