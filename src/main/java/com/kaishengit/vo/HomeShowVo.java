package com.kaishengit.vo;

import java.sql.Timestamp;

/**
 * Created by 刘忠伟 on 2016/12/30.
 */
public class HomeShowVo {

    private String topictime;
    private Integer topicnum;
    private Integer replynum;


    public String getTopictime() {
        return topictime;
    }

    public void setTopictime(String topictime) {
        this.topictime = topictime;
    }

    public Integer getTopicnum() {
        return topicnum;
    }

    public void setTopicnum(Integer topicnum) {
        this.topicnum = topicnum;
    }

    public Integer getReplynum() {
        return replynum;
    }

    public void setReplynum(Integer replynum) {
        this.replynum = replynum;
    }
}
