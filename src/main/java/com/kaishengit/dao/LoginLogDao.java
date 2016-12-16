package com.kaishengit.dao;

import com.kaishengit.entity.LoginLog;
import com.kaishengit.utils.DbHelp;

/**
 * Created by 刘忠伟 on 2016/12/15.
 */
public class LoginLogDao {

    /**
     * 修改登录记录数据库
     */
    public void LoginLogUpdate(LoginLog loginLog){
        String sql = "update t_login_log set logintime = ?,ip = ?, userid = ? where id = ?";
        DbHelp.update(sql,loginLog.getLogintime(),loginLog.getIp(),loginLog.getUserid(),loginLog.getId());
    }

    /**
     * 根据id删除
     * @param id 登录记录id
     */
    public void LoginLogDelete(Integer id){
        String sql = "delete from t_login_log where id = ?";
        DbHelp.update(sql,id);
    }

    /**
     * 添加
     * @param loginLog
     */
    public void LoginLogSave(LoginLog loginLog){
        String sql = "insert into t_login_log (logintime,ip,userid) values (?,?,?);";
        DbHelp.update(sql,loginLog.getLogintime(),loginLog.getId(),loginLog.getUserid());
    }

    /**
     * 根据帐户名查找
     * @param username
     */

/*    public void LoginLogFindById(String username){
        String sql = "select id,logintime,ip,userid from forum where";
    }*/
}
