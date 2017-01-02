<%--
  Created by IntelliJ IDEA.
  User: 刘忠伟
  Date: 2016/12/28
  Time: 14:06
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
    <link rel="stylesheet" type="text/css" href="/static/css/sweetalert.css">
    <style>
        .table td{
            vertical-align: middle;
        }
        .table select{
            width: 150px;
            margin: 0px;

        }

    </style>
</head>
<body>
<%@include file="../include/adminNavbar.jsp"%>
<!--header-bar end-->
<div class="container-fluid" style="margin-top:20px">
    <table class="table">
        <thead>
        <tr>
            <th>名称</th>
            <th>发布人</th>
            <th>发布时间</th>
            <th>回复数量</th>
            <th>最后回复时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.page.items}" var="topic">
            <tr>
                <td>
                    <a href="/post?topicid=${topic.id}" target="_blank">${topic.title}</a>
                </td>
                <td>${topic.user.username}</td>
                <td>${topic.createtime}</td>
                <td>${topic.replynum}</td>
                <td>${topic.lastreplytime}</td>
                <td>
                    <select name="nodeid" id ="${topic.id}">
                        <%--每次都要把全部节点作为下拉框，跳进页面就选中的由topic的nodeid决定--%>
                        <c:forEach items="${nodeList}" var="node">
                            <option ${topic.nodeid == node.id?'selected':''} value="${node.id}">${node.nodename}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <a href="javascript:;" rel="${topic.id}" class="update" >修改</a>
                    <a href="javascript:;" rel="${topic.id}" class="delete">删除</a><%--删除操作，需要知道点击那个删除哪个，需要知道topicid.--%>
                </td><%--如果不用异步请求，直接用超链接删除成功跳转回来。用异步，刷新本页就行--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagination pull-right">
        <ul id="pagination" style="margin-bottom:20px;"></ul>
    </div>
<%--    <div class="pagination pagination-mini pagination-centered">
        <ul id="pagination" style="margin-bottom:20px;"></ul>
    </div>--%>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script src="/static/js/sweetalert.min.js"></script>
<script>
    $(function () {
        $("#pagination").twbsPagination({
            totalPages: "${requestScope.page.totalPage}",
            visiblePages: "${requestScope.page.pageSize}",
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href: '/admin/topic?p={{number}}&_=1' //这里孩子需要页数，不需要其他值
        });
        $(".update").click(function () {
            var topicid = $(this).attr("rel");
            var nodeid = $("#"+topicid).val();
            swal({
                title: "确定要修改该主题节点?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                closeOnConfirm: false
            },
                    function () {/*回调函数*/
                        $.post("/admin/updateTopic",{"topicid":topicid,"newnodeid":nodeid},function (json) {
                            if(json.state == "success"){
                                swal({"title":"修改成功！"},function () {
                                    window.history.go(0);
                                });
                            } else {
                                swal(json.message);
                            }
                        });
                    });
        });

        $(".delete").click(function () {
            var topicid = $(this).attr("rel");
            swal({
                title: "确定要删除该主题?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                closeOnConfirm: false
            },
            function(){//这是一个回调函数,确定删除时触发
                $.post("/admin/topic",{"topicid":topicid},function (json) {
                    if(json.state == "success"){
                        //先提是删除成功，在刷新页面 swal();可以放对象，放函数
                        swal({"title":"删除成功！"},function () {
                            window.history.go(0);
                        });
                    } else if(json.state == "error"){
                        swal(json.message);
                    }

                });

            });

        });



    });
</script>


</body>
</html>
