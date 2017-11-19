const dbconnect = require('../Provider/dbconnect'),
checktype = require('../Validate/checktype'),
randomutil = require('../Tools/encrypt');

const TAG = "Teacher Provider";
 module.exports.addCourse = async (array)=>{
    try{
        //[0]id teacher
        //[1]location
        //[2]fee
        //[3]starttime
        //[4]endtime
        //[5]array buoi
        //[6]array thoi gian
        console.log(array+" addCourse param");
        let random = randomutil.randommanydigit(15);
        let specificarray = [array[5],array[6]];
        let arraydata =[];
        for(let i=0;i<array.length;i++){
            if(!checktype.isArray(array[i])){
                console.log("array thu i:"+i+" value :"+array[i]);
                arraydata.push(array[i]);
            }            
        }
        return await dbconnect.executeQuery(`
        INSERT ALL
        INTO SCHEDULE_STUDY (SCHE_ID,TEACHER_ID,LOCATION,FEE,START_TIME,END_TIME) VALUES (${random},:teacherid,:location,:fee,:start_time,:end_time)\n
        ${generateQueryForBuoi(specificarray,random)}
        SELECT * FROM DUAL
        `,arraydata,"I");
    }catch(err){
        console.log(`addCourse got error from ${TAG} log err: `,err);
    }
}
const generateQueryForBuoi=(arrayofarray,schedule_id)=>{
    let sql="";
    for(let i=0;i<arrayofarray[0].length;i++){
       sql+=`INTO BUOI_SUBSCHEDULESTUDY (ID,THU,TIME,SCHEDULE_STUDY) VALUES (get_seq,'${arrayofarray[0][i]}','${arrayofarray[1][i]}',${schedule_id}) \n`;
    }
    return sql;
}

/* addCourse([
    0,'',0,415105485546,415105485546,
    ['thứ 2','thứ 3','thứ 4'],
    ['11:40 SA','11:40 SA','11:40 SA']
]) */
module.exports.getCourseById = async (array)=>{
    try{
        /**
         * [0]:id of course
         */
        console.log(array+" getCourseById param");
        return await dbconnect.executeQuery(`SELECT * FROM SCHEDULE_STUDY S
            INNER JOIN BUOI_SUBSCHEDULESTUDY B ON S.SCHE_ID = B.SCHEDULE_STUDY
         WHERE s.SCHE_ID=:scheid`,array,"Q");
    }catch(err){
        console.log(`getCourseById got error from ${TAG} log err: `,err);
        
    }
}

module.exports.getAllCourseOfTeacher = async (array)=>{
    try{
        /**
         * [0]:id of teacher
         */
        console.log(array+" getAllCourseOfTeacher param");
        return await dbconnect.executeQuery(`SELECT * FROM SCHEDULE_STUDY S
            INNER JOIN SUBJECT SB ON S.TEACHER_ID = SB.TEACHER_ID
         WHERE S.TEACHER_ID=:teacherid`,array,"Q");
    }catch(err){
        console.log(`getAllCourseOfTeacher got error from ${TAG} log err: `,err);
        
    }
}
module.exports.updateCourse = async (array)=>{
    try{
        //process:deprecated
        /**
         * 
         * [0]:location
         * [1]:fee
         * [2]:start_time
         * [3]:end_time
         * [4]:sche_id
         * [6]:arrayjson thu
         * [7]:arrayjson time
         * 
         */
        console.log(array+" updateCourse param");
        let specificarray = [array[5],array[6],array[7]];
        let arraydata =[];
        for(let i=0;i<array.length;i++){
            if(!checktype.isArray(array[i])){
                arraydata.push(array[i]);
            }
            
        }
        let result = await dbconnect.doTransaction(`
        BEGIN
        SAVEPOINT start_tran;
        UPDATE SCHEDULE_STUDY S SET S.LOCATION=:location,S.FEE=:fee,S.START_TIME=:start_time,S.END_TIME=:end_time WHERE SCHE_ID=:sche_id; 
        UPDATE BUOI_SUBSCHEDULESTUDY B SET B.THU = CASE
        ${generateQueryForUpdateBuoi(specificarray)}
        WHERE B.SCHEDULE_STUDY=:sche_id; 
      EXCEPTION
        WHEN OTHERS THEN
          ROLLBACK TO start_tran;
          RAISE;
      END;
        `,arraydata);
        return result;
    }catch(err){
        console.log(`updateCourse got error from ${TAG} log err: `,err);
        
    }
}

module.exports.updateCourseDif = async (array)=>{
    try{
        /**
         * 
         * [0]:location
         * [1]:fee
         * [2]:start_time
         * [3]:end_time
         * [4]:sche_id
         * [5]:arrayjson thu
         * [6]:arrayjson time
         * 
         */
        console.log(array+" updateCourseDif param");
        let specificarray = [array[5],array[6]];
        let arraydata =[];
        for(let i=0;i<array.length;i++){
            if(!checktype.isArray(array[i])){
                arraydata.push(array[i]);
            }            
        }
        let result = await dbconnect.doTransaction(`
        BEGIN
        SAVEPOINT start_tran;
        UPDATE SCHEDULE_STUDY S SET S.LOCATION=:location,S.FEE=:fee,S.START_TIME=:start_time,S.END_TIME=:end_time WHERE SCHE_ID=:sche_id;
        DELETE FROM BUOI_SUBSCHEDULESTUDY B WHERE B.SCHEDULE_STUDY = :sche_id;   
        ${generateQueryForUpdateBuoiDif(specificarray,array[4])}
      EXCEPTION
        WHEN OTHERS THEN
          ROLLBACK TO start_tran;
          RAISE;
      END;
        `,arraydata);
        return result; 
    }catch(err){
        console.log(`updateCourseDif got error from ${TAG} log err: `,err);
        
    }
}


const generateQueryForUpdateBuoi = (arrayofarray)=>{
    let sql="";
    for(let i=0;i<arrayofarray[0].length;i++){
       sql+=`WHEN ID = ${arrayofarray[0][i]} THEN '${arrayofarray[1][i]}' \n`;
    }
    sql+="END,\n TIME = CASE\n";
    for(let i=0;i<arrayofarray[0].length;i++){
        sql+=`WHEN ID = ${arrayofarray[0][i]} THEN '${arrayofarray[2][i]}' \n`;
    }
    sql+="END";
    return sql;
}
const generateQueryForUpdateBuoiDif=(arrayofarray,schedule_id)=>{
    let sql="";
    if(arrayofarray[0].length===0){
        return sql;
    }
    sql +="INSERT ALL\n";
    for(let i=0;i<arrayofarray[0].length;i++){
       sql+=`INTO BUOI_SUBSCHEDULESTUDY (ID,THU,TIME,SCHEDULE_STUDY) VALUES (get_seq,'${arrayofarray[0][i]}','${arrayofarray[1][i]}',${schedule_id}) \n`;
    }
    sql +="SELECT * FROM DUAL;\n";
    return sql;
}
/* console.log(generateQueryForUpdateBuoi(
    [[1,2,3,4],['thu 3','thu 5','thu 7','chu nhat'],['09:32 SA','10:21 SA','09:32 SA','09:32 SA']]
)); */
/* console.log(updateCourse(
    ['mylocation',235234,734592745,43573495,4,
    [1,2,3,4],
    ['thu 3','thu 5','thu 7','chu nhat'],
    ['09:32 SA','10:21 SA','09:32 SA','09:32 SA']
    ]
));  */
/* console.log(updateCourseDif(
    ['mylocation',
    235234,
    734592745,
    43573495,
    4,
    ['thu 2','thu 2','thu 2','thu 2'],
    ['09:33 SA','10:33 SA','09:33 SA','09:33 SA']
    ]
)); */

module.exports.GetListFriend = async (array)=>{
    try{
        console.log(array+" GetListFriend param");
        let resultarray = [];
        let data = await dbconnect.executeQuery(`SELECT 
        U.ID,U.NAME,U.EMAIL,U.PHONENUMBER,U.ADDRESS,U.BIRTHDAY,U.USER_TYPE 
         FROM RELATIONSHIP R INNER JOIN USER_ U ON R.TOUSER = U.ID 
         WHERE (R.FROMUSER =:FROMUSER OR R.TOUSER =:FROMUSER) AND
        R.STATUS=1
        UNION 
        SELECT US.ID,US.NAME,US.EMAIL,US.PHONENUMBER,US.ADDRESS,US.BIRTHDAY,US.USER_TYPE 
         FROM RELATIONSHIP R INNER JOIN USER_ US ON R.FROMUSER = US.ID 
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