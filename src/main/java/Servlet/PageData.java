package Servlet;

import Agent.PVE.PveResult;
import Agent.PVE.PveResultType;
import Agent.PageData.*;
import Agent.MCSM.McsmResult;
import Agent.MCSM.McsmResultType;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PageData", value = "/PageData")
public class PageData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //让浏览器用utf8来解析返回的数据
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        //告诉servlet用UTF-8转码，而不是用默认的ISO8859
        response.setCharacterEncoding("UTF-8");

        PrintWriter printWriter = response.getWriter();

        JSONObject dataJsonObj = new JSONObject();//返回的数据

        String opt =  request.getParameter("opt");
        String sessionkey = request.getParameter("sessionkey");

        if(opt == null || sessionkey == null){
            dataJsonObj.put("statu","faild");
            dataJsonObj.put("errmsg","Paramter is null!");
            printWriter.print(dataJsonObj.toJSONString());
            return;
        }

        switch (opt){
            case "getFirstPageData" : {
                FirstPageDataAgent firstPageDataAgent =
                        new FirstPageDataAgent();
                String data = firstPageDataAgent
                        .getFirstPageDataJsonStringBySessionkey(sessionkey);

                if(data == null){//key在数据库中找不到数据
                    dataJsonObj.put("statu","faild");
                    dataJsonObj.put("errmsg","sessionkey invaild!");
                    break;
                }

                dataJsonObj.put("statu","success");
                dataJsonObj.put("firstpagedata",data);
                break;
            }
            case "getUserProfilePageData" : {
                UserProfilePageDataAgent userProfilePageDataAgent =
                        new UserProfilePageDataAgent();
                String data = userProfilePageDataAgent
                        .getUserProfilePageDataJsonStringBySessionkey(sessionkey);

                if(data == null){//key在数据库中找不到数据
                    dataJsonObj.put("statu","faild");
                    dataJsonObj.put("errmsg","sessionkey invaild!");
                    break;
                }

                dataJsonObj.put("statu","success");
                dataJsonObj.put("userprofilepagedata",data);
                break;
            }
            case "getFinancialCenterPageData" : {
                FinancialCenterPageDataAgent financialCenterPageDataAgent =
                        new FinancialCenterPageDataAgent();
                String data = financialCenterPageDataAgent
                        .getFinancialCenterPageDataJsonStringBySessionkey(sessionkey);

                if(data == null){//key在数据库中找不到数据
                    dataJsonObj.put("statu","faild");
                    dataJsonObj.put("errmsg","sessionkey invaild!");
                    break;
                }

                dataJsonObj.put("statu","success");
                dataJsonObj.put("financialcenterpagedata",data);
                break;
            }
            case "getMinecraftServerPageData" : {
                MCSMDataAgent minecraftServerPageDataAgent =
                        new MCSMDataAgent();
                McsmResult result = minecraftServerPageDataAgent
                        .getMinecraftServerPageDataJsonStringBySessionkey(sessionkey);
                //数据正常
                if(result.getResultType() == McsmResultType.Normal || result.getResultType() == McsmResultType.McsmDataNotFound){
                    dataJsonObj.put("statu","success");
                    dataJsonObj.put("minecraftserverpagedata",result.getData());
                    break;
                }else{//数据异常
                    dataJsonObj.put("statu","faild");
                    dataJsonObj.put("errmsg",result.getResultType().toString());
                    break;
                }
            }
            case "getVPSListPageData" : {
                VPSDataAgent vpsDataAgent =
                        new VPSDataAgent();
                PveResult pveResult = vpsDataAgent
                        .getVPSListPageDataBySessionkey(sessionkey);
                //数据正常
                if(pveResult.getResultType() == PveResultType.Normal){
                    dataJsonObj.put("statu","success");
                    dataJsonObj.put("vpslistpagedata",pveResult.getData());
                    break;
                }else{//数据异常
                    dataJsonObj.put("statu","faild");
                    dataJsonObj.put("errmsg",pveResult.getResultType().toString() + ":" + pveResult.getData());
                    break;
                }
            }
            default:{
                dataJsonObj.put("statu","faild");
                dataJsonObj.put("errmsg","opt invaild!");
                break;
            }
        }
        //向前端返回数据
        String rtstr = dataJsonObj.toJSONString();
        printWriter.print(rtstr);
    }
//
//    protected String getUsernameBySessionkey(String sessionkey){
//
//        Sessionkey sessionkeyobj =  databaseAgent.getSessionkeyBySessionkey(sessionkey);
//        //key在数据库中找不到数据
//        if(sessionkeyobj == null){
//            return null;
//        }
//        //key已过期
//        if(!sessionKeyAgent.checkSessionkeyVaild(sessionkey)){
//            return null;
//        }
//        //根据key获取User对象
//        User user = databaseAgent.getUserByUserid(sessionkeyobj.getUserid());
//        //如果不存在此用户
//        if(user == null){
//            return null;
//        }
//        return user.getUsername();
//    }
//
//    protected String getUserBaseInfoJSONStringBySessionkey(String sessionkey){
//
//        Sessionkey sessionkeyobj =  databaseAgent.getSessionkeyBySessionkey(sessionkey);
//        if(sessionkeyobj == null){//key在数据库中找不到数据
//            return null;
//        }
//        if(!sessionKeyAgent.checkSessionkeyVaild(sessionkey)){//key已过期
//            return null;
//        }
//        return getUserBaseInfoJSONStringByUserid(sessionkeyobj.getUserid());
//    }
//
//    protected String getUserBaseInfoJSONStringByUserid(String userid){
//
//        User user =  databaseAgent.getUserByUserid(userid);
//        if(user == null){//userid无效
//            return null;
//        }
//        return JSON.toJSONString(user);
//    }
}
