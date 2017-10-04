const JsonResponse = require("../FormatJson/JsonResponse"),
dataerror = require("../Exception/dataerror"),
chatprovider = require("../Provider/chatprovider"),
textvalidate = require("../Validate/textvalidate");



module.exports = (io)=>{
    io.on("connection",(socket)=>{
        socket.on("CLIENT_MSG",(datafromclient)=>{
            if(!textvalidate.isEmpty(datafromclient)){
                if(datafromclient.WorkerName === "chatcontroller"){
                    requestService(datafromclient,socket,resp);
                }
                    
            }
        });
    });
}

const requestService = (datafromclient,...args)=>{
    switch(datafromclient.ServiceName){
        case "Chat" : sendChat(datafromclient,socket,resp); break;
        case "Typing" : Typing(datafromclient,socket,resp); break;
        case "Buzz" : break;
        default :break;
      }
}

const sendChat = (datafromclient,socket,resp)=>{
    chatprovider.saveChat((data,err)=>{
        if(err) throw err;
        let resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,
            datafromclient.ClientSeq,
            JSON.stringify(data));
        socket.emit("RES_MSG",resp);
    },datafromclient.data);
}

const loadHistoryChat = (datafromclient,socket)=>{
    chatprovider.loadChat((data,err)=>{
        if(err) throw err;
        let resp = new JsonResponse.RESPONSE_MSG(socket.id,
        datafromclient.ClientSeq,
        JSON.stringify(data),
        dataerror.SUCCESS.Code,
        dataerror.SUCCESS.Message,
        1);
        socket.emit("RES_MSG",resp);
    },datafromclient.data);
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
    },datafromclient.data);
}
