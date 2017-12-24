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
      conn ? await doRelease(conn):undefined;
    }
}
module.exports.doTransaction = doTransaction;
async function doTransaction(sql,param){
  let conn;
  let returnstate=false;
  try{
    conn = await oracledb.getConnection(dbconfig);
    let result = await conn.execute(sql,param);
    await commitChange(conn);
    returnstate = true;
  }catch(err){
    console.log(err);
    conn.rollback();
  }finally{
    conn ? await doRelease(conn):undefined;
    return returnstate;
  }
}

const doProcedure = async(conn,sql,param)=>{
  try{
    let result = await conn.execute(sql,param,{extendedMetaData:true});
    return result.outBinds;
  }catch(err){
    console.log('doProcedure error ',err);
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
    let result = await conn.execute(sql,param,{autoCommit:true});
    return result.rowsAffected;
  }catch(err){
    console.log('Insert error ',err);
  }
}

const doDelete = async (conn,sql,param)=>{
  try{
    let result = await conn.execute(sql,param,{autoCommit:true});
    return result.rowsAffected;
  }catch(err){
    console.log('Delete error ',err);
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
module.exports.doProcedureForRefCursor = async(sql,param,callback)=>{
  try{
    let conn;
    try{
      conn = await oracledb.getConnection(dbconfig);
      let result = await conn.execute(sql,param,{outFormat:oracledb.OBJECT,extendedMetaData:true});
      fetchRowsFromRS(conn,result.outBinds.cursor,(data)=>{
        callback(data);
      });
      await commitChange(conn);
    }catch(err){
      console.log(err);
      conn.rollback();
    }
    
    
  }catch(err){
    console.log("doProcedureForRefCursor error log: ",err);
  }
}
const  fetchRowsFromRS =  (connection, cursor,callback)=>
{
    let result = [];
    let stream = cursor.toQueryStream(); 
    stream.on('data',(data)=>{
      result.push(data);
    })
    stream.on('end', function () {
       console.log("end stream");
       doRelease(connection);
       callback(result);
    });
    stream.on('error',(err)=>{
      console.log(err);
      doRelease(connection);
    })
    
}
const doCloseResultSet = (resultSet)=>
{
  try{
     resultSet.close();
  }catch(err){
    console.log(err);
  }
}
const requestOperation = async (operation,...args) => {
  let result;
  switch(operation){
    case "Q": result = await doSelect(...args);break;
    case "I": result = await doInsert(...args);break;
    case "U": result = await doUpdate(...args);break;
    case "D": result = await doDelete(...args);break;
    case "PR": result = await doProcedure(...args);break;
    case "C": await commitChange(...args);break;
    default : result = undefined;break; 
  }
  return result;
}

