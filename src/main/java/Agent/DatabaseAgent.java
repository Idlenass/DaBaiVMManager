package Agent;

import entity.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAgent {

    //sql语句执行器
    private Object doSql(String opt,Object param){
        //定义读取文件名
        String resources = "mybatis-config.xml";
        //创建流
        Reader reader=null;
        try {
            //读取mybatis-config.xml文件到reader对象中
            reader= Resources.getResourceAsReader(resources);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //初始化mybatis,创建SqlSessionFactory类的实例
        SqlSessionFactory sqlMapper=new SqlSessionFactoryBuilder().build(reader);
        //创建session实例
        SqlSession session=sqlMapper.openSession();
        //传入参数查询，返回结果
        Object obj = session.selectOne(opt,param);

        session.close();

        return obj;
    }

    //sql语句执行器
    private List<Object> doSqlList(String opt,Object param){
        //定义读取文件名
        String resources = "mybatis-config.xml";
        //创建流
        Reader reader=null;
        try {
            //读取mybatis-config.xml文件到reader对象中
            reader= Resources.getResourceAsReader(resources);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //初始化mybatis,创建SqlSessionFactory类的实例
        SqlSessionFactory sqlMapper=new SqlSessionFactoryBuilder().build(reader);
        //创建session实例
        SqlSession session=sqlMapper.openSession();
        //传入参数查询，返回结果
        List<Object> obj = session.selectList(opt,param);

        session.close();

        return obj;
    }

    //功能实现开始

        //Config Reader

    public Mcsmapiinfo getMcsmapiinfoById(int id){
        return (Mcsmapiinfo) doSql("queryMcsmapiinfoById",id);
    }


    public Pveapiinfo getPVEapiinfoById(int id){
        return (Pveapiinfo) doSql("queryPveapiinfoById",id);
    }
        //User

    public User getUserByUserid(String userid){
        return (User) doSql("queryUserById",userid);
    }

    public List<User> getUserByUserinfo(User user){
        List<Object> userlistobj = doSqlList("queryUserAll",user);
        List<User> userlist = new ArrayList<>();
        for (Object o : userlistobj) {
            userlist.add((User) o);
        }
        return userlist;
    }

    public void updateUser(User user){
        if(user.getUserid().equals("")){//如果userid为空，sql会报错
            return;
        }
        doSql("updateUser",user);
    }

        //Sessionkey

    public Sessionkey getSessionkeyByUserid(String userid){
        return (Sessionkey) doSql("querySessionkeyByUserid",userid);
    }

    public Sessionkey getSessionkeyBySessionkey(String sessionkey) {
        return (Sessionkey) doSql("querySessionkeyBySessionkey", sessionkey);
    }

    public void insertSessionkey(Sessionkey sessionkey){
        doSql("insertSessionkey",sessionkey);
    }

    public void updateSessionkey(Sessionkey sessionkey){
        doSql("updateSessionkey",sessionkey);
    }

    public void deleteSessionkey(String sessionkey){
        doSql("deleteSessionkeyBySessionkey",sessionkey);
    }

        //Userfinancial

    public Userfinancial getUserfinancialByUserid(String userid){
        return (Userfinancial) doSql("queryUserfinancialByUserid",userid);
    }

        //Mcsmuser

    public Mcsmuser getMcsmuserByUserid(String userid){
        return (Mcsmuser) doSql("queryMcsmuserByUserid",userid);
    }

    public Mcsmuser getMcsmuserByMcsmusername(String mcsmusername){
        return (Mcsmuser) doSql("queryMcsmuserByMcsmusername",mcsmusername);
    }

    public List<Mcsmuser> getMcsmuserByMcsmuser(Mcsmuser mcsmUser){
        List<Object> objlist = doSqlList("queryMcsmuserByMcsmuser",mcsmUser);
        List<Mcsmuser> mcsmuserList = new ArrayList<>();
        for(int i = 0;i < objlist.size();i++){
            mcsmuserList.add((Mcsmuser) objlist.get(i));
        }
        return  mcsmuserList;
    }

    public void insertMcsmuser(Mcsmuser mcsmuser){
        doSql("insertMcsmuser",mcsmuser);
    }

    public void updateMcsmuser(Mcsmuser mcsmuser){
        doSql("updateMcsmuser",mcsmuser);
    }

    public void insertOrUpdateMcsmuserBatch(List<Mcsmuser> entities){
        doSql("insertOrUpdateMcsmuserBatch",entities);
    }

    public void deleteMcsmuserByMcsmusername(String mcsmusername){
        doSql("deleteMcsmuserByMcsmusername",mcsmusername);
    }

    public void deleteMcsmuserByUserid(String userid){
        doSql("deleteMcsmuserByUserid",userid);
    }

    public Mcsmserverappendinfo getMcsaiByServername(String servername){
        return (Mcsmserverappendinfo) doSql("queryMcsaiByServername",servername);
    }

        //PVE User

    public Pveuser getPveuserByUserid(String userid){
        return (Pveuser) doSql("queryPveuserByUserid",userid);
    }

    public Pveuser getPveuserByPveuserid(String pveuserid){
        return (Pveuser) doSql("queryPveuserByPveuserid",pveuserid);
    }

    public List<Pveuser> getPveuserByPveuser(Pveuser pveuser){
        List<Object> objectList = doSqlList("queryPveuserByPveuser",pveuser);
        List<Pveuser> pveuserList = new ArrayList<>();
        //循环遍历每个对象转换为Pveuser对象
        for (Object o : objectList) {
            pveuserList.add((Pveuser) o);
        }
        return pveuserList;
    }

    public void insertPveuser(Pveuser pveuser){
        doSql("insertPveuser",pveuser);
    }

    public void updatePveuser(Pveuser pveuser){
        doSql("updatePveuser",pveuser);
    }

    public void deletePveuserByuserid(String userid){
        doSql("deletePveuserByuserid",userid);
    }

    public void deletePveuserByPveuserid(String pveuserid){
        doSql("deletePveuserByPveuserid",pveuserid);
    }

        //PVE Server

    public Pveserverappendinfo getPvesaiByvmid(int vmid){
        return (Pveserverappendinfo) doSql("queryPvesaiByvmid",vmid);
    }

    public List<Pveserverappendinfo> getPvesaiByPvesai(Pveserverappendinfo pveserverappendinfo){
        List<Object> objectList = doSqlList("queryPvesaiByPvesai",pveserverappendinfo);
        List<Pveserverappendinfo> pveserverappendinfoList = new ArrayList<>();
        for(Object o : objectList){
            pveserverappendinfoList.add((Pveserverappendinfo) o);
        }
        return pveserverappendinfoList;
    }

    public void insertPvesai(Pveserverappendinfo pveserverappendinfo){
        doSql("insertPvesai",pveserverappendinfo);
    }

    public void updatePvesai(Pveserverappendinfo pveserverappendinfo){
        doSql("updatePvesai",pveserverappendinfo);
    }

    public void deletePvesaiByvmid(String vmid){
        doSql("deletePvesaiByvmid",vmid);
    }

        // ISOFile

    public Isofile getIsofileById(int isoid){
        return (Isofile) doSql("queryIsofileById",isoid);
    }

    public List<Isofile> getIsofileByIsofile(Isofile isofile){
        List<Object> objectList = doSqlList("queryIsofileByIsofile",isofile);
        List<Isofile> isofileList = new ArrayList<>();
        for(Object o : objectList){
            isofileList.add((Isofile) o);
        }
        return isofileList;
    }

    public List<Isofile> getAllIsofile(){
        return this.getIsofileByIsofile(new Isofile());
    }

    public void insertIsofile(Isofile isofile){
        doSql("insertIsofile",isofile);
    }

    public void updateIsofile(Isofile isofile){
        doSql("updateIsofile",isofile);
    }

    public void deleteIsofileById(Integer isoid){
        doSql("deleteIsofileById",isoid);
    }
}
