var pagedatajsonstr;
var pagedatajsonobj;

function displayMinecraftServerPageDatas(){
    //根据本地localstorge中存放的sessionkey向服务器请求Firstpage页面包含的信息
    var sessionkey = window.localStorage.getItem("sessionkey");
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
            opt: "getMinecraftServerPageData",
            sessionkey: sessionkey
        },
        async: true,//异步
        dataType: "text",
        success: function (data, status) {
            var datajsonobj = $.parseJSON(data);
            if (status === "success") {//post 成功 不出错
                if(datajsonobj.statu === "success"){
                    //将数据传给VUE渲染
                    pagedatajsonstr = datajsonobj.minecraftserverpagedata;
                    pagedatajsonobj = $.parseJSON(pagedatajsonstr);
                    displaymcsmserversdatabyvue();
                }else if(datajsonobj.statu === "faild"){//失败
                    mdui.alert(datajsonobj.errmsg);
                }
            } else {
                alert(status + "\n" + data);
            }
        },
        error: function (xmlhr, msg) {
            mdui.alert(msg);
        }
    });
    mdui.mutation();
}

function displaymcsmserversdatabyvue(){
    var vueopts = {
        data(){
            return {
                serverlist:  $.parseJSON(pagedatajsonobj.serverlistarrstr),
                servernum:  pagedatajsonobj.servernum,
                runningnum:  pagedatajsonobj.runningnum,
                stoppingnum:  pagedatajsonobj.stoppingnum,
            }
        }
    };

    var app = Vue.createApp(vueopts);

    app.component('mcserverlist',{
        props:['serverlist'],
        template:
            `
            <div v-for="server in serverlist">
            <!--            单个节点，用于展示server信息-->
            <div class="mdui-panel" mdui-panel>
                <!--                mdui-panel-item-open使面板默认打开-->
                <div class="mdui-panel-item">
                    <!--                    标题头-->
                    <div class="mdui-panel-item-header">
                        <div class="mdui-panel-item-title">
<!--                            流式布局 使得运行状态指示灯和服务器名称左右并排-->
                            <div class="dby-mcserver-title">
                                <!--                                根据服务器运行状态，绑定状态指示灯为绿色或者灰色-->
                                <div class="mcserver-icon" v-bind:class="{'mcserver-run':server.run,'mcserver-stop':!server.run}"></div>
                                <div>{{server.serverName}}</div>
                            </div>
                        </div>
                        <!--                        显示在线玩家-->
                        <div class="mdui-panel-item-summary">{{server.currentPlayers}}/{{server.maxPlayers}}</div>
                        <i class="mdui-panel-item-arrow mdui-icon material-icons">keyboard_arrow_down</i>
                    </div>
                    <div class="mdui-panel-item-body">
<!--                        一行-->
                        <div class="mdui-row mdui-row-xs-9">
                            <div class="mdui-col-xs-3">
                                <div class="mdui-card mdui-shadow-0">
                                    <div class="mdui-card-header">
                                        <div class="mdui-card-header-title">
                                            服务器核心
                                        </div>
                                        <div class="mdui-card-header-subtitle">
                                            {{server.jarName}}
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="mdui-col-xs-3">
                                <div class="mdui-card mdui-shadow-0">
                                    <div class="mdui-card-header">
                                        <div class="mdui-card-header-title">
                                            内存大小
                                        </div>
                                        <div class="mdui-card-header-subtitle">
                                            {{server.xmx}}
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="mdui-col-xs-3">
                                <div class="mdui-card mdui-shadow-0">
                                    <div class="mdui-card-header">
                                        <div class="mdui-card-header-title">
                                            Java环境
                                        </div>
                                        <div class="mdui-card-header-subtitle">
                                            {{server.java}}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--                        一行-->
                        <div class="mdui-row mdui-row-xs-9">
                            <div class="mdui-col-xs-3">
                                <div class="mdui-card mdui-shadow-0">
                                    <div class="mdui-card-header">
                                        <div class="mdui-card-header-title">
                                            到期时间
                                        </div>
                                        <div class="mdui-card-header-subtitle">
                                            {{server.timeLimitDate}}
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="mdui-col-xs-3">
                                <div class="mdui-card mdui-shadow-0">
                                    <div class="mdui-card-header">
                                        <div class="mdui-card-header-title">
                                            服务器区域
                                        </div>
                                        <div class="mdui-card-header-subtitle">
                                            {{server.area}}
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="mdui-col-xs-3">
                                <div class="mdui-card mdui-shadow-0">
                                    <div class="mdui-card-header">
                                        <div class="mdui-card-header-title">
                                            自动重启
                                        </div>
                                        <div class="mdui-card-header-subtitle">
                                            {{server.autoRestart ? '是' : '否'}}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--                        一行-->
                        <div class="mdui-row mdui-row-xs-9">
                            <div class="mdui-col-xs-3">
                                <div class="mdui-card mdui-shadow-0">
                                    <div class="mdui-card-header">
                                        <div class="mdui-card-header-title">
                                            服务器地址
                                        </div>
                                        <div class="mdui-card-header-subtitle">
                                            {{server.address}}
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="mdui-col-xs-3">
                                <div class="mdui-card mdui-shadow-0">
                                    <div class="mdui-card-header">
                                        <div class="mdui-card-header-title">
                                            端口
                                        </div>
                                        <div class="mdui-card-header-subtitle">
                                            {{server.port}}
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="mdui-col-xs-3">
                                <div class="mdui-card mdui-shadow-0">
                                    <div class="mdui-card-header">
                                        <div class="mdui-card-header-title">
                                            管理控制台
                                        </div>
                                        <div class="mdui-card-header-subtitle">
                                            <a class="consolelink" href="https://panel.dabaiyun.net" target="_blank">点击进入>></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="mdui-panel-item-actions">
                            <button class="mdui-btn mdui-ripple" >启动服务器</button>
                            <button class="mdui-btn mdui-ripple" >关闭服务器</button>
<!--                            <button class="mdui-btn mdui-ripple" mdui-panel-item-close>收起</button>-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        `
    });

    app.component('mcserverlistheader',{
        props:['servernum',"runningnum","stoppingnum"],
        template:
        `
            <div class="mdui-card-header-subtitle">
                 总计 {{servernum}}  运行中 {{runningnum}}  未启动 {{stoppingnum}}
            </div>
        `
    });

    app.mount("#mcserverlist")

    mdui.mutation();

    //test
    // console.log(vueopts.data().serverlist);
    // console.log(vueopts.data().servernum);
    // console.log(vueopts.data().runningnum);
    // console.log(vueopts.data().stoppingnum);
}
