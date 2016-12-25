package com.kaishengit.entity;

import org.joda.time.DateTime;

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

    //jsp会需要很多值，所以把user和node封装到topic对象，可以传一个对象就把所有制传过去，user和node对象，是其两个外键对应的，所以每个topic都会有这两个
    //通过EL表达式链式获取
    private User user;
    private Node node;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

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


    //这里封装一个方法，以is或者get开头。判断是否可以编辑，这样服务端和客户端都可以使用。EL表达式默认调用is或get开头
    public boolean isEdit(){
        DateTime dateTime = new DateTime(getCreatetime());//调用本类中的方法，返回一个创建时间
        if(dateTime.plusMinutes(50).isAfterNow() && replynum == 0){//创建时间的50分钟后在现在时间后面，就是不超过50分钟
            return true;
        } else {
            return false;
        }

    }


}
