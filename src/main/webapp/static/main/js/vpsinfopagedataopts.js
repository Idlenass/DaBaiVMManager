function initPageData(){
    let CurrentVmid = window.sessionStorage.getItem("vmid");
    let sessionkey = window.localStorage.getItem("sessionkey");
    if(sessionkey == null){
        mdui.alert("身份验证失败！请重新登入！");
        window.location.href = "login.html";
        return;
    }
    displayVPSinfo(sessionkey,CurrentVmid);
}

function displayVPSinfo(sessionkey,CurrentVmid){
    //用ajax向服务器请求数据
    $.ajax({
        type: "POST",
        url: "PVEOption",
        data: {
            opt: "getVMinfo",
            sessionkey: sessionkey,
            vmid: CurrentVmid
        },
        async: true,//异步
        dataType: "text",
        success: function (data, status) {
            var datajsonobj = $.parseJSON(data);
            if (status === "success") {//post 成功 不出错
                if(datajsonobj.statu === "success"){
                    var vmdata = datajsonobj.vminfo;
                    var vmdataobj = $.parseJSON(vmdata);
                    // console.log("请求已完成");
                    displayVPSinfoByVue(vmdataobj);
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

function displayVPSinfoByVue(vpsobj){
    let vueopts = {
        data(){
            return {
                vps: vpsobj
            }
        }
    };

    let app = Vue.createApp(vueopts);

    app.component('vpsinfocard',{
        props:["vps"],
        methods: {
            transformByteToStr:(bytes)=>{
                let str;
                if(bytes < Math.pow(1024,1)){
                    // console.log("<1k");
                    str = bytes + " B";
                    return str;
                }
                if(bytes < Math.pow(1024,2)){
                    // console.log("<1m");
                    str = (bytes/Math.pow(1024,1)).toFixed(2) + " KB";
                    return str;
                }
                if(bytes < Math.pow(1024,3)){
                    // console.log("<1g");
                    str = (bytes/Math.pow(1024,2)).toFixed(2) + " MB";
                    return str;
                }
                if(bytes < Math.pow(1024,4)){
                    // console.log("<1t");
                    str = (bytes/Math.pow(1024,3)).toFixed(2) + " GB";
                    return str;
                }
                if(bytes < Math.pow(1024,5)){
                    // console.log("<1p");
                    str = (bytes/Math.pow(1024,4)).toFixed(2) + " TB";
                    return str;
                }
            }
        },
        mounted(){
            setCPUPercentValue(this.vps.cpuusage*100);
            setRAMPercentValue((this.vps.nowmem/this.vps.maxmem)*100);
            autoFillNet();
            autoFillDisk();
            mdui.mutation();
        },
        template:
        `
    <div class="mdui-card">
        <div class="dby-vpsinfo-card-header">
            <div class="mdui-card-header">
                <div class="mdui-card-header-avatar mdui-icon material-icons">storage</div>
                <div class="mdui-card-header-title">
                    VPS-{{vps.vmid}}
                </div>
                <div class="mdui-card-header-subtitle">
                    到期:{{vps.timelimitdate}}
                </div>
            </div>
            <button 
                class="dby-vpsinfo-reflushbtn mdui-float-right mdui-btn" 
                onclick="loadVpsInfoPage()"
                title="刷新"
                >
                    <i class="mdui-icon material-icons">cached</i>
                </button>
        </div>
    </div>

    <div class="mdui-row">
        <div class="mdui-col-xs-5 mdui-col-offset-xs-1">
            <div class="dby-vpsinfo-base-card mdui-card mdui-ripple">
                <div class="mdui-card-header">
                    <div class="dby-vpsinfo-card-header-title mdui-card-header-title">
                        实例信息
                    </div>
                </div>
                <div class="mdui-card-content">
                    <div class="dby-vpsinfo-content">
                        <div class="dby-vpsinfo-content-title">
                            实例名称
                        </div>
                        <div class="dby-vpsinfo-content-body">
                            VPS-{{vps.vmid}}
                        </div>
                    </div>
                    <div class="dby-vpsinfo-content">
                        <div class="dby-vpsinfo-content-title">
                            实例状态
                        </div>
                        <div class="dby-vpsinfo-content-body dby-vpsinfo-statu">
                            <div class="statu-icon statu-run"
                                 v-bind:class="{'statu-run':vps.running,'statu-stop':!vps.running}"></div>
                            <div>{{vps.running?'运行中':'已停止'}}</div>
                        </div>
                    </div>
                    <div class="dby-vpsinfo-content">
                        <div class="dby-vpsinfo-content-title">
                            CPU
                        </div>
                        <div class="dby-vpsinfo-content-body">
                            {{vps.vcpus}}核
                        </div>
                    </div>
                    <div class="dby-vpsinfo-content">
                        <div class="dby-vpsinfo-content-title">
                            RAM
                        </div>
                        <div class="dby-vpsinfo-content-body">
                            {{vps.maxmem/1024/1024/1024}}GB
                        </div>
                    </div>
                    <div class="dby-vpsinfo-content">
                        <div class="dby-vpsinfo-content-title">
                            系统盘
                        </div>
                        <div class="dby-vpsinfo-content-body">
                            {{vps.maxdisk/1024/1024/1024}}GB
                        </div>
                    </div>
                    <div class="dby-vpsinfo-content">
                        <div class="dby-vpsinfo-content-title">
                            网络带宽
                        </div>
                        <div class="dby-vpsinfo-content-body">
                            {{vps.bandwitdh==""?0:vps.bandwitdh}}M
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="mdui-col-xs-5">
            <div class="mdui-row">
                <div class="mdui-card mdui-col-xs-6 dby-vpsinfo-card-left mdui-ripple">
                    <div class="mdui-card-header">
                        <div class="dby-vpsinfo-card-header">
                            <div>
                                <div class="dby-vpsinfo-card-header-title mdui-card-header-title">
                                    CPU
                                </div>
                                <div class="dby-vpsinfo-card-header-title mdui-card-header-subtitle">
                                    <p>总量: {{vps.vcpus}}核</p>
                                </div>
                            </div>
                            <div>
                                <div class="con">
                                    <div class="percent-circle percent-circle-left">
                                        <div id="left-content-cpu" class="left-content"></div>
                                    </div>
                                    <div class="percent-circle percent-circle-right">
                                        <div id="right-content-cpu" class="right-content"></div>
                                    </div>
                                    <div id="text-circle-cpu" class="text-circle">0%</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="mdui-card mdui-col-xs-6 dby-vpsinfo-card-right mdui-ripple">
                    <div class="mdui-card-header">
                        <div class="dby-vpsinfo-card-header">
                            <div>
                                <div class="dby-vpsinfo-card-header-title mdui-card-header-title">
                                    RAM
                                </div>
                                <div class="dby-vpsinfo-card-header-title mdui-card-header-subtitle">
                                    <p>当前: {{transformByteToStr(vps.nowmem)}}</p>
                                    <p>总量: {{transformByteToStr(vps.maxmem)}}</p>
                                </div>
                            </div>
                            <div>
                                <div class="con">
                                    <div class="percent-circle percent-circle-left">
                                        <div id="left-content-ram" class="left-content"></div>
                                    </div>
                                    <div class="percent-circle percent-circle-right">
                                        <div id="right-content-ram" class="right-content"></div>
                                    </div>
                                    <div id="text-circle-ram" class="text-circle">0%</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="mdui-row">
                <div class="mdui-card mdui-col-xs-6 dby-vpsinfo-card-left mdui-ripple">
                    <div class="mdui-card-header">
                        <div class="dby-vpsinfo-card-header">
                            <div>
                                <div class="dby-vpsinfo-card-header-title mdui-card-header-title">
                                    网络
                                </div>
                                <div class="dby-vpsinfo-card-header-title mdui-card-header-subtitle">
                                    <p>带宽: {{vps.bandwitdh==""?0:vps.bandwitdh}}M</p>
                                </div>
                            </div>
                            <div>
                                <div class="con">
                                    <div class="percent-circle percent-circle-left">
                                        <div id="autofill-left-content-net" class="left-content"></div>
                                    </div>
                                    <div class="percent-circle percent-circle-right">
                                        <div id="autofill-right-content-net" class="right-content"></div>
                                    </div>
                                    <div id="autofill-text-circle-net" class="text-circle">{{vps.bandwitdh==""?0:vps.bandwitdh}}M</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="mdui-card mdui-col-xs-6 dby-vpsinfo-card-right mdui-ripple">
                    <div class="mdui-card-header">
                        <div class="dby-vpsinfo-card-header">
                            <div>
                                <div class="dby-vpsinfo-card-header-title mdui-card-header-title">
                                    系统盘
                                </div>
                                <div class="dby-vpsinfo-card-header-title mdui-card-header-subtitle">
                                    <p>总量: {{vps.maxdisk/1024/1024/1024}}GB</p>
                                </div>
                            </div>
                            <div>
                                <div class="con">
                                    <div class="percent-circle percent-circle-left">
                                        <div id="autofill-left-content-disk" class="left-content"></div>
                                    </div>
                                    <div class="percent-circle percent-circle-right">
                                        <div id="autofill-right-content-disk" class="right-content"></div>
                                    </div>
                                    <div id="autofill-text-circle-disk" class="text-circle">{{vps.maxdisk/1024/1024/1024}}GB</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="mdui-row">
        <div class="mdui-col-xs-5 mdui-col-offset-xs-1">
            <div class="mdui-card mdui-col-xs-6 dby-vpsinfo-count-card-left">
                <div class="mdui-card-header">
                    <ion-icon class="mdui-card-header-avatar" name="swap-vertical-outline"></ion-icon>
                    <div class="mdui-card-header-title">
                        网络IO
                    </div>
                    <div class="mdui-card-header-subtitle">
                        <p>总上行: {{transformByteToStr(vps.netout)}}</p>
                        <p>总下行: {{transformByteToStr(vps.netin)}}</p>
                    </div>
                </div>
            </div>
            <div class="mdui-card mdui-col-xs-6 dby-vpsinfo-count-card-right">
                <div class="mdui-card-header">
                    <ion-icon class="mdui-card-header-avatar" name="swap-horizontal-outline"></ion-icon>
                    <div class="mdui-card-header-title">
                        磁盘IO
                    </div>
                    <div class="mdui-card-header-subtitle">
                        <p>总读取: {{transformByteToStr(vps.diskread)}}</p>
                        <p>总写入: {{transformByteToStr(vps.diskwrite)}}</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="dby-vpsinfo-iso-card mdui-card mdui-col-xs-5">
            <div class="mdui-card-header">
                <i class="mdui-card-header-avatar mdui-icon material-icons">album</i>
                <div class="mdui-card-header-title">
                    换碟
                </div>
                <div class="mdui-card-header-subtitle">
                    当前镜像：{{vps.cdrom}}
                </div>
                <button class="mdui-btn mdui-color-theme-accent mdui-float-right"
                        onclick="loadVpsCDROMPage()">更换</button>
            </div>
        </div>
    </div>

    <div class="mdui-row">
        <div class="mdui-col-xs-5 mdui-col-offset-xs-1">
            <div class="dby-vpsinfo-remote-card mdui-card">
                <div class="mdui-card-header">
                    <div class="dby-vpsinfo-card-header-title mdui-card-header-title">
                        远程登入
                    </div>
                </div>
                <div class="dby-vpsinfo-remote-info-card mdui-row">
                    <div class="mdui-col-xs-4">
                        <div class="mdui-card mdui-ripple mdui-shadow-0">
                            <div class="mdui-card-header">
                                <div class="dby-vpsinfo-card-header-title mdui-card-header-title">
                                    服务器地址:
                                </div>
                                <div class="dby-vpsinfo-card-header-title mdui-card-header-subtitle">
                                    {{vps.ipaddr}}
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="mdui-col-xs-4">
                        <div class="mdui-card mdui-ripple mdui-shadow-0">
                            <div class="mdui-card-header">
                                <div class="dby-vpsinfo-card-header-title mdui-card-header-title">
                                    公网端口:
                                </div>
                                <div class="dby-vpsinfo-card-header-title mdui-card-header-subtitle">
                                    {{vps.natportbegin}} - {{vps.natportend}}
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="mdui-col-xs-4">
                        <div class="mdui-card mdui-color-theme-accent mdui-ripple mdui-shadow-0" onclick="openVNC()">
                            <div class="mdui-card-header">
                                <div class="dby-vpsinfo-card-header-title mdui-card-header-title">
                                    VNC控制台
                                </div>
                                <div class="dby-vpsinfo-card-header-title mdui-card-header-subtitle mdui-ripple">
                                    点击启动
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="dby-vpsinfo-pwropt-card mdui-card mdui-col-xs-5">
            <div class="mdui-card-header">
                <div class="dby-vpsinfo-card-header-title mdui-card-header-title">
                    操作台
                </div>
            </div>
            <div class="mdui-card-primary" onclick="mdui.alert('请在VNC控制台中操作！')">
                <div class="dby-vpsinfo-pwropt-row mdui-row">
                    <div class="mdui-col-xs-3">
                        <div class="dby-vpsinfo-pwropt-btn mdui-btn mdui-color-theme-accent mdui-ripple mdui-shadow-2"
                             mdui-tooltip="{content: '启动', position: 'top'}">
                            <i class="dby-vpsinfo-pwropt-btn-text mdui-icon material-icons">play_arrow</i>
                        </div>
                    </div>
                    <div class="mdui-col-xs-3">
                        <div class="dby-vpsinfo-pwropt-btn mdui-btn mdui-color-theme-accent mdui-ripple mdui-shadow-2"
                             mdui-tooltip="{content: '关机', position: 'top'}">
                            <i class="dby-vpsinfo-pwropt-btn-text mdui-icon material-icons">power_settings_new</i>
                        </div>
                    </div>
                    <div class="mdui-col-xs-3">
                        <div class="dby-vpsinfo-pwropt-btn mdui-btn mdui-color-theme-accent mdui-ripple mdui-shadow-2"
                             mdui-tooltip="{content: '重新启动', position: 'top'}">
                            <i class="dby-vpsinfo-pwropt-btn-text mdui-icon material-icons">loop</i>
                        </div>
                    </div>
                    <div class="mdui-col-xs-3">
                        <div class="dby-vpsinfo-pwropt-btn mdui-btn mdui-color-theme-accent mdui-ripple mdui-shadow-2"
                             mdui-tooltip="{content: '强制关机', position: 'top'}">
                            <i class="dby-vpsinfo-pwropt-btn-text mdui-icon material-icons">stop</i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    `
    })

    app.mount("#vpsinfo");

}

function openVNC(){
    var sessionkey = window.localStorage.getItem("sessionkey");
    var vmid = window.sessionStorage.getItem("vmid");
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
            opt: "getVNCinfo",
            sessionkey: sessionkey,
            vmid: vmid
        },
        async: true,//异步
        dataType: "text",
        success: function (data, status) {
            var datajsonobj = $.parseJSON(data);
            if (status === "success") {//post 成功 不出错
                if(datajsonobj.statu === "success"){
                    let vncinfo = $.parseJSON(datajsonobj.vncinfo);
                    let pvecookie = vncinfo.pveauthcookie;
                    let url = vncinfo.url;
                    $.cookie(
                        'PVEAuthCookie',
                        pvecookie,
                        {
                            domain:'.dabaiyun.net',
                            path:'/',
                            expires:1
                        }
                    );
                    window.open(url,"_blank");

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

function setCPUPercentValue(value) {
    var leftContent = document.getElementById("left-content-cpu");
    var rightContent  = document.getElementById("right-content-cpu");
    var textCircle  = document.getElementById("text-circle-cpu");

    var angle = 0;

    var timerId = setInterval(function(){
        angle += 1;
        if(angle > value*3.6){
            clearInterval(timerId);
        }else{
            if(angle > 180){
                rightContent.setAttribute('style', 'transform: rotate('+(angle-180)+'deg)');
            }else{
                leftContent.setAttribute('style', 'transform: rotate('+angle+'deg)');
            }
            textCircle.innerHTML = parseInt(angle*100/360) +'%';
        }
    },15);
}

function setRAMPercentValue(value) {
    var leftContent  = document.getElementById("left-content-ram");
    var rightContent  = document.getElementById("right-content-ram");
    var textCircle   = document.getElementById("text-circle-ram");

    var angle = 0;

    var timerId = setInterval(function(){
        angle += 1;
        if(angle > value*3.6){
            clearInterval(timerId);
        }else{
            if(angle > 180){
                rightContent.setAttribute('style', 'transform: rotate('+(angle-180)+'deg)');
            }else{
                leftContent.setAttribute('style', 'transform: rotate('+angle+'deg)');
            }
            textCircle.innerHTML = parseInt(angle*100/360) +'%';
        }
    },15);
}

function autoFillNet(){
    let leftContent  = document.getElementById("autofill-left-content-net");
    let rightContent  = document.getElementById("autofill-right-content-net");
    // let textCircle   = document.getElementById("autofill-text-circle-net");

    let angle = 0;

    let timerId = setInterval(function(){
        angle += 1;
        if(angle > 360){
            clearInterval(timerId);
        }else{
            if(angle > 180){
                rightContent.setAttribute('style', 'transform: rotate('+(angle-180)+'deg)');
            }else{
                leftContent.setAttribute('style', 'transform: rotate('+angle+'deg)');
            }
            // textCircle.innerHTML = parseInt(angle*100/360) +'%';
        }
    },15);
}

function autoFillDisk(){
    let leftContent  = document.getElementById("autofill-left-content-disk");
    let rightContent  = document.getElementById("autofill-right-content-disk");
    // let textCircle   = document.getElementById("autofill-text-circle-disk");

    let angle = 0;

    let timerId = setInterval(function(){
        angle += 1;
        if(angle > 360){
            clearInterval(timerId);
        }else{
            if(angle > 180){
                rightContent.setAttribute('style', 'transform: rotate('+(angle-180)+'deg)');
            }else{
                leftContent.setAttribute('style', 'transform: rotate('+angle+'deg)');
            }
            // textCircle.innerHTML = parseInt(angle*100/360) +'%';
        }
    },15);
}
