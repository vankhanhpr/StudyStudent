package com.example.studystudymorestudyforever.until.fragment_account

/**
 * Created by VANKHANHPR on 9/30/2017.
 */
class Student {
    private var name = ""
    private var age = ""

    fun EX(name: String, age: String){
        this.name = name
        this.age = age
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getAge(): String {
        return age
    }

    fun setAge(age: String) {
        this.age = age
    }
}