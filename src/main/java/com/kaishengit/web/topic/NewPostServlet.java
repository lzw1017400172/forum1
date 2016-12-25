package com.kaishengit.web.topic;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.utils.Config;
import com.kaishengit.web.BaseServlet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

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
        //跳转到文本编辑器要上传图片到七牛，所以这里获取token,并且文本编辑器Simditor是需要特定的json返回值
       /* Auth auth = Auth.create(Config.getConfig("qiniu.ak"),Config.getConfig("qiniu.sk"));
        StringMap map = new StringMap();
        //这里的map设置值如果是 returnbody是设置七牛返回值的模版，returnURL是设置七牛返回的地址
        String returnBody = "{\"success\":true,\"file_path\":\"http://oi04kst4a.bkt.clouddn.com/${key}\"}";
        //${key}是占位符，不是EL表达式
        map.put("rturnBody",returnBody);
        String token = auth.uploadToken(Config.getConfig("qiniu.bucketname"),null,3600,map);*/

        Auth auth = Auth.create(Config.getConfig("qiniu.ak"),Config.getConfig("qiniu.sk"));
        StringMap stringMap = new StringMap();
        stringMap.put("returnBody","{ \"success\": true,\"file_path\": \""+Config.getConfig("qiniu.domain")+"${key}\"}");
        String token = auth.uploadToken(Config.getConfig("qiniu.bucketname"),null,3600,stringMap);

        //在jsp的选择节点复选框，的值是不能写死的，我们存在数据库node就是这个节点，可以添加nodename，通过改数据库可以修改这个
        //每次访问这个页面都要获取一下数据库node，把所有节点的查询返回出来。给jsp，循环显示出来就好
        TopicService topicService = new TopicService();
        List<Node> nodeList = topicService.findAllNodeService();//查询所有的节点node对象，
        req.setAttribute("nodeList",nodeList);//可传Object类型
        req.setAttribute("token",token);
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
        JsonResult jsonResult = new JsonResult();
        try {
            Integer topicid = topicService.saveTopic(title, content, Integer.valueOf(nodeid), userid);
            jsonResult.setState(JsonResult.SUCCESS);
            jsonResult.setData(topicid);
        } catch(ServiceException ex){
            jsonResult.setState(JsonResult.ERROR);
            jsonResult.setMessage(ex.getMessage());
        }
        //service层没有异常就不用接受了
        //直接返回
        outPrintJson(jsonResult,resp);

    }
}
