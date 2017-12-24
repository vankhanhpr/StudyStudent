const dbconnect = require("../Provider/dbconnect");

const TAG = "Parent provider";
module.exports.GetHoSo = async (array)=>{
    /**
     * [0]:parentid
     * 
     */
    try{
        console.log(array+" GetHoSo param");
        return await dbconnect.executeQuery(`select * from table(GET_HOSO(:parentid)) where STATUS = 2`,[...array],"Q")
    }catch(err){
        console.log(`GetHoSo got error from ${TAG} log err `,err);
    }
}
module.exports.GetScheduleOfStudentRegistered = async (array)=>{
    /**
     * [0]:studentid
     * 
     */
    try{
        console.log(array+" GetScheduleOfStudentRegistered param");
        return await dbconnect.executeQuery(`SELECT US.USERID,S.LOCATION,S.FEE,S.SCHE_ID,S.TEACHER_ID,S.START_TIME,S.END_TIME,BS.THU,BS.TIME,SJ.SUB_NAME FROM USER_SCHEDULESTUDY US
        INNER JOIN SCHEDULE_STUDY S ON US.SCHEDULEID = S.SCHE_ID
        INNER JOIN BUOI_SUBSCHEDULESTUDY BS ON BS.SCHEDULE_STUDY = S.SCHE_ID
        LEFT OUTER JOIN SUBJECT SJ ON SJ.TEACHER_ID = S.TEACHER_ID
        WHERE US.USERID=:id`,[...array],"Q")
    }catch(err){
        console.log(`GetScheduleOfStudentRegistered got error from ${TAG} log err `,err);
    }
}
