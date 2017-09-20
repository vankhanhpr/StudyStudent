package com.example.studystudymorestudyforever.fragment.teacher

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studystudymorestudyforever.R

/**
 * Created by VANKHANHPR on 9/13/2017.

 */
class  Teacher: Fragment()
{
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater!!.inflate(R.layout.main_teacher_fragment, container, false)
        return  view
    }
}
