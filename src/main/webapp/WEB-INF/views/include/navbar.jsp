<%--
  Created by IntelliJ IDEA.
  User: 刘忠伟
  Date: 2016/12/15
  Time: 19:15
  To change this template use File | Settings | File Templates.
  导航条，当没有登录时，导航条应该只显示登录EL以及c标签，登录后显示显示所有并且包括退出。通过字体fa-sign-in登录，fa-sign-out来写退出
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Document</title>
</head>
<body>
<div class="header-bar">
    <div class="container">
        <a href="/home" class="brand">
            <i class="fa fa-reddit-alien"></i>
        </a>
        <ul class="unstyled inline pull-right">
            <c:choose>
                <c:when test="${not empty sessionScope.curr_user}"><%--通过在客户端获取session来判断是否处于登录状态--%>
                    <li>
                        <a href="/setting">
                            <img id="navbar_img" src="http://oi04kst4a.bkt.clouddn.com/${sessionScope.curr_user.avatar}?imageView2/1/w/20/h/20" class="img-circle" alt="">
                        </a>                                            <%--获取头像用在session的对象--%>
                    </li>
                    <li>
                        <a href="/newpost"><i class="fa fa-plus"></i></a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-bell"></i></a>
                    </li>
                    <li>
                        <a href="/setting"><i class="fa fa-cog"></i></a>
                    </li>
                    <li>
                        <a href="/logout"><i class="fa fa-sign-out"></i></a><%--退出--%>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="/login"><i class="fa fa-sign-in"></i></a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>
</body>
</html>
