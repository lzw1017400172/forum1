package com.kaishengit.web.admin;

import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.NodeService;
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
@WebServlet("/admin/newnode")
public class AdminNewNodeServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forWard("admin/newnode.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodename = req.getParameter("nodename");
        if(StringUtils.isNotEmpty(nodename)){
            try{
                new NodeService().saveNewNode(nodename);
                resp.sendRedirect("/admin/node");
            } catch (ServiceException ex){
                resp.sendError(404,ex.getMessage());
            }
        } else {
            resp.sendError(404,"请输新节点");
        }


    }
}
