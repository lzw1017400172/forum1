package com.kaishengit.web.admin;

import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 刘忠伟 on 2016/12/30.
 */
@WebServlet("/admin/logout")
public class AdminLogoutServle extends BaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //强制session死亡
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect("/admin/login");
    }
}
