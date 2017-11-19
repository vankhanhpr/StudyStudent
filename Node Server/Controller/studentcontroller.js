const studentprovider = require('../Provider/studentprovider'),
    exception = require('../Exception/exception'),
    notificationprovider = require('../Provider/notificationprovider'),
    textvalidate = require("../Validate/textvalidate"),
    parseutil = require("../Tools/parseutil"),
    JsonResponse = require('../FormatJson/JsonResponse');

var TAG = "student controller";

module.exports = (socket, datafromclient) => {
    requestService(datafromclient, socket);
}
const requestService = (datafromclient, ...args) => {
    switch (datafromclient.ServiceName) {
        case "findschedule":
            FindSchedule(datafromclient, ...args);
            break;
        case "getscheduleregistered":
            GetScheduleRegistered(datafromclient, ...args);
            break;
        case "registerschedule":
            RegisterSchedule(datafromclient, ...args);
            break;
        case "acceptparent":
            AcceptParent(datafromclient, ...args);
            break;
        case "addteacher":
            AddTeacher(datafromclient, ...args);
            break;
        case "getallnotification":
            GetAllNotification(datafromclient, ...args);
            break;
        case "getscheduletoregister":
            GetScheduleToRegister(datafromclient, ...args);
            break;
        case "getlistfriendofstudent":
            GetListFriendOfStudent(datafromclient, ...args);
            break;
            case "getsubscheduleofteacher":
            getSubScheduleOfTeacher(datafromclient,...args);
            break;
        default:
            break;
    }
}

const FindSchedule = async(datafromclient, socket) => {
    /**
     * [0]:findstr
     */
    try {
        let data = await studentprovider.FindSchedule(datafromclient.InVal);
        let resp;
        if (!textvalidate.isEmpty(data) && datafromclient.UserType === 0) {
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, data, datafromclient.UserType);
        } else {
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq, data, datafromclient.UserType)
        }
        socket.emit("RES_MSG", resp);
    } catch (err) {
        console.log(`FindSchedule got error from ${TAG} `, err);
    }
}

const GetScheduleRegistered = async(datafromclient, socket) => {
    try {
        /**
         * [0]:userid : number
         */
        let data = await studentprovider.GetScheduleRegistered(parseutil.parseIntArray(datafromclient.InVal));
        let resp;
        if (data) {
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, data, datafromclient.UserType);
        } else {
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq,null, datafromclient.UserType)
        }
        socket.emit("RES_MSG", resp);
    } catch (err) {
        console.log(`GetScheduleRegistered got error from ${TAG} `, err);
    }
}

const RegisterSchedule = async(datafromclient, socket) => {
    try {
        //[0] userid :number;
        //[1] scheduleid:number;
        let data = await studentprovider.RegisterSchedule(parseutil.parseIntArray(datafromclient.InVal));
        let resp;
        if (data.c0 === "Y") {
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, [data], datafromclient.UserType);
        } else {
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq, [data], datafromclient.UserType);
            resp.Message = "Lớp học của bạn đã được đăng ký hoặc xảy ra lỗi ở database";
        }
        socket.emit("RES_MSG", resp);
    } catch (err) {
        console.log(`RegisterSchedule got error from ${TAG} `, err);
    }
}

const AcceptParent = async(datafromclient, socket) => {
    try {
        /**
         * [0]:parentid:number
         * [1]:email:string
         */
        let data = await studentprovider.AcceptParent(parseutil.parseIntPosition(datafromclient.InVal, 0));
        let resp;
        if (data.c0 === "Y" && datafromclient.UserType === 0) {
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, [data], datafromclient.UserType);
        } else {
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq, [data], datafromclient.UserType)
        }
        socket.emit("RES_MSG", resp);
    } catch (err) {
        console.log(`AcceptParent got error from ${TAG} `, err);
    }
}

const AddTeacher = async(datafromclient, socket) => {
    /**
     * add to table relatioship
     * [0]:fromid:studentid:number
     * [1]:toid:teacherid:number
     */
    try {
        let data = await studentprovider.AddTeacher(parseutil.parseIntArray(datafromclient.InVal));
        let resp;
        if (data.c0 === "Y" && datafromclient.UserType === 0) {
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, [data], datafromclient.UserType);
        } else {
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq, [data], datafromclient.UserType)
        }
        socket.emit("RES_MSG", resp);
    } catch (err) {
        console.log(`AddTeacher got error from ${TAG} `, err);
    }
}
const getSubScheduleOfTeacher = async(datafromclient, socket) => {
        datafromclient.InVal = parseutil.parseIntArray(datafromclient.InVal);
        studentprovider.getSubScheduleOfTeacher(datafromclient.InVal).then(resolve=>{
            let resp;
            if (resolve) {
                resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, resolve, datafromclient.UserType);
            } else {
                resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq, null, datafromclient.UserType);
            }
            setTimeout(()=>{
                socket.emit("RES_MSG", resp);
            },100);
     
        }).catch(err=>{
            console.log(`getSubScheduleOfTeacher got eror from ${TAG} log err :`, err);
        });      
    
}
const GetAllNotification = (datafromclient, socket) => {
    /**
     * [0]:userid get all user notification for user
     * 
     */
    datafromclient.InVal = parseutil.parseIntArray(datafromclient.InVal);
    notificationprovider.GetAllNotification(datafromclient.InVal).then(resolve => {
        let resp = null;

        if (resolve) {
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, resolve, datafromclient.UserType);
        } else {
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq, null, datafromclient.UserType);
        }
        setTimeout(() => {
            socket.emit("RES_MSG", resp);
        }, 100);
    }).catch(err => {
        console.log(`AddManyUserToOneNotification got error from ${TAG} `, err);
    });
}


const GetScheduleToRegister = async(datafromclient, socket) => {
    /**
     * [0]:teacherid : number
     */
    datafromclient.InVal = parseutil.parseIntArray(datafromclient.InVal);
    studentprovider.GetScheduleToRegister(parseutil.parseIntArray(datafromclient.InVal)).then(resolve => {
        let resp;
        if (!textvalidate.isEmpty(resolve) && datafromclient.UserType === 0) {
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, resolve, datafromclient.UserType);
        } else {
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq, null, datafromclient.UserType)
        }
        socket.emit("RES_MSG", resp);
    }).catch(err => {
        console.log(`GetScheduleToRegister from ${TAG} log err: `, err);
    });
}
const GetListFriendOfStudent = async(datafromclient, socket) => {

    //InVal[0]:userid
    studentprovider.GetListFriendOfStudent(parseutil.parseIntArray(datafromclient.InVal)).then(resolve => {
        let resp = null;
        if (resolve.length > 0) {
            resp = new JsonResponse.RESPONSE_MSG_SUCCESS(socket.id, datafromclient.ClientSeq, resolve, datafromclient.UserType);
        } else {
            resp = new JsonResponse.RESPONSE_MSG_FAIL(socket.id, datafromclient.ClientSeq, [{
                c0: "N"
            }], datafromclient.UserType);
        }
        setTimeout(() => {
            socket.emit("RES_MSG", resp);
        }, 100);
    }).catch(err => {
        console.log(`GetListFriendOfStudent got error from ${TAG} `, err);
    });
}