package com.example.studystudymorestudyforever.fragment.student

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studystudymorestudyforever.R

/**
 * Created by VANKHANHPR on 10/8/2017.
 */

class Student: Fragment()
{
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view: View = inflater!!.inflate(R.layout.main_teacher_fragment_student, container, false)
        return  view
    }
}
