package com.kaishengit.web.admin;

import com.kaishengit.entity.Node;
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
@WebServlet("/admin/updateNodeName")
public class AdminUpdateNodeNameServlet extends BaseServlet {
    private NodeService nodeService = new NodeService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //需要知道修改那个节点，所以获取nodeid。并且查找到node返回给update.jsp
        String nodeid = req.getParameter("nodeid");
        if(StringUtils.isNumeric(nodeid)){
            try {
                Node node = nodeService.findNodeByNodeId(Integer.valueOf(nodeid));
                req.setAttribute("node",node);
                forWard("/admin/updateNode.jsp",req,resp);//切记forWard重定向跳转一定要写在404上面不然会报错，二次跳转
            } catch (ServiceException ex){
                resp.sendError(404,ex.getMessage());
            }
        } else {
            resp.sendError(404,"参数错误");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid = req.getParameter("nodeid");
        String nodename = req.getParameter("nodename");

        if(StringUtils.isNumeric(nodeid) && StringUtils.isNotEmpty(nodename)){
            try {
                nodeService.updateNodeName(Integer.valueOf(nodeid), nodename);
                resp.sendRedirect("/admin/node");//跳转一定要写在404前面。要不重复跳转。。sendError也是跳转。
            } catch (ServiceException ex){
                resp.sendError(404,ex.getMessage());
            }
        } else {
            resp.sendError(404,"参数错误");
        }

    }
}
