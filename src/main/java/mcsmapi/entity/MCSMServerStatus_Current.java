package mcsmapi.entity;

import com.alibaba.fastjson.JSONObject;

public class MCSMServerStatus_Current {
    private JSONObject jsonObject;
    private String jsonStr;

    private String id;
    private String motd;
    private String current_players;
    private String max_players;
    private String version;
    private String servername;
    private Boolean status;

    public MCSMServerStatus_Current(String jsonstr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonstr);
        this.jsonStr = jsonstr;
        this.jsonObject = jsonObject;

        this.status = jsonObject.getBoolean("status");
        this.id = jsonObject.getString("id");
        this.motd = jsonObject.getString("motd");
        this.current_players = jsonObject.getString("current_players");
        this.servername = jsonObject.getString("serverName");
        this.max_players = jsonObject.getString("max_players");
        this.version = jsonObject.getString("version");
    }

    public MCSMServerStatus_Current(JSONObject jsonObject){
        this.jsonObject = jsonObject;
        this.jsonStr = jsonObject.toJSONString();

        this.status = jsonObject.getBoolean("status");
        this.id = jsonObject.getString("id");
        this.motd = jsonObject.getString("motd");
        this.current_players = jsonObject.getString("current_players");
        this.servername = jsonObject.getString("serverName");
        this.max_players = jsonObject.getString("max_players");
        this.version = jsonObject.getString("version");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMotd() {
        return motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public String getCurrent_players() {
        return current_players;
    }

    public void setCurrent_players(String current_players) {
        this.current_players = current_players;
    }

    public String getMax_players() {
        return max_players;
    }

    public void setMax_players(String max_players) {
        this.max_players = max_players;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getServername() {
        return servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }
}
