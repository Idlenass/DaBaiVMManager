package pveapi.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class PVEVMStatus {
    //虚拟机运行状态数据实体
    private int vmid;
    private String name;
    private boolean running;
    private long uptime;
    private int cpus;
    private double cpu;
    private long mem;
    private long maxmem;
    private long balloon_min;
//    private long balloon;
    private long maxdisk;
    private long diskread;
    private long diskwrite;
    private long netout;
    private long netin;

    public PVEVMStatus() {
    }

    public PVEVMStatus(JSONObject jsonObject) throws JSONException {
        this.vmid = jsonObject.getInt("vmid");
        this.name = jsonObject.getString("name");
        this.running = jsonObject.getString("status").equals("running");
        this.uptime = jsonObject.getLong("uptime");
        this.cpus = jsonObject.getInt("cpus");
        this.cpu = jsonObject.getDouble("cpu");
        this.mem = jsonObject.getLong("mem");
        this.maxmem = jsonObject.getLong("maxmem");
        this.balloon_min = jsonObject.getLong("balloon_min");
//        this.balloon = jsonObject.getLong("balloon");
        this.maxdisk = jsonObject.getLong("maxdisk");
        this.diskread = jsonObject.getLong("diskread");
        this.diskwrite = jsonObject.getLong("diskwrite");
        this.netout = jsonObject.getLong("netout");
        this.netin = jsonObject.getLong("netin");
    }

    public int getVmid() {
        return vmid;
    }

    public void setVmid(int vmid) {
        this.vmid = vmid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public long getUptime() {
        return uptime;
    }

    public void setUptime(long uptime) {
        this.uptime = uptime;
    }

    public int getCpus() {
        return cpus;
    }

    public void setCpus(int cpus) {
        this.cpus = cpus;
    }

    public double getCpu() {
        return cpu;
    }

    public void setCpu(double cpu) {
        this.cpu = cpu;
    }

    public long getMem() {
        return mem;
    }

    public void setMem(long mem) {
        this.mem = mem;
    }

    public long getMaxmem() {
        return maxmem;
    }

    public void setMaxmem(long maxmem) {
        this.maxmem = maxmem;
    }

    public long getBalloon_min() {
        return balloon_min;
    }

    public void setBalloon_min(long balloon_min) {
        this.balloon_min = balloon_min;
    }

//    public long getBalloon() {
//        return balloon;
//    }
//
//    public void setBalloon(long balloon) {
//        this.balloon = balloon;
//    }

    public long getMaxdisk() {
        return maxdisk;
    }

    public void setMaxdisk(long maxdisk) {
        this.maxdisk = maxdisk;
    }

    public long getDiskread() {
        return diskread;
    }

    public void setDiskread(long diskread) {
        this.diskread = diskread;
    }

    public long getDiskwrite() {
        return diskwrite;
    }

    public void setDiskwrite(long diskwrite) {
        this.diskwrite = diskwrite;
    }

    public long getNetout() {
        return netout;
    }

    public void setNetout(long netout) {
        this.netout = netout;
    }

    public long getNetin() {
        return netin;
    }

    public void setNetin(long netin) {
        this.netin = netin;
    }
}
