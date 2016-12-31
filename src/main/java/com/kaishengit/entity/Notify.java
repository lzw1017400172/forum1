package com.kaishengit.entity;

import java.sql.Timestamp;

/**
 * Created by 刘忠伟 on 2016/12/26.
 * 通知表
 */
public class Notify {

    private Integer id;
    private Integer userid;//根据用户id查找对应的所有通知
    private String content;
    private Timestamp createtime; //消息创建也就是通知的时间
    private Integer state;
    private Timestamp readtime; //通知被阅读的时间

    //默认状态未读为0，已读为1
    public static final Integer NOTIFY_UNREAD = 0;
    public static final Integer NOTIFY_READ = 1;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Timestamp getReadtime() {
        return readtime;
    }

    public void setReadtime(Timestamp readtime) {
        this.readtime = readtime;
    }
}
