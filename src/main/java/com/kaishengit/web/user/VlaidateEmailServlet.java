package com.kaishengit.web.user;

import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 刘忠伟 on 2016/12/16.
 * 验证邮箱好似否存在
 */
@WebServlet("/validateemail")
public class VlaidateEmailServlet extends BaseServlet {

    private UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        User user = userService.validateEmail(email);
        if(user == null){
            outPrintText("true",resp);
        } else {
            outPrintText("false",resp);
        }
    }
}
