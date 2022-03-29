package entity;

import java.io.Serializable;

/**
 * (Mcsmuser)实体类
 *
 * @author makejava
 * @since 2021-10-09 20:49:00
 */
public class Mcsmuser implements Serializable {
    private static final long serialVersionUID = -98531390412252657L;
    
    private String userid;
    
    private String mcsmusername;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMcsmusername() {
        return mcsmusername;
    }

    public void setMcsmusername(String mcsmusername) {
        this.mcsmusername = mcsmusername;
    }

}

