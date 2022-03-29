package Agent.Thread;

import Agent.DatabaseAgent;
import Agent.DateAgent;
import Agent.PVE.PVEAgent;
import Agent.PVE.PveResult;
import Agent.PVE.PveResultType;
import Agent.PageData.VPSDataAgent;
import entity.Pveserverappendinfo;
import org.springframework.scheduling.concurrent.ScheduledExecutorTask;

import java.util.*;

public class VPSTimeOutCheacker extends Thread{

    //工具类
    private final DatabaseAgent databaseAgent = new DatabaseAgent();
    //循环间隔：秒
    private final int cycleTimeSecs = 30;
    //循环间隔：毫秒 由循环间隔秒计算得出
    private int cycleTimeMillSecs = cycleTimeSecs*1000;
    //PveServerAppendInfoList
    private List<Pveserverappendinfo> pveserverappendinfoList;


    public VPSTimeOutCheacker() {
        System.out.println((new Date()).toString() + " : VPSTimeOutChecker Initilazited.");
    }

    //执行具体的任务
    @Override
    public void run(){

        while(true){
            System.out.println("开始循环检测");

            checkAll();
            try {
                sleep(cycleTimeMillSecs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("下次检测间隔：" + cycleTimeSecs + " 秒");

        }

    }

    //线程销毁
    public void destory(){
        System.out.println("VPSTimeOutChecker 已销毁.");
    }

    //检测所有vps
    private void checkAll(){
        //记录检测结果
        int sum = 0;
        int timeoutNum = 0;
        int stop_success = 0;
        int stop_faild = 0;
        //用于shutdown VM的PVEAgent，不能放在类属性是因为cookie会过期，必须在每次检测时重新登入
        PVEAgent pveAgent = new PVEAgent();
        //获取所有PveServerAppendInfo
        this.pveserverappendinfoList =
                databaseAgent.getPvesaiByPvesai(new Pveserverappendinfo());
        //遍历所有PveServerAppendInfo
        for(Pveserverappendinfo vps : pveserverappendinfoList){
            sum++;
            //判断是否到期
            if(checkSingleVM(vps.getVmid())){
                timeoutNum++;
                //如果到期，执行强制关机
                PveResult pveResult = pveAgent.stopVM(vps.getVmid());
                //检测执行结果
                if(pveResult.getResultType() == PveResultType.Normal){
                    stop_success++;
                    System.out.println("VM " + vps.getVmid() + " Timeout and Stopped.");
                    System.out.println(pveResult.getData());
                }else{
                    stop_faild++;
                    System.out.println("Stop VM ERROR : " + pveResult.getResultType().toString());
                }
            }
        }
        System.out.println("本次检测结束，共检测 " + sum + " 个VPS，到期vps " + timeoutNum + " 个，成功关机 " + stop_success + " 个，关机失败 " + stop_faild + " 个");
    }

    //检测单个vps
    private boolean checkSingleVM(int vmid){
        //从属性数组中循环遍历获取对应对象，避免多次数据库链接
        Pveserverappendinfo pveserverappendinfo = null;
        for(Pveserverappendinfo vps : pveserverappendinfoList){
            if(vps.getVmid() == vmid){
                pveserverappendinfo = vps;
            }
        }
        //如果这个vmid不存在
        if (pveserverappendinfo == null) {
            return true;
        }
        String[] strlist = pveserverappendinfo.getTimelimitdate().split("/");
        if (strlist.length != 3) {
            return true;
        }
        DateAgent vpslimitdate
                = new DateAgent(
                Integer.parseInt(strlist[0]),
                Integer.parseInt(strlist[1]),
                Integer.parseInt(strlist[2])
        );
        return !(new DateAgent()).compareDate(vpslimitdate);
    }
}
