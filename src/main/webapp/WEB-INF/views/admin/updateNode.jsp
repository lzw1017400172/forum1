<%--
  Created by IntelliJ IDEA.
  User: 刘忠伟
  Date: 2016/12/28
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@include file="../include/adminNavbar.jsp"%>
<!--header-bar end-->
<div class="container-fluid" style="margin-top:20px">
    <form action="/admin/updateNodeName" method="post" id="update_form">
        <legend>编辑节点</legend>
        <label>节点名称</label>
        <input type="hidden" value="${requestScope.node.id}" name="nodeid">
        <input type="text" value="${requestScope.node.nodename}" id="nodename" name="nodename">
        <div class="form-actions">
            <button type="button" id="update_btn" class="btn btn-primary">保存</button>
        </div>
    </form>
</div>
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script>
    $(function () {
        $("#update_btn").click(function () {
            $("#update_form").submit();
        });
        $("#update_form").validate({
            errorElement:"span",
            errorClass:"text-error",
            rules:{
                nodename:{
                    required:true,
                    remote:"/admin/validateNodeName?nodeid=${node.id}"   //远程验证，会自动发送此input的键值因为是需要需要比添加多传一个id，用来判断新node和老node相同，通过验证。添加的验证没有nodeid。是否可以修改成这个
                }
            },
            messages:{
                nodename:{
                    required:"请输入节点名称",
                    remote:"节点名称已经存在"
                }
            }
            //想用异步请求也可以，submitHandler函数。确定修改成功收到返回值。在从此页面跳转window.location.href="/admin/node"..或者表单提交在post重定向

        });


    });


</script>

<!--container end-->
</body>
</html>

