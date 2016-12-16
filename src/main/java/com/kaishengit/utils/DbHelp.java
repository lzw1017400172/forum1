package com.kaishengit.utils;

import com.kaishengit.exception.DataAccessException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.SQLException;

/**
 * DbHelp工具类
 */
public class DbHelp {

    //为了让代码的日志有可切换性，就用slf4j写在代码中，经过添加切换层和工作层maven依赖可以换日志，所有slf4j写在代码中
    private static Logger logger = LoggerFactory.getLogger(DbHelp.class);//获取此类的日志对象
    private static Connection getConnection() {
        return ConnectionManager.getConnection();
    }

    public static void update(String sql,Object... params) throws DataAccessException {

        try {
            QueryRunner queryRunner = new QueryRunner(ConnectionManager.getDataSource());
            queryRunner.update(sql, params);

            //System.out.println("SQL: " + sql);这些就不能写了，写日志
            logger.debug("SQL: {}",sql);//这里是字符串连加，效率低，在slf4j中用替换占位符{}
        } catch (SQLException ex) {
            //抛出异常，要输出日志，属于错误
            logger.error("执行{}异常",sql);
            throw new DataAccessException("执行"+ sql + "异常",ex);
        }
    }

    public static <T> T query(String sql,ResultSetHandler<T> handler,Object... params) throws DataAccessException {

        QueryRunner queryRunner = new QueryRunner(ConnectionManager.getDataSource());
        try {
            T t = queryRunner.query(sql,handler,params);

            //System.out.println("SQL: " + sql);
            logger.debug("SQL: " + sql);//log4j不支持替换占位符｛｝，slf4j支持
            return t;
        } catch (SQLException e) {
            logger.error("执行{}异常",sql);
            throw new DataAccessException("执行"+ sql + "异常",e);
        }
    }

    private static void close(Connection connection) {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("关闭Connection异常");
                throw new DataAccessException("关闭Connection异常",e);
            }
        }

    }

}
