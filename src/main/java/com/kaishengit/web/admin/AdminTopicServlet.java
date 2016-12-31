package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.utils.Page;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by 刘忠伟 on 2016/12/28.
 */
@WebServlet("/admin/topic")
public class AdminTopicServlet extends BaseServlet {

    private TopicService topicService = new TopicService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //topic.jsp需要分页查询所有的主题，是和user表联查
        String p = req.getParameter("p");
        //不存在不是数字都去第一页
        int pageNo = StringUtils.isNumeric(p)?Integer.parseInt(p):1;
        Page page = topicService.findTopicAndUser(pageNo);
        List<Node> nodeList = topicService.findAllNodeService();
        req.setAttribute("page",page);
        req.setAttribute("nodeList",nodeList);
        forWard("admin/topic.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        JsonResult jsonResult = new JsonResult();
        if(StringUtils.isNumeric(topicid)){
            try {
                topicService.deleteTopic(Integer.valueOf(topicid));
                jsonResult.setState(JsonResult.SUCCESS);
            } catch(ServiceException ex){
                jsonResult.setState(JsonResult.ERROR);
                jsonResult.setMessage(ex.getMessage());
            }
        } else {
            jsonResult.setState(JsonResult.ERROR);
            jsonResult.setMessage("参数错误");
        }
        outPrintJson(jsonResult,resp);

    }
}
