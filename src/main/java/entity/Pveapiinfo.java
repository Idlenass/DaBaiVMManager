package entity;

import java.io.Serializable;

/**
 * (Pveapiinfo)实体类
 *
 * @author makejava
 * @since 2021-11-08 13:36:34
 */
public class Pveapiinfo implements Serializable {
    private static final long serialVersionUID = -25121117085839105L;
    
    private Integer id;
    
    private String hostname;
    
    private Integer port;
    
    private String username;
    
    private String password;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}