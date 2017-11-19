const commonprovider = require('../Provider/commonprovider')
,JsonResponse = require("../FormatJson/JsonResponse.js")
, dataerror = require("../Exception/dataerror.js")
, Exception = require("../Exception/Exception")
, textvalidate = require("../Validate/textvalidate");



module.exports = (socket,datafromclient)=>{
    requestService(datafromclient,socket);
} 

const FindTeacher = async (datafromclient,socket)=>{
    let data  = await commonprovider.FindTeacher(datafromclient.InVal);
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
  }
  

const requestService = (datafromclient,...args)=>{
    switch(datafromclient.ServiceName){
        case "findteacher" :FindTeacher(datafromclient,...args); break;        
        default :throw new Exception.UndefineException("undefine exception something was wrong").showMessage(); break;
      }
}