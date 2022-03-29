package pveapi.entity;


import org.json.JSONException;
import org.json.JSONObject;

public class PVEACL {
    //PVE主机系统中的用户访问控制表实体对象
    private String path;
    private String roleid;
    private int propagate;
    private String ugid;
    private PVEACLType type;

    public int getVmid(){
        String[] strlist = path.split("/");
        //根据PVE API返回的path字段，用/切割应当被切割成三份，如果切割出来不足三份，说明数据有误
        if(strlist.length < 3){
            return 0;
        }
        return Integer.parseInt(strlist[2]);
    }

    public PVEACL(JSONObject jsonObject) throws JSONException {
        this.path = jsonObject.getString("path");
        this.roleid = jsonObject.getString("roleid");
        this.propagate = jsonObject.getInt("propagate");
        this.ugid = jsonObject.getString("ugid");
        this.type =
                PVEACLType
                        .getPVEACLTypeFromString(
                                jsonObject.getString(
                                        "type"
                                )
        );
    }

    public PVEACL(String path, String roleid,
                  int propagate, String ugid, PVEACLType type) {
        this.path = path;
        this.roleid = roleid;
        this.propagate = propagate;
        this.ugid = ugid;
        this.type = type;
    }

    public PVEACL() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public int getPropagate() {
        return propagate;
    }

    public void setPropagate(int propagate) {
        this.propagate = propagate;
    }

    public String getUgid() {
        return ugid;
    }

    public void setUgid(String ugid) {
        this.ugid = ugid;
    }

    public PVEACLType getType() {
        return type;
    }

    public void setType(PVEACLType type) {
        this.type = type;
    }
}
