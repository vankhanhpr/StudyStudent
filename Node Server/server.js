//require section
//begin
var usercontroller = require('./Controller/usercontroller.js'),
  chatcontroller = require("./Controller/chatcontroller"),
  teachercontroller = require('./Controller/teachercontroller'),
  parentcontroller = require("./Controller/parentcontroller"),
  commoncontroller = require("./Controller/commoncontroller"),
  studentcontroller = require("./Controller/studentcontroller"),
  accessdeniedhandler = require('./Controller/accessdeniedhandler');
//, relationcontroller = require("./Controller/relationcontroller")
var timeutil = require('./Tools/timeutil'),
  DataError = require('./Exception/DataError.js'),
  errorhandler = require('./Controller/errorhandler'),
  path = require('path'),
  textvalidate = require("./Validate/textvalidate"),
  parseutil = require("./Tools/parseutil"),
  emailvalidate = require("./Validate/emailvalidate.js"),
  encrypt = require('./Tools/encrypt'),
  decrypt = require('./Tools/decrypt');
const server = require('http').createServer();
const io = require('socket.io')(8081 || process.env.PORT, {
  serveClient: false,
  // below are engine.IO options
  pingInterval: 10000,
  pingTimeout: 5000,
  cookie: false
});
//end
//Load router

//initialize variable section
//begin

//end


io.on('connection', (socket) => {
  socket.use((packet, next) => {
    return next();
  });
  console.log("user connected : " + socket.id);
  errorhandler(socket);
  socket.on("CLIENT_MSG", function (datafromclient) {
    //usercontroller.UserControllerObject.listUserObject.Handler(datafromclient,socket);

    if (!textvalidate.isEmpty(datafromclient)) {
      //attach timeout
      io.attach(server, {
        //not confirm not sure about timeout formatjson
        //pingTimeout:datafromclient.timeout
      });
      datafromclient = JSON.parse(datafromclient);
      console.log(datafromclient.WorkerName);
      switch (datafromclient.WorkerName) {
        case "usercontroller":
          usercontroller(socket, datafromclient);
          break;
        case "chatcontroller":
          chatcontroller(socket, datafromclient);
          break;
        case "commomcontroller":
          commoncontroller(socket, datafromclient);
          break;
        case "studentcontroller":
          datafromclient.UserType === 0 ? studentcontroller(socket, datafromclient) : accessdeniedhandler(socket,datafromclient);
          break;
        case "parentcontroller":
          datafromclient.UserType === 1 ? parentcontroller(socket, datafromclient) : accessdeniedhandler(socket,datafromclient);
          break;
        case "teachercontroller":
          datafromclient.UserType === 2 ? teachercontroller(socket, datafromclient) : accessdeniedhandler(socket,datafromclient);
          break;
        case "relationcontroller":
          break;
      }

    }
  });


});
io.sockets.setMaxListeners(0);
//Share io
/* usercontroller(io);
chatcontroller(io);
commoncontroller(io);
studentcontroller(io);
parentcontroller(io);
teachercontroller(io);  */
//relationcontroller(io);