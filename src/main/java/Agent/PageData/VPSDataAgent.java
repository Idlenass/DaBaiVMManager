package Agent.PageData;

import Agent.DatabaseAgent;
import Agent.DateAgent;
import Agent.PVE.PVEAgent;
import Agent.PVE.PVEServerMixInfo;
import Agent.PVE.PveResult;
import Agent.PVE.PveResultType;
import Agent.SessionKeyAgent;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import entity.Isofile;
import entity.Pveserverappendinfo;
import entity.Pveuser;
import entity.Sessionkey;
import org.json.JSONException;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class VPSDataAgent {
    private PVEAgent pveAgent;
    //工具类
    private DatabaseAgent databaseAgent;
    private SessionKeyAgent sessionKeyAgent;

    public VPSDataAgent() {
        this.pveAgent = new PVEAgent();
        this.databaseAgent = new DatabaseAgent();
        this.sessionKeyAgent = new SessionKeyAgent();
    }

    public VPSDataAgent(boolean asTool){
        this.databaseAgent = new DatabaseAgent();
        if(!asTool){
            this.pveAgent = new PVEAgent();
            this.sessionKeyAgent = new SessionKeyAgent();
        }
    }

    public VPSDataAgent(String sessionkey) {
        this.databaseAgent = new DatabaseAgent();
        this.sessionKeyAgent = new SessionKeyAgent();
        Sessionkey sessionkeyobj = databaseAgent.getSessionkeyBySessionkey(sessionkey);
        if (sessionkeyobj == null) {
            this.pveAgent = new PVEAgent(true);
            return;
        }
        Pveuser pveuser = databaseAgent.getPveuserByUserid(sessionkeyobj.getUserid());
        if (pveuser == null) {
            this.pveAgent = new PVEAgent(true);
            return;
        }
        this.pveAgent = new PVEAgent(pveuser.getPveuserid(), pveuser.getPvepassword());
    }

    public PveResult getVNCConnectinfo(int vmid, String sessionkey) {
        PveResult pveResult = new PveResult();

        //先检查sessionkey有效性
        if (!sessionKeyAgent.checkSessionkeyVaild(sessionkey)) {
            pveResult.setResultType(PveResultType.SessionkeyInvaild);
            return pveResult;
        }
        //检查访问权限
        Set<Integer> vmids = pveAgent.getAllowedVmidsBySessionkey(sessionkey);
        if (!vmids.contains(vmid)) {
            pveResult.setResultType(PveResultType.VmIllegalAccess);
            return pveResult;
        }
        //检查VPS是否已到期，如果已经过期，则禁止访问
        if(isVpsTimeOut(vmid)){
            pveResult.setResultType(PveResultType.VPSTimeOut);
            return pveResult;
        }
        //获取PVECookie
        Sessionkey sessionKey = databaseAgent.getSessionkeyBySessionkey(sessionkey);
        if (sessionKey == null) {
            pveResult.setResultType(PveResultType.SessionkeyInvaild);
            return pveResult;
        }
        Pveuser pveuser = databaseAgent.getPveuserByUserid(sessionKey.getUserid());
        if (pveuser == null) {
            pveResult.setResultType(PveResultType.UserAccessDenied);
            return pveResult;
        }
        PVEAgent userpveAgent = new PVEAgent(pveuser.getPveuserid(), pveuser.getPvepassword());
        if (!userpveAgent.isLogin()) {
            pveResult.setResultType(PveResultType.UserAccessDenied);
            return pveResult;
        }
        String pveCookie = userpveAgent.getPVEAuthCookie();
        //生成链接信息
        String site = "console.dabaiyun.net";
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        String url
                = "https://" + site + ":" + userpveAgent.getPort()
                + "/?console=kvm&novnc=1&vmid="
                + vmid + "&node=" + userpveAgent.getDefaultNodeName()
                + "&resize=off&cmd=";
        jsonObject.put("url", url);
        jsonObject.put("pveauthcookie", pveCookie);
        pveResult.setData(jsonObject.toJSONString());
        return pveResult;
    }

    //获取单个的VM信息
    public PveResult getSingleVMinfo(int vmid, String sessionkey) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        PveResult pveResult = new PveResult();
        //sessionkey无效
        if (!sessionKeyAgent.checkSessionkeyVaild(sessionkey)) {
            pveResult.setResultType(PveResultType.SessionkeyInvaild);
            return pveResult;
        }
        //vmid无授权
        Set<Integer> vmids = pveAgent.getAllowedVmidsBySessionkey(sessionkey);
        if (!vmids.contains(vmid)) {
            pveResult.setResultType(PveResultType.VmIllegalAccess);
            return pveResult;
        }
        pveResult = pveAgent.getPVEServerMixInfoByVmid(vmid);
        if (pveResult.getResultType().equals(PveResultType.Normal)) {
            PVEServerMixInfo pveServerMixInfo =
                    (PVEServerMixInfo) pveResult.getObjectData();
            pveResult.setData(com.alibaba.fastjson.JSONObject.toJSONString(pveServerMixInfo));
        }
        return pveResult;
    }

    public PveResult getVPSListPageDataBySessionkey(String sessionkey) {
        PveResult pveResult = new PveResult();
        //检查sessionkey有效性
        if (!sessionKeyAgent.checkSessionkeyVaild(sessionkey)) {
            sessionKeyAgent.deleteSessionKey(sessionkey);
            pveResult.setResultType(PveResultType.SessionkeyInvaild);
            return pveResult;
        }
        //用于返回的JSONObj对象
        JSONObject jsonObject = new JSONObject();
        //在这里使用trycatch以屏蔽在post方法内抛出异常导致Servlet异常
        try {
            pveResult = pveAgent.getPVEServerMixInfoListJsonArrayStrBySessionkey(sessionkey);
        } catch (JSONException e) {
            pveResult.setResultType(PveResultType.JSONException);
            pveResult.setData(e.toString());
            return pveResult;
        } catch (InvocationTargetException e) {
            pveResult.setResultType(PveResultType.InvocationTargetException);
            pveResult.setData(e.toString());
            return pveResult;
        } catch (NoSuchMethodException e) {
            pveResult.setResultType(PveResultType.NoSuchMethodException);
            pveResult.setData(e.toString());
            return pveResult;
        } catch (IllegalAccessException e) {
            pveResult.setResultType(PveResultType.IllegalAccessException);
            pveResult.setData(e.toString());
            return pveResult;
        }
        String pveservermixinfojsonarraystr = pveResult.getData();
        JSONArray jsonArray = JSON.parseArray(pveservermixinfojsonarraystr);
        //将数据放入jsonobject
        jsonObject.put("vpsinfolist", pveservermixinfojsonarraystr);
        jsonObject.put("servernum", jsonArray.size());
        jsonObject.put("runningnum", pveAgent.getRunningVPSNumByPVEServerMixInfoListJsonArray(jsonArray));
        jsonObject.put("stoppingnum", pveAgent.getStoppingVPSNumByPVEServerMixInfoListJsonArray(jsonArray));

        pveResult.setResultType(PveResultType.Normal);
        pveResult.setData(jsonObject.toJSONString());
        return pveResult;
    }

    public PveResult getAllIsoFilesJsonArrayStr(String sessionkey) {
        PveResult pveResult = new PveResult();
        //检查sessionkey有效性
        if (!sessionKeyAgent.checkSessionkeyVaild(sessionkey)) {
            sessionKeyAgent.deleteSessionKey(sessionkey);
            pveResult.setResultType(PveResultType.SessionkeyInvaild);
            return pveResult;
        }
        pveResult.setData(JSONArray.toJSONString(databaseAgent.getAllIsofile()));
        return pveResult;
    }

    public PveResult setVMISO(String sessionkey, int vmid, int isoid) {
        PveResult pveResult = new PveResult();
        //检查sessionkey有效性
        if (!sessionKeyAgent.checkSessionkeyVaild(sessionkey)) {
            sessionKeyAgent.deleteSessionKey(sessionkey);
            pveResult.setResultType(PveResultType.SessionkeyInvaild);
            return pveResult;
        }
        //根据isoid获取isofilepath
        Isofile isofile = databaseAgent.getIsofileById(isoid);
        //如果isoid对应数据不存在
        if (isofile == null) {
            pveResult.setResultType(PveResultType.CDROMError);
            return pveResult;
        }
        String isofilepath = isofile.getFilepath();
        return pveAgent.changeVMCDROM(vmid, isofilepath, isofile.getPveostype());
    }

    //检查vps是否过期，已过期返回true，未过期返回false
    //数据库中找不到数据或者数据无效，均按照已过期处理
    public boolean isVpsTimeOut(int vmid) {
        Pveserverappendinfo pveserverappendinfo
                = databaseAgent.getPvesaiByvmid(vmid);
        if (pveserverappendinfo == null) {
            return true;
        }
        String[] strlist = pveserverappendinfo.getTimelimitdate().split("/");
        if (strlist.length != 3) {
            return true;
        }
        DateAgent vpslimitdate
                = new DateAgent(
                Integer.parseInt(strlist[0]),
                Integer.parseInt(strlist[1]),
                Integer.parseInt(strlist[2])
        );
        return !(new DateAgent()).compareDate(vpslimitdate);
    }
}
