
function displayUserProfilePageDatas(){
    //根据本地localstorge中存放的sessionkey向服务器请求UserProfilepage页面包含的信息
    var sessionkey = window.localStorage.getItem("sessionkey");
    if(sessionkey == null){
        mdui.alert("身份验证失效！请重新登入！");
        window.location.href = "login.html";
        return;
    }
    //用ajax向服务器请求数据
    $.ajax({
        type: "POST",
        url: "PageData",
        data: {
            opt: "getUserProfilePageData",
            sessionkey: sessionkey
        },
        async: true,//异步
        dataType: "text",
        success: function (data, status) {
            var datajsonobj = $.parseJSON(data);
            if (status === "success") {//post 成功 不出错
                if(datajsonobj.statu === "success"){
                    var pagedatajsonstr = datajsonobj.userprofilepagedata;
                    var pagedatajsonobj = $.parseJSON(pagedatajsonstr);
                    var userdatajsonobj = $.parseJSON(pagedatajsonobj.userdatajsonstr);
                    var userfinancialjsonobj = $.parseJSON(pagedatajsonobj.userfinancialjsonstr);
                    //有更多条目时，在此添加
                    $("#userprofile-title-username").text(userdatajsonobj.username);
                    $("#userprofile-baseinfo-userid").text(userdatajsonobj.userid);
                    $("#userprofile-baseinfo-registertime").text(userdatajsonobj.registertime);
                    $("#userprofile-baseinfo-lastlogintime").text(userdatajsonobj.lastlogintime);
                    $("#userprofile-baseinfo-phone").text(userdatajsonobj.phone);
                    $("#userprofile-baseinfo-email").text(userdatajsonobj.email);
                    $("#userprofile-baseinfo-qq").text(userdatajsonobj.qq);
                    $("#userprofile-financial-balance").text(userfinancialjsonobj.balance);

                }else if(datajsonobj.statu === "faild"){//失败
                    mdui.alert(datajsonobj.errmsg);
                }
            } else {
                mdui.alert(status + "\n" + data);
            }
        },
        error: function (xmlhr, msg) {
            mdui.alert(msg);
        }
    });
    mdui.mutation();
}