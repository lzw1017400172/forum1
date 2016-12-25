package com.kaishengit.dao;

import com.kaishengit.utils.DbHelp;

/**
 * Created by 刘忠伟 on 2016/12/25.
 */
public class ThankDao {
    /**
     * 添加一条感谢关系
     * @param userid
     * @param topicid
     */
    public void save(Integer userid, Integer topicid) {
        String sql = "insert into t_thankyou (userid,topicid) values (?,?)";
        DbHelp.update(sql,userid,topicid);
    }

    /**
     * 删除一条感谢关系
     * @param userid
     * @param topicid
     */
    public void delete(Integer userid, Integer topicid) {
        String sql = "delete from t_thankyou where userid =? and topicid =? ";
        DbHelp.update(sql,userid,topicid);
    }
}
