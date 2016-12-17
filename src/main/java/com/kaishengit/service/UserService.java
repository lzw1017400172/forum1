package com.kaishengit.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kaishengit.dao.LoginLogDao;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.LoginLog;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.utils.Config;
import com.kaishengit.utils.EmailUtils;
import com.kaishengit.utils.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by 刘忠伟 on 2016/12/15.
 */
public class UserService {


    private Logger logger = LoggerFactory.getLogger(UserService.class);
    //激活邮件，当点击注册时，就创键子线程去发送邮件，不用排队，给邮件有效期设置6格小时，放在服务端缓存里面，存活时间6小时
    //创建缓存,发送token凭证到缓存
    private static Cache<String,String> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(6, TimeUnit.MINUTES)//6小时
            .build();//有就返回，没有才去执行匿名局部内部类，这个缓存框架是在获取值时写匿名局部内不累


    private UserDao userDao = new UserDao();

    /**
     * 验证用户名
     * @param username
     * @return
     */
    public boolean validateUserName(String username){
        String name = Config.getConfig("no.signup.usernames");//获取保留名字
        List<String> nameList = Arrays.asList(name.split(","));//逗号分割成数组
        if(nameList.contains(username)){//username.contains(username)在username集合中查找此元素，找到返回true
            return false;
        }
        User user = userDao.findByUserName(username);
        if (user == null) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 验证邮箱是否被占用，通过邮箱查找对象
     * @param email
     * @return
     */
        public User validateEmail(String email){
            return userDao.findByEmail(email);
        }


        public void saveUser(String username,String password,String email,String phone){
            User user = new User();
            user.setUsername(username);
            user.setPassword(DigestUtils.md5Hex(Config.getConfig("user.password.salt") + password));//密码必须加密MD5，加盐值
            user.setEmail(email);
            user.setPhone(phone);
            user.setStatus(User.DEFAULT_STATUS);//默认为0
            user.setAvatar(User.DEFAULT_AVATAR);//默认头型名称
            //注册时间不用设置
            userDao.UserSave(user);//添加数据也就是注册过程很慢，同时需要创建子线程，去发送邮件，要不然会很慢，添加完数据采取发送邮件
            //邮件的激活，
            //创建线程
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //给用户发送邮件，邮件的html为激活地址
                    String uuid = UUID.randomUUID().toString();//这是凭证随机值token
                    String url = "http://www.liuzhongwei.com/user/active?_="+uuid;//验证地址，传值token凭证，验证时，必须凭证对应用户username
                    //放入缓存，用来验证凭证是否对应username
                    cache.put(uuid,username);//验证时的凭证和用户名字username必须是缓存里面的对象，用uuid来获取username必须是和用户相同
                    String html = "<h3>Dear"+username+":</h3>请点击<a href='"+url+"'>该链接</a>去激活你的账号. <br> 凯盛软件";
                    //发送邮件
                    EmailUtils.sendHtmlEmail("帐号激活邮件",html,email);
                }
            });
            //启动 线程
            thread.start();
        }

    /**
     * 根据token激活账户
     * @param token
     */
    public void activeUser(String token){
            //通过token查询username
            String username = cache.getIfPresent(token);
            if(StringUtils.isEmpty(username)){
                logger.error("token无效或者已经过期");
                throw new ServiceException("token无效或者已经过期");
            } else {
                //判断此username是否在数据库存在
                User user = userDao.findByUserName(username);
                if(user == null){
                    logger.error("此用户不存在！");
                    throw new ServiceException("此用户不存在");
                } else {
                    //激活update修改，将状态0改为1
                    user.setStatus(User.ACTIVATE_STATUS);
                    userDao.UserUpdate(user);
                    //激活成功后删除缓存，只要走到这里就没有异常，就激活成功
                    cache.invalidate(token);
                }
            }

        }

    /**
     * 登录并且记录登录信息
     * @param username
     * @param password
     * @param ip
     * @return
     */
        public User login(String username,String password,String ip){
            User user = userDao.findByUserName(username);
            //password是经过加盐md5加密
            if(user != null && DigestUtils.md5Hex(Config.getConfig("user.password.salt") + password).equals(user.getPassword())){
                if(user.getStatus().equals(User.ACTIVATE_STATUS)){//根据状态判断是否激活或者禁用，

                    //登录成功需要记录登录信息，t_login_log表,    ip,userid,userid外键对应user表id列
                    LoginLog loginLog = new LoginLog();
                    loginLog.setIp(ip);
                    loginLog.setUserid(user.getId());

                    LoginLogDao loginLogDao = new LoginLogDao();
                    loginLogDao.LoginLogSave(loginLog);
                    logger.info("{}登录了系统，IP：{}",username,ip);
                    return user;
                } else if(user.getStatus().equals(User.DEFAULT_STATUS)){
                    throw new ServiceException("此帐号未激活");
                } else {
                    throw new ServiceException("此帐号已经被禁用");//这个错误信息，可以用ex.getMessage()获得
                }


            } else {
                throw new ServiceException("帐号或者密码错误");
            }

        }


}
