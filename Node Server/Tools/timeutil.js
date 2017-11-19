var days = ['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','','','']
module.exports.loopDate =(fromdate,todate,dosomething)=>{
    for (var d = fromdate; d <= todate; d.setDate(d.getDate() + 1)) {
        dosomething();
    }
}