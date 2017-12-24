const express = require('express')
, app = express()
, dbconnect = require("./dbconnect.js")
, crypto = require("../Tools/encrypt");

const TAG = "Common provider";
module.exports.FindTeacher = async (array)=>{
    try{
        /**
         * [0]:findstr teacher find with NAME or EMAIL
         * [1]:userid 
         */
        console.log(array+" FindTeacher param");
        return await dbconnect.executeQuery(`select * from table(GET_RLS_TCHER_W_STD(:findstr,:userid))`,[...array],"Q");
    }catch(err){
        console.log(`FindTeacher got error from ${TAG} log: `,err);
    }
}

module.exports.FindStudent = async (array)=>{
    try{
        /**
         * [0]:findstr teacher find with NAME or EMAIL
         * [1]:userid
         */
        console.log(array+" FindStudent param");
        return await dbconnect.executeQuery(`select * from table(FIND_STD(:findstr,:userid))`,[...array],"Q");
    }catch(err){
        console.log(`FindStudent got error from ${TAG} log: `,err);
    }
}



