const dbconnect = require("./dbconnect.js")
,oracledb = require('oracledb')
,rannum = require('../Tools/encrypt')
, crypto = require("../Tools/encrypt");

var TAG = "Relation provider";

module.exports.RequestRelationShip = async(array)=>{
    //array[0]:fromuser
    //array[1]:touser
    console.log(array+" param RequestRelationShip");
    let data = await dbconnect.executeQuery(`INSERT INTO RELATIONSHIP (FROMUSER,TOUSER,STATUS,ACTION_USER_ID,
        REQUEST_TIME) VALUES (:FROMUSER,:TOUSER,0,:FROMUSER,:REQUEST_TIME)`,
    [...array,new Date().getTime()],
    "I");
    if(data){
        data=data.lenght>0?{c0:"Y"}:{C0:"N"};
    }else{
        data ={C0:"N"};
    }
    return data;
}
module.exports.AcceptFriend = async(array)=>{
    //array[0]:acceptuserid
    //array[1]:sendrequestuserid
    let data = await dbconnect.executeQuery(`UPDATE RELATIONSHIP SET STATUS=1,ACTION_USER_ID=:ACCEPTFROM 
            WHERE USERFROM:WHOSENDREQUEST AND USERTO:ACCEPTFROM`,[...array],"U");
    return data;
}
module.exports.IsTwoUserAreFriend = async (array)=>{
    let data = await dbconnect.executeQuery(`SELECT * FROM RELATIONSHIP WHERE USERFROM=:USERACTIVE AND USERTO=:USERPASSIVE`,[...array,"Q"]);
    return data;
}
module.exports.GetRegistrationID = async(array)=>{
    let data = await dbconnect.executeQuery(`SELECT * FROM USER_ WHERE ID=:ID`
    ,[...array],
    "Q");
    return data;
}
module.exports.deleteRequestRelationship= async (array)=>{
    try{
         /**
         * [0]:fromuser
         * [1]:touser
         */
        console.log(array+" deleteRequestRelationship param");
        return await dbconnect.executeQuery(`
        delete from RELATIONSHIP where (FROMUSER=:fromuser and TOUSER=:touser) or (FROMUSER=:touser and TOUSER=:fromuser)
        `,array,"D");
    }catch(err){
        console.log(`deleteRequestRelationship got error from relation provider log err: `,err);
    }
}
module.exports.RequestRelationShip = async (array)=>{
    try{
        /**
         * data in array
         * [0]:fromuser
         * [1]:touser
         * [2]:content_notif
         */
        let bindvar={
            fromuser:array[0],
            touser:array[1],
            requesttime:new Date().getTime(),
            randomnumber:rannum.randommanydigit(15),
            content_notif:array[2],
            outvar:{type:oracledb.NUMBER,dir:oracledb.BIND_OUT}
        }
        console.log(JSON.stringify(bindvar)+" RequestRelationShip param");
        let result = await dbconnect.executeQuery(`BEGIN
        REQUEST_RELATIONSHIP_PROC(:fromuser,:touser,
           :requesttime,:randomnumber,
           :content_notif,:outvar);END;`,bindvar,"PR");
            console.log(result);
        if(result){
            result = result.outvar===1?{c0:"Y"}:{c0:"N"};
        }else{
            result = {c0:"N"};
        }
        return result;
    }catch(err){
        console.log(`RequestRelationShip got error from relation provider log err `,err);
    }
}

module.exports.AcceptRelationShip = async (array)=>{
    try{
        /**
         * data in array
         * [0]:fromuser
         * [1]:touser
         * [2]:notificationid
         */
        let bindvar={
            fromuser:array[0],
            touser:array[1],
            updatetime:new Date().getTime(),
            notificationid:array[2],
            outvar:{type:oracledb.NUMBER,dir:oracledb.BIND_OUT}
        }
        console.log(JSON.stringify(bindvar)+" AcceptRelationShip param");
        let result = await dbconnect.executeQuery(`begin ACCEPT_REQUEST_RELETIONSHIP(:fromuser,:touser,:updatetime,:notificationid,:outvar); end;`,bindvar,"PR");
            console.log(result);
        if(result){
            result = result.outvar===1?{c0:"Y"}:{c0:"N"};
        }else{
            result = {c0:"N"};
        }
        return result;
    }catch(err){
        console.log(`AcceptRelationShip got error from relation provider log err `,err);
    }
}

module.exports.DeleteRelationShip = async (array)=>{
    try{
        /**
         * data in array
         * [0]:fromuser
         * [1]:touser
         */
        let bindvar={
            fromuser:array[0],
            touser:array[1],
            outvar:{type:oracledb.NUMBER,dir:oracledb.BIND_OUT}
        }
        console.log(JSON.stringify(bindvar)+" DeleteRelationShip param");
        let result = await dbconnect.executeQuery(`begin DELETE_RELATIONSHIP(:fromuser,:touser,:outvar); end;`,bindvar,"PR");
            console.log(result);
        if(result){
            result = result.outvar===1?{c0:"Y"}:{c0:"N"};
        }else{
            result = {c0:"N"};
        }
        return result;
    }catch(err){
        console.log(`DeleteRelationShip got error from relation provider log err `,err);
    }
}