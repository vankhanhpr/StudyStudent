
var oracledb = require('oracledb');
var dbconfig = require("../Configuration/dbconfig.js");
exports.executeQuery= function(sql,callback,param,operation){
  oracledb.getConnection({
    user :dbconfig.user,
    password :dbconfig.password ,
    connectionString: dbconfig.connectionString
  },function(err,connection){
    if(err){
      callback(null,err.message);
      return;
    }
    /* connection.execute(sql,{},
      { outFormat: oracledb.OBJECT, extendedMetaData: true }
,function(err,result){
      if(err){
        callback(null,err.message);
        return;
      }
      //console.log(result.rows);
      doRelease(connection);
      callback(result.rows);
    }); */
    switch(operation){
      case "Q": doSelect(connection,sql,callback);break;
      case "I": doInsert(connection,sql,param,callback);break;
      case "U": doUpdate(connection,sql,callback);break;
      case "D": doDelete(connection,sql,callback);break;
    }
  });
}

function doRelease(connection){
  connection.close(
    function(err){
        if(err){
          console.error(err.message);
          return;
        }
  });
}
//done
function doSelect(connection,sql,callback){
  connection.execute(sql,{},{outFormat:oracledb.OBJECT,extendedMetaData:true},function(err,result){
    if(err){
      callback(null,err.message);
      return;
    }
    doRelease(connection);
    callback(result.rows);
  });
}
//done
function doInsert(connection,sql,param,callback){
  console.log(sql);
  connection.execute(sql,param,{autoCommit:true},function(err,result){
    if(err){
      callback(null,err.message);
      return;
    }
    doRelease(connection);   
    callback(result.rowsAffected);
  })
}



