package com.example.studystudymorestudyforever.sqlite.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.studystudymorestudyforever.sqlite.dataclass.User.Companion.TABLE_NAME
import org.jetbrains.anko.db.*

/**
 * Created by VANKHANHPR on 11/14/2017.
 */


class DatabaseHandler(mContext: Context): ManagedSQLiteOpenHelper(mContext,DATABASE_NAME,null, 1) {


    companion object {
        var instance: DatabaseHandler? = null
            val DATABASE_NAME = "MyStudy.db"
            val TABLE_NAME = "User"
            val COL_1 = "ID"
            val COL_2 = "NAME"
            val COL_3 = "EMAIL"
            val COL_4 = "PHONENUMBER"
            val COL_5 = "ADDRESS"
            val COL_6 = "BIRTHDAY"
            val COL_7 = "HASHCODE"
            val COL_8 = "ACTIVE"
            val COL_9 = "USER_TYPE"

        @Synchronized
        fun getInstance(mContext: Context): DatabaseHandler {
            if (instance == null) {
                instance = DatabaseHandler(mContext.applicationContext)
            }
            return instance!!
        }
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.dropTable(TABLE_NAME,true)
        Log.d("kiemtravaoread","ádfasdfasdfsadfsdf")
        db.execSQL("create table $TABLE_NAME (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,EMAIL TEXT, PHONENUMBER TEXT, ADDRESS TEXT,BIRTHDAY INTEGER, HASHCODE TEXT, ACTIVE TEXT, USER_TYPE INTEGER)")
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        Log.d("kiemtravao","ádfasdfasdfsadfsdf")
        db.dropTable(TABLE_NAME,true)
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
    fun insertData(id: Int, name: String,
                   email:String,phone:String,
                   address:String,birthaday:Int,
                   hascode:String,active:String,
                   usertye:Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, id)
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, email)
        contentValues.put(COL_4, phone)
        contentValues.put(COL_5, address)
        contentValues.put(COL_6, birthaday)
        contentValues.put(COL_7, hascode)
        contentValues.put(COL_8, active)
        contentValues.put(COL_9, usertye)
        db.insert(TABLE_NAME, null, contentValues)
        Log.d("ChangeInforUserlocal", "Xoa va cap nhat"+name)
    }


    fun updateData(id: Int, name: String,
                   email:String,phone:String,
                   address:String,birthaday:Int,
                   hascode:String,active:String,
                   usertye:Int):Boolean{

        val db = this.writableDatabase
        db.use {
            val contentValues = ContentValues()
            contentValues.put(COL_1, id)
            contentValues.put(COL_2, name)
            contentValues.put(COL_3, email)
            contentValues.put(COL_4, phone)
            contentValues.put(COL_5, address)
            contentValues.put(COL_6, birthaday)
            contentValues.put(COL_7, hascode)
            contentValues.put(COL_8, active)
            contentValues.put(COL_9, usertye)
            db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id.toString()))

            db.select(TABLE_NAME,"ID").whereArgs("ID={userID}","userID" to 142 ).parseList(object:MapRowParser<List<Int>>
            {
                override fun parseRow(columns: Map<String, Any?>): List<Int> {
                    Log.d("asdfasdfsdf ",columns.getValue("ID").toString())
                    var x:List<Int> = arrayListOf()
                    return x

                }
            })
            Log.d("asdfasdfsdf ","csdfdsfdsfdf")
        }
        return true
    }


    val allData : Cursor
        get(){
            val db = this.writableDatabase
            val res = db.rawQuery("select * from " + TABLE_NAME , null)
            return res
        }

    fun getData(id:Int):Cursor
    {
        var db = this.writableDatabase
/*        var res = db.select(TABLE_NAME,"*")
                .whereArgs("ID={id}","id" to id)
        */
        var res = db.rawQuery("select * from User where ID = "+id, null)

        return res
    }

    fun deleteData(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME,"ID = ?", arrayOf(id))

    }
}
val Context.database: DatabaseHandler
    get() = DatabaseHandler.getInstance(applicationContext)