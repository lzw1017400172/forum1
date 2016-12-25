package com.kaishengit.entity;

import java.sql.Timestamp;

/**
 * Created by 刘忠伟 on 2016/12/25.
 */
public class Thank {

    private Integer userid;
    private Integer Topicid;
    private Timestamp createtime;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getTopicid() {
        return Topicid;
    }

    public void setTopicid(Integer topicid) {
        Topicid = topicid;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }
}
