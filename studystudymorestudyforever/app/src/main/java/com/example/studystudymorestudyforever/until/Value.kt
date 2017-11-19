package com.example.studystudymorestudyforever.until

/**
 * Created by VANKHANHPR on 9/8/2017.
 */
 object Value
{
    var service:String?="CLIENT_MSG"
    var address:String?="http://192.168.137.113:8081"
   // var address:String?="http://192.168.43.47:8081"
    var connect:String?="connect"
    var disconnect:String?="disconnectSystem"


    //key longin when welcom
    var key_login_welcom = "keyloginwwelcom"

    //key and value bundle
    var bundle:String?="keybundle"
    var value:String?= "value"
    var value1:String?= "value1"
    var value2:String?= "value2"


    //login_layout
    var workername_login= "usercontroller"
    var servicename_login="login"
    var key_login="calllogin"
    //signin
    var workername_signin="usercontroller"
    var servicename_signin="signup"
    var key_sigin="callsignin"

    //import_code
    var workername_import_code="usercontroller"
    var servicename_import_code="authenticatehashcode"
    var key_import_code="call.import.code"

    //restart pass word
    var workername_restartpass=""
    var servicename_restartpass=""
    var key_restartpass="call_restarpass"
    //change password
    var workername_changepasss="usercontroller"
    var servicename_changepass="changepass"
    var key_changepass="call_changepass"
    //get infor user
    var workername_getuser="usercontroller"
    var servicename_getuser="getinfouser"
    var key_getuser="callgetuser"
    //change info user
    var workername_changeinfouser="usercontroller"
    var service_changeinfouser="updateuserinfo"
    var key_change_infor_user ="call_update_userinfo"

    //get list file student
    var workername_getfile=""
    var servicename_getfile=""
    var key_getfile="call_getfile"

    //get list teacher
    var workername_getlist_teacher=""
    var servicename_getlist_teacher=""
    var key_getlist_teacher="call_getlist.teacher"
    var key_getlist_tdialog="call_getlist.teacher.dialog"

    //get list noticafication
    var workername_getlistnotif=""
    var servicename_getlistnotif=""
    var key_getlistnotif="callgetlistnotif"

    //search friend
    var workername_searchfriend= "commoncontroller"
    var servicename_searchfriend="findteacher"
    var key_searchfriend= "callsearch"

    //add friend
    var workername_addfriend ="studentcontroller"
    var servicename_addfriend ="addteacher"
    var key_addfriend="call_addfriend"

    //signin  course
    var workername_signin_course=""
    var servicename_signin_course=""
    var key_call_signin_course="callsignincourse"

    //update course techer
    var workername_updatecourse= "teachercontroller"
    var servicename_updatecourse="updatecoursedif"
    var key_updatecourse="callupdatecourse"

    //get list friend
    /*var workername_getlistfriend=""
    var servicename_getlistfriend=""
    var key_getlistfriend="call_getlistfriend"*/

    //lay thong tin chat
    var workername_getlistmessage=""
    var servicename_getlistmessage=""
    var key_getlistmessage="callgetlistchat"

    //Them lịch dạy
    var workername_adđcourse= "teachercontroller"
    var servicename_addcourse="addcourse"
    var key_addcourse="call_addcourse"
   //Lấy thông tin lịch dạy
   var workername_getlisttechershedule="teachercontroller";
   var servicename_getlistteachershedule="getallcourseofteacher"
   var key_getlistteachershedule="call_getlistteacherschedule"

  // LẤY chi tiết thông tin lớp học
  var workername_get_detailcourse="teachercontroller"
  var servicename_get_listdetailcourse="getcoursebyid"
  var key_getdetailcourse="callgetlistdetailcourse"
 //Lấy thông tin về lịch học cho học sin
  var workername_get_coursestudent=""
  var servicename_get_coursestudent=""
  var key_getlistcoursestudent="callgetlistoursestudent"

 // Nhận và gửi tin nhắn
  var workername_sendmessager= ""
  var servicename_sendmessage=""
  var key_sentmessage="callsendmessage"


}