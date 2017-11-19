const dbconnect = require("../Provider/dbconnect");

const TAG = "Parent provider";
module.exports.GetHoSo = async (array)=>{
    /**
     * [0]:parentid
     * 
     */
    try{
        return await dbconnect.executeQuery(`SELECT * FROM USER_ WHERE PARENT_ID=:parentid `,[...array],"Q")
    }catch(err){
        console.log(`GetHoSo got error from ${TAG} log err `,err);
    }
}
