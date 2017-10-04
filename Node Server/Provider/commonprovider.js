const express = require('express')
, app = express()
, dbconnect = require("./dbconnect.js")
, crypto = require("../Tools/encrypt");


module.exports.FindTeacher = async (array)=>{
    try{
        let data = await dbconnect.executeQuery(`SELECT * FROM USER_ WHERE EMAIL LIKE '%:FINDSTR%' OR NAME LIKE '%:FINDSTR%' OR 
        PHONENUMBER LIKE '%:FINDSTR%' AND USER_TYPE=3`,[...array],"Q");
        return data;
    }catch(err){
        console.log(err);
    }
}

