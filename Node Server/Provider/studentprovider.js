const dbconnect = require("./dbconnect.js"),
oracledb = require('oracledb');

var TAG = "student provider"; 

module.exports.FindSchedule = async (array)=>{
    /**
     * [0] findstring:string
     * description : tim lich hoc theo subject name hoặc teacher name 
     */
    try{
        console.log(array+" FindSchedule param");
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
        console.log(array+" GetScheduleRegistered param");
        return await dbconnect.executeQuery(`SELECT US.USERID,S.LOCATION,S.FEE,S.SCHE_ID,S.TEACHER_ID,S.START_TIME,S.END_TIME,BS.THU,BS.TIME,SJ.SUB_NAME FROM USER_SCHEDULESTUDY US
        INNER JOIN SCHEDULE_STUDY S ON US.SCHEDULEID = S.SCHE_ID
        INNER JOIN BUOI_SUBSCHEDULESTUDY BS ON BS.SCHEDULE_STUDY = S.SCHE_ID
        LEFT OUTER JOIN SUBJECT SJ ON SJ.TEACHER_ID = S.TEACHER_ID
        WHERE US.USERID=:id`,[...array],"Q");
    }catch(err){
        console.log(`GetScheduleRegistered from ${TAG} got error `,err);
    }
}

module.exports.RegisterSchedule = async (array)=>{
    try{
        /**
         * [0] USERID:number 
            [1] SCHEDULEID:number
         */
        console.log(array+" RegisterSchedule param");
        let data= await dbconnect.executeQuery(`INSERT INTO USER_SCHEDULESTUDY (USERID,SCHEDULEID) VALUES (:USERID,:SCHEDULEID)`,[...array],"I");
        return updateResponse(data);
    }catch(err){
        console.log(`RegisterSchedule from ${TAG} got error `,err);
    }
}


module.exports.GetScheduleToRegister = async (array)=>{
    /**
     * [0]:teacherid:number
     */
    try{
        console.log(array+" GetScheduleToRegister param");
        return await dbconnect.executeQuery(`SELECT * FROM SCHEDULE_STUDY SS 
        WHERE SS.TEACHER_ID =:teacherid `,[...array],"Q");
    }catch(err){
        console.log(`GetScheduleToRegister from ${TAG} got error `,err);
    }
}
module.exports.GetListFriendOfStudent = async (array)=>{
    try{
        console.log(array+" GetListFriendOfStudent param");
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
        return data.filter((item)=>{return item.ID!==array[0];});
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
module.exports.DeleteScheduleRegistered = async (array)=>{
    try{
        /**
         * data in array
         * [0]:user_id
         * [1]:schedule_id
         */
        let bindvar={
            user_id:array[0],
            schedule_id:array[1],
            outvar:{type:oracledb.NUMBER,dir:oracledb.BIND_OUT}
        }
        console.log(JSON.stringify(bindvar)+" DeleteScheduleRegistered param");
        let result = await dbconnect.executeQuery(`begin DELETE_SCHEDULE_REGISTERED(:user_id,:schedule_id,:outvar); end;`,bindvar,"PR");
        
        if(result){
            result = result.outvar===1?{c0:"Y"}:{c0:"N"};
        }else{
            result = {c0:"N"};
        }
        return result;
    }catch(err){
        console.log(`DeleteScheduleRegistered got error from relation provider log err `,err);
    }
}
module.exports.GetListTeacherWhoStudentHaveAdd = async (array)=>{
    try{
        console.log(array+" GetListTeacherWhoStudentHaveAdd param");
        let data = await dbconnect.executeQuery(`SELECT 
        U.ID,U.NAME,U.EMAIL,U.PHONENUMBER,U.ADDRESS,U.BIRTHDAY,U.USER_TYPE,SB.SUB_ID,SB.SUB_NAME 
         FROM RELATIONSHIP R INNER JOIN USER_ U ON R.TOUSER = U.ID 
         INNER JOIN SUBJECT SB ON SB.TEACHER_ID = U.ID
         WHERE (R.FROMUSER =:FROMUSER OR R.TOUSER =:FROMUSER) AND
        R.STATUS=3
        UNION 
        SELECT US.ID,US.NAME,US.EMAIL,US.PHONENUMBER,US.ADDRESS,US.BIRTHDAY,US.USER_TYPE,SJ.SUB_ID,SJ.SUB_NAME
         FROM RELATIONSHIP R INNER JOIN USER_ US ON R.FROMUSER = US.ID
         INNER JOIN SUBJECT SJ ON SJ.TEACHER_ID = US.ID
         WHERE (R.FROMUSER =:FROMUSER OR R.TOUSER =:FROMUSER) AND
        R.STATUS=3`,[...array],"Q");
        return data.filter((item)=>{return item.ID!==array[0];});
    }catch(err){
        console.log(`GetListTeacherWhoStudentHaveAdd got error from relation provider log err `,err);
    }
}
const updateResponse = (data)=>{
    if(data>0){
        return {c0:"Y"};
    }
    return {c0:"N"};
}