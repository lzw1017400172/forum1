package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
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
@WebServlet("/admin/updateTopic")
public class AdminUpdateTopicServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        String newnodeid = req.getParameter("newnodeid");
        JsonResult jsonResult = null;
        if(StringUtils.isNumeric(topicid)&&StringUtils.isNumeric(newnodeid)){
            TopicService topicService = new TopicService();
            try {
                topicService.updateTopicNode(Integer.valueOf(topicid), Integer.valueOf(newnodeid));
                jsonResult = new JsonResult(true);
            } catch (ServiceException ex){
                jsonResult = new JsonResult(ex.getMessage());
            }
        } else {
            jsonResult = new JsonResult("参数错误");
        }
        outPrintJson(jsonResult,resp);
    }
}
