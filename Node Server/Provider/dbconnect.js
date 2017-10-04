
const oracledb = require('oracledb')
, dbconfig = require("../Configuration/dbconfig.js");

module.exports.executeQuery = executeQuery;
async function executeQuery (sql,param,operation){
  /**
   * sql : string query must have
   * param : data pass to function to process 
   * operation : "I" insert , "Q" query , "U" update , "D" delete , "C" commit 
   */
    let conn;
    try{
      conn = await oracledb.getConnection(dbconfig);
      let result = await requestOperation(operation,conn,sql,param);
      return result;
    }catch(err){
      console.log(err);
    }finally{
      conn ? doRelease(conn):undefined;
    }
}



const doRelease = async (conn)=>{
  try{
    return await conn.close(); 
  }catch(err){
    console.log(err);
  }
}

//done
const doSelect = async (conn,sql,param)=>{
  try{
    let result = await conn.execute(sql,param,{outFormat:oracledb.OBJECT,extendedMetaData:true});
    return result.rows;
  }catch(err){
    console.log('Select error ',err);
  }
}


//done
const doInsert = async (conn,sql,param)=>{
  try{
    let result = await conn.execute(sql,param,{autoCommit:true})
    return result.rowsAffected;
  }catch(err){
    console.log('Insert error ',err);
  }
}

const doUpdate = async (conn,sql,param)=>{
  try{
    let result = await conn.execute(sql,param,{autoCommit:true});
    return result.rowsAffected;
  }catch(err){
    console.log('Update error ',err);
  }
}

const commitChange = async(conn)=>{
  try{
    return await conn.commit();
  }catch(err){
    console.log('Commit Error Rollback plz ',err);
  }
  
}

const requestOperation = async (operation,...args) => {
  //console.log(...args);
  let result;
  switch(operation){
    case "Q": result = await doSelect(...args);break;
    case "I": result = await doInsert(...args);break;
    case "U": result = await doUpdate(...args);break;
    case "D": result = await doDelete(...args);break;
    case "C": await commitChange(...args);break;
    default : result = undefined;break; 
  }
  return result;
}

