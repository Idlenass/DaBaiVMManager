package Agent.PVE;

import Agent.DatabaseAgent;
import entity.Isofile;
import entity.Pveserverappendinfo;
import pveapi.entity.PVEVMConfig;
import pveapi.entity.PVEVMStatus;

import java.util.List;

public class PVEServerMixInfo {
    //向前端传递的VPS信息
    private int vmid;
    private boolean running = false;
    private long uptime;
    private int vcpus;
    private double cpuusage;
    private long nowmem;
    private long maxmem;
    private long maxdisk;
    private long diskread;
    private long diskwrite;
    private long netout;
    private long netin;
    private String natportbegin = "";
    private String natportend = "";
    private String bandwitdh = "";
    private String timelimitdate = "";
    private String module = "";
    private String ipaddr = "";
    private String ostype = "";
    private String vmgenid = "";
    private String smbios1 = "";
    private String scsihw = "";
    private String machine = "";
    private String name = "";
    private String digest = "";
    private String boot = "";
    private String cdrom = "";

    public PVEServerMixInfo(PVEVMStatus pvevmStatus, PVEVMConfig pvevmConfig) {
        DatabaseAgent databaseAgent = new DatabaseAgent();
        //datas from PVEVMStatus
        this.vmid = pvevmStatus.getVmid();
        this.running = pvevmStatus.isRunning();
        this.uptime = pvevmStatus.getUptime();
        this.vcpus = pvevmStatus.getCpus();
        this.cpuusage = pvevmStatus.getCpu();
        this.nowmem = pvevmStatus.getMem();
        this.maxmem = pvevmStatus.getMaxmem();
        this.maxdisk = pvevmStatus.getMaxdisk();
        this.diskread = pvevmStatus.getDiskread();
        this.diskwrite = pvevmStatus.getDiskwrite();
        this.netout = pvevmStatus.getNetout();
        this.netin = pvevmStatus.getNetin();
        //datas from PVEVMConfig
        this.vmgenid = pvevmConfig.getVmgenid();
        this.smbios1 = pvevmConfig.getSmbios1();
        this.scsihw = pvevmConfig.getScsihw();
        this.machine = pvevmConfig.getMachine();
        this.name = pvevmConfig.getName();
        this.digest = pvevmConfig.getDigest();
        this.boot = pvevmConfig.getBoot();
        this.cdrom = getCdromDisplayNameByVMConfigSubStr(pvevmConfig.getIde2());
        this.ostype = getDisplayOSTypeByCDRomConfig(pvevmConfig.getIde2());
        //从数据库中提取其他附加信息
        //如果数据缺失，将直接不插入数据
        if (this.vmid == 0)
            return;
        Pveserverappendinfo pvesai =
                databaseAgent.getPvesaiByvmid(this.vmid);
        if (pvesai == null)
            return;
        this.natportbegin = pvesai.getNatportbegin();
        this.natportend = pvesai.getNatportend();
        this.bandwitdh = pvesai.getBandwitdh();
        this.timelimitdate = pvesai.getTimelimitdate();
        this.module = pvesai.getModule();
        this.ipaddr = pvesai.getIpaddr();
    }

    public PVEServerMixInfo(PVEVMStatus pvevmStatus) {
        DatabaseAgent databaseAgent = new DatabaseAgent();

        this.vmid = pvevmStatus.getVmid();
        this.running = pvevmStatus.isRunning();
        this.uptime = pvevmStatus.getUptime();
        this.vcpus = pvevmStatus.getCpus();
        this.cpuusage = pvevmStatus.getCpu();
        this.nowmem = pvevmStatus.getMem();
        this.maxmem = pvevmStatus.getMaxmem();
        this.maxdisk = pvevmStatus.getMaxdisk();
        this.diskread = pvevmStatus.getDiskread();
        this.diskwrite = pvevmStatus.getDiskwrite();
        this.netout = pvevmStatus.getNetout();
        this.netin = pvevmStatus.getNetin();
        //从数据库中提取其他附加信息
        //如果数据缺失，将直接不插入数据
        if (this.vmid == 0)
            return;
        Pveserverappendinfo pvesai =
                databaseAgent.getPvesaiByvmid(this.vmid);
        if (pvesai == null)
            return;
        this.natportbegin = pvesai.getNatportbegin();
        this.natportend = pvesai.getNatportend();
        this.bandwitdh = pvesai.getBandwitdh();
        this.timelimitdate = pvesai.getTimelimitdate();
        this.module = pvesai.getModule();
    }

    public PVEServerMixInfo() {
    }

    private String getDisplayOSTypeByCDRomConfig(String confstr) {
        //去除后缀 ,media=cdrom
        if (confstr != null && !confstr.equals("")) {
            confstr = confstr.split(",")[0];
        }
        DatabaseAgent databaseAgent = new DatabaseAgent();
        Isofile isofileOri = new Isofile();
        isofileOri.setFilepath(confstr);
        List<Isofile> isofileList =
                databaseAgent.getIsofileByIsofile(isofileOri);
        //如果文件路径在数据库中找不到对应数据,返回直接文件全名
        if (isofileList.size() < 1) {
            return confstr;
        }
        return isofileList.get(0).getDisplayostype();
    }

    private String getCdromDisplayNameByVMConfigSubStr(String confstr) {
        //如果参数为null，说明PVE返回的配置文本中不存在ide2，即ide2未挂载
        if (confstr == null || confstr.equals("")) {
            return "No CDRom Mounted.";
        }
        //从数据库读取改文件路径对应的displayname
        String filepath = "";
        if (confstr != null && confstr != "") {
            filepath = confstr.split(",")[0];
        }
        DatabaseAgent databaseAgent = new DatabaseAgent();
        Isofile isofileOri = new Isofile();
        isofileOri.setFilepath(filepath);
        List<Isofile> isofileList =
                databaseAgent.getIsofileByIsofile(isofileOri);
        //如果文件路径在数据库中找不到对应数据,返回直接文件全名
        if (isofileList.size() < 1) {
            String[] strlist1 = confstr.split("/");
            //如果切割出来长度小于2，说明格式不正确
            if (strlist1.length < 2) {
                return "No CDRom Mounted.";
            }
            //进一步分割文本
            String[] strlist2 = strlist1[1].split(",");
            //如果切割出来长度小于2，说明格式不正确
            if (strlist2.length < 2) {
                return "No CDRom Mounted.";
            }
            //如果媒体类型不是media=cdrom，不予以展示
            if (!strlist2[1].equals("media=cdrom")) {
                return "No CDRom Mounted.";
            }
            return strlist2[0];
        }
        return isofileList.get(0).getDisplayname();
    }


//Getter And Setter

    public int getVmid() {
        return vmid;
    }

    public void setVmid(int vmid) {
        this.vmid = vmid;
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

    public int getVcpus() {
        return vcpus;
    }

    public void setVcpus(int vcpus) {
        this.vcpus = vcpus;
    }

    public double getCpuusage() {
        return cpuusage;
    }

    public void setCpuusage(double cpuusage) {
        this.cpuusage = cpuusage;
    }

    public long getNowmem() {
        return nowmem;
    }

    public void setNowmem(long nowmem) {
        this.nowmem = nowmem;
    }

    public long getMaxmem() {
        return maxmem;
    }

    public void setMaxmem(long maxmem) {
        this.maxmem = maxmem;
    }

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

    public String getBoot() {
        return boot;
    }

    public void setBoot(String boot) {
        this.boot = boot;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getCdrom() {
        return cdrom;
    }

    public void setCdrom(String cdrom) {
        this.cdrom = cdrom;
    }
}
