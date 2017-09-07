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
  socket.on(usercontroller.UserControllerObject.listUserObject.ListenClient,function(datafromclient){
    usercontroller.UserControllerObject.listUserObject.Handler(datafromclient,socket);
  });

  //Check User
  socket.on(usercontroller.UserControllerObject.AuthenticateUser.ListenClient,(datafromclient)=>{
    console.log(datafromclient);
    usercontroller.UserControllerObject.AuthenticateUser.Handler(datafromclient,socket);
  });
});