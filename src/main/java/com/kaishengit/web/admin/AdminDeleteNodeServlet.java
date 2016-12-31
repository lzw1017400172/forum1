package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.NodeService;
import com.kaishengit.utils.StringUtils;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 刘忠伟 on 2016/12/28.
 */
@WebServlet("/admin/deleteNode")
public class AdminDeleteNodeServlet extends BaseServlet {

    private NodeService nodeService = new NodeService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid = req.getParameter("nodeid");
        JsonResult jsonResult = null;
        if(StringUtils.isNumeric(nodeid)){
            try {
                nodeService.deleteNode(Integer.valueOf(nodeid));
                jsonResult = new JsonResult(true);//这里只要不是字符串，就是成功了
            } catch(ServiceException ex){
                jsonResult = new JsonResult(ex.getMessage());
            }
        } else {
            jsonResult = new JsonResult("参数错误！");
        }
        outPrintJson(jsonResult,resp);
    }
}
