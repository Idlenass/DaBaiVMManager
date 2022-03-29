package Agent;

import java.util.Random;
import entity.Sessionkey;

public class SessionKeyAgent {

    //随机生成sessionkey并存入数据库
    public Sessionkey generateSessionkey(String userid){

        String key = getRandomString(16);
        DateAgent dateAgent = new DateAgent();
        dateAgent.pushHours(2);//sessionkey 2小时过期
        String deadline = dateAgent.getDatestr();

        Sessionkey sessionkey = new Sessionkey();
        sessionkey.setSessionkey(key);
        sessionkey.setUserid(userid);
        sessionkey.setDeadline(deadline);

        DatabaseAgent databaseAgent = new DatabaseAgent();
        //先看是否存在同主键的记录，如果有则update，没有则insert
        Sessionkey sessionkeyori = databaseAgent.getSessionkeyBySessionkey(sessionkey.getSessionkey());
        if(sessionkeyori != null){//Update
            databaseAgent.updateSessionkey(sessionkey);
        }else{//Insert
            databaseAgent.insertSessionkey(sessionkey);
        }
        return sessionkey;
    }

    public void reflushSessionkeyDeadline(String sessionkey){
        DatabaseAgent databaseAgent = new DatabaseAgent();
        Sessionkey sessionkeyobj = databaseAgent.getSessionkeyBySessionkey(sessionkey);
        if(sessionkeyobj == null){
            return;
        }
        DateAgent dateAgent = new DateAgent();
        dateAgent.pushHours(2);//sessionkey 2小时过期
        String deadline = dateAgent.getDatestr();
        sessionkeyobj.setDeadline(deadline);
        databaseAgent.updateSessionkey(sessionkeyobj);
    }

    public boolean checkSessionkeyVaild(String sessionkey){
        DatabaseAgent databaseAgent = new DatabaseAgent();
        Sessionkey sessionkeyobj = databaseAgent.getSessionkeyBySessionkey(sessionkey);
        //sessionkey不存在于数据库
        if(sessionkeyobj == null){
            return false;
        }
        //实际sessionkey失效时间
        String deadline = sessionkeyobj.getDeadline();
        //通过从数据库中读出的字符串生成一个DateAgent对象，用于对比时间
        DateAgent deadlineDateAgentObj = new DateAgent(deadline);
        //根据现在时间生成一个DateAgent对象，用于对比时间
        DateAgent dateAgent = new DateAgent();
        //现在时间比传入参数的时间晚，则返回false，说明sessionkey无效
        return dateAgent.compareDate(deadlineDateAgentObj);
    }

    public void deleteSessionKey(String sessionkey){
        DatabaseAgent databaseAgent = new DatabaseAgent();
        databaseAgent.deleteSessionkey(sessionkey);
    }

    private String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
