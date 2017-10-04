const JsonResponse = require("../FormatJson/JsonResponse.js")
, dataerror = require("../Exception/dataerror.js")
, Exception = require("../Exception/Exception")
, express = require("express")
, textvalidate = require("../Validate/textvalidate");

module.exports = (io)=>{
    io.on("connection",(socket)=>{
        socket.on("CLIENT_MSG",(datafromclient)=>{
            if(!textvalidate.isEmpty(datafromclient)){
                //datafromclient = JSON.parse(datafromclient);
                if(datafromclient.WorkerName === "usercontroller"){
                  requestService(datafromclient,socket);
                }
              }
        });
    });
}

const requestService =  (datafromclient,...args) =>{
    switch(datafromclient.ServiceName){
      case "friendrequest": AuthenticateUser(datafromclient,...args); break;
      case "refuserequest":  listUserObject(datafromclient,...args); break;
      case "acceptfriendrequest": RegisterUser(datafromclient,...args); break;
      case "parentrequest" : ChangePassword(datafromclient,...args);break;
      case "getlistrequest" : AuthenticateHashCode(datafromclient,...args);break;
      case "getlistfriend":break;
      default: throw new Exception.UndefineException("undefine exception something was wrong").showMessage(); break;
    }
  }