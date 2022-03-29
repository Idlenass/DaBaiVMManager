package Agent.MCSM;

import Agent.DatabaseAgent;
import entity.Mcsmserverappendinfo;
import mcsmapi.entity.MCSMServer;

public class McsmserverMixInfo {
    private final DatabaseAgent databaseAgent = new DatabaseAgent();
    //传向前端的实体，提供minecraftserverpage页面所需要的数据

    private String serverName;
    private String jarName;
    private String xmx;
    private String java;
    private String timeLimitDate;;
    private String area;
    private boolean autoRestart;
    private String address;
    private int port;
    private boolean run;
    private String currentPlayers;
    private String maxPlayers;

    //根据MCSMServer信息生成前端所需的信息，部分喜喜根据servername到数据库中读取
    public McsmserverMixInfo(MCSMServer mcsmServer) {
        serverName = mcsmServer.getServerName();
        jarName = mcsmServer.getJarname();
        xmx = mcsmServer.getXmx();
        java = mcsmServer.getJava();
        timeLimitDate = mcsmServer.getTimeLimitDate();
        autoRestart = mcsmServer.getAutoRestart();
        run = mcsmServer.isRun();
        currentPlayers = mcsmServer.getCurrentPlayers();
        maxPlayers = mcsmServer.getMaxPlayers();

        Mcsmserverappendinfo mcsmserverappendinfo = databaseAgent.getMcsaiByServername(mcsmServer.getServerName());
        //如果数据库中不存在数据,直接跳过数据插入
        if(mcsmserverappendinfo != null){
            area = mcsmserverappendinfo.getServerarea();
            address = mcsmserverappendinfo.getServeraddr();
            port = mcsmserverappendinfo.getServerport();
        }

    }

    public String getJarName() {
        return jarName;
    }

    public void setJarName(String jarName) {
        this.jarName = jarName;
    }

    public String getXmx() {
        return xmx;
    }

    public void setXmx(String xmx) {
        this.xmx = xmx;
    }

    public String getJava() {
        return java;
    }

    public void setJava(String java) {
        this.java = java;
    }

    public String getTimeLimitDate() {
        return timeLimitDate;
    }

    public void setTimeLimitDate(String timeLimitDate) {
        this.timeLimitDate = timeLimitDate;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isAutoRestart() {
        return autoRestart;
    }

    public void setAutoRestart(boolean autoRestart) {
        this.autoRestart = autoRestart;
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
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
}
