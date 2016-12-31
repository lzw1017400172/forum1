package com.kaishengit.dao;

import com.kaishengit.entity.Notify;
import com.kaishengit.utils.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;

/**
 * Created by 刘忠伟 on 2016/12/26.
 */
public class NotifyDao {

    /**
     * 根据用户id去查找所有通知，并且按照未读在上，和创建时间
     * @param userid
     * @return
     */
    public List findNotifyByUserId(Integer userid){
        String sql = "select * from t_notify where userid = ? order by state asc,readtime desc,createtime desc";/*先按照state排先按照readtime排，然后readtime一样的，在按照createtime排,*/
        return DbHelp.query(sql,new BeanListHandler<Notify>(Notify.class),userid);
    }

    /**
     * 添加Notify，发送通知，回复时发送通知
     * @param notify
     */
    public void saveNotify(Notify notify){
        String sql = "insert into t_notify (userid,content,state,readtime) values (?,?,?,?)";
        DbHelp.update(sql,notify.getUserid(),notify.getContent(),notify.getState(),notify.getReadtime());
    }

    /**
     * 修改通知，阅读完毕之后修改状态，和添加阅读时间
     * @param notify
     */
    public void updateNotify(Notify notify){
        String sql = "update t_notify set userid = ?,content = ?,state = ?,readtime = ? where id = ?";
        DbHelp.update(sql,notify.getUserid(),notify.getContent(),notify.getState(),notify.getReadtime(),notify.getId());
    }

    /**
     * 查询未读通知的个数，当前用户的，此通知表装的是所有的通知
     * @param userid
     * @return
     */
    public List<Notify> findUnreadNotify(Integer userid,Integer state) {
        String sql = "select * from t_notify where userid = ? and state = ?";
        return DbHelp.query(sql,new BeanListHandler<Notify>(Notify.class),userid,state);//查询单行单个用Ser
    }

    /**
     * 找到对应通知根据id
     * @param id
     */
    public Notify findNotifyById(Integer id) {
        String sql = "select * from t_notify where id = ?";
        return DbHelp.query(sql,new BeanHandler<Notify>(Notify.class),id);
    }
}
