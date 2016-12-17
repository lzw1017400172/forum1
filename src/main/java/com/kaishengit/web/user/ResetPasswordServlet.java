package com.kaishengit.web.user;

import com.google.common.collect.Maps;
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
import java.io.IOException;
import java.util.Map;


/**
 * Created by 刘忠伟 on 2016/12/17.
 */
@WebServlet("/foundpassword/newpassword")
public class ResetPasswordServlet extends BaseServlet {

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String token = req.getParameter("token");


        //根据token获取需要修改的账户，并且把账户id和token传给jsp，让客户端修改对应的账户，并且下次提交token，验证提交表单时是否token过期
        try {
            User user = userService.resetPasswordGetUserByToken(token);
            req.setAttribute("id",user.getId());
            req.setAttribute("token",token);
            forWard("user/reset_password.jsp",req,resp);//跳转到重置密码
        } catch (ServiceException ex){//只接受Service异常，不接受数据库异常，数据库异常属于系统崩溃去报错就行了。service异常说明没成功，不用报错，告诉客户端可以
            //请求转发向jsp传值
            req.setAttribute("message",ex.getMessage());
            forWard("user/reset_error.jsp",req,resp);//跳转到重置错误页面
        }
    }

    /**
     * 修改密码表单提交
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String token = req.getParameter("token");
        String password = req.getParameter("password");

        Map<String,Object> map = Maps.newHashMap();

        //重置密码
        try {
            userService.resetPassword(Integer.valueOf(id), token, password);
            map.put("state","success");
        } catch (ServiceException ex){
            map.put("state","error");
            map.put("message",ex.getMessage());
        }
        outPrintJson(map,resp);//返回值json
    }
}
