const dbconnect = require("./dbconnect.js")
, crypto = require("../Tools/encrypt");




  
  // Get All User from db
  module.exports.GetListUser = async ()=>{
    try{
        let data = await dbconnect.executeQuery("Select * from USER_",[],"Q");
        //console.log(data);
        return data;
    }catch(err){
        console.log("GetListUser error from Provider log: ",err);
    }
    
}

module.exports.GetListFriend = async(array)=>{
    let data = await dbconnect.executeQuery(`SELECT * FROM RELATIONSHIP INNER JOIN USER_ ON RELATIONSHIP.TOUSER = USER_.ID WHERE (FROMUSER =:FROMUSER OR TOUSER =:FROMUSER) AND
     STATUS=1`,[...array],"Q");
     return data;
}

  //Get Specific User from db
module.exports.CheckUserExist = async (array)=>{
      let data = await dbconnect.executeQuery(`SELECT count(*) as NUMOFRECORD 
      from USER_ where EMAIL=:email and PASSWORD =:password`,[...array],"Q");
     return data[0].NUMOFRECORD;
}

  //Register User
  //status : not complete need improve
module.exports.RegisterUser = async(array)=>{
    /**
     * array[0]: email
     * array[1]: password
     * array[2]: user_type
     * array[3]: active
     * array[4]: hashvalue
     * array[5]: name
     * array[6]: address
     * array[7]: phonenumber
     * array[8]: birthday
     * array[9]: imagepath
     */

    dbconnect.executeQuery(`INSERT INTO USER_ (EMAIL,PASSWORD,USER_TYPE,ACTIVE,HASHVALUE,NAME,ADDRESS,PHONENUMBER,
    BIRTHDAY,IMAGEPATH) VALUES 
    (:email,:password,:user_type,:active,:hashvalue,:name,:address,:phonenumber,:birthday,:imagepath);`,
    [...array],"I");
}


module.exports.ActiveUser = async (array)=>{
      dbconnect.executeQuery(`UPDATE USER_ SET ACTIVE = 1 WHERE HASHCODE=:value`,[...array],"U");
}

module.exports.FindUserUnActive = async (array)=>{
    dbconnect.executeQuery(`SELECT COUNT(*) WHERE HASHCODE=:value`,[...array],"Q");
}

ChangePassword = async (array)=>{
      /**
       * [1] : password muốn set mới
        [2] : email 
        [3] : oldpass
       */
    let data = await dbconnect.executeQuery(`UPDATE USER_ SET 
    password=:password where email=:email and password=:oldpass`,
    [...array],"U");
    return updateResponse(data,"Password");
}

module.exports.GetInfoUser = async(array)=>{
    //[1] : Email
    let data = await dbconnect.executeQuery(`SELECT * FROM USER_ WHERE EMAIL=:EMAIL`,[...array],"Q");
    console.log(data[0]);
    return data[0];
}

module.exports.UpdateInfoUser = async(array)=>{
    let data = await dbconnect.executeQuery(`UPDATE USER_ SET EMAIL=:EMAIL,PHONENUMBER=:PHONENUMBER
    ,ADDRESS=:ADDRESS,BIRTHDAY=:BIRTHDAY`,
    [...array],
    "U");
    data = updateResponse(data,"Info user");
    return data;
}

const updateResponse = (data,whichUpdate)=>{
    if(data>0){
        return {OperationSuccess:true,Message:`update $resp success`};
    }
    return {OperationSuccess:false,Message:`update $resp fail`};
}
