//require section
//begin
var usercontroller = require('./Controller/usercontroller.js')
, chatcontroller = require("./Controller/chatcontroller")
, DataError = require('./Exception/DataError.js')
, express =  require('express')
, app = express()
, server = require('http')
, io = require('socket.io')(server)
, path = require('path')
, myusercontroller = require('./MyAPI/myusercontroller.js')
, textvalidate = require("./Validate/textvalidate")
, emailvalidate = require("./Validate/emailvalidate.js");// shit
//end
//Load router

//initialize variable section
//begin

//end
server.createServer(app).listen(8081||process.env.PORT);

app.use("/myusercontroller",myusercontroller);


app.use(express.static(path.join(__dirname, 'ClientTest')));
app.get('/', function (req, res) {
  res.sendFile(__dirname + '/index.html');
});
app.use("/static",express.static(path.join(__dirname,"public")))

io.on('connection',(socket)=>{
  //List User 
  socket.on("CLIENT_MSG",function(datafromclient){
    //usercontroller.UserControllerObject.listUserObject.Handler(datafromclient,socket);


    if(textvalidate.isEmpty(datafromclient)){
      //attach timeout
      io.attach(server,{
        //not confirm not sure about timeout formatjson
        //pingTimeout:datafromclient.timeout
      })
      datafromclient = JSON.parse(datafromclient);
     
    }
  });


});






//Share io
usercontroller(io);
chatcontroller(io);
