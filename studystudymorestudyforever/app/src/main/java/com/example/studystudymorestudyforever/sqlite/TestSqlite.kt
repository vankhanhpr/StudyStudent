package com.example.studystudymorestudyforever.sqlite

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.convert.milisecond.ConverMiliseconds
import com.example.studystudymorestudyforever.sqlite.dao.DatabaseHandler
import kotlinx.android.synthetic.main.testsqlite_layout.*

/**
 * Created by VANKHANHPR on 11/14/2017.
 */
class TestSqlite :AppCompatActivity(){

    internal var helper = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testsqlite_layout)


        tab_set.setOnClickListener()
        {
           var s =ConverMiliseconds().converttodate(1505667600000)
            var s2= ConverMiliseconds().converttodate(1505667600000)
            Log.d("st121212","asdfsa"+s+"--"+s2)
        }

    }
}