const parentprovider = require('../Provider/parentprovider'),
JsonResponse = require('../FormatJson/JsonResponse'),
chatprovider = require('../Provider/chatprovider'),
relationprovider = require('../Provider/relationprovider'),
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
        if(data){
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,data,datafromclient.UserType);
        }else{
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[],datafromclient.UserType);
            resp.Message ="Error occur plz try again";
        }
        socket.emit("RES_MSG",resp);
    }catch(err){
        console.log(`GetHoSo got error from ${TAG} `,err);
    }
}

const GetScheduleOfStudentRegistered = async (datafromclient,socket)=>{
    try{
        /**
         * [0]:student id;
         */
        let data = await parentprovider.GetScheduleOfStudentRegistered(parseutil.parseIntArray(datafromclient.InVal));
        let resp;
        if(data){
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id,datafromclient.ClientSeq,data,datafromclient.UserType);
        }else{
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[],datafromclient.UserType);
            resp.Message ="Error occur plz try again";
        }
        socket.emit("RES_MSG",resp);
    }catch(err){
        console.log(`GetScheduleOfStudentRegistered got error from ${TAG} `,err);
    }
}

const requestService = (datafromclient,...args)=>{
    switch(datafromclient.ServiceName){
        case "gethoso": GetHoSo(datafromclient,...args);break;
        case "getscheduleofstudentregistered":GetScheduleOfStudentRegistered(datafromclient,...args);break;
        default:break;
    }
}