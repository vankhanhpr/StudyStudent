const express = require('express')
, app = express()
, dbconnect = require("./dbconnect.js")
, crypto = require("../Tools/encrypt");

const TAG = "Common provider";
module.exports.FindTeacher = async (array)=>{
    try{
        /**
         * [0]:findstr teacher find with NAME or EMAIL
         */
        console.log(array+" FindTeacher param");
        return await dbconnect.executeQuery(`SELECT 
        U.ID,U.NAME,U.EMAIL,U.PHONENUMBER,U.ADDRESS,U.BIRTHDAY,U.ACTIVE,U.USER_TYPE,U.IMAGEPATH,U.PARENT_ID,
        R.STATUS,S.SUB_NAME
         FROM USER_ U
         LEFT OUTER JOIN RELATIONSHIP R ON U.ID = R.TOUSER
         INNER JOIN SUBJECT S ON U.ID = S.TEACHER_ID
        WHERE (U.EMAIL LIKE '%'||:findstr||'%' OR U.NAME LIKE '%'||:findstr||'%') AND U.USER_TYPE=2 `,[...array],"Q");
    }catch(err){
        console.log(`FindTeacher got error from ${TAG} log: `,err);
    }
}



