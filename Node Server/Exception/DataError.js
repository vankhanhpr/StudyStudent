module.exports = {
    SUCCESS:{
        Code :"000000",
        Message : "Success"
    },
    LOGIN_PROBLEM:{
        Code :"XXXXXX",
        Message : "SYSTEM message: You cannot login now. Please contact to us for support!"
    },
    WRONG_JSON_FORMAT:{
        Code :"XXXXX0",
        Message : "Request syntax error. Please contact to us for support"
    },
    SERVER_BUSY:{
        Code :"XXXXX1",
        Message : "Server is busy process. Please try again later"
    },
    LOGIN_ALREADY:{
        Code :"XXXXX2",
        Message : "You already loginged, Cannot login again!"
    },
    LOGIN_ID_NOT_MATCH:{
        Code :"XXXXX3",
        Message : "Login ID != Login request (AppLoginID)"
    },
    AUTHENTICATION_ALERT:{
        Code :"XXXXX4",
        Message : "You are not authentication OTP"
    },
    RELOGIN_ALERT:{
        Code :"XXXXX5",
        Message : "Please try to re-login to use our services."
    },
    WRONG_SESSION:{
        Code :"XXXXX6",
        Message : "You already loginged other session. System will disconnected this session now."
    },
    SESSION_TIMEOUT:{
        Code :"XXXXX7",
        Message : "Your session time out. System will disconnected this session now."
    },
    REQUEST_TIMEOUT:{
        Code :"XXXXX9",
        Message : "Request timeout"
    },
    NETWORK_WEAK:{
        Code :"XXXX10",
        Message : "Your network not good now. For safly reason, Please try to login again"
    },
    NETWORK_CHANGE:{
        Code :"XXXX11",
        Message : "Your network already change connection. For safely reason, Plase try to login again!"
    },
    SESSION_INFORMATION_CHANGE:{
        Code :"XXXX12",
        Message : "Your session information was changed. Please try to login again!"
    },
    SYSTEM_BUSY:{
        Code :"XXXX13",
        Message : "System is busy, Please try again later"
    },
    DICONNECT_SESSION:{
        Code :"XXXX14",
        Message : "Your session time out. System will disconnected this session now. Please try to refresh your client to using system"
    },
    INVALID_REQUEST:{
        Code :"XXXX00",
        Message : "Invalid request"
    },
    CANCEL_REQUEST:{
        Code :"XXXX01",
        Message : "Request cannot accept now"
    }
}