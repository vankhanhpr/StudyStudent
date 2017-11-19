const dbconnect = require("./dbconnect.js");

var TAG = "student provider"; 

module.exports.FindSchedule = async (array)=>{
    /**
     * [0] findstring:string
     * description : tim lich hoc theo subject name hoặc teacher name 
     */
    try{
        return await dbconnect.executeQuery(`SELECT * FROM SCHEDULE_STUDY 
         INNER JOIN USER_ ON SCHEDULE_STUDY.TEACHER_ID = USER_.ID
         INNER JOIN SUBJECT ON SUBJECT.TEACHER_ID = USER_.ID  
         WHERE NAME LIKE '%'||:findstr||'%' AND USER_TYPE=2`,[...array],"Q");
    }catch(err){
        console.log(`FindSchedule from ${TAG} got error `,err);
    }
}

module.exports.GetScheduleRegistered = async (array)=>{
    /**
     * [0]:userid:number
     */
    try{
        return await dbconnect.executeQuery(`SELECT * FROM USER_SCHEDULESTUDY US
        INNER JOIN SCHEDULE_STUDY S ON US.SCHEDULEID = S.SCHE_ID
        WHERE US.USERID=:id`,[...array],"Q");
    }catch(err){
        console.log(`GetSchedule from ${TAG} got error `,err);
    }
}

module.exports.RegisterSchedule = async (array)=>{
    try{
        /**
         * [0] USERID:number 
            [1] SCHEDULEID:number
         */
        let data= await dbconnect.executeQuery(`INSERT INTO USER_SCHEDULESTUDY (USERID,SCHEDULEID) VALUES (:USERID,:SCHEDULEID)`,[...array],"I");
        return updateResponse(data);
    }catch(err){
        console.log(`RegisterSchedule from ${TAG} got error `,err);
    }
}

module.exports.AddTeacher = async (array)=>{
    /**
     * [0]:fromid:studentid:number
     * [1]:toid:teacherid:number
     */
    try{
        let data = await dbconnect.executeQuery(`INSERT INTO RELATIONSHIP 
        (FROMUSER,TOUSER,STATUS,ACTION_USER_ID,REQUEST_TIME) VALUES (:FROMUSERID,:TOUSERID,0,:FROMUSERID,${new Date().getTime()})`,[...array],"I");
        return updateResponse(data);
    }catch(err){
        console.log(`AddTeacher from ${TAG} got error `,err);
    }
}

module.exports.AcceptParent = async (array)=>{
    /**
     * [0]:parentid
     * [1]:email who want to update
     */
    try{
        let data = await dbconnect.executeQuery(`UPDATE USER_ SET USER_.PARENT_ID=:parentid WHERE USER_.EMAIL=:email`,[...array],'U'); 
        return updateResponse(data);
              
    }catch(err){
        console.log(`AcceptParent from ${TAG} got error `,err);
    }
}
module.exports.GetScheduleToRegister = async (array)=>{
    /**
     * [0]:teacherid:number
     */
    try{
        return await dbconnect.executeQuery(`SELECT * FROM SCHEDULE_STUDY SS 
        WHERE SS.TEACHER_ID =:teacherid `,[...array],"Q");
    }catch(err){
        console.log(`GetScheduleToRegister from ${TAG} got error `,err);
    }
}
module.exports.GetListFriendOfStudent = async (array)=>{
    try{
        console.log(array+" GetListFriendOfStudent param");
        let resultarray = [];
        let data = await dbconnect.executeQuery(`SELECT 
        U.ID,U.NAME,U.EMAIL,U.PHONENUMBER,U.ADDRESS,U.BIRTHDAY,U.USER_TYPE,SB.SUB_ID,SB.SUB_NAME 
         FROM RELATIONSHIP R INNER JOIN USER_ U ON R.TOUSER = U.ID 
         INNER JOIN SUBJECT SB ON SB.TEACHER_ID = U.ID
         WHERE (R.FROMUSER =:FROMUSER OR R.TOUSER =:FROMUSER) AND
        R.STATUS=1
        UNION 
        SELECT US.ID,US.NAME,US.EMAIL,US.PHONENUMBER,US.ADDRESS,US.BIRTHDAY,US.USER_TYPE,SJ.SUB_ID,SJ.SUB_NAME
         FROM RELATIONSHIP R INNER JOIN USER_ US ON R.FROMUSER = US.ID
         INNER JOIN SUBJECT SJ ON SJ.TEACHER_ID = US.ID
         WHERE (R.FROMUSER =:FROMUSER OR R.TOUSER =:FROMUSER) AND
        R.STATUS=1`,[...array],"Q");
        for(let i=0;i<data.length;i++){
            if(data[i].ID!==array[0]){
                resultarray.push(data[i]);
            }
        }
        return resultarray;
    }catch(err){
        console.log("GetListFriend got error from userprovider log: ",err);
    }
}
module.exports.getSubScheduleOfTeacher = async (array)=>{
    try{
        /**
         * [0]:id of schedule
         */
        console.log(array+" getSubScheduleOfTeacher param");
        return await dbconnect.executeQuery(`SELECT * FROM SCHEDULE_STUDY S
            INNER JOIN BUOI_SUBSCHEDULESTUDY B ON S.SCHE_ID = B.SCHEDULE_STUDY
         WHERE s.SCHE_ID=:scheid`,array,"Q");
    }catch(err){
        console.log(`getSubScheduleOfTeacher got error from ${TAG} log err: `,err);
        
    }
}
const updateResponse = (data)=>{
    if(data>0){
        return {c0:"Y"};
    }
    return {c0:"N"};
}