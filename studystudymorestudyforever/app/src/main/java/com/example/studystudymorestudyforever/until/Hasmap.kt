package com.example.myapplication.value

/**
 * Created by VANKHANHPR on 9/6/2017.
 */
class Hasmap {
    private var keySystem: String? = null
    private var keyString: String?=null
    private var status: Int = 0


    fun getKeySystem(): String? {
        return keySystem
    }

    fun setKeySystem(keySystem: String) {
        this.keySystem = keySystem
    }

    fun getKeyString(): String? {
        return keyString
    }

    fun setKeyString(keyString: String) {
        this.keyString = keyString
    }

    fun getStatus(): Int {
        return status
    }

    fun setStatus(status: Int) {
        this.status = status
    }
}