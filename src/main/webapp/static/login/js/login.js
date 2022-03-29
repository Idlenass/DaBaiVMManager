function login() {
    var usr = $("#login-userid").val();
    var pwd = $("#login-passwd").val();

    if(usr === "" || pwd === ""){
        $("#login-errmsg").text("账号或密码不能为空");
        return;
    }

    $("#login-button").text("正在验证...");
    $.ajax({
        type: "POST",
        url: "Login",
        data: {
            loginmode: "pwd",
            username: usr,
            password: pwd
        },
        dataType: "text",
        success: function (data, status) {
            var datajsonobj = $.parseJSON(data);
            if (status === "success") {
                if(datajsonobj.loginstatu === "success"){
                    $("#login-button").text("登入成功! 正在跳转...");
                    setSessionkeyToLocalStorge(datajsonobj.sessionkey);
                    setTimeout(function (){
                        window.location.href = "main.html";
                    },500);
                }else if(datajsonobj.loginstatu === "faild"){
                    $("#login-errmsg").text("账号或密码错误");
                    $("#login-button").text("重新验证");
                    // alert(datajsonobj.errmsg);
                }
            } else {
                alert(status + "\n" + data);
            }
        },
        error: function (xmlhr, msg) {
            alert(msg);
        }
    });
}

function autoLoginAgent(){
    var localsessionkey = getSessionkeyFromLocalStorge();
    if(localsessionkey != null){
        autoLogin(localsessionkey);
    }
}

function autoLogin(sessionkeystr){
    $.ajax({
        type: "POST",
        url: "Login",
        data: {
            loginmode: "key",
            sessionkey: sessionkeystr
        },
        dataType: "text",
        success: function (data, status) {
            var datajsonobj = $.parseJSON(data);
            if (status === "success") {
                if(datajsonobj.loginstatu === "success"){
                    $("#login-button").text("登入成功! 正在跳转...");
                    setSessionkeyToLocalStorge(datajsonobj.sessionkey);
                    setTimeout(function (){
                        window.location.href = "main.html";
                    },500);
                }else if(datajsonobj.loginstatu === "faild"){//Key无效
                    $("#login-errmsg").text("验证信息已过期，请重新登入");
                    window.localStorage.removeItem("sessionkey");
                }
            } else {
                alert(status + "\n" + data);
            }
        },
        error: function (xmlhr, msg) {
            alert(msg);
        }
    });
}

function setSessionkeyToLocalStorge(sessionkey){
    var oStorge = window.localStorage;
    oStorge.setItem("sessionkey",sessionkey);
}

function getSessionkeyFromLocalStorge(){
    var oStorge = window.localStorage;
    return oStorge.getItem("sessionkey");
}