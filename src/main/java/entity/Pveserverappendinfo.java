package entity;

import java.io.Serializable;

/**
 * (Pveserverappendinfo)实体类
 *
 * @author makejava
 * @since 2021-10-24 21:51:56
 */
public class Pveserverappendinfo implements Serializable {
    private static final long serialVersionUID = -56945720436593748L;
    
    private int vmid;
    
    private String natportbegin;
    
    private String natportend;
    
    private String bandwitdh;
    
    private String timelimitdate;
    
    private String module;

    private String ipaddr;

    public int getVmid() {
        return vmid;
    }

    public void setVmid(int vmid) {
        this.vmid = vmid;
    }

    public String getNatportbegin() {
        return natportbegin;
    }

    public void setNatportbegin(String natportbegin) {
        this.natportbegin = natportbegin;
    }

    public String getNatportend() {
        return natportend;
    }

    public void setNatportend(String natportend) {
        this.natportend = natportend;
    }

    public String getBandwitdh() {
        return bandwitdh;
    }

    public void setBandwitdh(String bandwitdh) {
        this.bandwitdh = bandwitdh;
    }

    public String getTimelimitdate() {
        return timelimitdate;
    }

    public void setTimelimitdate(String timelimitdate) {
        this.timelimitdate = timelimitdate;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }
}

