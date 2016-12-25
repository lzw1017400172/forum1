package com.kaishengit.web.topic;

import com.kaishengit.entity.*;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by 刘忠伟 on 2016/12/20.
 */
@WebServlet("/post")
public class PostServlet extends BaseServlet {

    /**
     *  每次进入post.jsp展示主题的页面，就要先进入/post，所以每点击一次就进入这里一次，在这里记录一次
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //返回到post。jsp，这个页面需要展示刚刚写的内容，所以我需要知道展示谁的主题，所以就需要上个跳转往这里传值 主题id
        String topicid = req.getParameter("topicid");
        //这里跳转到post.jsp，跳转过去时，传值，因为主题页会有很多值需要获取
        //传过去一个topic对象，并不能全部解决，比如topic里面只有nodeid，但是需要nodename，所以封装给topic对象两个属性分别为Node和User对象，这样就可以解决所有值
        TopicService topicService = new TopicService();

        //不仅需要传入主题，还要传入所有回复，和对应user。。就数据来看，大量的回复，不能使用单表查询，次数太多，多表联查一次

        try {
            Topic topic = topicService.findByTopicId(topicid);
            //点击数就该在这里获取，并且要展示的是topicid，也就是topicid的点击数+1每次访问
            topicService.topicClickNum(topic);


            List<Reply> replyList = topicService.findReplyByTopicId(Integer.valueOf(topicid));

            req.setAttribute("replyList",replyList);//对应主题的所有回复，包括对应user
            req.setAttribute("topic",topic);//通过链式获取值

            User user = getCurrenUser(req);
            Fav fav = null;
            Thank thank = null;
            if(user != null && StringUtils.isNumeric(topicid)){

                //进去之后要判断是 加入收藏还是取消收藏，通过topicid查出来，收藏关系，再查有没有现在登录的userid
                fav = topicService.findFavByTopicIdAndUserId(user,topicid);//只要判断有没有被登录的人收藏，有返回值，没有不返回就好
                thank = topicService.finThankByTopicIdAndUserId(user.getId(),Integer.valueOf(topicid));

                req.setAttribute("fav",fav);//不用判断是不是null，在客户端获取到是不是null就说明已经被收藏
                req.setAttribute("thank",thank);
            } //这里session为null时不用管，因为jsp就不会显示这一块了

            forWard("topic/post.jsp",req,resp);

        } catch (ServiceException ex){
            resp.sendError(404,ex.getMessage());
        }

    }

    /*发布回复的表单提交*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        String content = req.getParameter("content");
        User user = getCurrenUser(req);

        //添加到数据库，并且在service进行判断
        TopicService topicService = new TopicService();
        try {
            topicService.saveTopicReply(content, topicid, user);
            //当添加成功时，重定向跳转到/post，或者请求转发到post.jsp
            //   /post需要值topicid,post.jsp需要topic对象
            //req.setAttribute("topic",topic);
            resp.sendRedirect("/post?topicid="+topicid);//可能需要把内容呈现出来，返回集合，
        } catch (ServiceException ex){
            resp.sendError(404,ex.getMessage());
        }

    }
}
