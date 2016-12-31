package com.kaishengit.web.admin;

import com.kaishengit.service.TopicService;
import com.kaishengit.utils.Page;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 刘忠伟 on 2016/12/28.
 */
@WebServlet("/admin/home")
public class AdminHomeServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //分页查询，按照日期降序
        String p = req.getParameter("p");//第一次进来没有p时显示第一页.如果大于少于总页数，在page也会判断
        int pageNo = StringUtils.isNumeric(p)?Integer.valueOf(p):1;

        TopicService topicService = new TopicService();
        Page page = topicService.findTopicNumAndReplyNum(pageNo);
        req.setAttribute("page",page);
        req.getRequestDispatcher("/WEB-INF/views/admin/home.jsp").forward(req,resp);
    }
}
