function removeAllItemsActiveClass(){
    $(".dby-leftmenu-item").removeClass("mdui-list-item-active");
}

function menu_mainpage_click(){
    removeAllItemsActiveClass();
    $("#menu_item_mainpage").addClass("mdui-list-item-active");
    loadFirstPage();
}

function menu_shop_click(){
    removeAllItemsActiveClass();
    $("#menu_item_shop").addClass("mdui-list-item-active");
}

function menu_mcserver_click(){
    removeAllItemsActiveClass();
    $("#menu_item_mcserver").addClass("mdui-list-item-active");
    loadMcPage();
}

function menu_vps_click(){
    removeAllItemsActiveClass();
    $("#menu_item_vps").addClass("mdui-list-item-active");
    loadVpsListPage();
}

function menu_userprofile_click(){
    removeAllItemsActiveClass();
    $("#menu_item_userprofile").addClass("mdui-list-item-active");
    loadUserprofilePage();
}

function menu_financialcenter_click(){
    removeAllItemsActiveClass();
    $("#menu_item_financialcenter").addClass("mdui-list-item-active");
    loadFinancialcenterPage();
}

function menu_workorder_click(){
    removeAllItemsActiveClass();
    $("#menu_item_workorder").addClass("mdui-list-item-active");
    alert("敬请期待");
}

function menu_accountsettings_click(){
    removeAllItemsActiveClass();
    $("#menu_item_accountsettings").addClass("mdui-list-item-active");
    loadAccountsettingsPage();
}