package com.kaishengit.web.user;

import com.google.gson.Gson;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘忠伟 on 2016/12/15.
 * 注册servlet
 */
@WebServlet("/reg")
public class RegServlet extends BaseServlet{

    /**
     * 跳转到注册reg.jsp
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forWard("user/reg.jsp",req,resp);
    }

    /**
     * 接受注册表单提交
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        UserService userService = new UserService();

        //客户端接收返回值是以json形式，state:success,error;message:"",data:{}
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            userService.saveUser(username, password, email, phone);
            //走到这里没有抛出异常，就说明注册成功，返回state:success
            map.put("state","success");
            //map.put("data","");
        } catch (ServiceException ex){//这里处理异常servlet只处理逻辑异常，不处理sql异常，saveUser里面所有的异常都会被处理，只要接受到就说明注册失败，返回state:error
            ex.printStackTrace();
            map.put("state","error");
            map.put("message","注册失败！稍后再试");//jiso模式
        }

        //放入outprint中返回
        outPrintJson(map,resp);
    }
}
