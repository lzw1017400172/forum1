package com.kaishengit.web.topic;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by 刘忠伟 on 2016/12/20.
 */
@WebServlet("/newpost")
public class NewPostServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //在jsp的选择节点复选框，的值是不能写死的，我们存在数据库node就是这个节点，可以添加nodename，通过改数据库可以修改这个
        //每次访问这个页面都要获取一下数据库node，把所有节点的查询返回出来。给jsp，循环显示出来就好
        TopicService topicService = new TopicService();
        List<Node> nodeList = topicService.findAllNodeService();//查询所有的节点node对象，
        req.setAttribute("nodeList",nodeList);//可传Object类型
        forWard("topic/newpost.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取表单提交得值
        String title = req.getParameter("newpost_title");
        String content = req.getParameter("content");
        String nodeid = req.getParameter("nodeid");

        //同步数据库,需要知道两个外键，通过session的userid和获取的codeid，有四个属性有默认值为0不用设置
        TopicService topicService = new TopicService();
        Integer userid = getCurrenUser(req).getId();
        Integer topicid = topicService.saveTopic(title,content,Integer.valueOf(nodeid),userid);
        //service层没有异常就不用接受了
        //直接返回
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(JsonResult.SUCCESS);
        jsonResult.setData(topicid);
        outPrintJson(jsonResult,resp);

    }
}
