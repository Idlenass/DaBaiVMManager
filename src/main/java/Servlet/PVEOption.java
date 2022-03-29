package Servlet;

import Agent.MCSM.McsmResult;
import Agent.MCSM.McsmResultType;
import Agent.PVE.PVEAgent;
import Agent.PVE.PveResult;
import Agent.PVE.PveResultType;
import Agent.PageData.*;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

@WebServlet(name = "PVEOption", value = "/PVEOption")
public class PVEOption extends HttpServlet {

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

        String opt = request.getParameter("opt");
        String sessionkey = request.getParameter("sessionkey");

        if(opt == null || sessionkey == null){
            dataJsonObj.put("statu","faild");
            dataJsonObj.put("errmsg","Paramter is null!");
            printWriter.print(dataJsonObj.toJSONString());
            return;
        }

        switch (opt){
            case "getVMinfo":{
                String vmidstr =  request.getParameter("vmid");
                int vmid = Integer.parseInt(vmidstr);
                VPSDataAgent vpsDataAgent = new VPSDataAgent(sessionkey);
                try {
                    PveResult pveResult = vpsDataAgent.getSingleVMinfo(vmid,sessionkey);
                    if(pveResult.getResultType() == PveResultType.Normal){
                        dataJsonObj.put("statu","success");
                        dataJsonObj.put("vminfo",pveResult.getData());
                    }else {
                        dataJsonObj.put("statu","faild");
                        dataJsonObj.put("errmsg",pveResult.getResultType().toString());
                    }
                } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                    dataJsonObj.put("statu","faild");
                    dataJsonObj.put("errmsg",e.toString());


                }
                break;
            }
            case "getVNCinfo" : {
                String vmid =  request.getParameter("vmid");
                VPSDataAgent vpsDataAgent = new VPSDataAgent(sessionkey);
                PveResult pveResult =
                        vpsDataAgent.getVNCConnectinfo(Integer.parseInt(vmid),sessionkey);
                if(pveResult.getResultType() != PveResultType.Normal){
                    dataJsonObj.put("statu","faild");
                    dataJsonObj.put("errmsg",pveResult.getResultType().toString());
                    break;
                }

                dataJsonObj.put("statu","success");
                dataJsonObj.put("vncinfo",pveResult.getData());
                break;
            }
            case "getISOFiles" : {
                VPSDataAgent vpsDataAgent = new VPSDataAgent(sessionkey);
                PveResult pveResult =
                        vpsDataAgent.getAllIsoFilesJsonArrayStr(sessionkey);
                if(pveResult.getResultType() != PveResultType.Normal){
                    dataJsonObj.put("statu","faild");
                    dataJsonObj.put("errmsg",pveResult.getResultType().toString());
                    break;
                }

                dataJsonObj.put("statu","success");
                dataJsonObj.put("isofiles",pveResult.getData());
                break;
            }
            case "setVMISO" : {
                String vmidstr = request.getParameter("vmid");
                int vmid = Integer.parseInt(vmidstr);
                String isoidstr = request.getParameter("isoid");
                int isoid = Integer.parseInt(isoidstr);
                VPSDataAgent vpsDataAgent = new VPSDataAgent();
                PveResult pveResult =
                        vpsDataAgent.setVMISO(sessionkey,vmid,isoid);
                if(pveResult.getResultType() != PveResultType.Normal){
                    dataJsonObj.put("statu","faild");
                    dataJsonObj.put("errmsg",pveResult.getResultType().toString());
                    break;
                }

                dataJsonObj.put("statu","success");
                dataJsonObj.put("data",pveResult.getData());
                break;
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
}
