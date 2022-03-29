function initMainPage(){
    $(document).ready(function (){
        $("#leftmenu").load("childpages/leftmenu.html");
        $("#footer-golbal").load("childpages/footer-golbal.html");
        loadFirstPage();
        var localStorge = window.localStorage;
        var nightmode = localStorge.getItem("nightmode");
        if(nightmode != null){
            if(nightmode == "1"){
                mdui.$('body').addClass('mdui-theme-layout-dark');
            }else{
                mdui.$('body').removeClass('mdui-theme-layout-dark');
            }
        }else{
            mdui.$('body').removeClass('mdui-theme-layout-dark');
        }
    });
}


function toggleNightMode(){
    var localStorge = window.localStorage;
    var nightmode = localStorge.getItem("nightmode");
    var newmode = "0";
    if(nightmode != null){
        if(nightmode == "0"){
            mdui.$('body').addClass('mdui-theme-layout-dark');
            newmode = "1";
        }else{
            mdui.$('body').removeClass('mdui-theme-layout-dark');
            newmode = "0";
        }
    }else{
        mdui.$('body').addClass('mdui-theme-layout-dark');
        newmode = "1";
    }
    localStorge.setItem("nightmode",newmode);
}

function logout(){
    window.localStorage.removeItem("sessionkey");
    window.location.href = "login.html";
}