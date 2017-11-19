package com.example.studystudymorestudyforever.fragment.schedule

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.ScheduleAdapter
import com.example.studystudymorestudyforever.until.fragment_account.ScheduleStudy
import com.ramotion.foldingcell.FoldingCell

/**
 * Created by VANKHANHPR on 9/13/2017.
 */
class Schedule: Fragment()
{
    var lv_schedulestudy:SwipeMenuListView?=null
    var srlLayout:SwipeRefreshLayout?=null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View? = inflater!!.inflate(R.layout.main_schedule_fragment, container, false)

        lv_schedulestudy= view!!.findViewById(R.id.lv_schedulestudy) as SwipeMenuListView
        srlLayout=view!!.findViewById(R.id.srlLayout) as SwipeRefreshLayout
        var tem:ArrayList<ScheduleStudy>?= arrayListOf()

        var x:ScheduleStudy?= ScheduleStudy()
        x!!.setC0("khanh")
        x.setC1("kahnh")
        tem!!.add(x)
        tem!!.add(x)

        var adapter= ScheduleAdapter(context,tem!!)
        lv_schedulestudy!!.adapter= adapter

        lv_schedulestudy!!.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            var abc:FoldingCell= view.findViewById(R.id.folding_cell) as FoldingCell
            abc.toggle(false)
        })

        var creator = SwipeMenuCreator { menu ->
            // create "open" item
            var openItem = SwipeMenuItem(context)
            // set item background
            openItem.background = ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25))
            // set item width
            openItem.width=200
            // set item title
            openItem.title = "Hủy"
            // set item title fontsize
            openItem.titleSize = 10
            // set item title font color
            openItem.titleColor = Color.WHITE
            // add to menu
            menu.addMenuItem(openItem)
        }
        lv_schedulestudy!!.setMenuCreator(creator)

        lv_schedulestudy!!.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {

            }
            override fun onScroll(listView: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                val topRowVerticalPosition = if (listView == null || listView.childCount === 0)
                    0
                else
                    lv_schedulestudy!!.getChildAt(0).getTop()
                srlLayout!!.setEnabled(topRowVerticalPosition >= 0)
            }
        })


        return  view
    }
}