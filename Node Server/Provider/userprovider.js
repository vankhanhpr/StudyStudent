var express = require('express');
var app = express();
var dbconnect = require("./dbconnect.js");

module.exports = {
  // Get All User from db
  GetListUser : (ControllerCallBack)=>{
    dbconnect.executeQuery("Select * from USER_TBL",function(data,err){
      ControllerCallBack(data,err);
      },null);
  },

  //Get Specific User from db
  CheckUserExist : (ControllerCallBack,Email,Password)=>{
      dbconnect.executeQuery("select count(*) from USER_TBL where EMAIL='"+Email+"' and PASSWORD ='"+Password+"'",(data,err)=>{
        ControllerCallBack(data,err);
      },null);
      
  },

  //Register User
  //status : not complete need improve
  RegisterUser : (ControllerCallBack,User)=>{
      dbconnect.executeQuery("insert into USER_TBL values (:id,:name,:email,:password,:phonenumber,:user_type)",(data,err)=>{

      },{id:4,name:"zxc",email:"zxvxc",password:"zxcv",phonenumber:02475,user_type:null});
  }
  


};
