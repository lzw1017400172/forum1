package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.utils.Page;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 刘忠伟 on 2016/12/29.
 */
@WebServlet("/admin/user")
public class AdminUserServlet  extends BaseServlet{
    UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //因为需要分页。所以需要传当前页面
        String p = req.getParameter("p");
        int pageNo = StringUtils.isNumeric(p) ? Integer.valueOf(p) : 1;/*不用的担心大于小于页数。在page封装时会解决*/
       /*需要user表和loginlog表联查，并且大量用户信息，需要多表联查*/
        Page page = userService.findUserAndLoginLogPage(pageNo);
        req.setAttribute("page",page);
        forWard("admin/user.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userid = req.getParameter("userid");
        String state = req.getParameter("state");
        JsonResult jsonResult = null;
        if(StringUtils.isNumeric(userid) && ("禁用".equals(state) || "恢复".equals(state))){
            try {
               userService.updateState(Integer.valueOf(userid), state);
                jsonResult = new JsonResult(true);//只要不是字符串就代表成功
           } catch (ServiceException ex){
               jsonResult = new JsonResult(ex.getMessage());
           }
        } else {
            jsonResult = new JsonResult("参数错误！");
        }
        outPrintJson(jsonResult,resp);
    }
}
