
var oracledb = require('oracledb');
var dbconfig = require("../Configuration/dbconfig.js");
exports.executeQuery= function(sql,callback,param){
  oracledb.getConnection({
    user :dbconfig.user,
    password :dbconfig.password ,
    connectionString: dbconfig.connectionString
  },function(err,connection){
    if(err){
      callback(null,err.message);
      return;
    }
    connection.execute(sql,param,
      { outFormat: oracledb.OBJECT, extendedMetaData: true }
,function(err,result){
      if(err){
        callback(null,err.message);
        return;
      }
      //console.log(result.rows);
      doRelease(connection);
      callback(result.rows);
    });
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
