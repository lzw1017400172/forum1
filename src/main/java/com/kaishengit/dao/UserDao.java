package com.kaishengit.dao;

import com.kaishengit.entity.User;
import com.kaishengit.utils.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by 刘忠伟 on 2016/12/15.
 */
public class UserDao {

    /**
     * 修改登录记录数据库
     */
    public void UserUpdate(User user){
        String sql = "update t_user set username = ?,password = ?, email = ?,phone = ?,status = ?,createtime = ?,avatar = ? where id = ?";
        DbHelp.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone(),user.getStatus(),user.getCreatetime(),user.getAvatar(),user.getId());
    }

    /**
     * 根据id删除
     * @param id
     */
    public void UserDelete(Integer id){
        String sql = "delete from t_user where id = ?";
        DbHelp.update(sql,id);
    }

    /**
     * 添加
     * status有默认值，可以不用添加
     * @param user
     */
    public void UserSave(User user){
        String sql = "insert into t_user (username,password,email,phone,status,avatar) values (?,?,?,?,?,?);";
        DbHelp.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone(),user.getStatus(),user.getAvatar());
    }

    public User findByUserName(String username) {
        String sql = "select id,username,password,email,phone,status,createtime,avatar from t_user where username = ?";
        return DbHelp.query(sql,new BeanHandler<User>(User.class),username);
    }
    public User findByEmail(String email){
        String sql = "select id,username,password,email,phone,status,createtime,avatar from t_user where email = ?";
        return DbHelp.query(sql,new BeanHandler<User>(User.class),email);
    }
    public User findById_User(Integer id){
        String sql = "select id,username,password,email,phone,status,createtime,avatar from t_user where id = ?";
        return DbHelp.query(sql,new BeanHandler<User>(User.class),id);
    }

}
