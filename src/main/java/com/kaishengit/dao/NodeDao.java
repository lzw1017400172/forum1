package com.kaishengit.dao;

import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.utils.DbHelp;
import com.kaishengit.utils.Page;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;

/**
 * Created by 刘忠伟 on 2016/12/20.
 */
public class NodeDao {

    public List<Node> findAllNode() {

        String sql = "select id,nodename,topicnum from t_node";
        return DbHelp.query(sql,new BeanListHandler<Node>(Node.class));//是查询所有，并封装成bean对象，返回集合
    }

    public Node findNodeById(Integer nodeid) {
        String sql ="select id,nodename,topicnum from t_node where id = ?";
        return DbHelp.query(sql,new BeanHandler<Node>(Node.class),nodeid);
    }

    public void updateNode(Node node) {
        String sql = "update t_node set nodename = ?,topicnum = ? where id = ?";
        DbHelp.update(sql,node.getNodename(),node.getTopicnum(),node.getId());
    }

    /**
     * 根据nodename查找
     * @param nodename
     */
    public Node findNodeByNodeName(String nodename) {
        String sql = "select * from t_node where nodename = ?";
        return DbHelp.query(sql,new BeanHandler<Node>(Node.class),nodename);
    }

    /**
     * 删除节点根据id
     * @param nodeid
     */
    public void deleteNodeById(Integer nodeid) {
        String sql = "delete from t_node where id = ?";
        DbHelp.update(sql,nodeid);
    }

    /**
     * 添加新节点
     * @param nodename
     */
    public void saveNode(String nodename) {
        String sql = "insert into t_node (nodename) values (?)";
        DbHelp.update(sql,nodename);
    }

    /**
     * 查询node总数
     * @return
     */
    public int findNodeCount() {
        String sql = "select count(*) from t_node";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    /**
     * 分页查询节点
     * @param page
     */
    public List<Node> findNodeByPage(Page page) {
        String sql = "select id,nodename,topicnum from t_node limit ?,?";
        return DbHelp.query(sql,new BeanListHandler<Node>(Node.class),page.getStart(),page.getPageSize());
    }
}
