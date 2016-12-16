package com.kaishengit.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by 刘忠伟 on 2016/12/15.
 * 解决硬编码问题，读config.propertise文件，只能读一次，但是要写很多东西，一次获取完，独立出来
 */
public class Config {

    private static Logger logger = LoggerFactory.getLogger(Config.class);
    private static Properties properties = new Properties();
    //静态代码块。只读一次
    static{
        //先读文件,只要读取一次，就会获取所有值，可以随便获取多次
        try {
            properties.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            logger.error("读取config文件异常");
            throw new RuntimeException("读取config文件异常",e);//抛出运行时异常是为了让日志显示异常，让人们知道哪里错了
        }

    }

    /**
     * 可以随便获取值，只需读一次就获取全部值了
     * @param key
     * @return
     */
    public static String getConfig(String key){
        return properties.getProperty(key);
    }
}
