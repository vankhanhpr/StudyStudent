const JsonResponse = require("../FormatJson/JsonResponse.js")
, dataerror = require("../Exception/dataerror.js")
, Exception = require("../Exception/Exception")
, express = require("express")
, parseutil = require('../Tools/parseutil')
, chatprovider = require("../Provider/chatprovider")
, notificationprovider = require("../Provider/notificationprovider")
, relationprovider = require("../Provider/relationprovider")
, textvalidate = require("../Validate/textvalidate");

 module.exports = (socket,datafromclient)=>{
  requestService(datafromclient,socket);
}
var TAG = "Relation controller";
const requestService =  (datafromclient,...args) =>{
    switch(datafromclient.ServiceName){
      case "friendrequest": FriendRequest(datafromclient,...args); break;
      case "refuserequest":  RefuseRequest(datafromclient,...args); break;
      case "acceptfriend": AcceptFriend(datafromclient,...args); break;
      case "requestrelationship":RequestRelationShip(datafromclient,...args);break;
      case "acceptrelationship":AcceptRelationShip(datafromclient,...args);break;
      case "ignorenotification":IgnoreNotification(datafromclient,...args);break;
      case "deleterequestrelationship":deleteRequestRelationship(datafromclient,...args);break;
      case "deleterelationship":DeleteRelationShip(datafromclient,...args);break;
      default:break;
    }
}

const FriendRequest = async (datafromclient,socket)=>{
      //InVal[0]:fromID
      //InVal[1]:toID
      //InVal[2]:fromName
      //...
      datafromclient.InVal = parseutil.parseIntPosition(datafromclient.InVal,0,1);
      let data = await relationprovider.RequestRelationShip([datafromclient.InVal[0],datafromclient.InVal[1]]);//insert relationship
      let resultfindsocket = chatprovider.FindSocketIdToChatWith(datafromclient);//find socket to notify
      let respfromuser;//response to user who send this request
      let resptouser;//response to user who receive this request
      if(data>0){
        resptouser = new JsonResponse.RESPONSE_MSG_SUCCESS(resultfindsocket,datafromclient.ClientSeq,`You receive friend request from  ${datafromclient.InVal[2]}`);
        respfromuser = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,"You have been waited for the person who you send this request to be accepted");
      }else{
        respfromuser = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,"Request you have made has been decline");

      }
      socket.emit("RES_MSG",respfromuser);
      if(!textvalidate.isEmpty(resptouser)){
        socket.to(resultfindsocket).emit("RES_MSG",resptouser);
      }
}

const AcceptFriend = async (datafromclient,socket)=>{
      //InVal[0]:fromID
      //InVal[1]:toID
      //InVal[2]:fromName
      datafromclient.InVal[0] = parseInt(datafromclient.InVal[0]);
      datafromclient.InVal[1] = parseInt(datafromclient.InVal[1]);
      let data = await relationprovider.AcceptFriend([datafromclient.InVal[0],datafromclient.InVal[1]]);
      let resultsocket = chatprovider.FindSocketIdToChatWith(datafromclient);
      let respfromuser;
      let resptouser;
      if(data>0){
        resptouser = new JsonResponse.RESPONSE_MSG_SUCCESS(resultsocket,datafromclient.ClientSeq,`A request you have send is accepted from ${datafromclient.InVal[2]}`);
        respfromuser = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,"success accept friend");   
      }else{
        respfromuser = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,"fail accept friend");
      }
      socket.emit("RES_MSG",resp);
      if(!textvalidate.isEmpty(resptouser)){
        socket.to(resultsocket).emit("RES_MSG",resptouser);
      }
}
const deleteRequestRelationship = async(datafromclient, socket) => {
  
      //InVal[0]:fromuser
      //InVal[1]:touser
      relationprovider.deleteRequestRelationship(parseutil.parseIntArray(datafromclient.InVal)).then(resolve => {
          let resp = null;
          if (resolve> 0) {
              resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, [{c0:"Y"}], datafromclient.UserType);
          } else {
              resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq, [{c0:"N"}], datafromclient.UserType);
          }
          socket.emit("RES_MSG",resp);
      }).catch(err => {
          console.log(`deleteRequestRelationship got error from ${TAG} `, err);
      });
  }
const RequestRelationShip = async(datafromclient,socket)=>{
  try{
   /**
   * data in array
   * [0]:fromuser
   * [1]:touser
   * [2]:content_notif
    */
        console.log("datafromclient "+JSON.stringify(datafromclient));
        let temparr = [...parseutil.parseIntArray(datafromclient.InVal),"Do you know him/her"];
        let data = await relationprovider.RequestRelationShip(temparr);
        let findsocket = chatprovider.FindSocketIdToChatWith(datafromclient);
        let resp;
        if(data.c0="Y"){
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,
                [{c0:"Y"}],datafromclient.UserType);
        }else{
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,
                [{c0:"N"}],datafromclient.UserType);

        }
        if(findsocket){
            if(data.c0 ==="Y"){
                socket.to(findsocket).emit("RES_MSG",resp);
                socket.emit("RES_MSG",resp);
            }else{
                socket.emit("RES_MSG",resp);
            }
            
        }else{
            socket.emit("RES_MSG",resp);
        }

    }catch(err){
        console.log(`RequestRelationShip got error from ${TAG} `,err);
    }
}

const AcceptRelationShip = async(datafromclient,socket)=>{
    try{
     /**
     * data in array
     * [0]:fromuser
     * [1]:touser
     * [2]:notificationid to update
    */
          let data = await relationprovider.AcceptRelationShip(parseutil.parseIntArray(datafromclient.InVal));
          let resp;
          if(data.c0="Y"){
              resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,
                  [{c0:"Y"}],datafromclient.UserType);
          }else{
              resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,
                  [{c0:"N"}],datafromclient.UserType);
  
          }
          socket.emit("RES_MSG",resp);
      }catch(err){
          console.log(`AcceptRelationShip got error from ${TAG} `,err);
      }
  }

const IgnoreNotification = async (datafromclient,socket )=>{
    try{
        /**
         * data in array
         * [0]:noti_id
         */
             let data = await notificationprovider.IgnoreNotification(parseutil.parseIntArray(datafromclient.InVal));
             let resp;
             if(data.c0="Y"){
                 resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,
                     [{c0:"Y"}],datafromclient.UserType);
             }else{
                 resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,
                     [{c0:"N"}],datafromclient.UserType);
     
             }
             socket.emit("RES_MSG",resp);
         }catch(err){
             console.log(`IgnoreNotification got error from ${TAG} `,err);
         }
}

const DeleteRelationShip = async (datafromclient,socket)=>{
    try{
        /**
         * data in array
         * [0]:fromuser
         * [1]:touser
         */
             let data = await relationprovider.DeleteRelationShip(parseutil.parseIntArray(datafromclient.InVal));
             let resp;
             if(data.c0="Y"){
                 resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,
                     [{c0:"Y"}],datafromclient.UserType);
             }else{
                 resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,
                     [{c0:"N"}],datafromclient.UserType);
     
             }
             socket.emit("RES_MSG",resp);
         }catch(err){
             console.log(`DeleteRelationShip got error from ${TAG} `,err);
         }
}