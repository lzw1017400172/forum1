package com.kaishengit.dao;

import com.kaishengit.entity.Topic;
import com.kaishengit.utils.DbHelp;

/**
 * Created by 刘忠伟 on 2016/12/20.
 */
public class TopicDao {
    public void save(Topic topic) {
        String sql = "insert into topic (title,content,nodeid,userid) values (?,?,?,?)";
        DbHelp.update(sql,topic.getTitle(),topic.getContent(),topic.getNodeid(),topic.getUserid());
    }
}
