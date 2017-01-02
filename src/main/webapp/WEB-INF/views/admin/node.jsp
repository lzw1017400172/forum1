<%--
  Created by IntelliJ IDEA.
  User: 刘忠伟
  Date: 2016/12/28
  Time: 20:28
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
    <link rel="stylesheet" href="/static/css/sweetalert.css">
    <style>
        .mt20 {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<%@include file="../include/adminNavbar.jsp" %>
<!--header-bar end-->
<div class="container-fluid mt20">
    <a href="/admin/newnode" class="btn btn-success">添加新节点</a>
    <table class="table">
        <thead>
        <tr>
            <th>节点名称</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.page.items}" var="node">
            <tr>
                <td>${node.nodename}</td>
                <td>
                    <a href="/admin/updateNodeName?nodeid=${node.id}">修改</a><%--修改几点名称。需要知道是哪个节点--%>
                    <a href="javascript:;" rel="${node.id}" class="delete">删除</a><%--在js中操作，如果删除失败，会弹框。提示哪里失败。只有删除成功才跳转--%>
                </td><%--需要知道删除的是谁，所以发送一个nodeid--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="pagination  pagination-centered">
    <ul id="pagination" style="margin-bottom:20px;"></ul>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script src="/static/js/sweetalert.min.js"></script>
<script>
    $(function () {
        //分页
        $("#pagination").twbsPagination({
            totalPages:${page.totalPage}, //总页数
            visiblePages:${page.pageSize},               //每页显示行数
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href: '?p={{number}}&_=2'        //超链接跳转,只用显示所有节点，不用其他参数
        });

        $(".delete").click(function () {
            var nodeid = $(this).attr("rel");
            swal({
                title: "确定要删除该节点?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                closeOnConfirm: false },
            function(){
                $.get("/admin/deleteNode",{"nodeid":nodeid},function (json) {
                    if(json.state == "success"){
                        swal({"title":"删除成功！"},function () {
                            window.history.go(0);//刷新本页面
                        });
                    } else {
                        swal(json.message);
                    }
                });
            });



        });

    });
</script>

</body>
</html>

