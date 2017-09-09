var socket = io.connect('http://127.0.0.1:8081');
$("#btnid").click(function(){
    socket.emit("REGISTER",{});
});

socket.on("RES_MSG", function(data){
    console.log(data);
});