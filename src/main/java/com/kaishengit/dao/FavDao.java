package com.kaishengit.dao;

import com.kaishengit.entity.Fav;
import com.kaishengit.utils.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by 刘忠伟 on 2016/12/24.
 */
public class FavDao {

    /**
     * 查看登录对象userid和topicid有对应关系吗
     * @param topicid
     * @param userid
     * @return
     */
    public Fav findFavByUserIdAndTopicId(Integer topicid,Integer userid){
        String sql = "select userid,topicid,createtime from t_fav where topicid = ? and userid = ?";
        return DbHelp.query(sql,new BeanHandler<Fav>(Fav.class),topicid,userid);
    }

    /**
     * 添加收藏
     * @param userid
     * @param topicid
     */
    public void saveFav(Integer userid, Integer topicid) {
        String sql = "insert into t_fav (userid,topicid) values (?,?)";
        DbHelp.update(sql,userid,topicid);
    }

    /**
     * 删除收藏
     * @param userid
     * @param topicid
     */
    public void deleteFav(Integer userid, Integer topicid) {
        String sql = "delete from t_fav where userid = ? and topicid = ?";
        DbHelp.update(sql,userid,topicid);
    }
}
