package entity;

import java.io.Serializable;

/**
 * (Mcsmserverappendinfo)实体类
 *
 * @author makejava
 * @since 2021-10-12 20:33:20
 */
public class Mcsmserverappendinfo implements Serializable {
    private static final long serialVersionUID = 879968126849664869L;
    
    private String servername;
    
    private String serveraddr;
    
    private int serverport;
    
    private String serverarea;


    public String getServername() {
        return servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    public String getServeraddr() {
        return serveraddr;
    }

    public void setServeraddr(String serveraddr) {
        this.serveraddr = serveraddr;
    }

    public int getServerport() {
        return serverport;
    }

    public void setServerport(int serverport) {
        this.serverport = serverport;
    }

    public String getServerarea() {
        return serverarea;
    }

    public void setServerarea(String serverarea) {
        this.serverarea = serverarea;
    }

}

