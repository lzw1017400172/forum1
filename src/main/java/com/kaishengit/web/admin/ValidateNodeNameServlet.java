package com.kaishengit.web.admin;

import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.NodeService;
import com.kaishengit.utils.StringUtils;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 刘忠伟 on 2016/12/28.
 */
@WebServlet("/admin/validateNodeName")
public class ValidateNodeNameServlet extends BaseServlet {

    private NodeService nodeService = new NodeService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //对于修改来说，远程验证nodename需要nodeid判断新老nodename相同可以通过，但是添加只判断存在就行，所以只传nodename
        String nodename = req.getParameter("nodename");
        String nodeid = req.getParameter("nodeid");

        //之前的表单提交都用post，可以用字符集过滤器，转成中文UTF-8，但是对get不能用，只能使用转换字符串
        nodename = StringUtils.isoToUtf8(nodename);

        String str = "";
        //区别修改nodename还是添加nodename
        if(StringUtils.isNotEmpty(nodeid)){
            //是修改
            if(StringUtils.isNumeric(nodeid) && StringUtils.isNotEmpty(nodename)){
                try {
                    boolean rw = nodeService.validateNode(Integer.valueOf(nodeid), nodename);
                    if(rw){
                        str = "true";
                    } else {
                        str = "false";
                    }
                } catch(ServiceException ex){
                    resp.sendError(404,ex.getMessage());
                }
            } else {
                resp.sendError(404,"参数错误");
            }
        } else {
            //是添加
            if(StringUtils.isNotEmpty(nodename)){
                str = nodeService.validateNode(nodename)?"true":"false";

            } else {
                resp.sendError(404,"参数错误");
            }

        }

        outPrintText(str,resp);

    }
}
