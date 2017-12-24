const dbconnect = require("./dbconnect.js")
, crypto = require("../Tools/encrypt");




  
  // Get All User from db
  module.exports.GetListUser = async ()=>{
    try{
        let data = await dbconnect.executeQuery("Select * from USER_",[],"Q");
        return data;
    }catch(err){
        console.log("GetListUser error from Provider log: ",err);
    }
    
}

module.exports.GetListFriendRequest = async(array)=>{
    try{
        console.log(array+" GetListFriendRequest param");
        let data = await dbconnect.executeQuery(`SELECT * FROM RELATIONSHIP INNER JOIN USER_ ON RELATIONSHIP.TOUSER = USER_.ID WHERE (FROMUSER =:FROMUSER OR TOUSER =:FROMUSER) AND
        STATUS=0 AND ACTION_USER_ID !=1`,[...array],"Q");
        return data;
    }catch(err){
        console.log("GetListFriendRequest got error from userprovider log: ",err);
    }
}


  //Get Specific User from db
module.exports.MyCheckUserExist = async (array)=>{
    try{
        console.log(array+" MyCheckUserExist param");
        let data = await dbconnect.executeQuery(`SELECT *
        from USER_ where EMAIL=:email and PASSWORD =:password`,[...array],"Q");
       return data;
    }catch(err){
        console.log("MyCheckUserExist got error from userprovider log: ",err);
    }
}
  //Get Specific User from db
module.exports.CheckUserExist = async (array)=>{
      try{
        console.log(array+" CheckUserExist param");
        let data = await dbconnect.executeQuery(`SELECT ID
        from USER_ where EMAIL=:email and PASSWORD =:password and USER_TYPE=:user_type`,[...array],"Q");
        console.log(JSON.stringify(data));
        if(data){
           data= data.length>0? {c0:"Y",id:data[0].ID}:{c0:"N"};
        }else{
            data ={c0:"N"};
        }
        return data;
      }catch(err){
        console.log("CheckUserExist got error from userprovider log: ",err);
      }
}


  //Register User
  //status : not complete need improve
module.exports.RegisterUser = async(array)=>{
    /**
     * array[0]: NAME!!
     * array[1]: EMAIL!!
     * array[2]: PASSWORD!!
     * array[3]: PHONENUMBER?
     * array[4]: ADDRESS?
     * array[5]: BIRTHDAY?
     * array[6]: HASHCODE?
     * array[7]: ACTIVE
     * array[8]: USER_TYPE
     * array[9]: IMAGEPATH?
     */

    try{
        console.log(array+" RegisterUser param");
        let rannum = crypto.randomNum();
        let data = await dbconnect.executeQuery(`INSERT INTO USER_ (NAME,EMAIL,PASSWORD,ACTIVE,HASHCODE,
            USER_TYPE) VALUES (:NAME,:EMAIL,:PASSWORD,0,:HASHCODE,:USERTYPE)`,
            [...array],"I");
        data = updateResponse(data);
        return data;
    }catch(err){
        console.log("RegisterUser got error from userprovider log: ",err);
    }
}


module.exports.ActiveUser = async (array)=>{
      try{
        console.log(array+" ActiveUser param");
        let data = await dbconnect.executeQuery("UPDATE USER_  SET ACTIVE = 1 WHERE EMAIL=:EMAIL AND HASHCODE=:VALUE",[...array],"U");
        data = updateResponse(data);
        return data;
      }catch(err){
        console.log("ActiveUser got error from userprovider log: ",err);
      }
}

module.exports.FindUserUnActive = async (array)=>{
    try{
        console.log(array+" FindUserUnActive param");
        let data = await dbconnect.executeQuery(`SELECT COUNT(*) WHERE HASHCODE=:value`,[...array],"Q");
        return data;
    }catch(err){
        console.log("FindUserUnActive got error from userprovider log: ",err);
    }
}

module.exports.ChangePassword = async (array)=>{
      /**
       * [0] : password muốn set mới
        [1] : email 
        [2] : oldpass
       */
    try{
        console.log(array+" ChangePassword param");
        let data = await dbconnect.executeQuery(`UPDATE USER_ SET 
        password=:password where email=:email and password=:oldpass`,
        [...array],"U");
        data = updateResponse(data);
        return data;
    }catch(err){
        console.log("FindUserUnActive got error from userprovider log: ",err);
    }
}

module.exports.GetInfoUser = async(array)=>{
    //[1] : Email
    try{
        console.log(array+" GetInfoUser param");
        let data = await dbconnect.executeQuery(`SELECT 
            ID,NAME,EMAIL,PHONENUMBER,ADDRESS,BIRTHDAY,ACTIVE,USER_TYPE,IMAGEPATH,PARENT_ID
         FROM USER_ WHERE EMAIL=:EMAIL OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY`,[...array],"Q");
        return data;
    }catch(err){
        console.log("GetInfoUser got error from userprovider log: ",err);
    }
}

module.exports.UpdateInfoUser = async(array)=>{
    try{
        console.log(array+" UpdateInfoUser param");
        let data = await dbconnect.executeQuery(`UPDATE USER_ SET NAME=:NAME,PHONENUMBER=:PHONENUMBER
        ,ADDRESS=:ADDRESS,BIRTHDAY=:BIRTHDAY WHERE ID=:id`,
        [...array],
        "U");
        data = updateResponse(data);
        return data;
    }catch(err){
        console.log("UpdateInfoUser got error from userprovider log: ",err);
    }
}




const updateResponse = (data)=>{
    if(data>0){
        return {c0:"Y"};
    }
    return {c0:"N"};
}
