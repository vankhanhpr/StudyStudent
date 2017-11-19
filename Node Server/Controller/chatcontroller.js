const JsonResponse = require("../FormatJson/JsonResponse"),
dataerror = require("../Exception/dataerror"),
chatprovider = require("../Provider/chatprovider"),
parseutil = require('../Tools/parseutil'),
textvalidate = require("../Validate/textvalidate");

module.exports = (socket,datafromclient)=>{  
    requestService(datafromclient,socket);
}


const requestService = (datafromclient,...args)=>{
    switch(datafromclient.ServiceName){
        case "sendchat" : sendChat(datafromclient,...args); break;
        case "typingto" : Typing(datafromclient,...args); break;
        case "loadhistorychat":loadHistoryChat(datafromclient,...args);break;
        case "buzzto" : break;
        default :break;
      }
}

const sendChat = async (datafromclient,socket)=>{
    let resp;
    let result = chatprovider.FindSocketIdToChatWith(datafromclient);
    if(result){
        //Inval[0] :userfrom
        //Inval[1] :userto
        //Inval[2] :message
        resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,[{message:datafromclient.InVal[2]}],datafromclient.UserType);
        setTimeout(()=>{
            try{
                socket.emit("RES_MSG",resp);
                resp.ClientSeq = -1;
                socket.to(result).emit("RES_MSG",resp);
            }catch(err){
                console.log(err);
            }
            
        },100);
    }else{
        //Save chat to remote db
        /**
         * [0]:SENDERID
         * [1]:RECEIVERID
         * [2]:MESSAGE
         */
        let data = await chatprovider.SaveChat(parseutil.parseIntPosition(datafromclient.InVal,0,1));
        let resp;
        if(data.c0==="Y"){
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,[{c0:"Y",message:datafromclient.InVal[2]}],datafromclient.UserType);
            
        }else{
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[{c0:"N"}],datafromclient.UserType);
        }
        socket.emit("RES_MSG",resp);
    }

}

const loadHistoryChat = async (datafromclient,socket)=>{
    /**
     * [0]:senderid:number
     * [1]:receiverid:number
     * [2]:offset:number fetch more message
     */
    let data = await chatprovider.LoadHistoryChat(parseutil.parseIntArray(datafromclient.InVal));
    let resp;
    if(data){
        resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,data,datafromclient.UserType);
    }else{
        resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,null,datafromclient.UserType);
    }
    socket.emit("RES_MSG",resp);
}

const Typing = (datafromclient,socket)=>{
    chatprovider.Typing((data,err)=>{
        let resp = new JsonResponse.RESPONSE_MSG(socket.id,
        datafromclient.ClientSeq,
        JSON.stringify(data),
        dataerror.SUCCESS.Code,
        dataerror.SUCCESS.Message,
        1);
        socket.emit("RES_MSG",resp);
    },datafromclient.InVal);
}



