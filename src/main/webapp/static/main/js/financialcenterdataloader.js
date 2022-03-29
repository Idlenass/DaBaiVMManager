function displayFinancialCenterDatas(){
    //根据本地localstorge中存放的sessionkey向服务器请求Firstpage页面包含的信息
    var sessionkey = window.localStorage.getItem("sessionkey");
    if(sessionkey == null){
        mdui.alert("身份验证失败！请重新登入！");
        window.location.href = "login.html";
        return;
    }
    //保存data的json格式字符串，用于返回
    var pagedatajsonstr;
    //用ajax向服务器请求数据
    $.ajax({
        type: "POST",
        url: "PageData",
        data: {
            opt: "getFinancialCenterPageData",
            sessionkey: sessionkey
        },
        async: true,//异步
        dataType: "text",
        success: function (data, status) {
            var datajsonobj = $.parseJSON(data);
            if (status === "success") {//post 成功 不出错
                if(datajsonobj.statu === "success"){
                    pagedatajsonstr = datajsonobj.financialcenterpagedata;
                    var pagedatajsonobj = $.parseJSON(pagedatajsonstr);
                    var userfinancialjsonobj = $.parseJSON(pagedatajsonobj.userfinancialjsonstr);
                    //有更多条目时，在此添加
                    $("#financialcenter-financial-balance").text(userfinancialjsonobj.balance);
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
