<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%--使用指令导入html--%>
<%@ include file="include/navbar.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="talk-item">
            <ul class="topic-type unstyled inline" style="margin-bottom:0px;">
                <li class="${empty param.nodeid ? 'active' : ''}"><a href="/home">全部</a></li>
                <c:forEach items="${requestScope.nodeList}" var="node">
                    <li class="${node.id == param.nodeid ? 'active' :''}"><a href="/home?nodeid=${node.id}">${node.nodename}</a></li>
                </c:forEach>
            </ul>
        </div>
        <c:forEach items="${requestScope.page.items}" var="topic">
            <div class="talk-item">
                <table class="talk-table">
                    <tr>
                        <td width="50">
                            <img class="avatar" src="${topic.user.avatar}?imageView2/1/w/40/h/40" alt="">
                        </td>
                        <td width="80">
                            <a href="">${topic.user.username}</a>
                        </td>
                        <td width="auto">
                            <a href="/post?topicid=${topic.id}">${topic.title}</a>
                        </td>
                        <c:if test="${topic.replynum != 0}">
                            <td width="50" align="center">
                                <span class="badge">${topic.replynum}</span>
                            </td>
                        </c:if>
                    </tr>
                </table>
            </div>
        </c:forEach>
    <!--box end-->

    </div>
</div>
<%--<ul id="pagination-demo" class>="pagination-sm"></ul>--%>
<c:if test="${requestScope.page.totalPage != 1}">
    <div class="pagination pagination-centered"> <%--&lt;%&ndash;//centered居中，right右边，left左边&ndash;%&gt;--%>
        <ul id="pagination" style="margin-bottom:20px;"></ul>
    </div>>
</c:if>
<!--container end-->
<div class="footer">
    <div class="container">
        Copyright © 2016 kaishengit
    </div>
</div>
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script>
    $(function () {
        $("#pagination").twbsPagination({

            totalPages:${requestScope.page.totalPage},//总页数
            visblePages:${requestScope.page.pageSize},//每页显示行数
            href:"?nodeid=${param.nodeid}&p={{number}}",     //home一定需要nodeid，和页好p按照版本不同填page或者number//超链接
            first:"首页",
            last:"末页",
            prev:"上一页",
            next:"下一页"


        });



    });


</script>


</body>
</html>
