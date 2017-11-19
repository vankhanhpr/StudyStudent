const dbconnect = require('../Provider/dbconnect');
const TAG = "Chat provider";
var connectedclient = [];
var mapClientArray = [];
module.exports.connectedclient = connectedclient;
module.exports.mapClientArray = mapClientArray;
module.exports.popClientFromArray = (socket) => {
    if (connectedclient[socket.userfrom] === socket.id) {
        delete connectedclient[socket.userfrom];
        console.log("Pop client");
    }
    Object.keys(connectedclient).forEach(function (key, index) {
        console.log(key + "connected now in array");
    }, connectedclient);
}


module.exports.pushClientToArray = (socket) => {
    //[0] userfrom
    //[1] userto
    //[2] message
    for (let key in connectedclient) {
        if (key === socket.userfrom) {
            return false;
        }
    }
    console.log("client connected from pushClientToArray");
    connectedclient[socket.userfrom] = socket.id;
    return true;
}
module.exports.FindSocketIdToChatWith = (datafromclient) => {
    console.log("datafromclient " + JSON.stringify(connectedclient[datafromclient.InVal[1]]));
    Object.keys(connectedclient).forEach(function (key, index) {
        console.log("user in array : " + key + " with socket id: " + this[key]);
    }, connectedclient);
    return connectedclient[datafromclient.InVal[1]];

}
module.exports.FindSocketIdForNotif = (userid) => {
    Object.keys(connectedclient).forEach(function (key, index) {
        console.log("user in array : " + key + " with socket id: " + this[key]);
    }, connectedclient);
    return connectedclient[userid];

}

module.exports.SaveChat = async(array) => {
    /**
     * [0]:SENDERID
     * [1]:RECEIVERID
     * [2]:MESSAGE
     */
    try {
        let data = await dbconnect.executeQuery(`INSERT INTO MESSAGE (ID,SENDERID,RECEIVERID,MESSAGE_TYPE,MESSAGE,CREATED_AT) 
        VALUES (MESSAGE_SEQ.nextval,:senderid,:receiverid,0,:message,${new Date().getTime()})`, array, "I");
        if (data > 0) {
            data = {
                c0: "Y"
            }
        } else {
            data = {
                c0: "N"
            }
        }
        return data;
    } catch (err) {
        console.log(`SaveChat got error from ${TAG} log err: `, err)
    }

}
module.exports.LoadHistoryChat = async(array) => {
    /**
     * [0]:SENDERID
     * [1]:RECEIVERID
     * [2]:OFFSET FETMORE OLD CHAT
     */
    try {
        return await dbconnect.executeQuery(`SELECT * FROM MESSAGE 
        WHERE (SENDERID=:senderid AND RECEIVERID=:receiverid) OR (SENDERID=:receiverid AND RECEIVERID=:senderid)
         ORDER BY CREATED_AT ASC OFFSET :offset ROWS FETCH NEXT 10 ROWS ONLY`, array, "I");
    } catch (err) {
        console.log(`SaveChat got error from ${TAG} log err: `, err)
    }
}