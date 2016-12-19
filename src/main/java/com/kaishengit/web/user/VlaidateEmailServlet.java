package com.kaishengit.web.user;

import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 刘忠伟 on 2016/12/16.
 * 验证邮箱好似否存在
 */
@WebServlet("/validateemail")
public class VlaidateEmailServlet extends BaseServlet {

    private UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String type = req.getParameter("type");//不需要判断的type==null，需要判断的type=1
        //不仅注册账户时要验证邮箱是否已经存在，在修改账户的时候也要验证账户是否存在
        //修改邮箱时，会出现我填写原邮箱也会提示邮箱已经存在,所以需要区分当用户写自己邮箱时，可以通过验证返回true
        //当使用session区分是不是当时登录的邮箱时，这样又带来个问题，处于登录状态去注册帐号，邮箱添自己的，因为session里面和这个一致，就返回true通过验证，。解决：给需要验证邮箱和当前一致与否的remote添加参数，使这个参数时session验证，不是就不验证

        //所以就让需要判断和登录帐号是否一致的去进行这种验证，不需要的就不进行验证，remote时多传个参数就可以区分
        //判断type是否需要和当前找那个户进行对比
        if("1".equals(type)){
            User currenuser = getCurrenUser(req);//获取登录后的对象,当获取补刀user就不能强转需要判断
            if(currenuser != null) {
                if ((currenuser.getEmail()).equals(email)){//相同时返回true
                    outPrintText("true",resp);
                    //到这里程序就该结束了
                    return;
                }
            }
        }

        User user = userService.validateEmail(email);
        if(user == null){
            outPrintText("true",resp);
        } else {
            outPrintText("false",resp);
        }
    }
}
