package com.kaishengit.web.topic;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 刘忠伟 on 2016/12/25.
 * 用户感谢topic
 */
@WebServlet("/topicThank")
public class TopicThankServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        String action = req.getParameter("action");
        JsonResult jsonResult = null;
        User user = getCurrenUser(req);
        if(!StringUtils.isNumeric(topicid) || user == null || !("thank".equals(action) || "unthank".equals(action))){
            jsonResult = new JsonResult("参数错误");

        } else {
            TopicService topicService = new TopicService();
            Topic topic = topicService.findByTopicId(topicid);//用来判断此主题在数据库是否存在,不存在就不保存
            if(topic != null){
                topic = topicService.thankTopic(user,topic,action);
                jsonResult = new JsonResult(topic.getThankyounum());
            } else {
                jsonResult = new JsonResult("参数错误");
            }
        }
        outPrintJson(jsonResult,resp);

    }
}
