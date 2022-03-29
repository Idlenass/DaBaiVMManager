package entity;

import java.io.Serializable;

/**
 * (Mcsmapiinfo)实体类
 *
 * @author makejava
 * @since 2021-11-07 14:56:41
 */
public class Mcsmapiinfo implements Serializable {
    private static final long serialVersionUID = -59394474032092821L;
    
    private Integer id;
    
    private String url;
    
    private String apikey;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

}