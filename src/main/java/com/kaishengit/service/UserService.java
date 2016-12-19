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

    //修改密码缓存
    private static Cache<String,Object> passwordCache = CacheBuilder.newBuilder()
            .expireAfterAccess(30,TimeUnit.MINUTES)//缓存存活时间30分钟
            .build();

    //sessionID限制请求频率缓存
    private static Cache<String,Object> sessionIdCache = CacheBuilder.newBuilder()
            .expireAfterAccess(60,TimeUnit.SECONDS)  //60S发一次
            .build();
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

    /**
     * 找回密码
     * @param type 找回密码方式email|phone
     * @param value 邮箱地址或者手机号
     */
    public void foundPassWord(String sessionId,String type,String value) {
        //进来就判断sessionID过期没，时间不到不让发，下面代码就不需要了
        if (sessionIdCache.getIfPresent(sessionId) == null) {
            if (type.equals("email")) {
                //根据email查找对象
                User user = userDao.findByEmail(value);
                //系统之外的不发送，系统之内发送
                if (user == null) {
                    logger.error("email:{}不存在", value);
                    throw new ServiceException("邮箱" + value + "不存在");
                } else {
                    //可以发送邮件，无论是不是自己的邮件，因为是按照谁的邮件返回修改谁的账户，所以相对安全
                    Thread thred = new Thread(new Runnable() { //发邮件要新建线程，只管提示发送成功，让邮件慢慢去发
                        @Override//线程接口runnable必须重写run执行方法，写这个线程要办的事
                        public void run() {
                            String token = UUID.randomUUID().toString();
                            //创建缓存，存入token和username(根据email找到的username),说明要修改的是对应邮件账户的密码
                            passwordCache.put(token, user.getUsername());
                            //创建验证邮件，点击url验证,传值token，点击地址进入服务端，根据token获取用户名，用户名为邮箱对应的
                            String url = "http://www.liuzhongwei.com/foundpassword/newpassword?token=" + token;
                            String htmlMsg = user.getUsername() + "<br>请点击该<a href='" + url + "'>链接</a>进行找回密码操作，链接在30分钟内有效";
                            EmailUtils.sendHtmlEmail("密码找回邮件", htmlMsg, value);
                            //发完邮件把sessionID作为键传入缓存值不重要，只要通过sessionID获取到值就行
                            sessionIdCache.put(sessionId, "XXX");

                        }
                    });
                    thred.start();
                }
            } else if (type.equals("phone")) {
                //根据手机号吗找回密码，先不写
            }
        } else {
            //sessionID还在缓存中存在，就不能发
            logger.error("操作频率太快");
            throw new ServiceException("操作频率太快");
        }

    }

    /**
     * 根据token返回找回密码的用户，这个用户是和邮件对应的，给谁发邮件就修改谁的
     * @param token
     */
    public User resetPasswordGetUserByToken(String token){
        //去缓存中找token
            String username = (String)passwordCache.getIfPresent(token);
            if(username != null){
                User user = userDao.findByUserName(username);
                if(user != null){
                    return user;
                } else {
                    logger.error("未找到对应账户 ");
                    throw new ServiceException("未找到对应帐号");
                }
            } else {
                logger.error("token过期或者错误");
                throw new ServiceException("token过期或者错误");
            }

        }


    /**
     * 重置密码
     * @param id 要修改账户的id
     * @param token passwordcache缓存token，提交时是否过期
     * @param password 新密码
     */
    public void resetPassword(Integer id,String token,String password){

            //验证token首先
            if(passwordCache.getIfPresent(token) == null){
                logger.error("token过期或者错误！");
                throw new ServiceException("token过期或者错误！");
            } else {
                User user = userDao.findById_User(id);
                //修改密码前，要加密后存入数据库

                user.setPassword(DigestUtils.md5Hex(Config.getConfig("user.password.salt") + password));
                userDao.UserUpdate(user);
                //帐号修改完毕要删除token，清除缓存
                passwordCache.invalidate(token);
                //记录一下，修改密码
                logger.info("{}修改密码",user.getUsername());
            }
        }

    /**
     * 修改邮件
     * @param email
     * @param user
     */
    public void updateEmail(String email,User user) {
        user.setEmail(email);
        userDao.UserUpdate(user);
    }

    public void updatePassword(String oldpassword,String newpassword,User user){
        //密码加盐了
        oldpassword = DigestUtils.md5Hex(Config.getConfig("user.password.salt") + oldpassword);
        if(user.getPassword().equals(oldpassword)){
            newpassword = DigestUtils.md5Hex(Config.getConfig("user.password.salt") + newpassword);
            user.setPassword(newpassword);
            userDao.UserUpdate(user);
        } else {
            logger.error("用户{}原始密码错误",user.getUsername());
            throw new ServiceException("原始密码错误！");
        }

    }

    public void uploaderAvatar(String filekey,User user) {
        user.setAvatar(filekey);
        userDao.UserUpdate(user);
    }
}
