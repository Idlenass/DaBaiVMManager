package entity;

import java.io.Serializable;

/**
 * (Isofile)实体类
 *
 * @author makejava
 * @since 2021-11-20 21:11:33
 */
public class Isofile implements Serializable {
    private static final long serialVersionUID = -18550595183084638L;
    
    private Integer isoid;
    
    private String displayname;
    
    private String filepath;
    
    private String displayostype;
    
    private String pveostype;


    public Integer getIsoid() {
        return isoid;
    }

    public void setIsoid(Integer isoid) {
        this.isoid = isoid;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getDisplayostype() {
        return displayostype;
    }

    public void setDisplayostype(String displayostype) {
        this.displayostype = displayostype;
    }

    public String getPveostype() {
        return pveostype;
    }

    public void setPveostype(String pveostype) {
        this.pveostype = pveostype;
    }

}

