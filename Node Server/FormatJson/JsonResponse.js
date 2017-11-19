
const dataerror = require("../Exception/dataerror.js");

module.exports.RESPONSE_MSG_SUCCESS = RESPONSE_MSG_SUCCESS;
module.exports.RESPONSE_MSG_FAIL = RESPONSE_MSG_FAIL;
function RESPONSE_MSG(TransId=0,ClientSeq=0,Data) {
    this.TransId = TransId;
    this.ClientSeq= ClientSeq;
    this.Data = Data;
}
function RESPONSE_MSG_SUCCESS(TransId=0,ClientSeq=0,Data,UserType=0) {
    RESPONSE_MSG.call(this,TransId,ClientSeq,Data)
    this.Code = dataerror.SUCCESS.Code;
    this.Message = dataerror.SUCCESS.Message;
    this.UserType = UserType;
    this.Result = 1;
}
function RESPONSE_MSG_FAIL(TransId =0,ClientSeq=0,Data,UserType=0){
    RESPONSE_MSG.call(this,TransId,ClientSeq,Data);
    this.Code = dataerror.WRONG_JSON_FORMAT.Code;
    this.Message = dataerror.WRONG_JSON_FORMAT.Message;
    this.UserType = UserType;
    this.Result = 0;
}
