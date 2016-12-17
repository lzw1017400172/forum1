package com.kaishengit.web.user;

import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘忠伟 on 2016/12/15.
 */
@WebServlet("/login")
public class LoginServlet extends BaseServlet {

    /**
     * 跳转到登录页面login.jsp
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forWard("/user/login.jsp",req,resp);
    }

    /**
     * 接受表单值
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Logger logger = LoggerFactory.getLogger(LoginServlet.class);
        logger.debug("{}+{}",username,password);
        //获取ip地址
        String ip = req.getRemoteAddr();
        //判断是否登录成功，返回值json
        Map<String,Object> map = new HashMap<>();
        UserService userService = new UserService();
        try {
            User user = userService.login(username, password, ip);//user就是登录成功的对象，所有其他可能已经在service排除，
            //登录成功需要把对象放到session里面
            HttpSession session =req.getSession();
            session.setAttribute("curr_user",user);//session会在过滤器使用，还有导航的功能显示是根据session的键curr_user获取的，

            map.put("state","success");
        } catch (ServiceException ex){//可能收到数据库的异常,不能搅浑，这里只处理service异常
            map.put("state","error");
            map.put("message",ex.getMessage());
        }
        outPrintJson(map,resp);
    }
}
