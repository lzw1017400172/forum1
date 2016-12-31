package com.kaishengit.service;

import com.kaishengit.dao.AdminDao;
import com.kaishengit.entity.Admin;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.utils.Config;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 刘忠伟 on 2016/12/28.
 */
public class AdminService {

    private AdminDao adminDao = new AdminDao();

    //管理层slf4j写代码，底层logback执行输出日志，中间适配器层练级sel4j和logback
    private Logger logger = LoggerFactory.getLogger(AdminService.class);
    /**
     * 判断管理员登录
     * @param username
     * @param password
     */
    public Admin adminLogin(String username, String password,String ip) {

        Admin admin = adminDao.findAdminByUserName(username);
        //密码加盐md5加密，盐在前
        if(admin != null && DigestUtils.md5Hex(Config.getConfig("user.password.salt") + password).equals(admin.getPassword())){
            //登录成功，记录管理员日志，和ip
            logger.info("管理员{}登录系统，ip地址:{}",username,ip);
            return admin;
        } else {
            throw new ServiceException("帐号或者密码错误！");
        }


    }
}
