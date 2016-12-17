<%--
  Created by IntelliJ IDEA.
  User: 刘忠伟
  Date: 2016/12/17
  Time: 8:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@ include file="../include/navbar.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title">找回密码</span>
        </div>

        <form action="" class="form-horizontal" id="foundPassword_form">

            <div class="control-group">
                <label class="control-label">选择找回方式</label>
                <div class="controls">
                    <select class="form-control" id="type" name="select_type"><%--这里根据select的value属性来判断是那个登录，下面的value属性选择那个，select的value就是那个,z在js里面修改--%>
                        <option value="email" >根据电子邮件查找</option>
                        <option value="phone" >根据手机号查找</option>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" id="name_type">电子邮件</label><%--在js判断哪种方式--%>
                <div class="controls">
                    <input type="text" name="value">
                </div>
            </div>
            <div class="form-actions">
                <button type="button" class="btn btn-primary" id="foundPassword_btn">提交</button>
            </div>

        </form>



    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/user/foundPassword.js"></script>
</body>
</html>
