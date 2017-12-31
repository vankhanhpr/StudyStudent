package com.example.studystudymorestudyforever.sqlite.dataclass

/**
 * Created by VANKHANHPR on 11/14/2017.
 */
data class User(var ID:Int,var NAME:String,var EMAIL:String,
                var PHONENUMBER:Int,var ADDRESS:String,
                var BIRTHDAY :Int,var HASHCODE:String,var ACTIVE:String,
                var USER_TYPE:String,var IMAGEPATH:String) {

    companion object {

        val TABLE_NAME = "books"
        val COLUMN_TITLE = "title"
        val COLUMN_AUTHOR = "author"
    }
}
