package com.kaishengit.web.topic;

import com.kaishengit.entity.Topic;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 刘忠伟 on 2016/12/20.
 */
@WebServlet("/post")
public class PostServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //返回到post。jsp，这个页面需要展示刚刚写的内容，所以我需要知道展示谁的主题，所以就需要上个跳转往这里传值 主题id
        String topicid = req.getParameter("topicid");
        //这里跳转到post.jsp，跳转过去时，传值，因为主题页会有很多值需要获取
        //传过去一个topic对象，并不能全部解决，比如topic里面只有nodeid，但是需要nodename，所以封装给topic对象两个属性分别为Node和User对象，这样就可以解决所有值
        TopicService topicService = new TopicService();
        try {
            Topic topic = topicService.findByTopicId(topicid);
            req.setAttribute("topic",topic);//通过链式获取值
            forWard("user/post.jsp",req,resp);
        } catch (ServiceException ex){
            resp.sendError(404);
        }

    }
}
