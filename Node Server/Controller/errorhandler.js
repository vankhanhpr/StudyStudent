

module.exports = (io)=>{
    io.on("connection",(socket)=>{
        socket.on('disconnect', () =>{
            console.log('disconnect');
        });
        socket.on('connect_failed', ()=> {
            throw new Error("Connect to server has failed");
        });
        socket.on('error', (err) =>{
            throw new Error(err);
        });
        socket.on('reconnect_failed', ()=> {
            throw new Error("Reconnect to server has failed");
        });
        socket.on('reconnect',  () =>{
            console.log('client reconnected ');
        });
        socket.on('reconnecting',() =>{
            console.log('client on reconnect to server');
        });
    });
}