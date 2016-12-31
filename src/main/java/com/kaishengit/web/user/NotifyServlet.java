package com.kaishengit.web.user;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Notify;
import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by 刘忠伟 on 2016/12/26.
 * 必须登录后才能查看用户自己的通知
 */
@WebServlet("/notify")
public class NotifyServlet extends BaseServlet {

    private UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = getCurrenUser(req);
        if(user != null) {
            //跳转传入notifyList集合
            List<Notify> notifyList = userService.findNotifyByUserIdService(user.getId());
            req.setAttribute("notifyList",notifyList);
            forWard("user/notify.jsp",req,resp);
        } else {
            resp.sendError(404,"session过期或者错误");//forWard一定要在这个之上要不会报错
        }

    }

    /**
     * 完成通知的异步请求
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //没有传过来任何值，，只要返回未读通（state=0）知个数。。查询的肯定是当前登录用户的通知
        User user = getCurrenUser(req);
        JsonResult jsonResult = null;
        if(user != null){

            List<Notify> notifyList = userService.findUnReadNotifyService(user.getId());
            jsonResult = new JsonResult(notifyList.size());
        } else {
            jsonResult = new JsonResult("session过期或者错误");
        }
        outPrintJson(jsonResult,resp);

    }
}
