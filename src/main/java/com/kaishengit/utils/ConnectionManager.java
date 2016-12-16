package com.kaishengit.utils;

import com.kaishengit.exception.DataAccessException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接池
 */
public class ConnectionManager {

    private static Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    private static String DRIVER = Config.getConfig("jdbc.driver");//= "com.mysql.jdbc.Driver";
    private static String URL = Config.getConfig("jdbc.url");//= "jdbc:mysql:///lib_22";
    private static String USERNAME = Config.getConfig("jdbc.username");//= "root";
    private static String PASSWORD = Config.getConfig("jdbc.password");//= "rootroot";
    private static BasicDataSource dataSource = new BasicDataSource();

    static {//数据库也只是配置一次
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(20);
        dataSource.setMaxWaitMillis(5000);
    }


    /**
     * 获取数据库连接池
     * @return
     */
    public static DataSource getDataSource() {
        return dataSource;
    }


    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("获取数据库连接异常");
            throw new DataAccessException("获取数据库连接异常",e);
        }
        return connection;
    }

}
