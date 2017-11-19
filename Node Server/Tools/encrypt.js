var crypto = require('crypto');
var secretkey = require('../Tools/secretkey.js')
module.exports.encrypt = (something)=>{
    /**
     * something is value need to encrypt
     */
    let cipher = crypto.createCipher('aes-256-ctr',secretkey);
    let returnvalue = cipher.update(something,'utf8','hex');
    returnvalue+=cipher.final('hex');
    return returnvalue;
}

module.exports.randomNum = ()=>{
    /**
     * random 4 num
     */
    var text = "";
    var possible = "0123456789";
    for(let i=0;i<4;i++){text+=possible.charAt(Math.floor(Math.random()*possible.length));}
    return text;
}
module.exports.randommanydigit=(numofdigit)=>{
    /**
     * random specific num
     */
    var text = "";
    var possible = "0123456789";
    for(let i=0;i<numofdigit;i++){text+=possible.charAt(Math.floor(Math.random()*possible.length));}
    return parseInt(text);
}