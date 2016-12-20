package com.kaishengit.service;

import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;

import java.util.List;

/**
 * Created by 刘忠伟 on 2016/12/20.
 * 帖子的业务处理层
 */
public class TopicService {

    NodeDao nodeDao = new NodeDao();
    TopicDao topicDao = new TopicDao();
    /**
     * 查找所有节点
     */
    public List<Node> findAllNodeService(){
        return nodeDao.findAllNode();
    }

    public void saveTopic(String title, String content, Integer nodeid, Integer userid) {
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setNodeid(nodeid);
        topic.setUserid(userid);
        topicDao.save(topic);

    }
}
