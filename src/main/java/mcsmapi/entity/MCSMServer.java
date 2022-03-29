package mcsmapi.entity;

import com.alibaba.fastjson.JSONObject;

public class MCSMServer {
    private String jsonStr;
    private JSONObject jsonObject;

    private String serverName;

    private String filename;
    private String createDate;
    private String lastDate;
    private String timeLimitDate;
    private String ie;
    private String oe;
    private Boolean autoRestart;
    private String java;
    private String jarname;
    private String Xmx;
    private String Xms;
    private String highCommande;
    private boolean run;
    private String currentPlayers;
    private String maxPlayers;
//    private String dockerConfig;
//    private String dockerCommand;
//    private String dockerImageName;
//    private String dockerXmx;
//    private String dockerPorts;
//    private String isDocker;

    public MCSMServer(String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        this.jsonStr = jsonStr;
        this.jsonObject = jsonObject;

        this.serverName = (String) jsonObject.get("serverName");

        JSONObject data = jsonObject.getJSONObject("data");

        this.filename = data.getString("__filename__");
        this.createDate = data.getString("createDate");
        this.lastDate = data.getString("lastDate");
        this.timeLimitDate = data.getString("timeLimitDate");
        this.ie = data.getString("ie");
        this.oe = data.getString("oe");
        this.autoRestart = data.getBoolean("autoRestart");
        this.java = data.getString("java");
        this.jarname = data.getString("jarName");
        this.Xmx = data.getString("Xmx");
        this.Xms = data.getString("Xms");
        this.highCommande = data.getString("highCommande");
        this.run = data.getBoolean("run");
        this.currentPlayers = data.getString("currentPlayers");
        this.maxPlayers = data.getString("maxPlayers");

    }

    public MCSMServer(JSONObject jsonObject){
        this.jsonStr = jsonObject.toJSONString();
        this.jsonObject = jsonObject;

        this.serverName = (String) jsonObject.get("serverName");

        JSONObject data = jsonObject.getJSONObject("data");

        this.filename = data.getString("__filename__");
        this.createDate = data.getString("createDate");
        this.lastDate = data.getString("lastDate");
        this.timeLimitDate = data.getString("timeLimitDate");
        this.ie = data.getString("ie");
        this.oe = data.getString("oe");
        this.autoRestart = data.getBoolean("autoRestart");
        this.java = data.getString("java");
        this.jarname = data.getString("jarName");
        this.Xmx = data.getString("Xmx");
        this.Xms = data.getString("Xms");
        this.highCommande = data.getString("highCommande");
        this.run = data.getBoolean("run");
        this.currentPlayers = data.getString("currentPlayers");
        this.maxPlayers = data.getString("maxPlayers");
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getTimeLimitDate() {
        return timeLimitDate;
    }

    public void setTimeLimitDate(String timeLimitDate) {
        this.timeLimitDate = timeLimitDate;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    public String getOe() {
        return oe;
    }

    public void setOe(String oe) {
        this.oe = oe;
    }

    public Boolean getAutoRestart() {
        return autoRestart;
    }

    public void setAutoRestart(Boolean autoRestart) {
        this.autoRestart = autoRestart;
    }

    public String getJava() {
        return java;
    }

    public void setJava(String java) {
        this.java = java;
    }

    public String getJarname() {
        return jarname;
    }

    public void setJarname(String jarname) {
        this.jarname = jarname;
    }

    public String getXmx() {
        return Xmx;
    }

    public void setXmx(String xmx) {
        Xmx = xmx;
    }

    public String getXms() {
        return Xms;
    }

    public void setXms(String xms) {
        Xms = xms;
    }

    public String getHighCommande() {
        return highCommande;
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public String getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(String currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public String getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(String maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setHighCommande(String highCommande) {
        this.highCommande = highCommande;
    }
}
