package com.kaishengit.vo;

import java.sql.Timestamp;

/**
 * Created by 刘忠伟 on 2016/12/29.
 * vo层专门封装查询结果,属性名字必须与列名相同，因为回来此类根据列名找方法
 */
public class UserVo {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String username;
    private Timestamp createtime;
    private Timestamp logintime;
    private Integer status;
    private String lastip;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Timestamp getLogintime() {
        return logintime;
    }

    public void setLogintime(Timestamp logintime) {
        this.logintime = logintime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLastip() {
        return lastip;
    }

    public void setLastip(String lastip) {
        this.lastip = lastip;
    }
}
