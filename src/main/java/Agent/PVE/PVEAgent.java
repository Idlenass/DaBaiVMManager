package Agent.PVE;

import Agent.DatabaseAgent;
import entity.Pveuser;
import entity.Sessionkey;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pveapi.PveClient;
import pveapi.entity.PVEACL;
import pveapi.entity.PVEVMConfig;
import pveapi.entity.PVEVMStatus;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class PVEAgent {

    private PveClient pveClient;
    private boolean isLogin = false;
    private String hostname;
    private int port;
    private String pveUsername;
    private String pvePassword;
    private Map<String,PveClient.PVENodes.PVEItemNode> nodeMap;
    private String defaultNodeName;
    private PveClient.PVENodes.PVEItemNode defaultNode;
    private Map<Integer,PveClient.PVENodes.PVEItemNode.PVEQemu.PVEItemVmid> vmMap;

    //工具类
    private final DatabaseAgent databaseAgent = new DatabaseAgent();

    //构造

    public PVEAgent() {
        this.pveClient = new PveClient(hostname,port);
        this.isLogin = pveClient.login(pveUsername,pvePassword);
        this.generateNodeMap();
        this.generateDefaultNode();
        this.generateVMMap();
    }

    //只使用本类中的工具方法而不登入任何PVEUser账号
    public PVEAgent(boolean asTool){
        this.pveClient = new PveClient(hostname,port);
        if(!asTool){
            this.isLogin = pveClient.login(pveUsername,pvePassword);
            this.generateNodeMap();
            this.generateDefaultNode();
            this.generateVMMap();
        }
    }

    public PVEAgent(String hostname, int port, String pveUsername, String pvePassword) throws JSONException {
        this.hostname = hostname;
        this.port = port;
        this.pveUsername = pveUsername;
        this.pvePassword = pvePassword;
        this.pveClient = new PveClient(hostname,port);
        this.isLogin = pveClient.login(pveUsername,pvePassword);
        this.generateNodeMap();
        this.generateDefaultNode();
        this.generateVMMap();
    }

    public PVEAgent(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.pveClient = new PveClient(hostname,port);
    }

    public PVEAgent(String pveUsername, String pvePassword) throws JSONException {
        this.pveUsername = pveUsername;
        this.pvePassword = pvePassword;
        this.pveClient = new PveClient(hostname,port);
        this.isLogin = pveClient.login(pveUsername,pvePassword);
        this.generateNodeMap();
        this.generateDefaultNode();
        this.generateVMMap();
    }

    //Methods

    public String getPVEAuthCookie(){
        return pveClient.getPVEAuthCookie();
    }

    public boolean login() throws JSONException {
        if(this.isLogin){
            return true;
        }
        if(this.pveClient == null){
            return false;
        }
        this.isLogin = this.pveClient.login(pveUsername,pvePassword);
        return this.isLogin;
    }

    public PveResult generateNodeMap() throws JSONException {
        this.nodeMap  = new HashMap<>();
        PveResult pveResult = new PveResult();
        //如果未登入
        if(!isLogin){
            pveResult.setResultType(PveResultType.ClientError);
            return pveResult;
        }
        //向PVE主机请求节点列表
        JSONArray pveNodesArray = pveClient.getNodes().index().getResponse().getJSONArray("data");
        if(pveNodesArray == null){
            pveResult.setResultType(PveResultType.ClientError);
            return pveResult;
        }
        //循环遍历列表，将节点加入Map
        for(int i = 0;i < pveNodesArray.length();i++){
            //根据JSONArray内的信息获取节点名称
            String nodeName = pveNodesArray.getJSONObject(i).getString("node");
            if(nodeName == null){
                pveResult.setResultType(PveResultType.NodeNameNotFound);
                continue;
            }
            //根据节点名称从主机获取节点对象
            PveClient.PVENodes.PVEItemNode node = pveClient.getNodes().get(nodeName);
            //如果获取到Null
            if(node == null){
                pveResult.setResultType(PveResultType.NodeNotFound);
                continue;
            }
            //将Node对象加入Map
            nodeMap.put(nodeName,node);
        }
        pveResult.setData(String.valueOf(pveNodesArray.length()));
        return pveResult;
    }

    public PveResult generateDefaultNode() throws JSONException {
        //用于返回的Result
        PveResult pveResult = new PveResult();
        //如果未登入
        if(!isLogin){
            pveResult.setResultType(PveResultType.ClientError);
            return pveResult;
        }
        //判断是否已生成节点列表，没有则进行生成
        if(this.nodeMap == null){
            PveResult genMapResult = this.generateNodeMap();
            //如果生成节点列表过程中出错，则直接将错误信息向上传递
            if(genMapResult.getResultType() != PveResultType.Normal){
                return genMapResult;
            }
        }
        //PVE主机上至少有一个自身节点，因此map如果不出错则至少有一个元素
        //迭代器的第一个next必然成立
        this.defaultNodeName = nodeMap.keySet().iterator().next();
        this.defaultNode = nodeMap.get(defaultNodeName);
        return pveResult;
    }

    //生成VM MAP
    public PveResult generateVMMap() throws JSONException {
        this.vmMap = new HashMap<>();
        PveResult pveResult = new PveResult();
        if(!this.isLogin){
            pveResult.setResultType(PveResultType.ClientError);
            return pveResult;
        }
        //如果没有设定默认节点名
        if(this.defaultNodeName == null || defaultNodeName.equals("")){
            pveResult.setResultType(PveResultType.ClientError);
            return pveResult;
        }
        //向PVE主机请求VM列表
        JSONArray pveVMsArray = pveClient.getNodes().get(defaultNodeName).getQemu().vmlist().getResponse().getJSONArray("data");
        if(pveVMsArray == null){
            pveResult.setResultType(PveResultType.ClientError);
            return pveResult;
        }
        //循环遍历列表，将VM加入Map
        for(int i = 0;i < pveVMsArray.length();i++){
            //根据JSONArray内的信息获取节点名称
            int vmid = pveVMsArray.getJSONObject(i).getInt("vmid");
            if(vmid == 0){
                pveResult.setResultType(PveResultType.VmidNotFound);
                continue;
            }
            //根据VM名称从主机获取VM对象
            PveClient.PVENodes.PVEItemNode.PVEQemu.PVEItemVmid vm =
                    pveClient.getNodes().get(defaultNodeName).getQemu().get(vmid);
            //如果获取到Null
            if(vm == null){
                pveResult.setResultType(PveResultType.VmNotFound);
                continue;
            }
            //将vm对象加入Map
            vmMap.put(vmid,vm);
        }
        pveResult.setResultType(PveResultType.Normal);
        pveResult.setData(String.valueOf(pveVMsArray.length()));
        return pveResult;
    }

    //给虚拟机更换光盘镜像
    public PveResult changeVMCDROM(int vmid,String isofilepath,String pveostype){
        PveResult pveResult = new PveResult();
        if(!isLogin){
            pveResult.setResultType(PveResultType.ClientError);
            return pveResult;
        }
        if(!vmMap.containsKey(vmid)){
            pveResult.setResultType(PveResultType.VmidNotFound);
            return pveResult;
        }
        JSONObject jsonObject =
                pveClient
                        .getNodes().get(defaultNodeName)
                        .getQemu().get(vmid)
                        .getConfig().updateVm(null, null, null, null, null, null, null, null, null, null,
                                isofilepath, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                                pveostype, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null
                        ).getResponse();
        //判断返回的JSONObject中是否含有data属性，如出错则不含有data属性，如正常，data属性存在但值为null
        if(!jsonObject.has("data")){
            pveResult.setResultType(PveResultType.CDROMError);
            return pveResult;
        }
        pveResult.setResultType(PveResultType.Normal);
        pveResult.setData(jsonObject.toString());
        return pveResult;
    }

    //检查VPS是否到期
    public boolean checkVPSTimeLimit(int vmid){
        return false;
    }

    //向PVE主机请求ACL列表，获得JSONArray格式数据，再转换成ACL对象列表返回
    public List<PVEACL> getPVEACLList() throws JSONException {
        List<PVEACL> pveaclList = new ArrayList<>();
        JSONArray aclJsonArray = pveClient.getAccess().getAcl().getRest().getResponse().getJSONArray("data");
        for(int i = 0;i < aclJsonArray.length();i++){
            pveaclList.add(new PVEACL(aclJsonArray.getJSONObject(i)));
        }
        return pveaclList;
    }

//    //获取pveuserid下分配的vmid列表,即分配给该用户的vps
//    public Set<Integer> getAllowedVmidsByPVEUserid(String pveUserid) throws JSONException {
//        Set<Integer> vmidList = new HashSet<>();
//        List<PVEACL> pveaclList = this.getPVEACLList();
//        for(PVEACL pveacl : pveaclList){
//            if(pveacl.getUgid().equals(pveUserid)){
//                vmidList.add(pveacl.getVmid());
//            }
//        }
//        return vmidList;
//    }

    //获取pveuserid下分配的vmid列表,即分配给该用户的vps
    public Set<Integer> getAllowedVmidsByPVEUserid(String pveUserid,String password) throws JSONException {
        PVEAgent userAgent = new PVEAgent(pveUserid,password);
        return userAgent.getVmMap().keySet();
    }

    //根据Userid获取对应pveUser下分配的vmidlist
    public Set<Integer> getAllowedVmidsByUserid(String userid) throws JSONException {
        Pveuser pveuser = databaseAgent.getPveuserByUserid(userid);
        //如果pveuser表中无此userid对应的pveuser
        //则说明该userid下暂无pve服务器，返回空set
        if(pveuser == null){
            return new HashSet<Integer>();
        }
        return this.getAllowedVmidsByPVEUserid(pveuser.getPveuserid(),pveuser.getPvepassword());
    }

    //根据sessionkey获取对应pveUser下分配的vmidlist
    public Set<Integer> getAllowedVmidsBySessionkey(String sessionkey) throws JSONException {
        Sessionkey sessionkeyobj = databaseAgent.getSessionkeyBySessionkey(sessionkey);
        //如果读取不到该sessionkey，返回空列表
        if(sessionkeyobj == null){
            return new HashSet<Integer>();
        }
        return this.getAllowedVmidsByUserid(sessionkeyobj.getUserid());
    }

    //根据vmid获取vmstatus
    public PveResult getVMStatusJSONObjectByVmid(int vmid) throws JSONException {
        PveResult pveResult = new PveResult();
        //PVEAgent未登入
        if(!this.isLogin){
            pveResult.setResultType(PveResultType.ClientError);
            return pveResult;
        }
        //如果VMid不存在
        if(vmMap.get(vmid) == null){
            pveResult.setResultType(PveResultType.VmNotFound);
            return pveResult;
        }
        JSONObject vmstatus = pveClient
                        .getNodes().get(defaultNodeName)
                        .getQemu().get(vmid)
                        .getStatus().getCurrent().vmStatus()
                        .getResponse().getJSONObject("data");
        pveResult.setObjectData(vmstatus);
        return pveResult;
    }

    //根据vmid获取vmconfig
    public PveResult getVMConfigJSONObjectByVmid(int vmid) throws JSONException {
        PveResult pveResult = new PveResult();
        //PVEAgent未登入
        if(!this.isLogin){
            pveResult.setResultType(PveResultType.ClientError);
            return pveResult;
        }
        //如果VMid不存在
        if(vmMap.get(vmid) == null){
            pveResult.setResultType(PveResultType.VmNotFound);
            return pveResult;
        }
        JSONObject vmconfig = pveClient
                .getNodes().get(defaultNodeName)
                .getQemu().get(vmid)
                .getConfig().vmConfig()
                .getResponse().getJSONObject("data");
        pveResult.setObjectData(vmconfig);
        return pveResult;
    }

    //根据VMID获取PVEServerMixInfo对象
    public PveResult getPVEServerMixInfoByVmid(int vmid) throws JSONException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        PveResult pveResult_statu = this.getVMStatusJSONObjectByVmid(vmid);
        PveResult pveResult_config = this.getVMConfigJSONObjectByVmid(vmid);
        //如果数据读取失败，将直接返回
        if(pveResult_statu.getResultType() != PveResultType.Normal){
            return pveResult_statu;
        }
        if(pveResult_config.getResultType() != PveResultType.Normal){
            return pveResult_config;
        }
        PVEVMStatus pvevmStatus = new PVEVMStatus((JSONObject) pveResult_statu.getObjectData());
        PVEVMConfig pvevmConfig = new PVEVMConfig((JSONObject) pveResult_config.getObjectData());
        pveResult_statu.setObjectData(new PVEServerMixInfo(pvevmStatus,pvevmConfig));
        return pveResult_statu;
    }

    public List<PVEServerMixInfo> getPVEServerMixInfoListBySessionkey(String sessionkey) throws JSONException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Set<Integer> vmids = this.getAllowedVmidsBySessionkey(sessionkey);
        List<PVEServerMixInfo> pveServerMixInfoList = new ArrayList<>();
        //遍历vmid列表，获取vmid对应的QEMU信息，生成Mixinfo列表
        for(int vmid : vmids){
            PveResult pveResult = this.getPVEServerMixInfoByVmid(vmid);
            if(pveResult.getResultType().equals(PveResultType.Normal)){
                PVEServerMixInfo pveServerMixInfo =
                        (PVEServerMixInfo) pveResult.getObjectData();
                pveServerMixInfoList.add(pveServerMixInfo);
            }//如果找不到vmid对应的QEMU数据跳过该vmid
        }
        return pveServerMixInfoList;
    }

    public PveResult getPVEServerMixInfoListJsonArrayStrBySessionkey(String sessionkey) throws JSONException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        PveResult pveResult = new PveResult();
        List<PVEServerMixInfo> pveServerMixInfoList = this.getPVEServerMixInfoListBySessionkey(sessionkey);
        pveResult.setData(com.alibaba.fastjson.JSONArray.toJSONString(pveServerMixInfoList));
        return pveResult;
    }

    public int getRunningVPSNumByPVEServerMixInfoListJsonArray(com.alibaba.fastjson.JSONArray jsonArray){
        int num = 0;
        for(int i = 0;i < jsonArray.size();i++){
            if(jsonArray.getJSONObject(i).getBoolean("running")){
                num++;
            }
        }
        return num;
    }

    public int getStoppingVPSNumByPVEServerMixInfoListJsonArray(com.alibaba.fastjson.JSONArray jsonArray){
        int num = 0;
        for(int i = 0;i < jsonArray.size();i++){
            if(!jsonArray.getJSONObject(i).getBoolean("running")){
                num++;
            }
        }
        return num;
    }

    //关闭VM，在守护进程循环检测到期VPS时被调用
    public PveResult stopVM(int vmid){
        PveResult pveResult = new PveResult(PveResultType.Normal,"");
        PveClient.PVENodes.PVEItemNode.PVEQemu.PVEItemVmid targetVM =
                this.vmMap.get(vmid);
        //如果这个VMID不存在
        if(targetVM == null){
            pveResult.setResultType(PveResultType.VmidNotFound);
            return pveResult;
        }
        //先检查是否在运行，如果已经关机则跳过
        if(targetVM
                .getStatus().getCurrent().vmStatus()
                .getResponse().getJSONObject("data")
                .getString("status").equals("stopped")){
            pveResult.setData("VM " + vmid + " has been stopped yet.Nothing to do.");
            return pveResult;
        }
        //执行Stop
        JSONObject jsonObject =
                targetVM.getStatus()
                        .getStop().vmStop()
                        .getResponse();
        pveResult.setData(jsonObject.toString());
        return pveResult;
    }

    //Getter and Setter

    public PveClient getPveClient() {
        return pveClient;
    }

    public void setPveClient(PveClient pveClient) {
        this.pveClient = pveClient;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPveUsername() {
        return pveUsername;
    }

    public void setPveUsername(String pveUsername) {
        this.pveUsername = pveUsername;
    }

    public String getPvePassword() {
        return pvePassword;
    }

    public void setPvePassword(String pvePassword) {
        this.pvePassword = pvePassword;
    }

    public Map<String, PveClient.PVENodes.PVEItemNode> getNodeMap() {
        return nodeMap;
    }

    public void setNodeMap(Map<String, PveClient.PVENodes.PVEItemNode> nodeMap) {
        this.nodeMap = nodeMap;
    }

    public String getDefaultNodeName() {
        return defaultNodeName;
    }

    public void setDefaultNodeName(String defaultNodeName) {
        this.defaultNodeName = defaultNodeName;
    }

    public PveClient.PVENodes.PVEItemNode getDefaultNode() {
        return defaultNode;
    }

    public Map<Integer, PveClient.PVENodes.PVEItemNode.PVEQemu.PVEItemVmid> getVmMap() {
        return vmMap;
    }

    public void setVmMap(Map<Integer, PveClient.PVENodes.PVEItemNode.PVEQemu.PVEItemVmid> vmMap) {
        this.vmMap = vmMap;
    }

    public void setDefaultNode(PveClient.PVENodes.PVEItemNode defaultNode) {
        this.defaultNode = defaultNode;
    }
}
