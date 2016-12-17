<%--
  Created by IntelliJ IDEA.
  User: 刘忠伟
  Date: 2016/12/17
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="../include/navbar.jsp"%>

    <div class="container">
            <div class="box">
                <div class="box-header">
                    <span class="title"><i class="fa fa-key"></i> 密码设置</span>
                    <span class="pull-right muted" style="font-size: 12px">如果你不打算更改密码，请留空以下区域</span>
                </div>

                <form action="" class="form-horizontal" id="reset_form">
                    <input type="hidden" name="id" value="${id}"><%--这两个隐藏域作为给客户端的凭证，token是验证过期没，id是证明修改的哪个账户的密码--%>
                    <input type="hidden" name="token" value="${token}">
                    <div class="control-group">
                        <label class="control-label">密码</label>
                        <div class="controls">
                            <input type="password" name="password" id="password">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">重复密码</label>
                        <div class="controls">
                            <input type="password" name="repetition_password">
                        </div>
                    </div>
                    <div class="form-actions">
                        <button type="button" class="btn btn-primary" id="reset_btn">保存</button>
                    </div>

                </form>
            </div>

    </div>

<!--box end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/user/reset_password.js"></script>
</body>
</html>
