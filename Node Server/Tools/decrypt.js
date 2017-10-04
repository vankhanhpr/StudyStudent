var crypto = require('crypto');
var secretkey = require('./Tools/secretkey.js')
module.exports = (something)=>{
    /**
     * something is value need to decrypt
     */
    let decipher = crypto.createDecipher("aes-256-ctr",secretkey)
    let dec = decipher.update(something,'hex','utf8')
    dec += decipher.final('utf8');
    return dec;
}