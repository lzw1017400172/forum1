package com.kaishengit.entity;

import java.sql.Timestamp;

/**
 * Created by 刘忠伟 on 2016/12/20.
 */
public class Topic {

    private Integer id;
    private String title;
    private String content;
    private Timestamp createtime;//帖子的创建时间
    private Integer clicknum;
    private Integer favnum;
    private Integer thankyounum;
    private Integer replynum;
    private Timestamp lastreplytime;//最后一次回帖时间
    private Integer userid;//外键对应用户
    private Integer nodeid;//外键对应发帖节点

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getClicknum() {
        return clicknum;
    }

    public void setClicknum(Integer clicknum) {
        this.clicknum = clicknum;
    }

    public Integer getFavnum() {
        return favnum;
    }

    public void setFavnum(Integer favnum) {
        this.favnum = favnum;
    }

    public Integer getThankyounum() {
        return thankyounum;
    }

    public void setThankyounum(Integer thankyounum) {
        this.thankyounum = thankyounum;
    }

    public Integer getReplynum() {
        return replynum;
    }

    public void setReplynum(Integer replynum) {
        this.replynum = replynum;
    }

    public Timestamp getLastreplytime() {
        return lastreplytime;
    }

    public void setLastreplytime(Timestamp lastreplytime) {
        this.lastreplytime = lastreplytime;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getNodeid() {
        return nodeid;
    }

    public void setNodeid(Integer nodeid) {
        this.nodeid = nodeid;
    }
}
