<%--
  Created by IntelliJ IDEA.
  User: 刘忠伟
  Date: 2016/12/28
  Time: 11:24
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
<div>

</div>
<div class="container-fluid" style="margin-top:20px">
    <table class="table">
        <thead>
        <tr>
            <th>日期</th>
            <th>新主题数</th>
            <th>新回复数</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
                <c:forEach items="${requestScope.page.items}" var="HomeShowVo">
                    <tr>
                        <td>${HomeShowVo.topictime}</td>
                        <td>${HomeShowVo.topicnum}</td>
                        <td>${HomeShowVo.replynum}</td>
                    </tr>
                </c:forEach>
        </tbody>
    </table>
    <div class="pagination  pagination-centered"> <%--/centered居中，right右边，left左边--%>
        <ul id="pagination" style="margin-bottom:20px;"></ul>
    </div>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script>
    $(function () {
        $("#pagination").twbsPagination({

            totalPages:${page.totalPage}, //总页数
            visiblePages:${page.pageSize},               //每页显示行数
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href: 'admin/home?p={{number}}&_=0'

        });



    });


</script>

</body>
</html>

