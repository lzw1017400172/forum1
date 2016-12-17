package com.kaishengit.web.user;

import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 刘忠伟 on 2016/12/16.
 * 退出，session强制过期,session一个客户端一个session
 */
@WebServlet("/logout")
public class LogoutServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //session强制过期，安全退出
        session.invalidate();
        //可以请求转发到jsp也可以重定向到servlet
        req.setAttribute("message","你已经安全退出");
        forWard("user/login.jsp",req,resp);
        //或者重定向到/home
        //resp.sendRedirect("/login?message=你已经安全退出");
    }
}
