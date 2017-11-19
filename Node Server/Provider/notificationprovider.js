const dbconnect = require('../Provider/dbconnect'),
    parseutil = require('../Tools/parseutil'),
    rannum = require('../Tools/encrypt');

const TAG = "notification provider";
module.exports.addoneusertomanyNotification = async(array) => {
    try {
        /**
         * [0]:User send notif
         * [1]:Title notification
         * [2]:Content notification
         * [3]:userid who receive notification
         */
        let randomnumber = rannum.randommanydigit(15);
        let data = await dbconnect.doTransaction(`
        BEGIN
        SAVEPOINT start_tran;
        INSERT INTO NOTIFICATION_ (NO_ID,NO_STATUS,USER_ID_SEND_NOTIFICATION,NO_TITLE,NO_MESSAGE,NOTIF_TYPE) VALUES (${randomnumber},0,:USER_SEND_NOTIF,:TITLE,:CONTENT,0);
        INSERT INTO USER_NOTIF VALUES (:USERRECEIVENOTIF_ID,${randomnumber},0);
      EXCEPTION
        WHEN OTHERS THEN
          ROLLBACK TO start_tran;
          RAISE;
      END;
        `, [...array]);
        if (data) {
            return {
                c0: "Y"
            };
        }
        return {
            c0: "N"
        };
    } catch (err) {
        console.log(`addNotification got error from ${TAG} log: `, err);
    }
}
module.exports.addmanyUsertooneNotification = async(array) => {
    /**
     * [0]:User id who send notification
     * [1]:Title Notif
     * [2]:Content Notif
     * [3]:jsonarray arrayofuserid who receive notification string
     * 
     */
    try {
        let arrayofuserid = array[3];
        array.pop();

        let randomnumber = rannum.randommanydigit(15);
        let sql = "";
        for (let i = 0; i < arrayofuserid.length; i++) {
            sql += `INTO USER_NOTIF (USER_ID,NOTI_ID,UNREAD) VALUES (${arrayofuserid[i]},${randomnumber},0) \n`
        }
        let data = await dbconnect.executeQuery(`
        INSERT ALL
        INTO NOTIFICATION_ (NO_ID,NO_STATUS,USER_ID_SEND_NOTIFICATION,NO_TITLE,NO_MESSAGE,NOTIF_TYPE) VALUES (${randomnumber},0,:USER_SEND_NOTIF,:TITLE,:CONTENT,0)
        ${sql}
        SELECT * FROM dual
        `, [...array], "I");
        if (data) {
            return {
                c0: "Y"
            };
        } else {
            return {
                c0: "N"
            };
        }
    } catch (err) {
        console.log(`addNotification got error from ${TAG} log: `, err);
    }
}

module.exports.MarkAsReadNotification = async(array) => {
    try {
        /**
         * [0]:userid who read notification already
         * [1]:notif_id which user read
         */
        return await dbconnect.executeQuery(`UPDATE USER_NOTIF SET UNREAD=1 WHERE USER_ID=:userid  
        AND NOTI_ID=:noti_id`, [...array], "U");
    } catch (err) {
        console.log(`MarkAsReadNotification got error from ${TAG} log: `, err);
    }
}

module.exports.GetUnReadNotification = async(array) => {
    try {
        /**
         * [0]:userid get unread notification for user
         * 
         */
        return await dbconnect.executeQuery(`SELECT N.NO_ID,N.NO_TITLE,N.NO_MESSAGE,N.NO_STATUS FROM NOTIFICATION_ N
        INNER JOIN USER_NOTIF UN ON UN.NOTI_ID=N.NO_ID 
        INNER JOIN USER_ U ON U.ID=UN.USER_ID
        WHERE U.ID =:userid AND UN.UNREAD=0`, [...array], "Q");
    } catch (err) {
        console.log(`MarkAsReadNotification got error from ${TAG} log: `, err);
    }
}

module.exports.GetAllNotification = async(array) => {
    try {
        /**
         * [0]:userid get all user notification for user
         * 
         */
        console.log(array + " array pass to getallnotification");
        return await dbconnect.executeQuery(`SELECT N.NO_ID,N.NO_TITLE,N.NO_MESSAGE,N.NOTIF_TYPE,UN.UNREAD FROM NOTIFICATION_ N
        INNER JOIN USER_NOTIF UN ON UN.NOTI_ID=N.NO_ID 
        INNER JOIN USER_ U ON U.ID=UN.USER_ID
        WHERE U.ID =:userid`, [...array], "Q");
    } catch (err) {
        console.log(`GetAllNotification got error from ${TAG} log: `, err);
    }
}