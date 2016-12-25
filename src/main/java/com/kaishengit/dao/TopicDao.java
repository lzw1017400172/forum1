package com.kaishengit.dao;

import com.kaishengit.entity.Thank;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.utils.Config;
import com.kaishengit.utils.DbHelp;
import com.kaishengit.utils.Page;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by 刘忠伟 on 2016/12/20.
 */
public class TopicDao {
    public Integer save(Topic topic) {
        String sql = "insert into t_topic (title,content,nodeid,userid,lastreplytime) values (?,?,?,?,?)";
        return DbHelp.insert(sql,topic.getTitle(),topic.getContent(),topic.getNodeid(),topic.getUserid(),topic.getLastreplytime());
    }

    public Topic findTopicId(Integer topicid) {
        String sql = "select * from t_topic where id = ?";
        return DbHelp.query(sql,new BeanHandler<Topic>(Topic.class),topicid);
    }

    public void updateTopic(Topic topic) {
        String sql = "update t_topic set title = ?,content = ?,clicknum=?,favnum=?,thankyounum=?,replynum=?,lastreplytime=?,nodeid=?,userid=? where id=?";
        DbHelp.update(sql,topic.getTitle(),topic.getContent(),topic.getClicknum(),topic.getFavnum(),topic.getThankyounum(),topic.getReplynum(),topic.getLastreplytime(),topic.getNodeid(),topic.getUserid(),topic.getId());
    }

    /**
     * 查询全部帖子，按照随后回复时间排序，并且需要分页,是多表联查,多表联查需要重写自动封装
     * @return
     */
    public List<Topic> findTopicAndUserDao(Page page) {
        String sql = "select tt.*,tu.username,tu.avatar from t_topic tt,t_user tu where tt.userid = tu.id order by lastreplytime desc limit ?,? ";
        return DbHelp.query(sql, new AbstractListHandler<Topic>() {
            @Override
            protected Topic handleRow(ResultSet resultSet) throws SQLException {
                Topic topic = new BasicRowProcessor().toBean(resultSet,Topic.class);//这句话写上就代表返回Topic集合，这段代表自动封装给Topic
                //再封装User
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setAvatar(Config.getConfig("qiniu.domain") + resultSet.getString("avatar"));
                topic.setUser(user);
                return topic;
            }
        },page.getStart(),page.getPageSize());
    }

    /**
     * 查询多少个帖子
     */
    public int findTopicCount() {
        String sql = "select count(*) from t_topic";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    /**
     * 查询指定节点的帖子数量
     * @param nodeid
     * @return
     */
    public int findTopicCountByNodeId(Integer nodeid) {
        String sql = "select count(*) from t_topic where nodeid = ?";
        return DbHelp.query(sql,new ScalarHandler<Long>(),nodeid).intValue();
    }

    /**
     * 根据指定节点查询帖子，多表联查，分页查询，按照lastreplytime排序
     * @param nodeid 指定节点
     * @param page 分页对象
     */
    public List<Topic> findTopicAndUserByNodeIdDao(Integer nodeid, Page page) {
        String sql = "select tt.*,tu.avatar,tu.username from t_topic tt,t_user tu where tt.userid = tu.id and tt.nodeid = ? order by lastreplytime desc limit ?,?";
        return DbHelp.query(sql, new AbstractListHandler<Topic>() {//new BeanListHandler;只把查询结果封装一个对象的，avatar和username就没办法封装给topic，所以重写匿名局部内部类
            @Override
            protected Topic handleRow(ResultSet resultSet) throws SQLException {
                Topic topic = new BasicRowProcessor().toBean(resultSet,Topic.class);
                User user = new User();
                user.setAvatar(Config.getConfig("qiniu.domain") + resultSet.getString("avatar"));
                user.setUsername(resultSet.getString("username"));
                topic.setUser(user);
                return topic;
            }

        },nodeid,page.getStart(),page.getPageSize());
    }

    /**
     *  查询关系表t_thankyou
     * @param userid
     * @param topicid
     * @return
     */
    public Thank finThankByUsericIdAndTopicId(Integer userid, Integer topicid) {
        String sql = "select * from t_thankyou where userid = ? and topicid = ?";
        return DbHelp.query(sql,new BeanHandler<Thank>(Thank.class),userid,topicid);
    }
}
