package Agent.PageData;

import Agent.DatabaseAgent;
import Agent.SessionKeyAgent;
import com.alibaba.fastjson.JSONObject;
import entity.User;
import entity.Userfinancial;

public class FirstPageDataAgent {
    private final SessionKeyAgent sessionKeyAgent = new SessionKeyAgent();
    private final DatabaseAgent databaseAgent = new DatabaseAgent();

    public FirstPageDataAgent() {
    }

    public String getFirstPageDataJsonStringBySessionkey(String sessionkey){

        if(!sessionKeyAgent.checkSessionkeyVaild(sessionkey)){
            return null;
        }

        User user = databaseAgent.getUserByUserid(databaseAgent.getSessionkeyBySessionkey(sessionkey).getUserid());
        Userfinancial userfinancial = databaseAgent.getUserfinancialByUserid(user.getUserid());
        String username = user.getUsername();
        String userbalance = String.valueOf(userfinancial.getBalance());

        JSONObject jsonObject = new JSONObject();
        //页面数据条目，增加条目直接增加对应的json put即可
        jsonObject.put("username",username);
        jsonObject.put("userbalance",userbalance);

        return jsonObject.toJSONString();
    }
}
