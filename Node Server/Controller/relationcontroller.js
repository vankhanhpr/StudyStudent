const JsonResponse = require("../FormatJson/JsonResponse.js")
, dataerror = require("../Exception/dataerror.js")
, Exception = require("../Exception/Exception")
, express = require("express")
, parseutil = require('../Tools/parseutil')
, chatprovider = require("../Provider/chatprovider")
, relationprovider = require("../Provider/relationprovider")
, textvalidate = require("../Validate/textvalidate");

 module.exports = (socket,datafromclient)=>{
  requestService(datafromclient,socket);
}

const requestService =  (datafromclient,...args) =>{
    switch(datafromclient.ServiceName){
      case "friendrequest": FriendRequest(datafromclient,...args); break;
      case "refuserequest":  RefuseRequest(datafromclient,...args); break;
      case "acceptfriend": AcceptFriend(datafromclient,...args); break;
      default: throw new Exception.UndefineException("undefine exception something was wrong").showMessage(); break;
    }
}

const FriendRequest = (datafromclient,socket)=>{
      //InVal[0]:fromID
      //InVal[1]:toID
      //InVal[2]:fromName
      //...
      datafromclient.InVal = parseutil.parseIntPosition(datafromclient.InVal,0,1);
      let data = await relationprovider.FriendRequest([datafromclient.InVal]);//insert relationship
      let resultfindsocket = chatprovider.FindSocketIdToChatWith(datafromclient);//find socket to notify
      let respfromuser;//response to user who send this request
      let resptouser;//response to user who receive this request
      if(data>0){
        resptouser = new JsonResponse.RESPONSE_MSG_SUCCESS(resultfindsocket.id,datafromclient.ClientSeq,`You receive friend request from  ${datafromclient.InVal[2]}`);
        respfromuser = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,"You have been waited for the person who you send this request to be accepted");
      }else{
        respfromuser = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,"Request you have made has been decline");

      }
      socket.emit("RES_MSG",respfromuser);
      if(!textvalidate.isEmpty(resptouser)){
        socket.to(resultfindsocket.id).emit("RES_MSG",resptouser);
      }
}

const AcceptFriend = (datafromclient,socket)=>{
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
        resptouser = new JsonResponse.RESPONSE_MSG_SUCCESS(resultsocket.id,datafromclient.ClientSeq,`A request you have send is accepted from ${datafromclient.InVal[2]}`);
        respfromuser = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,"success accept friend");   
      }else{
        respfromuser = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,"fail accept friend");
      }
      socket.emit("RES_MSG",resp);
      if(!textvalidate.isEmpty(resptouser)){
        socket.to(resultsocket.id).emit("RES_MSG",resptouser);
      }
}



