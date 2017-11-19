const checktype = require('../Validate/checktype')
,exception = require('../Exception/exception');
var TAG = "parseUtil.js"
module.exports.parseIntArray = (array)=>{
    if(checktype.isArray(array)){
        try{
            let totalvalue = array.length;
            for(i=0;i<totalvalue;i++){
                array[i] = parseInt(array[i]);
            }
            return array;
        }catch(err){
            throw new exception.ParseExpcetion(`Parse All to Int value throw exception log :/n ${err}`).showMessage();
        }
    }else{
        return;
    }
    
}
module.exports.parseFloatArray = (array)=>{
    if(checktype.isArray(array)){
        try{
            let totalvalue = array.length;
            for(i=0;i<totalvalue;i++){
                array[i] = parseFloat(array[i]);
            }
            return array;
        }catch(err){
            throw new exception.ParseExpcetion(`Parse All to Float value throw exception log :/n ${err}`).showMessage();
        }
    }else{
        return;
    }
}
module.exports.parseStringArray = (array)=>{
    if(checktype.isArray(array)){
        try{
            let totalvalue = array.length;
            for(i=0;i<totalvalue;i++){
                array[i] = array[i].toString();
            }
            return array;
        }catch(err){
            throw new exception.ParseExpcetion(`Parse All to String value throw exception log :/n ${err}`).showMessage();
        }
    }else{
        return;
    }
    
}
module.exports.parseIntPosition = (array,...argsposition)=>{
    if(checktype.isArray(array)){
        try{
            let arrposition = [...argsposition];
            let totalvalue = arrposition.length;
            for(i=0;i<totalvalue;i++){
                array[arrposition[i]] = parseInt(array[arrposition[i]]);
            }
            return array;
        }catch(err){
            console.log(err);
            //throw new exception.ParseExpcetion(`Parse Int Position throw exception log :/n ${err}`).showMessage();
        }
    }else{
        return;
    }
}
module.exports.parseFloatPosition = (array,...argsposition)=>{
    if(checktype.isArray(array)){
        try{
            let arrposition = [...argsposition];
            let totalvalue = arrposition.length;
            for(i=0;i<totalvalue;i++){
                array[arrposition[i]] = parseFloat(array[arrposition[i]]);
            }
            return array;
        }catch(err){
            throw new exception.ParseExpcetion(`Parse Float Position throw exception log :/n ${err}`).showMessage();
        }
    }else{
        return;
    }
}
module.exports.parseStringPosition = (array,...argsposition)=>{
    if(checktype.isArray(array)){
        try{
            let arrposition = [...argsposition];
            let totalvalue = arrposition.length;
            for(i=0;i<totalvalue;i++){
                array[arrposition[i]] = array[arrposition[i]].toString();
            }
            return array;
        }catch(err){
            throw new exception.ParseExpcetion(`Parse String Position throw exception log :/n ${err}`).showMessage();
        }
    }else{
        return;
    }
}
