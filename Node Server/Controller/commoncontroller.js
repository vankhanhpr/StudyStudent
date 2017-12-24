const commonprovider = require('../Provider/commonprovider')
,JsonResponse = require("../FormatJson/JsonResponse.js")
, dataerror = require("../Exception/dataerror.js")
, parseutil = require("../Tools/parseutil")
, notificationprovider = require('../Provider/notificationprovider')
, Exception = require("../Exception/Exception")
, textvalidate = require("../Validate/textvalidate");


var TAG = "Common controller";
module.exports = (socket,datafromclient)=>{
    console.log("log from common : "+JSON.stringify(datafromclient));
    requestService(datafromclient,socket);
} 

const FindTeacher = async (datafromclient,socket)=>{
    try{
        /**
         * [0]:findstr teacher find with NAME or EMAIL
         * [1]:userid 
         */
        let data  = await commonprovider.FindTeacher(parseutil.parseIntPosition(datafromclient.InVal,1));
        let resp;
        if(textvalidate.isEmpty(data)){
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[]);
          }else{
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,data);
            if(data.length===0){
                resp.Result=0;
            }
          } 
          socket.emit("RES_MSG",resp);
    }catch(err){
        console.log(`FindTeacher got error from ${TAG} log err :`,err);
    }
}

const FindStudent = async (datafromclient,socket)=>{
    try{
        console.log("go find student");
        let data  = await commonprovider.FindStudent(datafromclient.InVal);
        let resp;
        if(!data){
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[]);
          }else{
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,data);
            if(data.length===0){
                resp.Result=0;
            }
          } 
          socket.emit("RES_MSG",resp);
      }catch(err){
        console.log(`FindStudent got error from ${TAG} log err :`,err);
      }
}

const GetAllNotification = (datafromclient, socket) => {
    /**
     * [0]:userid get all user notification for user
     * 
     */
    datafromclient.InVal = parseutil.parseIntArray(datafromclient.InVal)
    notificationprovider.GetAllNotification(datafromclient.InVal).then(resolve => {
        let resp = null;
        if (resolve) {
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, resolve, datafromclient.UserType);
        } else {
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq, null, datafromclient.UserType);
        }
        socket.emit("RES_MSG",resp);
    }).catch(err => {
        console.log(`GetAllNotification got error from ${TAG} `, err);
    });
}
const MarkAsReadNotification = (datafromclient, socket) => {
    /**
         * [0]:userid who read notification already
         * [1]:notif_id which user read
         */
    datafromclient.InVal = parseutil.parseIntArray(datafromclient.InVal)
    notificationprovider.MarkAsReadNotification(datafromclient.InVal).then(resolve => {
        let resp = null;
        if (resolve.c0==="Y") {
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq,[{c0:"Y"}], datafromclient.UserType);
        } else {
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq,[{c0:"N"}], datafromclient.UserType);
        }
        socket.emit("RES_MSG",resp);
    }).catch(err => {
        console.log(`MarkAsReadNotification got error from ${TAG} `, err);
    });
}

const requestService = (datafromclient,...args)=>{
    switch(datafromclient.ServiceName){
        case "findteacher" :FindTeacher(datafromclient,...args); break; 
        case "findstudent" :FindStudent(datafromclient,...args);break;
        case "getallnotification":GetAllNotification(datafromclient,...args);break;
        case "markasreadnotification":MarkAsReadNotification(datafromclient,...args);break;    
        default : break;
      }
}