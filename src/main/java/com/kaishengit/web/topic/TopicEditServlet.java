package com.kaishengit.web.topic;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by 刘忠伟 on 2016/12/23.
 */
@WebServlet("/topicEdit")
public class TopicEditServlet extends BaseServlet {

    private TopicService topicService = new TopicService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //登录之后直接跳转到这个页面是需要传值，topicid，不传之就报错。所以，是被过滤器截下来的，在过滤器重定向/login传值时，不仅要传原来网址，还要把参数也传过去。


        String topicid = req.getParameter("topicid");


        try {
            Topic topic = topicService.findByTopicId(topicid);
            //修改topic，新的jsp还需要节点nodeList
            List<Node> nodeList = topicService.findAllNodeService();
            req.setAttribute("topic",topic);
            req.setAttribute("nodeList",nodeList);
            forWard("topic/topicEdit.jsp",req,resp);
        } catch (ServiceException ex){
            resp.sendError(404,ex.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String nodeid = req.getParameter("nodeid");

        //需要满足条件才能修改主题，1不超过五分钟，2没有回复，
        JsonResult jsonResult = new JsonResult();
        try {
            topicService.updateTopic(topicid, title, content, nodeid);
            //客户端接受到响应成功之后，会跳转到/post需要topicid值，所以当返回值返回，会比在jsp获取地址栏，更为准确
            jsonResult.setState(JsonResult.SUCCESS);
            jsonResult.setData(topicid);
        } catch (ServiceException ex){
            jsonResult.setState(JsonResult.ERROR);
            jsonResult.setMessage(ex.getMessage());
        }
        outPrintJson(jsonResult,resp);
    }
}
