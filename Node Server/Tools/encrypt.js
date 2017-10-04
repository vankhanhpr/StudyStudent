var crypto = require('crypto');
var secretkey = require('../Tools/secretkey.js')
module.exports = (something)=>{
    /**
     * something is value need to encrypt
     */
    let cipher = crypto.createCipher('aes-256-ctr',secretkey);
    let returnvalue = cipher.update(something,'utf8','hex');
    returnvalue+=cipher.final('hex');
    return returnvalue;
}