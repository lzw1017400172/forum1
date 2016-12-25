package com.kaishengit.web.topic;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
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
 * Created by 刘忠伟 on 2016/12/24.
 * 加入收藏或者取消收藏
 */
@WebServlet("/topicFav")
public class TopicFavServlet extends BaseServlet {

    private TopicService topicService = new TopicService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        String action = req.getParameter("action");

        if(StringUtils.isNumeric(topicid) && StringUtils.isNotEmpty(action)){
            User user = getCurrenUser(req);
            JsonResult jsonResult = null;
            if(user != null){
                try {
                    //收藏或者取消完毕之后的收藏数量
                    int favnum = topicService.favTopic(user, topicid, action);
                    jsonResult = new JsonResult(favnum);
                } catch (ServiceException ex){
                    jsonResult = new JsonResult(ex.getMessage());//字符串就直接是错误
                }
            } else {
                jsonResult = new JsonResult("session过期或者错误");
            }
            outPrintJson(jsonResult,resp);
        } else {
            resp.sendError(404,"参数错误");
        }

    }
}
