var express = require('express');
var app = express();
var dbconnect = require("./dbconnect.js");

module.exports = {
  // Get All User from db
  GetListUser : (ControllerCallBack)=>{
    dbconnect.executeQuery("Select * from USER_TBL",function(data,err){
      ControllerCallBack(data,err);
      },null,"Q");
  },

  //Get Specific User from db
  CheckUserExist : (ControllerCallBack,Email,Password)=>{
      dbconnect.executeQuery("select count(*) from USER_TBL where EMAIL='"+Email+"' and PASSWORD ='"+Password+"'",(data,err)=>{
        ControllerCallBack(data,err);
      },null,"Q");
      
  },

  //Register User
  //status : not complete need improve
  RegisterUser : (ControllerCallBack)=>{
      dbconnect.executeQuery("insert into USER_TBL values (:id,:name,:email,:password,:phonenumber,:user_type)",(data,err)=>{
          ControllerCallBack(data,err);
      },[4,'zxc','zxvxc','zxcv',02475,null],"I");
  }
  


};
