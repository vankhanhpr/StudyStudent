var userprovider = require("../Provider/userprovider.js")
    ,JsonResponse = require("../FormatJson/JsonResponse.js")
    , dataerror = require("../Exception/dataerror.js")
    , Exception = require("../Exception/Exception")
    , textvalidate = require("../Validate/textvalidate")
    , nodemailer = require("nodemailer")
    , mailconfig  = require("../Configuration/mailconfig.js");



module.exports = (io)=>{
  io.on("connection",(socket)=>{
    socket.on("CLIENT_MSG",(datafromclient)=>{
      console.log(datafromclient);
      if(!textvalidate.isEmpty(datafromclient)){
        //datafromclient = JSON.parse(datafromclient);
        if(datafromclient.WorkerName === "usercontroller"){
          requestService(datafromclient,socket);
        }
      }
      
    });
  });
}

const listUserObject = async  (datafromclient,socket)=>{
      try{
        let data = await userprovider.GetListUser();
        let resp;
        if(!data){
          resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,null);
        }else{
          resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,JSON.stringify(data));
        }
        socket.emit('RES_MSG',resp);
      }catch(err){console.log(err);}
}   
  

const AuthenticateUser = (datafromclient,socket)=>{
      userprovider.CheckUserExist((data,err)=>{
          let resp;
          if(err){
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,
              datafromclient.ClientSeq,null);
            throw err.message;
          }
          else{
            resp = new JsonResponse.RESPONSE_MSG(socket.id,
              datafromclient.ClientSeq,
              JSON.stringify(data));
          }
          socket.emit('RES_MSG',resp);          
          
      },datafromclient.InVal[0],datafromclient.InVal[1]);  
}
  


const RegisterUser = (datafromclient,socket)=>{
      userprovider.RegisterUser((data,err)=>{
        if(err) throw err.message;        
        console.log("Row inserted "+data);
        //suppose data[6] is Hashcode
        let subject = "Confirm Email Do Not Reply";
        let content = "<html><a href='localhost:8081/usercontroller/ConfirmEmail?hashcode='"+data[6]+"></a></html>"
        //suppose data[0] is Email
        SendEmail(datafromclient.data[0],subject,content);
          
      });
}
  
const ChangePassword = (datafromclient,socket)=>{
      userprovider.ChangePassword((data,err)=>{
        let res;
        if(err){ 
          throw err.message;
          res = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,null);
        }else{
           res = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,
            JSON.stringify(data))
           
        }
        socket.emit("RES_MSG",res);
      },datafromclient.InVal);
}

const GetInfoUser = (datafromclient,socket)=>{
    userprovider.GetInfoUser((data,err)=>{
      let resp;
      if(err) {
        resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,
          null);
        throw err.message;
      }else{
        resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,
          JSON.stringify(data));
      }
      socket.emit("RES_MSG",resp);
    },datafromclient.InVal);
}

const UpdateInfo = (datafromclient,socket)=>{
    userprovider.UpdateInfoUser((data,err)=>{
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


const SendEmail =(toEmail,subJect,content)=>{
  var transporter  = nodemailer.createTransport(mailconfig);
  var mailOption = {
      from : mailconfig.auth.user,
      to : toEmail,
      subject:subJect,
      html: content
  }
  transporter.sendMail(mailOption,(error,info)=>{
      if(error)console.log(err);

      console.log("email sent "+info)
      
  });
  transporter.close();
}

const requestService =  (datafromclient,...args) =>{
  switch(datafromclient.ServiceName){
    case "login": AuthenticateUser(datafromclient,...args); break;
    case "listuser":  listUserObject(datafromclient,...args); break;
    case "registeruser": RegisterUser(datafromclient,...args); break;
    case "changepass" : ChangePassword(datafromclient,...args);break;
    case "authenticatehashcode" : AuthenticateHashCode(datafromclient,...args);break;
    case "updateuserinfo":UpdateInfo(datafromclient,...args); break;
    case "getinfouser": GetInfoUser(datafromclient,...args);break;
    default: throw new Exception.UndefineException("undefine exception something was wrong").showMessage(); break;
  }
}

