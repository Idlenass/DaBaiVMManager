package mcsmapi.entity;

import com.alibaba.fastjson.JSONArray;

import java.util.*;

public class MCSMUserList {
    private Map<String,MCSMUser> usermap = new HashMap<>();
    private List<MCSMUser> userlist = new ArrayList<>();

    public MCSMUserList(JSONArray dataJsonArray) {
        for(int i = 0;i < dataJsonArray.size();i++){
            MCSMUser user = new MCSMUser(dataJsonArray.getJSONObject(i));//从jsonarray字符串数组中提取单个jsonobj
            usermap.put(user.getUsername(),user);
            userlist.add(user);
        }
    }

    public Map<String, MCSMUser> getUsermap() {
        return usermap;
    }

    public List<MCSMUser> getUserlist() {
        return userlist;
    }

    public MCSMUser getUserByUsername(String username){
        return usermap.get(username);
    }

    public Set<String> getKeySet(){
        return usermap.keySet();
    }

    public int size(){
        return usermap.size();
    }
}
