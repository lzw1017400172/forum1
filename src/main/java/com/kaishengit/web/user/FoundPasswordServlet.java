package com.kaishengit.web.user;

import com.google.common.collect.Maps;
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
 * 修改密码方式：邮件或者手机
 */
@WebServlet("/foundPassword")
public class FoundPasswordServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        forWard("user/foundPassword.jsp",req,resp);//请求转发

    }

    /**
     * 获取表单提交的值
     *  type 复选框的值email或者phone
     *  value 提交的邮箱或者手机号
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //ajax的data传值，是序列化流，不会再地址栏出现，不用担心被修改，只要客户端限制了，就不用在这里限制，但是url传值需要
        //所以这两个参数传过来是一定不会为null
        String type = req.getParameter("select_type");
        String value = req.getParameter("value");

        Logger logger = LoggerFactory.getLogger(FoundPasswordServlet.class);
        logger.debug("{}+{}", type, value);
        //判断是邮件还是手机号，以及是否存在，去service层处理
        UserService userService = new UserService();
        Map<String,Object> map = Maps.newHashMap();
        try {
            userService.foundPassWord(type, value);
            map.put("state","success");
        } catch (ServiceException ex){
            map.put("state","error");
            map.put("message",ex.getMessage());
        }
        outPrintJson(map,resp);
    }
}
