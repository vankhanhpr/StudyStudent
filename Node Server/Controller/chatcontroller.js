const JsonResponse = require("../FormatJson/JsonResponse"),
dataerror = require("../Exception/dataerror"),
chatprovider = require("../Provider/chatprovider"),
parseutil = require('../Tools/parseutil'),
textvalidate = require("../Validate/textvalidate");

module.exports = (socket,datafromclient)=>{  
    requestService(datafromclient,socket);
}

var TAG = "Chat controller";
const requestService = (datafromclient,...args)=>{
    switch(datafromclient.ServiceName){
        case "sendchat" : sendChat(datafromclient,...args); break;
        case "typingto" : Typing(datafromclient,...args); break;
        case "loadhistorychat":loadHistoryChat(datafromclient,...args);break;
        case "loaddetailconversation":CreateOrLoadDetailConversation(datafromclient,...args);break;
        case "loadallconversation":LoadAllConversation(datafromclient,...args);break;
        case "buzzto" : break;
        default :break;
      }
}

const sendChat = async (datafromclient,socket)=>{
    try{
        let resp;
        let result = chatprovider.FindSocketIdToChatWith(datafromclient);
        if(result){
            //Inval[0] :userfrom
            //Inval[1] :userto
            //Inval[2] :message
            let savechat = await chatprovider.SaveChat(parseutil.parseIntPosition(datafromclient.InVal,0,1));
            if(savechat.c0==="Y"){
                console.log("save chat success");
                resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,[{message:datafromclient.InVal[2]}],datafromclient.UserType);
                socket.emit("RES_MSG",resp);
                resp.ClientSeq = -1;
                socket.to(result).emit("RES_MSG",resp);
            }else{
                console.log("can't save chat");
                resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[{c0:"N"}],datafromclient.UserType);
                resp.Message = "Can't save chat plz try again";
                resp.Code = "HIHI";
                socket.emit("RES_MSG",resp);
            }
            
        }else{
            //Save chat to remote db
            //User offline go here
            /**
             * [0]:SENDERID
             * [1]:RECEIVERID
             * [2]:MESSAGE
             */
            
            let data = await chatprovider.SaveChat(parseutil.parseIntPosition(datafromclient.InVal,0,1));
            let resp;
            if(data.c0==="Y"){
                resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,
                    datafromclient.ClientSeq,
                    [{c0:"Y",message:datafromclient.InVal[2]}],
                    datafromclient.UserType);
                
            }else{
                resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,
                    datafromclient.ClientSeq,
                    [{c0:"N"}],datafromclient.UserType);
            }
            socket.emit("RES_MSG",resp);
            
        }

    }catch(err){
        console.log(`sendChat got error from ${TAG} `, err);
    }
    

}

const loadHistoryChat = async (datafromclient,socket)=>{
    /**
     * deprecated
     * [0]:senderid:number
     * [1]:receiverid:number
     * [2]:offset:number fetch more message
     */
    try{
        let data = await chatprovider.LoadHistoryChat(parseutil.parseIntArray(datafromclient.InVal));
        console.log(data+" load history chat data");
        let resp;
        if(data){
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,data,datafromclient.UserType);
        }else{
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[],datafromclient.UserType);
        }
        socket.emit("RES_MSG",resp);
    }catch(err){
        console.log(`loadHistoryChat got error from ${TAG} `, err);
    }
}

const LoadAllConversation = async (datafromclient,socket)=>{
    /**
     * [0]:userid;
     */
    try{
        datafromclient.InVal = parseutil.parseIntArray(datafromclient.InVal);
        let data = await chatprovider.LoadAllConversation(datafromclient.InVal);
        let resp;
        if(data){
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,data,datafromclient.UserType);
        }else{
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[],datafromclient.UserType);
        }
        socket.emit("RES_MSG",resp);
    }catch(err){
        console.log(`LoadAllConversation got error from ${TAG} `, err);
    }
}

const CreateOrLoadDetailConversation = async (datafromclient,socket)=>{
    try{
        /**
         * description : first find conversation if not exist create one finally load detail conversation
         * [0]:senderid
         * [1]:receiverid
         */
        let resp;
        let findresult = await chatprovider.FindConversation(datafromclient.InVal);
        console.log(JSON.stringify(findresult));
        if(findresult.length===0){//Không tìm thấy conversation
            let createresult = await chatprovider.CreateNewConversation(datafromclient.InVal);
            if(createresult.c0==="N"){//Không thể tạo conversation
                resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[],datafromclient.UserType);
                resp.Message="Can't create new Conversation";
                resp.Code = "XYYYYY";
            }else{//Tạo thành công
                let loadresult = await chatprovider.LoadDetailConversation(datafromclient.InVal);
                if(loadresult){//lấy cuộc hội thoại trả về cho client
                    resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,loadresult,datafromclient.UserType);
                }else{//không lấy được
                    resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[],datafromclient.UserType);
                    resp.Message="Find conversation fail can't load old detail conversation";
                    resp.Code = "XYXYXY";
                }
            }
        }else{
            ////tìm thấy conversation
            let loadnewresult = await chatprovider.LoadDetailConversation(datafromclient.InVal);
            if(loadnewresult){
                resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,loadnewresult,datafromclient.UserType);
            }else{
                resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[],datafromclient.UserType);
                resp.Message="Find conversation success but can't load old detail conversation ";
                resp.Code = "XYXYXY";
            }
        }
        socket.emit("RES_MSG",resp);


    }catch(err){
        console.log(`CreateOrLoadDetailConversation got error from ${TAG} `, err);
    }
}

const StreamCreateOrLoadDetailConversation = async (datafromclient,socket)=>{
    try{
        /**
         * description : first find conversation if not exist create one finally load detail conversation
         * [0]:senderid
         * [1]:receiverid
         */
        


    }catch(err){
        console.log(`StreamCreateOrLoadDetailConversation got error from ${TAG} `, err);
    }
}

