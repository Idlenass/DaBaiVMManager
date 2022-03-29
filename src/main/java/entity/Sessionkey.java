package entity;

import java.io.Serializable;

/**
 * (Sessionkey)实体类
 *
 * @author makejava
 * @since 2021-10-02 00:55:15
 */
public class Sessionkey implements Serializable {
    private static final long serialVersionUID = 583957562250632399L;
    
    private String userid;
    
    private String sessionkey;
    
    private String deadline;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSessionkey() {
        return sessionkey;
    }

    public void setSessionkey(String sessionkey) {
        this.sessionkey = sessionkey;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

}

