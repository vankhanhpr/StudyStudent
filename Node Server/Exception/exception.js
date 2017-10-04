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

function UndefineException(message){
    Exception.call(this,message);
}

ParseJsonException.prototype = Object.create(Exception.prototype);
module.exports = LogicalException;
module.exports = ParseJsonException;
module.exports = UndefineException;
