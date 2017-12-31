package com.example.studystudymorestudyforever.adapter.adapter.welcom

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studystudymorestudyforever.R

import android.widget.ImageView
import android.widget.Toast
import com.example.studystudymorestudyforever.myinterface.ISelectAccount
import com.example.studystudymorestudyforever.until.datalocal.LocalData

/**
 * Created by VANKHANHPR on 9/22/2017.
 */
class  ImageAdapter(mContext: Context,iter:ISelectAccount) : PagerAdapter() {

    var mContext:Context
    var iter:ISelectAccount
    init {
        this.mContext=mContext
        this.iter=iter
    }

    override fun isViewFromObject(view: View?, target: Any?): Boolean = view == target

    override fun getCount()= 3

    override fun destroyItem(container: ViewGroup?, position: Int, view: Any?) {
        container?.removeView(view as View)
    }


    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        var view = ImageView(mContext)
        view.scaleType= ImageView.ScaleType.CENTER_CROP


        var resId = when (position) {
            0 -> R.drawable.icon_select_student
            1 -> R.drawable.icon_select_parent
            else -> R.drawable.icon_select_teacher
        }
        view.setImageResource(resId)
        container?.addView(view)
        view.setOnClickListener()
        {
            LocalData.usertype=position

            iter.call()//goi chuyen activity

        }

        return view
    }


     override fun getItemPosition(`object`: Any?): Int {
        return super.getItemPosition(`object`)
    }


}