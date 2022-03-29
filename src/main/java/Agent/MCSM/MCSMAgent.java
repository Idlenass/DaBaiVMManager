package Agent.MCSM;

import Agent.DatabaseAgent;
import Agent.SessionKeyAgent;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import entity.Mcsmuser;
import entity.Sessionkey;
import mcsmapi.McsmModule;
import mcsmapi.entity.MCSMServer;
import mcsmapi.entity.MCSMServerList;
import mcsmapi.entity.MCSMUser;
import mcsmapi.entity.MCSMUserList;

import java.util.ArrayList;
import java.util.List;

public class MCSMAgent {

    private final String url = "https://panel.dabaiyun.net";
    private final String APIkey = "96e77c74017a4b24b67aa55ff9d2f343";
    private final McsmModule mcsmModule = new McsmModule(url,APIkey);
    private final DatabaseAgent databaseAgent = new DatabaseAgent();
    private final SessionKeyAgent sessionKeyAgent = new SessionKeyAgent();

    public MCSMAgent() {}

    public Mcsmuser getMcsmuserByUserid(String userid){
        return databaseAgent.getMcsmuserByUserid(userid);
    }

    public List<MCSMServer> getUserMCSMServerListByUserid(String userid){
        //用于返回的List
        List<MCSMServer> serverList = new ArrayList<>();
        //数据库查询
        Mcsmuser mcsmuser = databaseAgent.getMcsmuserByUserid(userid);
        //如果userid查不到对应数据，返回空
        if(mcsmuser == null) return null;
        //向MCSM端查询该用户名下的servers
        MCSMUserList mcsmUserList = mcsmModule.getUserList();
        //取出该用户名的MCSMUser对象
        MCSMUser mcsmUser = mcsmUserList.getUserByUsername(mcsmuser.getMcsmusername());
        //如果username查不到对应数据，返回空
        if(mcsmUser == null) return null;
        //该用户名下的servers
        JSONArray allowedservers = mcsmUser.getAllowedServer();
        //MCSM查询全部Server列表
        MCSMServerList mcsmServerList = mcsmModule.getServerList();
        //循环 将数据库查询到的allowedserver加入到serverlist
        for (Object allowedserver : allowedservers) {
            //从list中读取该名称对应的Server对象
            MCSMServer mcsmServer = mcsmServerList.getServerByServerName((String) allowedserver);
            //如果MCSM面板端不存在该服务器，跳过插入，否则会插入null，导致空指针
            if (mcsmServer == null) {
                continue;
            }
            serverList.add(mcsmServer);
        }
        return serverList;
    }

    public JSONArray getUserMCSMServerListJSONArrayByUserid(String userid){
        List<MCSMServer> mcsmServerList =
                this.getUserMCSMServerListByUserid(userid);
        //把array中每个obj中不必要的信息剔除
        JSONArray jsonArrayori = JSONArray.parseArray(JSON.toJSONString(mcsmServerList));
        //如果mcsmServerList查不到对应数据，返回空JSONArray
        if(jsonArrayori == null) {
            return new JSONArray();
        }
        //用于返回的JSONArray
        JSONArray jsonArray = new JSONArray();
        for(int i = 0;i < jsonArrayori.size();i++){
            JSONObject jsonObject = jsonArrayori.getJSONObject(i);
            jsonObject.remove("jsonStr");
            jsonObject.remove("jsonObject");
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    public JSONArray getUserMCSMServerListJSONArrayBySessionkey(String sessionkey){
        //先检查sessionkey是否有效，无效返回null
        if(!sessionKeyAgent.checkSessionkeyVaild(sessionkey)){
            return null;
        }
        //如果key有效，则从数据库中读取
        Sessionkey sessionkeyobj = databaseAgent.getSessionkeyBySessionkey(sessionkey);
        String userid = sessionkeyobj.getUserid();
        return this.getUserMCSMServerListJSONArrayByUserid(userid);
    }

    public McsmResult getMCServerMixInfoListJsonArrayStrBySessionkey(String sessionkey){
        //Result返回对象
        McsmResult result = new McsmResult();
        //Userid
        String userid = databaseAgent.getSessionkeyBySessionkey(sessionkey).getUserid();
        //获取该userid用户下的serverlist
        List<MCSMServer> mcsmServerList = this.getUserMCSMServerListByUserid(userid);
        //userid查询不到任何数据
        if(mcsmServerList == null){
            result.setResultType(McsmResultType.McsmDataNotFound);
            return result;
        }
        //返回前端的server数据的列表
        List<McsmserverMixInfo> mcsmserverMixInfoList = new ArrayList<>();
        //循环将MCSMServer通过McsmserverMixInfo的构造器转换为McsmserverMixInfo对象并加入list
        for(MCSMServer mcsmServer : mcsmServerList){
            mcsmserverMixInfoList.add(new McsmserverMixInfo(mcsmServer));
        }
        //返回JSONArrayString
        result.setResultType(McsmResultType.Normal);
        result.setData(JSON.toJSONString(mcsmserverMixInfoList));
        return result;
    }

    //根据serverlist数组，循环遍历，找出有几个服务器在运行中
    public int getRunningMCServerNum(JSONArray serverMixInfoJsonArray){
        int num = 0;
        //如果传入参数是null或者数组为空，直接返回0个
        if(serverMixInfoJsonArray == null || serverMixInfoJsonArray.size() == 0){
            return 0;
        }
        //循环遍历
        for (Object o : serverMixInfoJsonArray) {
            if (((JSONObject) o).getBoolean("run")) {
                num++;
            }
        }
        return num;
    }

    //根据serverlist数组，循环遍历，找出有几个服务器不在运行中
    public int getStoppingMCServerNum(JSONArray serverMixInfoJsonArray){
        int num = 0;
        //如果传入参数是null或者数组为空，直接返回0个
        if(serverMixInfoJsonArray == null || serverMixInfoJsonArray.size() == 0){
            return 0;
        }
        //循环遍历
        for (Object o : serverMixInfoJsonArray) {
            if (!((JSONObject) o).getBoolean("run")) {
                num++;
            }
        }
        return num;
    }
}
