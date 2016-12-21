package com.kaishengit.service;

import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by 刘忠伟 on 2016/12/20.
 * 帖子的业务处理层
 */
public class TopicService {

    UserDao userDao = new UserDao();
    NodeDao nodeDao = new NodeDao();
    TopicDao topicDao = new TopicDao();
    Logger logger = LoggerFactory.getLogger(TopicService.class);
    /**
     * 查找所有节点
     */
    public List<Node> findAllNodeService(){
        return nodeDao.findAllNode();
    }

    public Integer saveTopic(String title, String content, Integer nodeid, Integer userid) {
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setNodeid(nodeid);
        topic.setUserid(userid);
        Integer topicid = topicDao.save(topic);
        return topicid;
    }

    /**
     * 通过topicid找到需要展示的主题对象，并且把user和node的值当属性给他
     * @param topicid
     */
    public Topic findByTopicId(String topicid) {
        //通过topicid找到topic对象，获取两个外键，在获取两个对象，封装给topic
        if(StringUtils.isNumeric(topicid)){//地址栏传值，会被改变，必须是数字，也可能找不到
            Topic topic = topicDao.findTopicId(Integer.valueOf(topicid));
            if(topic != null){
                User user = userDao.findById_User(topic.getUserid());
                Node node = nodeDao.findNodeById(topic.getNodeid());
                topic.setUser(user);
                topic.setNode(node);
                return topic;
            } else {
                logger.error("该帖子不存在或者已经被删除");
                throw new ServiceException("该帖子不存在或者已经被删除");
            }

        } else {
            logger.error("topicid不是数字");
            throw new ServiceException("topicid不是数字");
        }

    }
}
