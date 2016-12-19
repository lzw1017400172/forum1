package com.kaishengit.web.user;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.utils.Config;
import com.kaishengit.web.BaseServlet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 刘忠伟 on 2016/12/19.
 */
@WebServlet("/setting")
public class SettingServlet extends BaseServlet {

    Logger logger = LoggerFactory.getLogger(SettingServlet.class);
    private UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //当来到这个页面就给凭证，获取凭证token
        String ak = Config.getConfig("qiniu.ak");
        String sk = Config.getConfig("qiniu.sk");
        String bucketname =Config.getConfig("qiniu.bucketname");//上传到七牛的空间名称
        Auth auth = Auth.create(ak,sk);
        String token = auth.uploadToken(bucketname);
        req.setAttribute("token",token);
        forWard("user/setting.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if("password".equals(action)){ //把action写后面，就算为null也不会报错，不执行而已
            updatePassword(req,resp);
        } else if("email".equals(action)){
            updateProfile(req,resp);
        } else if("avatar".equals(action)){
            uploader(req,resp);
        }
    }

    /**
     * 修改邮件
     * @param req
     * @param resp
     */
    private void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        if(email != null){
            //需要知到email要存给谁，登录的对象在session里面
            //获取session
            User user = getCurrenUser(req);
            userService.updateEmail(email,user);
        }
        //给客户端的jsp返回的值，这汇总json形式，可以包装成一个对象把对象返回，原来是装到一个map集合里面，现在封装懂啊一个自定义的对象里面JsonResult
        JsonResult jsonResult = new JsonResult();
        //只需要返回给客户端修改成功
        jsonResult.setState("success");
        outPrintJson(jsonResult,resp);

    }

    //由于一个setting.jsp有三个form需要提交到post，，写一个里面肯定行不通，写成三个方法，根据url传参不同，来区别调用那个方法
    /**
     * 修改密码
     */
    private void updatePassword(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String oldpassword = req.getParameter("oldpassword");
        String newpassword = req.getParameter("newpassword");

        //需要验证密码是否正确，和session里面的密码对比一下就好
        User user = getCurrenUser(req);
        JsonResult jsonResult;
        try {
            userService.updatePassword(oldpassword, newpassword, user);
            jsonResult = new JsonResult(JsonResult.SUCCESS,null,"修改成功");
        } catch (ServiceException ex){
            jsonResult = new JsonResult(JsonResult.ERROR,ex.getMessage(),null);
        }
        outPrintJson(jsonResult,resp);
    }

    /**
     * 上传头像
     * @param req
     * @param resp
     */
    public void uploader(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String filekey = req.getParameter("filekey");
        User user = getCurrenUser(req);

        userService.uploaderAvatar(filekey,user);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState("success");
        outPrintJson(jsonResult,resp);
    }


}
