package com.example.studystudymorestudyforever.until

/**
 * Created by VANKHANHPR on 9/8/2017.
 */
 object Value
{
    var service:String?="CLIENT_MSG"
    //var address:String?="http://192.168.1.17:8081"
    var address:String?="http://10.134.40.200:8081"
    //var address:String?="http://10.132.153.72:8081"
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
    var key_loginshape="call_loginwellcom1111"
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
     var key_getuserdetial= "callgetuserdetail"
    //change info user
     var workername_changeinfouser="usercontroller"
     var service_changeinfouser="updateuserinfo"
     var key_change_infor_user ="call_update_userinfo"

    //get list file student
     var workername_getfile="parentcontroller"
     var servicename_getfile="gethoso"
     var key_getfile="call_getfile"

    //get list teacher
     var workername_getlist_teacher="teachercontroller"
     var servicename_getlist_teacher="getlistfriend"
     var key_getlist_teacher="call_getlist.teacher"
     var key_getlist_tdialog="call_getlist.teacher.dialog"
    var key_get_listfriendchat="callgetlistfriendchat"
    //get list friend of student
     var workername_getlistfriendofstudent="studentcontroller"
     //var servicename_getlistfriendofstudent="getlistfriendofstudent"
     var servicename_getlistfriendofstudent="getlistteacherwhostudenthaveadd"
     var key_getlistfriendofstudent="callgetlistfriendofstudent"



    //Lấy danh sách giáo viên của phụ huynh
    var workername_getlistteacher="parentcontroller"
    var servicename_getlistteacher="getlistteacherhaveacceptrelationshipwithother"
    //var key_getlissttc="callgetlistteacherparent"

    //get list noticafication
     var workername_getlistnotif="commoncontroller"
     var servicename_getlistnotif="getallnotification"
     var key_getlistnotif="callgetlistnotif"

    //search friend
     var workername_searchfriend= "commoncontroller"
     var servicename_searchfriend="findteacher"
     var key_searchfriend= "callsearch"

    //Tìm kiếm phụ huynh và học sinh
    var workername_searchstudent= "commoncontroller"
    var servicename_searchstudent="findstudentandparent"
    var key_searchstudent= "callsearch"


    //add friend
     var workername_addfriend ="relationcontroller"
     var servicename_addfriend ="requestrelationship"
     var key_addfriend="call_addfriend"

    //signin  course
     var workername_signin_course="studentcontroller"
     var servicename_signin_course="registerschedule"
     var key_call_signin_course="callsignincourse"

    //update course techer
     var workername_updatecourse= "teachercontroller"
     var servicename_updatecourse="updatecoursedif"
     var key_updatecourse="callupdatecourse"

    //get list friend
    /*var workername_getlistfriend=""1491325200000/1512406800000
    var servicename_getlistfriend=""
    var key_getlistfriend="call_getlistfriend"*/

    //lay thong tin chat
     var workername_getlistmessage="chatcontroller"
     var servicename_getlistmessage="loadallconversation"
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
     var workername_get_coursestudent="studentcontroller"
     var servicename_get_coursestudent="getscheduleregistered"
     var key_getlistcoursestudent="callgetlistoursestudent"

  // Nhận và gửi tin nhắn
     var workername_sendmessager= "chatcontroller"
     var servicename_sendmessage="sendchat"
     var key_sentmessage="callsendmessage"

    //Gửi thông báo
    var workername_sentnotifilecation= "teachercontroller"
    var service_sentnotifilecation="addmanytoonenotif"
    var key_sendnotifile="call_sendnotifile"
    //get new message
    var wokername_getnewmessage=""
    var service_getnewmessage=""
    var key_getnewmessage="callgetbnewmessage"

    //đồng ý kết bạn
    var workername_agreeaddfriend="relationcontroller"
    var servicename_agreeaddfriend="acceptrelationship"
    var key_agreeaddfriend="callagreeaddfriend"

    //Không đồng ý kết bạn
    var workername_disagreefriend="relationcontroller"
    var servicename_disagreefriend="ignorenotification"
    var key_disagreeaddfriend="call_disagreeaddfriend"

    //Đã đọc thông báo
    var workername_readnotif="commoncontroller"
    var service_readnotif="markasreadnotification"
    var key_readnotifi="call_readnotif"

    //Lấy danh sách lớp học của giáo viên
    var workername_getlisstcoursestudent="studentcontroller"
    var servicename_getlisstcoursestudent="getscheduletoregister"
    var key_getlistcoursestudentbuck="callgetlistcoursestudent"

    //Lấy chi tiết lớp học
    var workername_getdetailteacher="studentcontroller"
    var servicename_getdetailteacher="getsubscheduleofteacher"
    var key_getdetailcourseteacher="callgetlistdetailcourseteacher"

    //Lấy nội dung tin nhắn
    var workername_getcontentmessage= "chatcontroller"
    var service_getcontentmessage="loaddetailconversation"
    var key_getcontent_message="callgetcontentmessage"

   /* //Thêm file cho phụ huynh
    var workername_addusertofile="parentcontroller"
    var servicename_addusertofile="requestparentrealationship"
    var key_addusertofile="call_add_file_student"*/

    //tìm kiếm học sinh để thêm vào teacher
    var workername_search_student_file="commoncontroller"
    var servicename_search_student_file="findstudent"
    var key_search_file="callsearchtofile"

    //Hủy kết bạn
    var workername_unfriend="relationcontroller"
    var servicename_unfriend="deleterequestrelationship"
    var key_call_unfriend="callunfriend"

    //Thêm hồ sơ học sinh cho phụ huynh
    var workername_addfilestudent="relationcontroller"
    var servicename_addfilestudent="requestrelationship"
    var key_addfilestudent="calladdfilestudent"

    //Hủy thêm hồ sơ học sinh cho phụ huynh
    var workername_unadddfileesstudent="relationcontroller"
    var servicename_unaddfilestudent="deleterequestrelationship"
    var key_unaddfilestudent="callunaddfilestudent"

    //Lấy lớp học phụ huynh học sinh
    var workername_setscheulestudentofparent="parentcontroller"
    var servicename_setschedulestudentofparent="getscheduleofstudentregistered"
    var key_setlistcourse_studentoffteacher="callgetlistoursestudent"

    //Hủy bạn bè
    var workername_cancel_friend="relationcontroller"
    var servicename_cancel_friend="deleterelationship"
    var key_cancelfriend="callcancelfriend"

    //Hủy môn học
    var workername_cancelcourse="studentcontroller"
    var servicename_cancelcourse="deletescheduleregistered"
    var key_cancelcourse="callcancelcourse"


}