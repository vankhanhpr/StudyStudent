var socket = io.connect('http://127.0.0.1:8081');
$("#btnid").click(function(){
    socket.emit("LOGIN",{EMAIL:"zxv",PASSWORD:"zxv"});
});

socket.on("RES_MSG", function(data){
    console.log(data);
});