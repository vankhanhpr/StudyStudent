var userprovider = require("../Provider/userprovider.js")
    ,JsonResponse = require("../FormatJson/JsonResponse.js")
    , dataerror = require("../Exception/dataerror.js")
    , Exception = require("../Exception/Exception")
    , textvalidate = require("../Validate/textvalidate")
    , chatprovider = require('../Provider/chatprovider')
    , nodemailer = require("nodemailer")
    , encrypt = require("../Tools/encrypt")
    , decrypt = require("../Tools/decrypt")
    , parseutil = require("../Tools/parseutil")
    , mailconfig  = require("../Configuration/mailconfig.js");

var TAG = "user controller";

module.exports = (socket,datafromclient)=>{
  requestService(datafromclient,socket);
} 


const listUserObject = async  (datafromclient,socket)=>{
      try{
        let data = await userprovider.GetListUser();
        let resp;
        if(!data){
          resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[{c0:"N"}],datafromclient.UserType);
        }else{
          resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,[data],datafromclient.UserType);
        }
        socket.emit('RES_MSG',resp);
      }catch(err){console.log(err);}
}   
  

const AuthenticateUser = async (datafromclient,socket)=>{
        datafromclient.InVal.push(datafromclient.UserType);
        datafromclient.InVal[1] = encrypt.encrypt(datafromclient.InVal[1]);
        userprovider.CheckUserExist(datafromclient.InVal).then(data=>{
          if(data.id){
            socket.userfrom = data.id.toString();
          }
          let resp;
          if(socket.userfrom){
            if(chatprovider.pushClientToArray(socket)){
              if(data.c0==="Y"){
                resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,
                  datafromclient.ClientSeq,
                  [{c0:"Y"}],datafromclient.UserType);
              }else{
                resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,
                  datafromclient.ClientSeq,[{c0:"N"}],datafromclient.UserType);
              }
            }
            }else{
              resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[{c0:"N"}],datafromclient.UserType);
              resp.Message = "You or another people have logged in this account now pls contact us if you lost account";
              resp.Code= -1000;
              }
              socket.emit('RES_MSG',resp);
        }).catch(err=>{
          console.log(`AuthenticateUser got error from ${TAG} `,err);
          console.log("go here");
          resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[{c0:"N"}],datafromclient.UserType);
          resp.Message = "You or another people have logged in this account now pls contact us if you lost account";
          resp.Code= "X0X0X0";
          socket.emit('RES_MSG',resp);
        });
        
        
  
}
  


const RegisterUser = async (datafromclient,socket)=>{
      try{
        let random = encrypt.randomNum().toString();
        datafromclient.InVal.push(random);
        datafromclient.InVal.push(datafromclient.UserType);
        datafromclient.InVal[2] = encrypt.encrypt(datafromclient.InVal[2]);//encrypt pass here
        let resp;
        let subject = "Confirm Email Do Not Reply";
        let content = `Import code to verify your account ${random}`;
        //data[1] is Email
        let sendSuccess =await SendEmail(datafromclient.InVal[1],subject,content);
        if(!sendSuccess){
          resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[{c0:"N"}],datafromclient.UserType);
          resp.Message = "Mail not existed";
          resp.Code = "ZCVZNC<MX";
        }else{
          let data = await userprovider.RegisterUser(datafromclient.InVal);
          if(data){
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,[data],datafromclient.UserType);
          }else{
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[{c0:"N"}],datafromclient.UserType);
            resp.Message = "Can't save email to database plz retry";
            resp.Code = "ABXCZCV";
          }
          
        }
        socket.emit("RES_MSG",resp);
      }catch(err){
        console.log(`RegisterUser got error from ${TAG} `,err);
      }
     
}
  
const ChangePassword = async (datafromclient,socket)=>{
      try{
        let data = await userprovider.ChangePassword(datafromclient.InVal);
        let resp;
        if(data.c0==="N"){ 
          resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[data],datafromclient.UserType);
        }else{
          resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,
            [data],datafromclient.UserType);
        }
        socket.emit('RES_MSG',resp);
      }catch(err){
        console.log(`ChangePassword got error from ${TAG} `,err);
      }
      
}

const GetInfoUser = async (datafromclient,socket)=>{
  try{
    let data = await userprovider.GetInfoUser(datafromclient.InVal);
    let resp;
    if(!textvalidate.isEmpty(data)) {
      resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,
        data,datafromclient.UserType);
    }else{
      resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,null
        ,datafromclient.UserType);
    }
    socket.emit('RES_MSG',resp);
    
  }catch(err){
    console.log(`GetInfoUser got error from ${TAG} `,err);
  }
}

const UpdateInfo = async (datafromclient,socket)=>{
  try{
    datafromclient.InVal = parseutil.parseIntPosition(datafromclient.InVal,1,3,4);
    let data = await userprovider.UpdateInfoUser(datafromclient.InVal);
    let resp;
    if(data.c0==="Y"){
      resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,[data],datafromclient.UserType);
    }else{
      resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[data],datafromclient.UserType);
    }
    socket.emit('RES_MSG',resp);
    
  }catch(err){
    console.log(`UpdateInfo got error from ${TAG} `,err);
  }

}

const AuthenticateHashCode = async  (datafromclient,socket)=>{
  try{
    let data = await  userprovider.ActiveUser(datafromclient.InVal);
    let resp;
    if(data.c0==="Y"){
      resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,[data],datafromclient.UserType);
    }else{
      resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[data],datafromclient.UserType);
    }
    socket.emit('RES_MSG',resp);
   
  }catch(err){
    console.log(`AuthenticateHashCode got error from ${TAG} `,err);
  }

}

const SendEmail = async(toEmail,subJect,content)=>{
  let success = true;
  var transporter  = nodemailer.createTransport(mailconfig);
  
  var mailOption = {
      from : mailconfig.auth.user,
      to : toEmail,
      subject:subJect,
      text:content
  }
  let result =await transporter.sendMail(mailOption,(error,info)=>{
      if(error){
        console.log(error);
      }
      else{
        success = true;
        console.log("send success!!");
      }
      
  });
  transporter.close();
  console.log("log success "+success);
  return success;
}


const requestService =  (datafromclient,...args) =>{
  switch(datafromclient.ServiceName){
    case "login": AuthenticateUser(datafromclient,...args); break;
    case "listuser":  listUserObject(datafromclient,...args); break;
    case "signup": RegisterUser(datafromclient,...args); break;
    case "changepass" : ChangePassword(datafromclient,...args);break;
    case "authenticatehashcode" : AuthenticateHashCode(datafromclient,...args);break;
    case "updateuserinfo":UpdateInfo(datafromclient,...args); break;
    case "getinfouser": GetInfoUser(datafromclient,...args);break;
    case "getlistpendingrequest":GetListPending(datafromclient,...args);break;
    //default: throw new Exception.UndefineException("undefine exception something was wrong").showMessage(); break;
  }
}

