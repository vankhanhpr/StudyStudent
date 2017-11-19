chatprovider = require("../Provider/chatprovider");

module.exports = (socket)=>{
    socket.on("error",()=>{
        console.log("error");
        chatprovider.popClientFromArray(socket);
    });
    socket.on("reconnect",()=>{
        console.log("user reconnect");
    })
    socket.on("disconnect",()=>{
        console.log("socket disconnect "+socket.id);
        chatprovider.popClientFromArray(socket);
    });
}