package mcsmapi.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ResponseBody {
    private int status;
    private JSONArray data;
    private String error;

    public ResponseBody(String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        this.status = jsonObject.getInteger("status");
        this.data = (JSONArray) jsonObject.get("data");
        this.error = (String) jsonObject.get("error");
    }

    public ResponseBody(JSONObject jsonObject){
        this.status = jsonObject.getInteger("status");
        this.data = (JSONArray) jsonObject.get("data");
        this.error = (String) jsonObject.get("error");
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
