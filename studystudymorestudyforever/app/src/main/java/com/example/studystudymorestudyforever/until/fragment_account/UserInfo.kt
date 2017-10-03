package com.example.studystudymorestudyforever.until.fragment_account

/**
 * Created by VANKHANHPR on 9/28/2017.
 */
 class UserInfo {
    private var username = ""
    private var userage = ""
    private var useraddress = ""
    private var userphone = ""

    fun UserInfo(username: String, userage: String, useraddress: String, userphone: String) {
        this.username = username
        this.userage = userage
        this.useraddress = useraddress
        this.userphone = userphone
    }

    fun getUsername(): String {
        return username
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun getUserage(): String {
        return userage
    }

    fun setUserage(userage: String) {
        this.userage = userage
    }

    fun getUseraddress(): String {
        return useraddress
    }

    fun setUseraddress(useraddress: String) {
        this.useraddress = useraddress
    }

    fun getUserphone(): String {
        return userphone
    }

    fun setUserphone(userphone: String) {
        this.userphone = userphone
    }
}