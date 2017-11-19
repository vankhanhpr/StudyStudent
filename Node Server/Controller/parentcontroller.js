const parentprovider = require('../Provider/parentprovider'),
JsonResponse = require('../FormatJson/JsonResponse'),
chatprovider = require('../Provider/chatprovider'),
parseutil = require('../Tools/parseutil'),
notificationprovider = require('../Provider/notificationprovider'),
textvalidate = require('../Validate/textvalidate');

var TAG = "parent controller";
module.exports = (socket,datafromclient)=>{
    requestService(datafromclient,socket);
}

const GetHoSo = async (datafromclient,socket)=>{
    try{
        let data = await parentprovider.GetHoSo(datafromclient.InVal);
        let resp;
        if(!textvalidate.isEmpty(data) && datafromclient.UserType===1){
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,data,datafromclient.UserType);
        }else{
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,null,datafromclient.UserType);
        }
        setTimeout(()=>{
            socket.emit("RES_MSG",resp);
        },datafromclient.TimeOut||4000);
    
    }catch(err){
        console.log(`GetHoSo got error from ${TAG} `,err);
    }
}

const RequestRelationShipStudent = async (datafromclient,socket)=>{
    try{
        /**
         * [0] : content
         * [1] : userid who receive notification
         */
        let data =await notificationprovider.addoneusertomanyNotification(parseutil.parseIntPosition(datafromclient.InVal,1));
        let findsocket = chatprovider.FindSocketIdToChatWith(datafromclient);
        let resp;
        if(findsocket){
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,
                [{c0:"Y",message:datafromclient.InVal[0]}],datafromclient.UserType)
            setTimeout(()=>{
                socket.to(findsocket.id).emit("RES_MSG",resp);
                socket.emit("RES_MSG",resp);
            },datafromclient.TimeOut||4000);
        }else{
            if(data){
                resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,
                    [{c0:"Y",message:datafromclient.InVal[0]}],datafromclient.UserType)
            }else{
                resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,
                    [{c0:"N"}],datafromclient.UserType)
            }
            setTimeout(()=>{
                socket.emit("RES_MSG",resp);
            },datafromclient.TimeOut||4000);
        }

    }catch(err){
        console.log(`RequestRelationShipStudent got error from ${TAG} `,err);
    }
}
const requestService = (datafromclient,...args)=>{
    switch(datafromclient.ServiceName){
        case "gethoso": GetHoSo(datafromclient,...args);break;
        default:break;
    }
}