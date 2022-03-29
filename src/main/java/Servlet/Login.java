package Servlet;

import Agent.DatabaseAgent;
import Agent.DateAgent;
import Agent.SessionKeyAgent;
import com.alibaba.fastjson.JSONObject;
import entity.Sessionkey;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("Servlet.Login posted");
        //让浏览器用utf8来解析返回的数据
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        //告诉servlet用UTF-8转码，而不是用默认的ISO8859
        response.setCharacterEncoding("UTF-8");

        String loginmode = request.getParameter("loginmode");
        JSONObject jsondata = new JSONObject();
        PrintWriter printWriter = response.getWriter();

        if(Objects.equals(loginmode, "pwd")){//账号密码登入方式
            String usr = request.getParameter("username");
            String pwd = request.getParameter("password");

            String sessionkey = loginByPassword(usr,pwd);//执行登入，并返回sessionkey

            if(sessionkey != null){
                jsondata.put("loginstatu","success");
                jsondata.put("sessionkey",sessionkey);
            }else{
                jsondata.put("loginstatu","faild");
                jsondata.put("errmsg","用户名或密码错误!");
            }
        }else if(Objects.equals(loginmode, "key")){//SessionKey自动登入
            String sessionkeyori = request.getParameter("sessionkey");
            String sessionkey = loginBySessionKey(sessionkeyori);

            if(sessionkey != null){
                jsondata.put("loginstatu","success");
                jsondata.put("sessionkey",sessionkey);
            }else{
                jsondata.put("loginstatu","faild");
                jsondata.put("errmsg","身份验证失效!");
            }
        }

        String jsonstrdata = jsondata.toJSONString();
        printWriter.print(jsonstrdata);
    }

    protected String loginByPassword(String username,String password){//登入成功返回sessionkey值，失败返回null
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);

        if(username == null || username.equals("") || password == null || password.equals("")){
            //账号密码不接受空数据
            return null;
        }

        DatabaseAgent databaseAgent = new DatabaseAgent();
        SessionKeyAgent sessionKeyAgent = new SessionKeyAgent();

        //通过账号密码建立一个user对象，到数据库中匹配，如果返回了0个元素的list说明数据库中不存在此账号密码的数据
        List<User> userlist = databaseAgent.getUserByUserinfo(u);

        if(userlist.size() != 0){//登入成功
            User user = userlist.get(0);
            DateAgent dateAgent = new DateAgent();
            user.setLastlogintime(dateAgent.getDatestr());//设置最后登入时间
            databaseAgent.updateUser(user);//更新最后登入时间
            //先检查是否存在此用户id的sessionkey，有则直接返回key
            Sessionkey sessionkey = databaseAgent.getSessionkeyByUserid(user.getUserid());
            if(sessionkey != null){//userid找到了对应的sessionkey
                //检查sessionkey是否过期
                if(!sessionKeyAgent.checkSessionkeyVaild(sessionkey.getSessionkey())){//过期
                    sessionKeyAgent.deleteSessionKey(sessionkey.getSessionkey());//删除
                    Sessionkey newsessionkey = sessionKeyAgent.generateSessionkey(user.getUserid());
                    return newsessionkey.getSessionkey();
                }else{//未过期
                    sessionKeyAgent.reflushSessionkeyDeadline(sessionkey.getSessionkey());
                    return sessionkey.getSessionkey();
                }

            }else{//此userid没有sessionkey
                Sessionkey newsessionkey = sessionKeyAgent.generateSessionkey(user.getUserid());
                return newsessionkey.getSessionkey();
            }
        }else{
            return null;
        }
    }

    protected String loginBySessionKey(String sessionkey){
        DatabaseAgent databaseAgent = new DatabaseAgent();
        SessionKeyAgent sessionKeyAgent = new SessionKeyAgent();

        Sessionkey sessionkeyobj = databaseAgent.getSessionkeyBySessionkey(sessionkey);
        if(sessionkeyobj == null){//如果sessionkey未找到，登入失败
            return null;
        }

        if(!sessionKeyAgent.checkSessionkeyVaild(sessionkey)){//检查key是否过期
            sessionKeyAgent.deleteSessionKey(sessionkey);//过期了就删掉，并登入失败
            return null;
        }

        sessionKeyAgent.reflushSessionkeyDeadline(sessionkey);//更新sessionkey的过期时间

        return sessionkey;

    }
}
