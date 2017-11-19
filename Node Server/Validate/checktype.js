module.exports.isObject = (param)=>{
    if(typeof param ==='object')return true;
    return false;
}
module.exports.isFunction = (param)=>{
    if(typeof param === 'function')return true;
    return false;
}
module.exports.isArray = (param)=>{
    if(Array.isArray(param))return true;
    return false;
}
module.exports.isNumber = (param)=>{
    if(typeof param === 'number')return true;
    return false;
}
module.exports.isString = (param)=>{
    if(typeof param === 'string')return true;
    return false;
}