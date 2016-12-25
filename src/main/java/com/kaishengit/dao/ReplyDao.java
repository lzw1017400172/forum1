package com.kaishengit.dao;

import com.kaishengit.entity.Reply;
import com.kaishengit.entity.User;
import com.kaishengit.utils.Config;
import com.kaishengit.utils.DbHelp;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by 刘忠伟 on 2016/12/21.
 */
public class ReplyDao {
    /**
     * 添加回复
     */
    public void saveReply(Reply reply) {
        String sql = "insert into t_reply (content,userid,topicid) values (?,?,?)";
        DbHelp.update(sql,reply.getContent(),reply.getUserid(),reply.getTopicid());
    }

    /**
     * 需要user和reply两个表的值，并且大量回复，所以多表联查，
     * @param topicid
     * @return
     */
    public List<Reply> findReplyTopicId(Integer topicid) {
        String sql ="select tu.id,tu.username,tu.avatar,tr.* from t_reply tr,t_user tu where tr.userid=tu.id and tr.topicid = ? ";
        //这个是两个表联查，返回值类型
        //以前全是单表查询，new BeanListHandler<Reply>(Reply.class),写返回后封装成bean的类型就好，现在是两种类型，所以需要把user封装给reply，让bean为reply返回值就是reply
        return DbHelp.query(sql, new AbstractListHandler<Reply>() {
            @Override//把所有结果封装给reply，所以给reply增加属性
            protected Reply handleRow(ResultSet resultSet) throws SQLException {
                //这个自动封装
                Reply reply = new BasicRowProcessor().toBean(resultSet,Reply.class);//返回值类型
                //手动把结果封装给reply
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setAvatar(Config.getConfig("qiniu.domain") + resultSet.getString("avatar"));//封装头像时把前面域名也封装
                reply.setUser(user);

                return reply;
            }
        },topicid);

    }
}
