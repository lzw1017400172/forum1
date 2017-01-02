<%--
  Created by IntelliJ IDEA.
  User: 刘忠伟
  Date: 2016/12/29
  Time: 14:31
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
    <link href="/static/css/sweetalert.css" rel="stylesheet">
</head>
<body>
<%@include file="../include/adminNavbar.jsp" %>
<!--header-bar end-->
<div class="container-fluid" style="margin-top:20px">
    <table class="table">
        <thead>
        <tr>
            <th>账号</th>
            <th>注册时间</th>
            <th>最后登录时间</th>
            <th>最后登录IP</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.page.items}" var="userVo">
            <tr>
                <td>${userVo.username}</td>
                <td>${userVo.createtime}</td>
                <td>${userVo.logintime}</td>
                <td>${userVo.ip}</td>
                <td>
                    <a href="javascript:;" rel="${userVo.id}" class="state">${userVo.status == 1 ?"禁用":"恢复"}</a><%--这里只显示禁用和正常，未激活不会显示在这里。在sql里查的时候过滤掉--%>
                </td><%--可以直接超链接，跳转改变状态之后，再请求转发回来。用ajax更可观，可以弹窗成功后才刷新本页--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagination  pagination-centered"> <%--//centered居中，right右边，left左边--%>
        <ul id="pagination" style="margin-bottom:20px;"></ul>
    </div>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script src="/static/js/sweetalert.min.js"></script>
<script>
    $(function () {

        $("#pagination").twbsPagination({
            totalPages:"${requestScope.page.totalPage}",//总页数
            visiblePages:"${requestScope.page.pageSize}",//每页显示
            first:"首页",
            last:"末页",
            prev:'上一页',
            next:'下一页',
            href:"/admin/user?p={{number}}&_=3"

        });


        $(".state").click(function () {
            var userid = $(this).attr("rel");
            var state = $(this).text();
            state = state == "禁用" ? "禁用" : "恢复";
            swal({
                        title: "确定要" + state + "该用户?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定",
                        closeOnConfirm: false },
                    function(){
                        $.post("/admin/user",{"userid":userid,"state":state},function (json) {
                            if(json.state == "success"){
                                swal({"title":state+"成功！"},function () {
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

