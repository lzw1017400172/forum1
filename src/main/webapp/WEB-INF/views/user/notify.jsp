<%--
  Created by IntelliJ IDEA.
  User: 刘忠伟
  Date: 2016/12/26
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>通知中心</title>
    <link href="//cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="//cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<!--header-bar end-->
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-bell"></i> 通知中心</span>
        </div>
        <button id="markBtn" style="margin-left: 8px;" disabled class="btn btn-mini">标记为已读</button>
        <table class="table">
            <thead>
            <tr>
                <th width="30">
                    <c:if test="${not empty requestScope.notifyList}">
                        <input type="checkbox" id="ckFather">
                    </c:if>
                </th>
                <th width="200">发布日期</th>
                <th>内容</th>
            </tr>
            </thead>
            <tbody><%--notifyList为空的化不显示--%>
            <c:choose>
                <c:when test="${not empty requestScope.notifyList}">
                    <c:forEach items="${requestScope.notifyList}" var="notify">
                        <%--循环时判断一下，是已读state=1就不显示多选框，并且被划线。表示已读--%>
                        <c:choose>
                            <c:when test="${notify.state == 1}">
                                <tr style="text-decoration: line-through"><%--这一行要全部被划线--%>
                                    <td><%--已读不显示多选框，但要有这一列--%></td>
                                    <td>${notify.createtime}</td>
                                    <td>${notify.content}</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td><input value="${notify.id}" type="checkbox" class="ckSon"></td>
                                    <td>${notify.createtime}</td>
                                    <td>${notify.content}</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr><td colspan="2"><p>暂时没有消息</p></td></tr>
                </c:otherwise>
            </c:choose>

            </tbody>
        </table>
    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<c:if test="${not empty sessionScope.curr_user}"><%--引入的外部js内部不能值接似用EL表达式--%>
    <script src="/static/js/user/notify.js"></script>
</c:if>
<script>

    $(function () {
        //处理多选框的选中问题

        //ckFather选中就全部选中..size()是jquery方法，length是dom中的属性，不用jquery也能用
        $("#ckFather").click(function () {//$(this)当前时间的控件
            for(var i = 0;i<$(".ckSon").size();i++){//原生js checked属性选中多选框，返回true选中
                $(".ckSon")[i].checked = $(this)[0].checked;//jquery循环之后$(".ckSon")[i]自动转换成原生js
            } //第一次点击$(this)[0].checked=true，第二次点击=false，所以第一次全选，第二次全不选
            if($(this)[0].checked){
                $("#markBtn").removeAttr("disabled");
            } else {
                $("#markBtn").attr("disabled","disabled");
            }
        });
        //当全部选中$("#ckFather")也被选中,循环每个框被选中就记录一个，如果全部选中就count==总数size.这个循环要在每点击一个多选框做一次
        var size = $(".ckSon").size();
        //这格循环判断每点击一次就要做一次
        $(".ckSon").click(function () {
            var count = 0;
            for(var i = 0;i<size;i++){
                if($(".ckSon")[i].checked){
                    count++;
                }
            }
            if(count == size){
                $("#ckFather")[0].checked = true;
            } else {//全部选中后，再次点击，只要有一个没选中，就false，一定要写
                $("#ckFather")[0].checked = false;
            }
            if(count > 0){
                $("#markBtn").removeAttr("disabled");
            } else {//=0时必须再加上属性，不然先删除，在else还是可选btn
                $("#markBtn").attr("disabled","disabled");
            }
        });
        //当数据库没有数据时，$("#ckFather").被隐藏
        //有多选被选中时才能btn可选，没东西被选中就不可选btn.不仅要在点击子写，也要在点击父写，不然不会触发事件，点父就不会被选中
        //已读通知被划线，并且不显示多选框。

        //点击按钮时，应该提交被选中的子的notify.id用来标记为已读，发给服务端
        $("#markBtn").click(function () {
            var ids = [];//定义一个数组，把被选中的id放里面，发送给服务端
            //使用循环判断被选中的,push()给数组末尾添加新元素
            for(var i = 0;i<size;i++){
                if($(".ckSon")[i].checked){
                    ids.push($(".ckSon")[i].value);
                }
            }
            //join(",")将数组装换成字符串，并且用制定分隔
           ids = ids.join(",");

            //进行异步请求提交数据.在js中使用EL一定要加双引号
            $.get("/read",{"ids":ids},function (json) {
                if(json.state == "success"){
                    //返回成功时，即原来的未读要变成已读，刷新一下，就回去再查数据库，就行了
                    //刷新页面
                    window.history.go(0);//history.go(0)跳到本页面。history.go(-1)上一个页面。history.go(1)下一个页面
                } else {
                    alert(json.message);
                }
            });
            //并且已读的要划线
        });
    });
</script>
</body>
</html>