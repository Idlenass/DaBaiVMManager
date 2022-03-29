package mcsmapi.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class MCSMUser {
    private String username;
    private String lastDate;
    private String createDate;
    private JSONArray allowedServer;

    public MCSMUser(String jsonStr) {//使用JSON字符串的构造
        JSONObject data = JSONObject.parseObject(jsonStr);
        this.username = data.getString("username");
        this.lastDate = data.getString("lastDate");
        this.createDate = data.getString("createDate");
        this.allowedServer = data.getJSONArray("allowedServer");
    }

    public MCSMUser(JSONObject jsonObject){//使用JSON对象的构造
        this.username = jsonObject.getString("username");
        this.lastDate = jsonObject.getString("lastDate");
        this.createDate = jsonObject.getString("createDate");
        this.allowedServer = jsonObject.getJSONArray("allowedServer");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public JSONArray getAllowedServer() {
        return allowedServer;
    }

    public void setAllowedServer(JSONArray allowedServer) {
        this.allowedServer = allowedServer;
    }
}
