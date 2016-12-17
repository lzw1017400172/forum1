package com.kaishengit.exception;

/**
 * Created by 刘忠伟 on 2016/12/16.
 */
public class ServiceException extends RuntimeException {

    public ServiceException(){}
    public ServiceException(String message){super(message);}
    public ServiceException(String message,Throwable throwable){super(message,throwable);}
    public ServiceException(Throwable throwable){super(throwable);}

}
