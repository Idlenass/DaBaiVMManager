function initPageData(){
    let CurrentVmid = window.sessionStorage.getItem("vmid");
    let sessionkey = window.localStorage.getItem("sessionkey");
    if(sessionkey == null){
        mdui.alert("身份验证失败！请重新登入！");
        window.location.href = "login.html";
        return;
    }
    displayISOFileList(sessionkey);
}

function displayISOFileList(sessionkey){
    //用ajax向服务器请求数据
    $.ajax({
        type: "POST",
        url: "PVEOption",
        data: {
            opt: "getISOFiles",
            sessionkey: sessionkey
        },
        async: true,//异步
        dataType: "text",
        success: function (data, status) {
            var datajsonobj = $.parseJSON(data);
            if (status === "success") {//post 成功 不出错
                if(datajsonobj.statu === "success"){
                    displayISOFileListByVue(datajsonobj.isofiles);
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
}

function displayISOFileListByVue(arr){
    let vueopts = {
        data(){
            return {
                isofiles:$.parseJSON(arr),
                vmid:window.sessionStorage.getItem("vmid")
            }
        },
        mounted(){
            mdui.mutation();
        }
    };

    let app = Vue.createApp(vueopts);

    app.component('isofileradio',{
        props:['isofile'],
        data(){
            return {
                isoid : this.isofile.isoid,
                oslogofilepath : "static/common/oslogo/Windows.svg"
            }
        },
        template:
            `
        <label class="mdui-list-item mdui-ripple">
<!--            <i class="mdui-list-item-icon mdui-icon material-icons">adjust</i>-->
            <img class="mdui-list-item-icon mdui-icon" 
                 v-bind:src="oslogofilepath">
            <div class="mdui-list-item-content">[{{isofile.displayostype}}] {{isofile.displayname}}</div>
            <div class="mdui-radio">
                <input type="radio" name="iso" v-bind:value="this.isoid"/>
                <i class="mdui-radio-icon"></i>
            </div>
        </label>
        `,
        created(){
            oslogofilerootpath = "static/common/oslogo/";
            ostype = this.isofile.displayostype;
            switch (ostype){
                case "CentOS":{
                    this.oslogofilepath = oslogofilerootpath + "CentOS.svg";
                    break;
                }
                case "Debian":{
                    this.oslogofilepath = oslogofilerootpath + "Debian.svg";
                    break;
                }
                case "Fedora":{
                    this.oslogofilepath = oslogofilerootpath + "Fedora.webp";
                    break;
                }
                case "FydeOS":{
                    this.oslogofilepath = oslogofilerootpath + "FydeOS.png";
                    break;
                }
                case "Deepin":{
                    this.oslogofilepath = oslogofilerootpath + "Deepin.png";
                    break;
                }
                case "Ubuntu":{
                    this.oslogofilepath = oslogofilerootpath + "Ubuntu.svg";
                    break;
                }
                case "UOS":{
                    this.oslogofilepath = oslogofilerootpath + "UOS.png";
                    break;
                }
                case "Windows":{
                    this.oslogofilepath = oslogofilerootpath + "Windows.svg";
                    break;
                }
                default :{
                    this.oslogofilepath = oslogofilerootpath + "Linux.png";
                    break;
                }
            }
        }
    })

    app.mount('#isofilelist');
}

function setIso(){//根据本地storge中存放的sessionkey向服务器请求page页面包含的信息
    let radios = $("input[name='iso']:checked");
    if(radios.length === 0){
        mdui.alert("请选择一个镜像","错误");
        return;
    }
    let isoid = radios[0].value;
    let vmid = window.sessionStorage.getItem("vmid");
    let sessionkey = window.localStorage.getItem("sessionkey");
    if(sessionkey == null){
        mdui.alert("身份验证失败！请重新登入！");
        window.location.href = "login.html";
        return;
    }
    //用ajax向服务器请求数据
    $.ajax({
        type: "POST",
        url: "PVEOption",
        data: {
            opt: "setVMISO",
            sessionkey: sessionkey,
            vmid:vmid,
            isoid:isoid
        },
        async: true,//异步
        dataType: "text",
        success: function (data, status) {
            let datajsonobj = $.parseJSON(data);
            if (status === "success") {//post 成功 不出错
                if(datajsonobj.statu === "success"){
                    let data = datajsonobj.data;
                    mdui.alert("更换成功！","更换镜像");
                    loadVpsInfoPage();
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
}
