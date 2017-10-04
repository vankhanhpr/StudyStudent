var userprovider = require("../Provider/userprovider.js")
,JsonResponse = require("../FormatJson/JsonResponse.js")
, dataerror = require("../Exception/dataerror.js")
, Exception = require("../Exception/Exception")
, express = require("express")
, textvalidate = require("../Validate/textvalidate")
, nodemailer = require("nodemailer")
, mailconfig  = require("../Configuration/mailconfig.js");

var router = express.Router();

router.get("/Authenticate",async (req,res)=>{
    res.setHeader('Content-Type', 'application/json');
    let data = await userprovider.CheckUserExist([req.query.email,req.query.password]);
    console.log(data);
     if(data>0){
      res.send(JSON.stringify({ success:true,message:"Authenticate Success"}));
      res.end();
    }else{
      res.send(JSON.stringify({ success:false,message:"Authenticate Fail"}));
      res.end();
    } 
  });
  
  router.get("/ConfirmEmail",(req,res)=>{
    let hashvalue = req.param.hashcode;
    if(userprovider.FindUserUnActive((data,err)=>{
      if(data>=1){
        userprovider.ActiveUser((data,err)=>{
          if(err)throw err;
          res.setHeader('Content-Type', 'text/html');
          res.writeHead(res.statusCode);
          res.write("Confirm Success Have Fun!!!");
          res.end();
        },hashvalue);
      }
    }),[hashvalue]);
  });
  
  router.get("/GetListFriend", async(req,res)=>{
    res.setHeader('Content-Type', 'application/json');
    let data = await userprovider.GetListFriend([req.query.userid]);
    console.log(data);
     if(data){
      res.send(JSON.stringify(data));
      res.end();
    }else{
      res.send(JSON.stringify({ success:false,message:"Authenticate Fail"}));
      res.end();
    } 
  });

  
  
  router.get('/test', function(req, res){
    res.setHeader('Content-Type', 'application/json');
    res.send(JSON.stringify({ success:false,message:"Authenticate Fail"}));
    res.end();
  });
  router.post('/', function(req, res){
     res.send('POST route on things.');
  });
  
  //export this router to use in our index.js
  module.exports = router;