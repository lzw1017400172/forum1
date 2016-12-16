package com.kaishengit.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by 刘忠伟 on 2016/12/15.
 * 登录记录表的实体类
 * 实现于序列化接口，为了加入缓存时，。此对象是一个可序列化
 */
public class LoginLog implements Serializable{

    private Integer id;
    private Timestamp logintime;   //登录时间
    private String ip;          //登录地址ip
    private Integer userid;     //外键，和user表的联系是，多对1（多个登录记录对应一个账户），外键建在多的表

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getLogintime() {
        return logintime;
    }

    public void setLogintime(Timestamp logintime) {
        this.logintime = logintime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
