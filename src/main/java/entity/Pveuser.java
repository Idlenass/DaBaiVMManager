package entity;

import java.io.Serializable;

/**
 * (Pveuser)实体类
 *
 * @author makejava
 * @since 2021-10-24 17:26:42
 */
public class Pveuser implements Serializable {
    private static final long serialVersionUID = 999588617834461077L;
    
    private String userid;
    
    private String pveuserid;
    
    private String pvepassword;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPveuserid() {
        return pveuserid;
    }

    public void setPveuserid(String pveuserid) {
        this.pveuserid = pveuserid;
    }

    public String getPvepassword() {
        return pvepassword;
    }

    public void setPvepassword(String pvepassword) {
        this.pvepassword = pvepassword;
    }

}

