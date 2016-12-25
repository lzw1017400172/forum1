package com.kaishengit.web;



import com.kaishengit.entity.Node;

import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.utils.Page;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by 刘忠伟 on 2016/12/15.
 */
@WebServlet("/home")
public class HomeServlet extends BaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //显示节点,是每次都要传过去的(必传)
        TopicService topicService = new TopicService();
        List<Node> nodeList = topicService.findAllNodeService();

        String p = req.getParameter("p");
        String nodeid = req.getParameter("nodeid");
        int pageNo = StringUtils.isNumeric(p)?Integer.parseInt(p):1;
        try {
            if(StringUtils.isNumeric(nodeid)){
                //当点击节点，默认跳到第一个页面page=1,但是没有传过来值p=null,上面判断了不是数字为1，
                Page page = topicService.findTopicAndUserByNodeId(Integer.valueOf(nodeid), pageNo);
                req.setAttribute("page",page);
            } else {
                //不是数字，或者空的时候返回全部,多表联查，需要user
                Page page = topicService.findTopicAndUser(pageNo);
                req.setAttribute("page",page);

            }
            //判断nodeid，为null显示全部
            req.setAttribute("nodeList",nodeList);
            forWard("index.jsp",req,resp);//index.jsp
        } catch (ServiceException ex){
            resp.sendError(404,"页面没有找到");//只有forward跳转写在sendError之前才不会冲突
        }
    }
}
