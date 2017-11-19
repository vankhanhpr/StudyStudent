function Exception(message){
    this.message = message;
}
Exception.prototype.showMessage = ()=>{console.log(this.message);};
function LogicalException(message){
    Exception.call(this,message);
}
LogicalException.prototype = Object.create(Exception.prototype);
function ParseJsonException(message){
    Exception.call(this,message);
}
ParseJsonException.prototype = Object.create(Exception.prototype);
function UndefineException(message){
    Exception.call(this,message);
}
function ParseExpcetion(message){
    Exception.call(this,message);
}
ParseExpcetion.prototype = Object.create(Exception.prototype);
function UnhandleException(message){
    Exception.call(this,message);
}
UnhandleException.prototype = Object.create(Exception.prototype);

module.exports.LogicalException = LogicalException;
module.exports.ParseJsonException = ParseJsonException;
module.exports.UndefineException = UndefineException;
module.exports.ParseExpcetion = ParseExpcetion;
module.exports.UnhandleException = UnhandleException;
