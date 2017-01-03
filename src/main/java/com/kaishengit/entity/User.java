package com.kaishengit.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by 刘忠伟 on 2016/12/15.
 * 实现于可序列化类，为了加入缓存时使用
 */
public class User implements Serializable{

    //设置默认头像七牛图片名字和静态常量
    // 默认状态0，
    //激活1
    //禁用2
    public static final Integer DEFAULT_STATUS = 0;
    public static final Integer ACTIVATE_STATUS = 1;
    public static final Integer DISABLED_STATUS = 2;
    public static final String DEFAULT_AVATAR = "yadan";

    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Integer status;
    private Timestamp createtime; //创建账户的时间
    private String avatar;   //头像，记录的是上传到犀牛的图片七牛返回来的key(文件名七牛内部)值，经过base64加密
    private String lastip;

    public String getLastip() {
        return lastip;
    }

    public void setLastip(String lastip) {
        this.lastip = lastip;
    }

    private LoginLog loginLog;

    public LoginLog getLoginLog() {
        return loginLog;
    }

    public void setLoginLog(LoginLog loginLog) {
        this.loginLog = loginLog;
    }


    //user表和loginlog表联查的属性
   /* private String logintime;
    private String ip;

    public String getLogintime() {
        return logintime;
    }

    public void setLogintime(String logintime) {
        this.logintime = logintime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }*/




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
