package com.kaishengit.dao;

import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.utils.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by 刘忠伟 on 2016/12/20.
 */
public class NodeDao {

    public List<Node> findAllNode() {

        String sql = "select id,nodename,topicnum from node";
        return DbHelp.query(sql,new BeanListHandler<Node>(Node.class));//是查询所有，并封装成bean对象，返回集合
    }

    public Node findNodeById(Integer nodeid) {
        String sql ="select id,nodename,topicnum from node where id = ?";
        return DbHelp.query(sql,new BeanHandler<Node>(Node.class),nodeid);
    }
}
