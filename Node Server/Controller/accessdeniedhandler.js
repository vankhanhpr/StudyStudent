const exception = require('../Exception/exception'),
parseutil = require("../Tools/parseutil"),
JsonResponse = require('../FormatJson/JsonResponse');
module.exports = (socket,datafromclient)=>{
    let resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id,datafromclient.ClientSeq,[{c0:"N"}],datafromclient.UserType);
    resp.Message = " You are not allowed to use this api pls don't do this again";
    resp.Code = -10001;
    socket.emit("RES_MSG",resp);
}