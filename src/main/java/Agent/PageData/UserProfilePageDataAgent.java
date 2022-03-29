package Agent.PageData;

import Agent.DatabaseAgent;
import Agent.SessionKeyAgent;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import entity.User;
import entity.Userfinancial;

public class UserProfilePageDataAgent {
    private final SessionKeyAgent sessionKeyAgent = new SessionKeyAgent();
    private final DatabaseAgent databaseAgent = new DatabaseAgent();

    public UserProfilePageDataAgent() {
    }

    public String getUserProfilePageDataJsonStringBySessionkey(String sessionkey){

        if(!sessionKeyAgent.checkSessionkeyVaild(sessionkey)){
            return null;
        }

        User user = databaseAgent.getUserByUserid(databaseAgent.getSessionkeyBySessionkey(sessionkey).getUserid());
        Userfinancial userfinancial = databaseAgent.getUserfinancialByUserid(user.getUserid());

        JSONObject jsonObject = new JSONObject();
        //页面数据条目，增加条目直接增加对应的json put即可
        jsonObject.put("userdatajsonstr", JSON.toJSONString(user));
        jsonObject.put("userfinancialjsonstr",JSON.toJSONString(userfinancial));

        return jsonObject.toJSONString();
    }
}
