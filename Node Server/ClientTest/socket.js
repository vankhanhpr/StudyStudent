var socket = io.connect('http://127.0.0.1:8081');

$(document).ready(function(){
    $('#btnid').click(function(){
        console.log("hihi");
        socket.emit("CLIENT_MSG",{ClientSeq:12412,
            WorkerName:'usercontroller',
            ServiceName:'listuser',
            data:[],
            packet:1});
    });
});
var json = {
    
}
socket.on("RES_MSG", function(data){
    console.log(data);
});

var JsonFormat = function (){
    this.ClientSeq = ClientRect;
    this.WorkerName = WorkerName;
    this.ServiceName = ServiceName;
    this.data = data;
    this.packet = packet;
}