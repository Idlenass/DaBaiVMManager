function displayVpsListPageDatas(){
    //根据本地localstorge中存放的sessionkey向服务器请求page页面包含的信息
    let sessionkey = window.localStorage.getItem("sessionkey");
    if(sessionkey == null){
        mdui.alert("身份验证失败！请重新登入！");
        window.location.href = "login.html";
        return;
    }
    //用ajax向服务器请求数据
    $.ajax({
        type: "POST",
        url: "PageData",
        data: {
            opt: "getVPSListPageData",
            sessionkey: sessionkey
        },
        async: true,//异步
        dataType: "text",
        success: function (data, status) {
            let datajsonobj = $.parseJSON(data);
            if (status === "success") {//post 成功 不出错
                if(datajsonobj.statu === "success"){
                    let pagedatajsonstr = datajsonobj.vpslistpagedata;
                    let pagedatajsonobj = $.parseJSON(pagedatajsonstr);
                    //将数据放进sessionStorge供详情页调用
                    // window.sessionStorage.setItem(
                    //     "vpslistdata",
                    //     pagedatajsonobj.vpsinfolist
                    // );
                    //将数据传给VUE渲染
                    displayvpslistsdatabyvue(pagedatajsonobj);
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

function displayvpslistsdatabyvue(pagedatajsonobj){
    let vueopts = {
        data() {
            return {
                vpsinfolist: $.parseJSON(pagedatajsonobj.vpsinfolist),
                servernum: pagedatajsonobj.servernum,
                runningnum: pagedatajsonobj.runningnum,
                stoppingnum: pagedatajsonobj.stoppingnum,
            }
        }
    };
    let app = Vue.createApp(vueopts);

    app.component('vpslistcard',{
        props:["vps"],
        data(){
            return {
                vmid : this.vps.vmid,
                oslogofilepath : "static/common/oslogo/Windows.svg"
            }
        },
        template:
            `
        <div class="mdui-col-xs-4">
            <div class="mdui-card mdui-ripple" v-on:click="toInfoPage">
                <div class="mdui-card-header">
                    <img class="dby-oslogp mdui-card-header-avatar"
                         v-bind:src="oslogofilepath">
                    <div class="mdui-card-header-title dby-server-title">
                        <div>
                            VPS-{{vps.vmid}}
                        </div>
                    </div>
                    <div class="mdui-card-header-subtitle">
                        CPU {{vps.vcpus}}核  - 
                        内存 {{vps.maxmem/1024/1024/1024}}GB - 
                        系统盘 {{vps.maxdisk/1024/1024/1024}}GB -
                        带宽 {{vps.bandwitdh==""?0:vps.bandwitdh}}M
                    </div>
                </div>
                <div class="mdui-card-actions">
                    <div class="dby-server-status mdui-float-left">
                        <div class="server-icon"
                            v-bind:class="{'server-run':vps.running,'server-stop':!vps.running}"></div>
                        <div class="dby-server-status-text">{{vps.running?'运行中':'已停止'}}</div>
                    </div>
                    <div class="mdui-float-right">
                        <button class="mdui-btn mdui-ripple">详情</button>
                    </div>
                </div>
            </div>
        </div>
        `,
        methods:{
            toInfoPage: function (){
                let vmid = this.vmid;
                window.sessionStorage.setItem("vmid",vmid);
                loadVpsInfoPage();
            }
        },
        created(){
            oslogofilerootpath = "static/common/oslogo/";
            ostype = this.vps.ostype;
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
    });

    app.component('vpslistheader',{
        props:['servernum',"runningnum","stoppingnum"],
        template:
            `
        <div class="mdui-card-header-subtitle">
             总计 {{servernum}}  运行中 {{runningnum}}  已停止 {{stoppingnum}}
        </div>
        `
    });

    app.mount("#vpslist");

    mdui.mutation();
}


