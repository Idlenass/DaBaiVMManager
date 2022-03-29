package Agent.PageData;

import Agent.DatabaseAgent;
import Agent.MCSM.MCSMAgent;
import Agent.MCSM.McsmResult;
import Agent.MCSM.McsmResultType;
import Agent.SessionKeyAgent;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class MCSMDataAgent {
    private final MCSMAgent mcsmAgent = new MCSMAgent();
    private final DatabaseAgent databaseAgent = new DatabaseAgent();
    private final SessionKeyAgent sessionKeyAgent = new SessionKeyAgent();

    public MCSMDataAgent() {
    }

    public McsmResult getMinecraftServerPageDataJsonStringBySessionkey(String sessionkey){
        //Result返回对象
        McsmResult result = new McsmResult();
        //检测sessionkey是否有效
        if(!sessionKeyAgent.checkSessionkeyVaild(sessionkey)){
            result.setResultType(McsmResultType.SessionKeyInvaild);
            return result;
        }
        //用于返回的JSONObj对象
        JSONObject jsonObject = new JSONObject();
        //根据sessionkey获取servermixinfo列表
        result = mcsmAgent.getMCServerMixInfoListJsonArrayStrBySessionkey(sessionkey);
        //返回的数据
        String serverMixInfoJsonArrayStr = "";
        //根据返回的数据分类处理
        switch (result.getResultType()){
            //完全正常
            case Normal:{
                serverMixInfoJsonArrayStr = result.getData();//转换到jsonarray用于后续数据处理
                JSONArray serverMixInfoJsonArray = JSONArray.parseArray(serverMixInfoJsonArrayStr);
                //将数据写入jsonObj
                jsonObject.put("serverlistarrstr",serverMixInfoJsonArrayStr);
                jsonObject.put("servernum",serverMixInfoJsonArray.size());
                jsonObject.put("runningnum",mcsmAgent.getRunningMCServerNum(serverMixInfoJsonArray));
                jsonObject.put("stoppingnum",mcsmAgent.getStoppingMCServerNum(serverMixInfoJsonArray));
                result.setResultType(McsmResultType.Normal);
                result.setData(jsonObject.toJSONString());
                break;
            }
            //找不到user对应的MCSM用户
            case McsmDataNotFound:{
                //如果没有数据，则用0填充
                //转换到jsonarray用于后续数据处理
                serverMixInfoJsonArrayStr = "[]";
                //将数据写入jsonObj
                jsonObject.put("serverlistarrstr",serverMixInfoJsonArrayStr);
                jsonObject.put("servernum",0);
                jsonObject.put("runningnum",0);
                jsonObject.put("stoppingnum",0);
                result.setResultType(McsmResultType.McsmDataNotFound);
                result.setData(jsonObject.toJSONString());
                break;
            }
            //其他错误
            default:{
                //如果没有数据，则用0填充
                //转换到jsonarray用于后续数据处理
                serverMixInfoJsonArrayStr = "[]";
                //将数据写入jsonObj
                jsonObject.put("serverlistarrstr",serverMixInfoJsonArrayStr);
                jsonObject.put("servernum",0);
                jsonObject.put("runningnum",0);
                jsonObject.put("stoppingnum",0);
                result.setData(jsonObject.toJSONString());
                break;
            }
        }

        return result;
    }
}
