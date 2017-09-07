

module.exports = {
    RESPONSE_MSG:function(TransId=0,ClientSeq=0,Data,Code,Message=0,Result) {
        this.TransId = TransId,
        this.ClientSeq= ClientSeq,
        this.Data = Data,
        this.Code = Code,
        this.Message = Message,
        this.Result =Result
    } 
}
    
