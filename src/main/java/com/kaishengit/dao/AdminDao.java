package com.kaishengit.dao;

import com.kaishengit.entity.Admin;
import com.kaishengit.utils.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by 刘忠伟 on 2016/12/28.
 */
public class AdminDao {


    public Admin findAdminByUserName(String username){
        String sql = "select id,username,password,email,phone,createtime from t_admin where username = ?";
        return DbHelp.query(sql,new BeanHandler<Admin>(Admin.class),username);
    }


}
