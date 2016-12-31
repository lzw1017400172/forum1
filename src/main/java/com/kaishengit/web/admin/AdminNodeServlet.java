package com.kaishengit.web.admin;

import com.kaishengit.entity.Node;
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
@WebServlet("/admin/node")
public class AdminNodeServlet extends BaseServlet {

    private TopicService topicService = new TopicService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //进入node.jsp要传入所有节点。分页
        String p = req.getParameter("p");
        int pageNo = StringUtils.isNumeric(p)?Integer.valueOf(p):1;
        Page page = topicService.findAllNodeByPage(pageNo);
        req.setAttribute("page",page);
        forWard("admin/node.jsp",req,resp);

    }
}
