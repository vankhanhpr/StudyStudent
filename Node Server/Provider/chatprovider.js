const dbconnect = require('../Provider/dbconnect');
const oracledb = require('oracledb');
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
     *
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
     * deprecated
     * [0]:SENDERID
     * [1]:RECEIVERID
     * [2]:OFFSET FETMORE OLD CHAT // not test
     * //offset OFFSET :offset ROWS FETCH NEXT 10 ROWS ONLY
     * thay LoadHistoryChat bằng LoadDetailConversation
     */
    try {
        console.log(array+" LoadHistoryChat param");
        return await dbconnect.executeQuery(`SELECT * FROM MESSAGE 
        WHERE (SENDERID=:senderid AND RECEIVERID=:receiverid) OR (SENDERID=:receiverid AND RECEIVERID=:senderid)
         ORDER BY CREATED_AT ASC `, array, "Q");
    } catch (err) {
        console.log(`LoadHistoryChat got error from ${TAG} log err: `, err)
    }
}
module.exports.LoadDetailConversation = async (array)=>{
    try {
        /**
         * [0]:senderid
         * [1]:receiverid
         */
        let newarray = [array[0]+"/"+array[1],array[1]+"/"+array[0]]// example "121/142" and "142/121"
        console.log(array+" LoadDetailConversation param");
        let result = await dbconnect.executeQuery(`SELECT M.ID,M.SENDERID,M.RECEIVERID,M.MESSAGE,M.MESSAGE_TYPE,M.ATTACHMENT_URL,M.CREATED_AT FROM CONVERSATION C
        INNER JOIN MESSAGE M ON M.CONVERSATIONID = C.CONVERSATIONID
        WHERE C.MAPCLIENT =:case1 OR C.MAPCLIENT = :case2
        ORDER BY M.CREATED_AT ASC`,newarray, "Q");
        return result;
    } catch (err) {
        console.log(`LoadDetailConversation got error from ${TAG} log err: `, err)
    }
}
module.exports.CreateNewConversation = async (array)=>{
    try {
        /**
         * [0]:senderid
         * [1]:receiverid
         */
        let newarray = [array[0]+"/"+array[1],array[1]+"/"+array[0]]// example "121/142" and "142/121"
        console.log(array+" CreateNewConversation param");
        let result = await dbconnect.executeQuery(`
        INSERT INTO CONVERSATION (CONVERSATIONID,
            CONVERSATION_NAME,
            MAPCLIENT,
            CREATED_DATE,USER1,USER2) VALUES (CONVERSATION_SEQ.nextval,'cuoc hoi thoi giua ${array[0]} và ${array[1]}'
            ,:mapclient,${new Date().getTime()},:user1,:user2)
        `, [newarray[0],...array], "I");
        
        return result= result>0?{c0:"Y"}:{c0:"N"};
    } catch (err) {
        console.log(`CreateNewConversation got error from ${TAG} log err: `, err);
    }
}
module.exports.LoadAllConversation = async (array)=>{
    try{
        /**
         * [0]:userid
         */
        console.log(array+" LoadAllConversation param");
        return await dbconnect.executeQuery(`select T.CONVERSATIONID,T.CONVERSATION_NAME,T.MAPCLIENT,T.RECENTLY_ACTIVITY,T.CREATED_DATE,T.UPDATE_AT,U.ID,U.NAME from table(CONV_USER_PROCESS(:userid)) T
        LEFT OUTER JOIN USER_ U ON T.USER_CONV = U.ID
        ORDER BY UPDATE_AT DESC`, [...array], "Q");
    }catch(err){
        console.log(`LoadAllConversation got error from ${TAG} log err: `, err);
    }
}
module.exports.FindConversation = async (array)=>{
    try{
        /**
         * [0]:senderid
         * [1]:receiverid
         */
        let newarray = [array[0]+"/"+array[1],array[1]+"/"+array[0]]// example "121/142" and "142/121"
        console.log(array+" FindConversation param");
        console.log(newarray[0]);
        return await dbconnect.executeQuery(`SELECT * FROM CONVERSATION C
        WHERE C.MAPCLIENT =:case1 OR C.MAPCLIENT =:case2 
        `
    ,newarray,"Q");

    }catch(err){
        console.log(`FindConversation got error from ${TAG} log err: `, err)
    }
}
module.exports.LoadMyConversation = async (array,callback)=>{
    try{
        /**
         * [0]:senderid
         * [1]:receiverid
         */
        
        
        var bindvars = {
            cursor:  { type: oracledb.CURSOR, dir: oracledb.BIND_OUT }
          }
        console.log(array+" LoadMyConversation param");
        dbconnect.doProcedureForRefCursor(`
        begin myprocedure(:cursor);end;
        `
    ,bindvars,(data)=>{
        callback(data);
    });      
    }catch(err){
        console.log(`LoadMyConversation got error from ${TAG} log err: `, err)
    }
}

