package com.kaishengit.web;

import com.google.gson.Gson;
import com.kaishengit.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 刘忠伟 on 2016/12/15.
 * 作为工具类，serlet跳转jsp要一直使用，写个工具类不用麻烦写了,并且拥有父类的方法
 */
@WebServlet("/base")
public class BaseServlet extends HttpServlet {

    /**
     * servlet跳转到jsp
     * @param path
     * @param req
     * @param resp
     */
    public void forWard(String path ,HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/" + path).forward(req,resp);//此方法抛出异常，谁调用水处理，
    }

    public void outPrintText(Object object,HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");//往外输出都要中文编码
        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(object);
        out.flush();
        out.close();
    }
    public void outPrintJson(Object object,HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");//往外输出都要中文编码
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String json = new Gson().toJson(object);
        out.print(json);
        out.flush();
        out.close();
    }
    //很多servlet需要获取session里面的User对象的地方，所以写成一个方法
    public User getCurrenUser(HttpServletRequest req){
        HttpSession session = req.getSession();//session可能会找不到
        Object currenUser = session.getAttribute("curr_user");//这个获取出来的是String可能为null，为null时不能够强转，需要判断
        if(currenUser == null){
            return null;
        } else {
            return (User)currenUser;
        }
    }
}
