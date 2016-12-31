package com.kaishengit.web.user;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 刘忠伟 on 2016/12/27.
 *  把notify。jsp异步发送的数据nitify的id，变成已读的
 */
@WebServlet("/read")
public class ReadServlet extends BaseServlet{

    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ids = req.getParameter("ids");
        //字符串解析成数组
        if(ids != null){
            String[] notifyids = ids.split(",");
            JsonResult jsonResult = new JsonResult();
            //把未读变成已读
            try {
                userService.updateNotifyStateById(notifyids);
                jsonResult.setState(JsonResult.SUCCESS);
            } catch (ServiceException ex){
                jsonResult.setState(JsonResult.ERROR);
                jsonResult.setMessage(ex.getMessage());
            }
            outPrintJson(jsonResult,resp);
        } else {
            resp.sendError(404,"找不到参数");//跳转时forward一定呀哦卸载这个之上，要不就报错，重复跳转
        }


    }
}
