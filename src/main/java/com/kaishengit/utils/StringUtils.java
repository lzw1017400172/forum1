package com.kaishengit.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created by 刘忠伟 on 2016/12/15.
 *  这个工具类继承于lang3StringUtils可以使用其方法，自己独有解决中文乱码
 *  并且以后想使用不是数字等方法，调用此类不用导入
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static Logger logger = LoggerFactory.getLogger(StringUtils.class);
    public static String isoToUtf8(String str){

        try {
            return new String(str.getBytes("ISO8859-1"),"UTF-8");   //这个异常就在这里处理了，其实是不需要处理的，在servlet里面会被上抛，但是为了让其输出日志，所以在这里处理，让其抛出异常让外面知道那里问题
        } catch (UnsupportedEncodingException e) {
            logger.error("字符串{}转换异常",str);//抛出异常就不运行了，不用return
            throw new RuntimeException("字符串" + str +"转换异常",e);
        }

    }



}
