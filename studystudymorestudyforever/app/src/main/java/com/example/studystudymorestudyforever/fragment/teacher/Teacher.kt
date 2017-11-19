package com.example.studystudymorestudyforever.fragment.teacher

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.studystudymorestudyforever.R
import com.example.studystudymorestudyforever.adapter.adapter.teacher.TeacherAdapter
import com.example.studystudymorestudyforever.model.OnEmitService
import com.example.studystudymorestudyforever.until.teacher.TeacherData
import android.view.View.OnFocusChangeListener
import android.widget.*
import com.example.myapplication.value.MessageEvent
import com.example.studystudymorestudyforever.until.Value
import com.example.studystudymorestudyforever.until.teacher.TeacherSearch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import android.widget.AdapterView.OnItemClickListener
import com.example.studystudymorestudyforever.fragment.teacher.courseteacher.SelectCourse
import com.example.studystudymorestudyforever.myinterface.ISetTeacher
import com.afollestad.materialdialogs.MaterialDialog
import com.example.studystudymorestudyforever.until.datalocal.LocalData
import com.example.studystudymorestudyforever.until.user.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.main_teacher_fragment.*
import kotlinx.android.synthetic.main.teacher_add_course.*
import java.lang.reflect.Type


/**
 * Created by VANKHANHPR on 9/13/2017.

 */
class  Teacher: Fragment(),ISetTeacher
{


    var call= OnEmitService.getIns()
    var email:String=""
    var recicleview_list_teacher:RecyclerView?=null
    var tab_add_teacher:LinearLayout?=null
    var dialog_add_teacher:Dialog?=null
    var tab_search : LinearLayout?=null
    var edt_search_friend:EditText?=null
    var progress_search_friend:ProgressBar?=null
    var mCountDownTimer: CountDownTimer? = null
    var tab_no_data:LinearLayout?=null
    var tab_teacher:RelativeLayout?=null
    var person_name:TextView?=null
    var course_name:TextView?=null
    var imv_add_friend:ImageView?=null
    var emailfriend:String?=null
    var listUser:ArrayList<TeacherData> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater!!.inflate(R.layout.main_teacher_fragment, container, false)
        EventBus.getDefault().register(this)

        recicleview_list_teacher= view.findViewById(R.id.recicleview_list_teacher) as RecyclerView
        tab_add_teacher= view.findViewById(R.id.tab_add_teacher) as LinearLayout

        //Lấy danh sách giáo viên lên từ sv
        getUser()

        /*var listteacher : ArrayList<TeacherData> = arrayListOf()
        var tem :TeacherData= TeacherData()

        var adapter= TeacherAdapter(context,listteacher,this)
        recicleview_list_teacher!!.layoutManager= LinearLayoutManager(context)
        recicleview_list_teacher!!.adapter= adapter*/


        tab_add_teacher!!.setOnClickListener()
        {
            dialog_add_teacher= Dialog(context)
            dialog_add_teacher!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_add_teacher!!.setContentView(R.layout.dialog_add_teacher_layout)
            tab_search= dialog_add_teacher!!.findViewById(R.id.tab_search) as LinearLayout
            edt_search_friend= dialog_add_teacher!!.findViewById(R.id.edt_search_friend) as EditText
            progress_search_friend= dialog_add_teacher!!.findViewById(R.id.progress_search_friend) as ProgressBar
            tab_no_data= dialog_add_teacher!!.findViewById(R.id.tab_no_data) as  LinearLayout
            tab_teacher= dialog_add_teacher!!.findViewById(R.id.tab_teacher) as RelativeLayout
            person_name= dialog_add_teacher!!.findViewById(R.id.person_name) as TextView
            course_name= dialog_add_teacher!!.findViewById(R.id.course_name) as TextView
            imv_add_friend=dialog_add_teacher!!.findViewById(R.id.imv_add_friend) as  ImageView



            var tab_cancel= dialog_add_teacher!!.findViewById(R.id.tab_cancel)

            tab_cancel!!.setOnClickListener() //cancel dialog
            {
                dialog_add_teacher!!.cancel()
            }

            dialog_add_teacher!!.show()

            tab_search!!.setOnClickListener()
            {
                progress_search_friend!!.visibility =View.VISIBLE

                var emailsearch = edt_search_friend!!.text.toString()
                var invalsearch :Array<String> = arrayOf(emailsearch)
                call.Call_Service(Value.workername_searchfriend,Value.servicename_searchfriend,invalsearch,Value.key_searchfriend)

                //timeout
                mCountDownTimer = object : CountDownTimer(15000, 1000) {
                    var i = 0
                    override fun onTick(millisUntilFinished: Long) {
                        i++
                        OnEmitService.getIns().Sevecie()
                        if (i == 5) {

                            for (i in 0..OnEmitService.getIns().hasmap!!.size - 1) {
                                OnEmitService.getIns().hasmap!![i].setStatus(0)
                            }
                        }
                        //mProgressBar.progress = i
                        if (i == 15) {
                            OnEmitService.getIns().Sevecie()
                            progress_search_friend!!.visibility = View.GONE
                        }
                    }

                    override fun onFinish() {
                        //Do what you want
                        i++
                        try {
                            progress_search_friend!!.visibility = View.GONE
                        } catch (e: Exception) {
                        }
                    }
                }
                mCountDownTimer!!.start()

            }

            imv_add_friend!!.setOnClickListener()
            {
                var ival2: Array<String> = arrayOf(LocalData.user.getID().toString(),listUser[0].getID().toString())
                call.Call_Service(Value.workername_addfriend,Value.servicename_addfriend,ival2,Value.key_addfriend)

                imv_add_friend!!.setImageResource(R.drawable.icon_add_friend2)
                Toast.makeText(context,"Đã add friend",Toast.LENGTH_SHORT).show()
            }
        }

        return  view
    }

    //Nhận kết quả trả về khi login_layout
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {

        if(event.getKey()==Value.key_getlist_teacher)
        {
            Log.d("ketquatrsve","là"+event.getData()!!.getResult().toString())
            if(event.getData()!!.getResult().toString()=="0")//không có dữ liệu
            {
                recicleview_list_teacher!!.visibility=View.GONE
                tab_no_data_teacher.visibility= View.VISIBLE


            }
            else{

                recicleview_list_teacher!!.visibility=View.VISIBLE
                tab_no_data_teacher.visibility= View.GONE
            }
        }
        Log.d("traveket","haha"+event.getKey().toString())
        if (event.getKey() == Value.key_searchfriend)
        {

            progress_search_friend!!.visibility= View.GONE
            if(event.getData()!!.getResult()=="1")
            {
                tab_no_data!!.visibility= View.GONE
                tab_teacher!!.visibility= View.VISIBLE

                var gson= Gson()
                var list= event!!.getData()!!.getData()
                /*var type : Type = object:TypeToken<List<User>>(){}.type
                var temp:List<User> = gson!!.fromJson(event!!.getData()!!.getData().toString(),type)
                Log.d("logjson",temp[0].getADDRESS().toString())*/

                for(i in 0..list!!.size-1)
                {
                    var temp:TeacherData = gson.fromJson(list[i].toString(),TeacherData::class.java)
                    listUser.add(temp)
                }
                person_name!!.setText(listUser[0].getNAME())
                course_name!!.setText(listUser[0].getADDRESS())
                Log.d("logjson",listUser[0].getADDRESS().toString())
            }
            else{
                var dialog = MaterialDialog.Builder(context)
                        .title("Lỗi")
                        .content("Không tìm thấy tài khoản người dùng")
                        .positiveText("Đồng ý")
                        .show()

            }
        }
        if (event.getKey()== Value.key_addfriend)
        {

        }
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    //lấy danh sách bạn bè
    fun getUser()
    {
        var inval3:Array<String> = arrayOf(LocalData.email)
        call.Call_Service(Value.workername_getlist_teacher,Value.servicename_getlist_teacher,inval3,Value.key_getlist_teacher)
    }

    // Đọc file Json để lấy kết quả
    fun readJson(json1: ArrayList<JSONObject>): TeacherSearch
    {
        var jsonO: JSONObject?=null
        if(json1.size>0)
        {
            jsonO = json1[0]
        }
        var tname: String? =jsonO!!.getString("teachername")
        var temail: String? =jsonO!!.getString("teacheremail")
        var course: String? =jsonO!!.getString("course")
        email=jsonO!!.getString("teacheremail")
        var teacher : TeacherSearch = TeacherSearch()
        teacher.setEmail(temail!!)
        teacher.setTeachername(tname!!)
        teacher.setCourse(course!!)
        return teacher
    }

    override fun setteacher() {
        var intent= Intent(context,SelectCourse::class.java)
        startActivity(intent)
    }
}
