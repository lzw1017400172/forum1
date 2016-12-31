package com.kaishengit.web.admin;

import com.google.gson.Gson;
import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Admin;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.AdminService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 刘忠伟 on 2016/12/28.
 */
@WebServlet("/admin/login")
public class AdminLoginServlet extends BaseServlet {

    private AdminService adminService = new AdminService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //登录之前要判断一下账户是否已经有用户登录过，有的话删除，不能同时登录两个
        HttpSession session = req.getSession();
        session.removeAttribute("curr_admin");//user登录和admin登录存入session的键不要相同。
        req.getRequestDispatcher("/WEB-INF/views/admin/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String ip = req.getRemoteAddr();


        if(StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)){
            //登录需要记录管理员的ip
            String json = "";
            try{
                Admin admin = adminService.adminLogin(username,password,ip);
                //上传session，用来判断是否已经登录
                HttpSession session = req.getSession();
                session.setAttribute("curr_admin",admin);
                json = "{\"state\":\"success\"}";
            } catch (ServiceException ex){
                json = "{\"state\":\"error\",\"message\":\""+ex.getMessage()+"\"}";
            }

            //响应json
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            //转成json，1用方法可以把对象或者数组转成json，或者手拼。{"":"","":""}这种，放入流就行
            //String json = new Gson().toJson();
            out.print(json);
            out.flush();
            out.close();

        } else {
            resp.sendError(404,"帐号密码不能为空！");
        }


    }
}
