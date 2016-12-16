package com.kaishengit.web.user;

import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.utils.StringUtils;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 刘忠伟 on 2016/12/15.
 * 验证账户是否存在
 */
@WebServlet("/validateusername")
public class ValidateUsernameServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //url传值是可以在地址栏修改的，若果必须是数字，一定要限制，字符串就不用了，字符串需要解决中文乱码
        //中文乱码的解决需要很多次使用，写一个工具类
        //StringUtils类是lang3的类，可以继承于他写一个解决中文乱码的方法
        String username = req.getParameter("username");
        username = StringUtils.isoToUtf8(username);

        //然后判断帐号是否存在，service层获取查找
        UserService userService = new UserService();
        boolean result = userService.validateUserName(username);
        //需要把内容返回，用最原始的输出内容的方法，以后每个servlet都要用，就写成工具BaseServlet
        //判断返回值，找到和没有找到
        if(result){
            outPrintText("true",resp);//父类方法直接用
        } else {
            outPrintText("false",resp);
        }


    }
}
