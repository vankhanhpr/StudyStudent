const teacherprovider = require('../Provider/teacherprovider'),
    checktype = require('../Validate/checktype'),
    JsonResponse = require('../FormatJson/JsonResponse'),
    parseutil = require('../Tools/parseutil'),
    chatprovider = require('../Provider/chatprovider'),
    notificationprovider = require('../Provider/notificationprovider'),
    textvalidate = require('../Validate/textvalidate');
const TAG = "Teacher Controller";
module.exports = (socket, datafromclient) => {
    requestService(datafromclient, socket);
}

const requestService = (datafromclient, ...args) => {
    switch (datafromclient.ServiceName) {
        case "addcourse":
            addCourse(datafromclient, ...args);
            break;
        case "getcoursebyid":
            getCourseById(datafromclient, ...args);
            break;
        case "getallcourseofteacher":
            getAllCourseOfTeacher(datafromclient, ...args);
            break;
        case "updatecoursedif":
            updateCourseDif(datafromclient, ...args);
            break;
        case "getlistfriend":
            GetListFriendAndStudent(datafromclient, ...args);
            break;
        case "addmanytoonenotif":
            AddManyUserToOneNotification(datafromclient, ...args);
            break;
        default:
            break;
    }
}
const addCourse = async(datafromclient, socket) => {
    try {
        datafromclient.InVal = parseutil.parseIntPosition(datafromclient.InVal, 0, 2, 3, 4);
        datafromclient.InVal[5] = JSON.parse(datafromclient.InVal[5]);
        datafromclient.InVal[6] = JSON.parse(datafromclient.InVal[6]);
        let data = await teacherprovider.addCourse(datafromclient.InVal);
        let resp;
        if (data) {
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, [{
                c0: "Y"
            }], datafromclient.UserType);
        } else {
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq, [{
                c0: "N"
            }], datafromclient.UserType);
        }
        socket.emit("RES_MSG",resp);
    } catch (err) {
        console.log(`addCource got eror from ${TAG} log err :`, err);
    }
}

const getCourseById = async(datafromclient, socket) => {
    try {
        datafromclient.InVal = parseutil.parseIntArray(datafromclient.InVal);
        let data = await teacherprovider.getCourseById(datafromclient.InVal);
        let resp;
        if (data) {
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, data, datafromclient.UserType);
        } else {
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq, null, datafromclient.UserType);
        }
        socket.emit("RES_MSG",resp);
    } catch (err) {
        console.log(`getCourseById got eror from ${TAG} log err :`, err);
    }
}

const getAllCourseOfTeacher = async(datafromclient, socket) => {
    try {

        datafromclient.InVal = parseutil.parseIntArray(datafromclient.InVal);
        let data = await teacherprovider.getAllCourseOfTeacher(datafromclient.InVal);
        let resp;
        if (data) {
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, data, datafromclient.UserType);
        } else {
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq, null, datafromclient.UserType);
        }
        socket.emit("RES_MSG",resp);
    } catch (err) {
        console.log(`getAllCourseOfTeacher got eror from ${TAG} log err :`, err);
    }
}

const updateCourse = async(datafromclient, socket) => {
    try {
        /**
         * Deprecated
         * [0]:location
         * [1]:fee
         * [2]:start_time
         * [3]:end_time
         * [4]:idcourse
         * [5]:arrayjson idofbuoi
         * [6]:arrayjson thu
         * [7]:arrayjson time
         * 
         */
        datafromclient.InVal = parseutil.parseIntPosition(datafromclient.InVal, 1, 2, 3, 4);
        datafromclient.InVal[5] = parseutil.parseIntArray(JSON.parse(datafromclient.InVal[5]));
        datafromclient.InVal[6] = JSON.parse(datafromclient.InVal[6]);
        datafromclient.InVal[7] = JSON.parse(datafromclient.InVal[7]);
        let data = await teacherprovider.updateCourse(datafromclient.InVal);
        let resp;
        if (data) {
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, data, datafromclient.UserType);
        } else {
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq, null, datafromclient.UserType);
        }
        socket.emit("RES_MSG",resp);
    } catch (err) {
        console.log(`updateCourse got eror from ${TAG} log err :`, err);
    }
}

const updateCourseDif = async(datafromclient, socket) => {
    try {
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
        datafromclient.InVal = parseutil.parseIntPosition(datafromclient.InVal, 1, 2, 3, 4);
        datafromclient.InVal[5] = JSON.parse(datafromclient.InVal[5]);
        datafromclient.InVal[6] = JSON.parse(datafromclient.InVal[6]);
        let data = await teacherprovider.updateCourseDif(datafromclient.InVal);
        let resp;
        if (data) {
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, [{
                c0: "Y"
            }], datafromclient.UserType);
        } else {
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq, [{
                c0: "N"
            }], datafromclient.UserType);
        }
        socket.emit("RES_MSG",resp);
    } catch (err) {
        console.log(`updateCourseDif got eror from ${TAG} log err :`, err);
    }
}

const GetListFriendAndStudent = async(datafromclient, socket) => {
    try {
        //InVal[0]:userid
        let data = await teacherprovider.GetListFriendAndStudent(parseutil.parseIntArray(datafromclient.InVal));
        let resp = null;
        if (data.length > 0) {
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, data, datafromclient.UserType);
        } else {
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq, [{
                c0: "N"
            }], datafromclient.UserType);
        }
        setTimeout(() => {
            socket.emit("RES_MSG",resp);
        }, 100);

    } catch (err) {
        console.log(`GetListFriendAndStudent got error from ${TAG} `, err);
    }

}

const AddManyUserToOneNotification = (datafromclient, socket) => {
    /**
     * [0]:User id who send notification
     * [1]:Title Notif
     * [2]:Content Notif
     * [3]:jsonarray arrayofuserid who receive notification string
     * 
     */
    datafromclient.InVal[3] = JSON.parse(datafromclient.InVal[3]);
    arruserreceivenotif = datafromclient.InVal[3];
    notificationprovider.addmanyUsertooneNotification(datafromclient.InVal).then(resolve => {
        let respuserreceive = null;
        let respusersend = null;
        if (resolve) {
            respuserreceive = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, [{
                notiftitle: datafromclient.InVal[1],
                notifcontent: datafromclient.InVal[2]
            }], datafromclient.UserType);
            respusersend = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, [{
                c0: "Y"
            }], datafromclient.UserType);
        } else {
            respusersend = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq, [{
                c0: "N"
            }], datafromclient.UserType);
        }
        setTimeout(() => {
            socket.emit("RES_MSG",respusersend);
            if (respuserreceive) {
                arruserreceivenotif.map((currentValue,index,arr)=>{
                    socket.to(chatprovider.FindSocketIdForNotif(datafromclient[index])).emit("RES_MSG",respuserreceive);    
                });
            }

        });
    }).catch(err => {
        console.log(`AddManyUserToOneNotification got error from ${TAG} `, err);
    });
}
