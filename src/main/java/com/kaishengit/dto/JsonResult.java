package com.kaishengit.dto;

/**
 * Created by 刘忠伟 on 2016/12/19.
 *
 * 服务端返回客户端的json形式，把他封装成一个对象，更方便返回，原来利用的是封装到map集合
 */
public class JsonResult {


    //state会有两个确认的值,并且可以硬编码，因为返回值就是固定的这种规则，永远不会改变
    public static final String ERROR = "error";
    public static final String SUCCESS = "success";

    private String state;
    private String message;
    private Object data;

    //写一些构造方法
    public JsonResult(){};
    public JsonResult(String state,String message,Object data){
        this.state = state;
        this.message = message;
        this.data = data;
    }
    public JsonResult(String message){
        this.state = ERROR;
        this.message = message;
    }
//    public JsonResult(Object data){
//        this.state = SUCCESS;
//        this.data = data;
//    }



    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
