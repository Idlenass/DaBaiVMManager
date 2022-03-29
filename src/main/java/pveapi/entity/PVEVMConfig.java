package pveapi.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PVEVMConfig {

    private String net0;
    private Long ballon;
    private Long memory;
    private Integer numa;
    private String cpu;
    private String ostype;
    private String vmgenid;
    private String smbios1;
    private Integer cores;
    private String scsihw;
    private String machine;
    private String name;
    private String digest;
    private Integer sockets;
    private String boot;
    private String ide2;//CDROM

    public void genData(JSONObject jsonObject) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, JSONException {
        Field fieldlist[] = this.getClass().getDeclaredFields();
        for(Field field : fieldlist){
            String fieldname = field.getName();
//            System.out.println("fieldname = " + fieldname);
            String fieldtype = field.getGenericType().toString();
//            System.out.println("fieldtype = " + fieldtype);
            String setterMethodName = "set" + fieldname.substring(0,1).toUpperCase() + fieldname.substring(1);
//            System.out.println("setterMethodName = " + setterMethodName);

            if(jsonObject.has(fieldname)){
//                System.out.println("containsKey : " + fieldname);
                Method method;
                switch (fieldtype) {
                    case "class java.lang.String" :{
                        method = this.getClass().getMethod(setterMethodName,java.lang.String.class);
                        method.invoke(this,jsonObject.getString(fieldname));
                        break;
                    }
                    case "class java.lang.Long" :{
                        method = this.getClass().getMethod(setterMethodName,java.lang.Long.class);
                        method.invoke(this,jsonObject.getLong(fieldname));
                        break;
                    }
                    case "class java.lang.Integer" :{
                        method = this.getClass().getMethod(setterMethodName,java.lang.Integer.class);
                        method.invoke(this,jsonObject.getInt(fieldname));
                        break;
                    }
                    default:{
                        System.out.println("PVEVMConfig get a unknow filed type : " + fieldtype + " , at filed " + fieldname);
                        break;
                    }
                }
            }
        }
    }

    public PVEVMConfig(JSONObject jsonObject) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, JSONException {
        genData(jsonObject);
    }

    public PVEVMConfig(String vmconfigstring) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, JSONException {
        JSONObject jsonObject = new JSONObject(vmconfigstring);
        genData(jsonObject);
    }

    public String getNet0() {
        return net0;
    }

    public void setNet0(String net0) {
        this.net0 = net0;
    }

    public Long getBallon() {
        return ballon;
    }

    public void setBallon(Long ballon) {
        this.ballon = ballon;
    }

    public Long getMemory() {
        return memory;
    }

    public void setMemory(Long memory) {
        this.memory = memory;
    }

    public Integer getNuma() {
        return numa;
    }

    public void setNuma(Integer numa) {
        this.numa = numa;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getOstype() {
        return ostype;
    }

    public void setOstype(String ostype) {
        this.ostype = ostype;
    }

    public String getVmgenid() {
        return vmgenid;
    }

    public void setVmgenid(String vmgenid) {
        this.vmgenid = vmgenid;
    }

    public String getSmbios1() {
        return smbios1;
    }

    public void setSmbios1(String smbios1) {
        this.smbios1 = smbios1;
    }

    public Integer getCores() {
        return cores;
    }

    public void setCores(Integer cores) {
        this.cores = cores;
    }

    public String getScsihw() {
        return scsihw;
    }

    public void setScsihw(String scsihw) {
        this.scsihw = scsihw;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Integer getSockets() {
        return sockets;
    }

    public void setSockets(Integer sockets) {
        this.sockets = sockets;
    }

    public String getBoot() {
        return boot;
    }

    public void setBoot(String boot) {
        this.boot = boot;
    }

    public String getIde2() {
        return ide2;
    }

    public void setIde2(String ide2) {
        this.ide2 = ide2;
    }
}
