var userprovider = require("../Provider/userprovider.js");
var JsonResponse = require("../FormatJson/JsonResponse.js");
var DataError = require("../Exception/DataError.js");
exports.UserControllerObject = {
  listUserObject :{
    ListenClient: "CLIENT_MSG",
    Handler:(datafromclient,socket)=>{
      userprovider.GetListUser(function(data,err){
            if(err){
              console.error(err);
            }else{
              socket.emit('RES_MSG',new JsonResponse.RESPONSE_MSG( socket.id,
                datafromclient.ClientSeq,              
                JSON.stringify(data),
                DataError.SUCCESS.Code
                ,DataError.SUCCESS.Message
                ,1));                
            }
        });
      },   
  },
  

  AuthenticateUser : {
    ListenClient : "LOGIN",
    Handler : (datafromclient,socket)=>{
      userprovider.CheckUserExist((data,err)=>{
          if(err){
            console.error(err);
          }else{
            //not complete
            //ServerCallBack(JSON.stringify(data));
            console.log(data);
            let res = new JsonResponse.RESPONSE_MSG(socket.id,
              datafromclient.ClientSeq,
              JSON.stringify(data),
              DataError.SUCCESS.Code
              ,DataError.SUCCESS.Message
              ,1);
            console.log(res);
            socket.emit('RES_MSG',res);          
          }
      },datafromclient.InVal[0],datafromclient.InVal[1]);  
    } 
  },


  RegisterUser : {
    ListenClient: "REGISTER",
    Handler : (datafromclient,socket)=>{
      userprovider.RegisterUser((data,err)=>{
          if(err){
              console.log(err);
          }else{
              console.log("Row inserted "+data);
          }
      });
    }
  }
  

  
}

