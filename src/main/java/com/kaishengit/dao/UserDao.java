package com.kaishengit.dao;

import com.kaishengit.entity.LoginLog;
import com.kaishengit.entity.User;
import com.kaishengit.utils.DbHelp;
import com.kaishengit.utils.Page;
import com.kaishengit.vo.UserVo;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    /**
     * 查所有用户数量
     * @return
     */
    public int findUserCount() {
        String sql = "select count(*) from t_user";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    /**
     * 管理员系统的--用户管理。需要分页加两表联查。需要 createtime,最后一次logintime,最后一次登录lastip
     * @param page
     * @return
     */
    public List<UserVo> findUserAndLoginLog(Page page) {//先把两表联合查询的结果分组，然后用聚合函数按照其中一列取最大值
        String sql = "select tu.*,max(tll.logintime) as logintime from t_user tu,t_login_log tll where tu.id = tll.userid  group by tu.id limit ?,?";
        return DbHelp.query(sql, new BeanListHandler<UserVo>(UserVo.class), page.getStart(), page.getPageSize());
                    /*因为UserVo封装了需要查询结果的全部，此方法通过列名找方法时，把需要的都添加给了。*/
    }


}
