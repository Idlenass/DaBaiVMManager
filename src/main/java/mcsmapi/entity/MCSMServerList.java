package mcsmapi.entity;

import com.alibaba.fastjson.JSONArray;

import java.util.*;

public class MCSMServerList {
    private Map<String, MCSMServer> servermap = new HashMap<>();
    private List<MCSMServer> serverlist = new ArrayList<>();

    public MCSMServerList(JSONArray dataJsonArray) {
        for(int i = 0;i < dataJsonArray.size();i++){
            MCSMServer server = new MCSMServer(dataJsonArray.getJSONObject(i));//从jsonarray字符串数组中提取单个jsonobj
            servermap.put(server.getServerName(),server);
            serverlist.add(server);
        }
    }

    public Map<String, MCSMServer> getServermap() {
        return servermap;
    }

    public List<MCSMServer> getServerlist() {
        return serverlist;
    }

    public MCSMServer getServerByServerName(String serverName){
        return servermap.get(serverName);
    }

    public Set<String> getKeySet(){
        return servermap.keySet();
    }

    public int size(){
        return servermap.size();
    }
}
