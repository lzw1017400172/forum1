package com.kaishengit.web.user;

import com.kaishengit.service.UserService;
import com.kaishengit.utils.StringUtils;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 刘忠伟 on 2016/12/16.
 * 邮箱激活帐号跳转的servlet
 */
@WebServlet("/user/active")
public class ActiveUserServlet extends BaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.和resp的字符编码已经在字符集过滤器处理
        //上传到缓存的键值对，和传过来的token，用token去获取，在service逻辑处理层处理，在servlet用是否异常来判断是否激活成功，并且进行跳转
        String token = req.getParameter("_");
        if(StringUtils.isEmpty(token)){//url传值，可以在地址栏修改，防止为空给个404
            resp.sendError(404);
        }
        //去service查询token对象username,根据是否异常来判断激活成功吗
        UserService userService = new UserService();
        try {
            userService.activeUser(token);
            //激活成功跳转到成功页面
            forWard("user/active_success.jsp",req,resp);
        } catch (Exception ex){
            //激活失败跳转到失败页面
            req.setAttribute("message",ex.getMessage());
            forWard("user/active_error.jsp",req,resp);
        }

    }
}
