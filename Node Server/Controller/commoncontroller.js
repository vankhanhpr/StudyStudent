const commonprovider = require('../Provider/commonprovider')
,JsonResponse = require("../FormatJson/JsonResponse.js")
, dataerror = require("../Exception/dataerror.js")
, Exception = require("../Exception/Exception")
, textvalidate = require("../Validate/textvalidate");



module.exports = (io)=>{
    io.on("connection",(socket)=>{
        socket.on("CLIENT_MSG",(datafromclient)=>{
            if(!textvalidate.isEmpty(datafromclient)){
                if(datafromclient.WorkerName === "commoncontroller"){
                    requestService(datafromclient,socket,resp);
                }
                    
            }
        });
    });
}

const FindTeacher = (datafromclient,socket)=>{
    userprovider.FindTeacher((data,err)=>{
      let resp;
      if(err){
        resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,null);
        throw err.message;
      }else{
        resp = new JsonResponse.RESPONSE_MSG(socket.id,datafromclient.ClientSeq,JSON.stringify(data));
      } 
      socket.emit("RES_MSG",resp);
    },datafromclient.InVal);
  }
  

const requestService = (datafromclient,...args)=>{
    switch(datafromclient.ServiceName){
        case "findteacher" :commonprovider.FindTeacher(datafromclient,...args); break;        
        default :throw new Exception.UndefineException("undefine exception something was wrong").showMessage(); break;
      }
}