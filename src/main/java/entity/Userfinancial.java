package entity;

import java.io.Serializable;

/**
 * (Userfinancial)实体类
 *
 * @author makejava
 * @since 2021-10-03 18:11:18
 */
public class Userfinancial implements Serializable {
    private static final long serialVersionUID = 586401209457829921L;
    
    private String userid;
    
    private Double balance;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}

