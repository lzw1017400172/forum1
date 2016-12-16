package com.kaishengit.exception;

/**
 * Created by 刘忠伟 on 2016/12/15.
 */
public class DataAccessException extends RuntimeException {//自定义异常一定要继承异常类，重写父类方法，传参上传给父类

    public DataAccessException(){super();}

    public DataAccessException(String message){super(message);}

    public DataAccessException(String message, Throwable throwable){super(message,throwable);}

    public DataAccessException(Throwable throwable){super(throwable);}




}
