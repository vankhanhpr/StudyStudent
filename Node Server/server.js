var usercontroller = require('./Controller/usercontroller.js');
var DataError = require('./Exception/DataError.js');
var express =  require('express')
var app = express();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var path = require('path');
server.listen(8081||process.env.PORT);

app.use(express.static(path.join(__dirname, 'ClientTest')));
app.get('/', function (req, res) {
  res.sendfile(__dirname + '/index.html');
});


io.on('connection',(socket)=>{
  //List User 
  console.log(socket.id+" connected");

  socket.on(usercontroller.UserControllerObject.listUserObject.ListenClient,function(datafromclient){
    //usercontroller.UserControllerObject.listUserObject.Handler(datafromclient,socket);
    datafromclient = JSON.parse(datafromclient);
    if(datafromclient.WorkerName === "login"){
        switch(datafromclient.ServiceName){
          case "login01": console.log(datafromclient);usercontroller.UserControllerObject.AuthenticateUser.Handler(datafromclient,socket); break;
          default: break;
        }
    }
  });

  //Check User
  socket.on(usercontroller.UserControllerObject.AuthenticateUser.ListenClient,(datafromclient)=>{
    console.log(datafromclient);
    usercontroller.UserControllerObject.AuthenticateUser.Handler(datafromclient,socket);
  });

  //
  socket.on(usercontroller.UserControllerObject.RegisterUser.ListenClient,(datafromclient)=>{
    usercontroller.UserControllerObject.RegisterUser.Handler(datafromclient,socket);
  });
});