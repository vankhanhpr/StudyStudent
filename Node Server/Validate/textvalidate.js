module.exports = {
    isEmpty : (str)=>{
        return str == null || str == undefined ?  true : false;
    },
    findString : (strcheck,strwanttofind)=>{
        return strwanttofind.indexOf(strcheck)>0? true : false;
    }
}