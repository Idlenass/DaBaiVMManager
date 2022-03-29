package mcsmapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import mcsmapi.entity.MCSMUserList;
import mcsmapi.entity.ResponseBody;
import mcsmapi.entity.MCSMServerList;
import mcsmapi.entity.MCSMServerStatus_Current;

public class McsmModule {

    private McsmPoster mcsmPoster;
    public String url;
    public String APIkey;

    public MCSMServerStatus_Current getServerStatus(String serverName){
        String url =
                this.url +
                        "/api/status/" +
                        serverName;
        String response = mcsmPoster.sendPost(url);
        return new MCSMServerStatus_Current(response);
    }

    public ResponseBody startServer(String serverName){
        String url =
                this.url +
                        "/api/start_server/" +
                        serverName +
                        "?apikey=" +
                        APIkey;
        String response = mcsmPoster.sendPost(url);
        return new ResponseBody(response);
    }

    public ResponseBody stopServer(String serverName){
        String url =
                this.url +
                        "/api/stop_server/" +
                        serverName +
                        "?apikey=" +
                        APIkey;
        String response = mcsmPoster.sendPost(url);
        return new ResponseBody(response);
    }

    public ResponseBody restartServer(String serverName){
        String url =
                this.url +
                        "/api/restart_server/" +
                        serverName +
                        "?apikey=" +
                        APIkey;
        String response = mcsmPoster.sendPost(url);
        return new ResponseBody(response);
    }

    public ResponseBody sendCommand(String serverName,String cmd){
        String url =
                this.url +
                        "/api/execute/" +
                        "?apikey=" +
                        APIkey;
        JSONObject jsonParam = new JSONObject();

        jsonParam.put("name",serverName);
        jsonParam.put("command",cmd);

        String response = mcsmPoster.sendPost(url,jsonParam.toJSONString());
        return new ResponseBody(response);
    }

    public MCSMServerList getServerList(){
        String url =
                this.url +
                        "/api/server_list/" +
                        "?apikey=" +
                        APIkey;
        String response = mcsmPoster.sendPost(url);
        ResponseBody responseBody = new ResponseBody(response);
        return new MCSMServerList(responseBody.getData());
    }

    public MCSMUserList getUserList(){
        String url =
                this.url +
                        "/api/user_list/" +
                        "?apikey=" +
                        APIkey;
        String response = mcsmPoster.sendPost(url);
        ResponseBody responseBody = new ResponseBody(response);
        return new MCSMUserList(responseBody.getData());
    }

    public ResponseBody createUser(String username,String password,String serverlist){
        String url =
                this.url +
                        "/api/create_user/" +
                        "?apikey=" +
                        APIkey;
        JSONObject jsonParam = new JSONObject();

        jsonParam.put("username",username);
        jsonParam.put("password",password);
        jsonParam.put("serverlist",serverlist);

        String response = mcsmPoster.sendPost(url,jsonParam.toJSONString());
        return new ResponseBody(response);
    }

    public ResponseBody deleteUser(String username){
        String url =
                this.url +
                        "/api/delete_user/" +
                        username +
                        "?apikey=" +
                        APIkey;

        String response = mcsmPoster.sendPost(url);
        return new ResponseBody(response);
    }

    public ResponseBody deleteServer(String servername){
        String url =
                this.url +
                        "/api/delete_server/" +
                        servername +
                        "?apikey=" +
                        APIkey;

        String response = mcsmPoster.sendPost(url);
        return new ResponseBody(response);
    }

    public ResponseBody createServer(String servername,
                                     String javapath,
                                     String servercore,
                                     String maxram,
                                     String minram,
                                     String inputcoding,
                                     String outputcoding,
                                     String timelimitdate,
                                     String serverpath,
                                     String customcmd,
                                     String addcmd){
        String url =
                this.url +
                        "/api/create_server/" +
                        "?apikey=" +
                        APIkey;
        JSONObject jsonParam = new JSONObject();

        jsonParam.put("serverName",servername);
        jsonParam.put("java",javapath);
        jsonParam.put("jarName",servercore);
        jsonParam.put("Xmx",maxram);
        jsonParam.put("Xms",minram);
        jsonParam.put("ie",inputcoding);
        jsonParam.put("oe",outputcoding);
        jsonParam.put("timeLimitDate",timelimitdate);
        jsonParam.put("cwd",serverpath);
        jsonParam.put("highCommande",customcmd);
        jsonParam.put("addCmd",addcmd);

        String response = mcsmPoster.sendPost(url,jsonParam.toJSONString());
        return new ResponseBody(response);
    }

    public ResponseBody createServer_quick(
            String servername,
            String javapath,
            String servercore,
            String maxram,
            String timelimitdate
    ){
        String url =
                this.url +
                        "/api/create_server/" +
                        "?apikey=" +
                        APIkey;
        JSONObject jsonParam = new JSONObject();

        jsonParam.put("serverName",servername);
        jsonParam.put("java",javapath);
        jsonParam.put("jarName",servercore);
        jsonParam.put("Xmx",maxram);
        jsonParam.put("timeLimitDate",timelimitdate);

        String response = mcsmPoster.sendPost(url,jsonParam.toJSONString());
        return new ResponseBody(response);
    }

    //尚未完工
    public JSONObject advanced_createServer_full(
            String servername,
            String javapath,
            String servercore,
            String maxram,
            String minram,
            String timelimitdate,
            String serverpath,
            String customcmd,
            String addcmd,//传入多个附加参数时，用空格分割
            String stopcmd,
            String dockercmd,
            String dockerimg,
            String dockerXmx,
            String dockerports,
            Boolean isdocker,
            String mcpingname,
            String mcpinghost,
            String mcpingport,
            String mcpingmotd
    ){
        String url =
                this.url +
                        "/api/advanced_create_server/" +
                        "?apikey=" +
                        APIkey;

        JSONObject jsonParam = new JSONObject();
        JSONObject jsonParam_data = new JSONObject();
        String jsonParam_str_addCmd;
        JSONObject jsonParam_dockerConfig = new JSONObject();
        JSONObject jsonParam_mcpingConfig = new JSONObject();

        //用空格分割多个附加参数并放入json
        String[] addCmd_array = addcmd.split(" ");
        jsonParam_str_addCmd = "[";
        for(int i = 0;i < addCmd_array.length;i++){
            jsonParam_str_addCmd += "\"" + addCmd_array[i] + "\",";
        }
        jsonParam_str_addCmd = "]";

        jsonParam.put("serverName",servername);

        jsonParam_data.put("addCmd",jsonParam_str_addCmd);//未完成
        jsonParam_data.put("java",javapath);
        jsonParam_data.put("jarName",servercore);
        jsonParam_data.put("Xmx",maxram);
        jsonParam_data.put("Xms",minram);
        jsonParam_data.put("timeLimitDate",timelimitdate);
        jsonParam_data.put("cwd",serverpath);
        jsonParam_data.put("highCommande",customcmd);
        jsonParam_data.put("stopCommande",stopcmd);

        jsonParam_dockerConfig.put("dockerCommand",dockercmd);
        jsonParam_dockerConfig.put("dockerImageName",dockerimg);
        jsonParam_dockerConfig.put("dockerXmx",dockerXmx);
        jsonParam_dockerConfig.put("dockerPorts",dockerports);
        jsonParam_dockerConfig.put("isDocker",isdocker);

        jsonParam_data.put("dockerConfig",jsonParam_dockerConfig.toJSONString());

        jsonParam_mcpingConfig.put("mcpingName",mcpingname);
        jsonParam_mcpingConfig.put("mcpingHost",mcpinghost);
        jsonParam_mcpingConfig.put("mcpingPort",mcpingport);
        jsonParam_mcpingConfig.put("mcpingMotd",mcpingmotd);

        jsonParam_data.put("mcpingConfig",jsonParam_mcpingConfig.toJSONString());

        jsonParam.put("config",jsonParam_data.toJSONString());

        String response = mcsmPoster.sendPost(url,jsonParam.toJSONString());
        JSONObject jsonObject = JSON.parseObject(response);
        return jsonObject;
    }

    public ResponseBody advanced_configureserver(
            String servername,
            JSONObject config
            ){//由于此处参数可变，因此需要在外部直接构建好JSON数据
        String url =
                this.url +
                        "/api/advanced_configure_server/?apikey=" +
                        this.APIkey;
        JSONObject jsonParam = new JSONObject();

        jsonParam.put("serverName",servername);
        jsonParam.put("config",config.toJSONString());

        String response = mcsmPoster.sendPost(url,jsonParam.toJSONString());
        return new ResponseBody(response);
    }

    public ResponseBody resetServerTimeLimitDate(String servername,String timeLimitDate){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("timeLimitDate",timeLimitDate);
        return advanced_configureserver(servername,jsonObject);
    }

    public McsmModule(String url, String APIkey) {//在构造方法中传入APIkey并实例化post业务类
        this.url = url;
        this.APIkey = APIkey;
        this.mcsmPoster = new McsmPoster();
    }

    public String getAPIkey() {
        return APIkey;
    }

    public void setAPIkey(String APIkey) {
        this.APIkey = APIkey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
