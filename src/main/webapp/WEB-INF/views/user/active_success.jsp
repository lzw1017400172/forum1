<%--
  Created by IntelliJ IDEA.
  User: 刘忠伟
  Date: 2016/12/16
  Time: 20:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <span class="title"><i class="fa fa-sign-in"></i>系统提示</span>
        </div>
        <div class="box-padding">
            <h4 style="font-size: 18px">账号已经激活，请 <a href="/login">登录</a></h4>
        </div>
    </div>
    <!--box end-->
</div>
<!--container end-->
</body>
</html>
