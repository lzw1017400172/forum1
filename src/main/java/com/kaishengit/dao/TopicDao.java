package com.kaishengit.dao;

import com.kaishengit.entity.Topic;
import com.kaishengit.utils.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * Created by 刘忠伟 on 2016/12/20.
 */
public class TopicDao {
    public Integer save(Topic topic) {
        String sql = "insert into topic (title,content,nodeid,userid) values (?,?,?,?)";
        return DbHelp.insert(sql,topic.getTitle(),topic.getContent(),topic.getNodeid(),topic.getUserid());
    }

    public Topic findTopicId(Integer topicid) {
        String sql = "select * from topic where id = ?";
        return DbHelp.query(sql,new BeanHandler<Topic>(Topic.class),topicid);
    }
}
