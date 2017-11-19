const express = require('express')
, app = express()
, dbconnect = require("./dbconnect.js")
, crypto = require("../Tools/encrypt");

module.exports.FriendRequest = async(array)=>{
    //array[0]:fromuser
    //array[1]:touser
    //array[2]:fromuser action_user_id is a person who perform this action
    let data = await dbconnect.executeQuery(`INSERT INTO RELATIONSHIP (:FROMUSER,:TOUSER,0,:ACTION_USER_ID,:REQUEST_TIME)`,
    [...array,new Date().getTime()],
    "I");
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
