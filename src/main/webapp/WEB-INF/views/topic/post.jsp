<%--
  Created by IntelliJ IDEA.
  User: 刘忠伟
  Date: 2016/12/20
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--添加fn标签对集合操作--%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>论坛--${requestScope.topic.title}</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
    <style>
        body{
            background-image: url(/static/img/bg.jpg);
        }
        .simditor .simditor-body {
            min-height: 100px;
        }
    </style>
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <ul class="breadcrumb" style="background-color: #fff;margin-bottom: 0px;">
            <li><a href="/home">首页</a> <span class="divider">/</span></li>
            <li class="active">${requestScope.topic.node.nodename}</li>
        </ul>
        <div class="topic-head">
            <img class="img-rounded avatar" src="http://oi04kst4a.bkt.clouddn.com/${requestScope.topic.user.avatar}?imageView2/1/w/60/h/60" alt="">
            <h3 class="title">${requestScope.topic.title}</h3>
            <p class="topic-msg muted"><a href="">${requestScope.topic.user.username}</a > · <span id="topictime">${requestScope.topic.createtime}</span></p>
        </div>
        <div class="topic-body">
            ${requestScope.topic.content}
           <%-- <p>AngularJS is an MVC framework for building web applications. The core features include HTML enhanced with custom component and data-binding capabilities, dependency injection and strong focus on simplicity, testability, maintainability and boiler-plate reduction.</p>
            <p>下载之前先检查一下是否准备好了一个代码编辑器(我们推荐使用 Sublime Text 2) ，你是否已经掌握了足够的HTML和CSS知识以开展工作。这里我们不详述源码文件，但是它们可以随时被下载。在这里我们只着重介绍使用已经编译好的Bootstrap文件进行入门讲解。</p>--%>
        </div>

        <%--没登陆不显示加入收藏，登陆之后显示，并且判断登录帐号userid和topic里面的userid是否相同，相同可以编辑--%>
        <div class="topic-toolbar">
            <c:if test="${ not empty sessionScope.curr_user}">
                <ul class="unstyled inline pull-left">
                    <c:choose>
                        <c:when test="${not empty requestScope.fav}"><%--在servlet判断是否收藏过，href是跳转，不需要跳转，解决办法，javascript:;让js发送异步请求收藏过就返回会一个对象fav--%>
                            <li><a href="javascript:;" id="favtopic">取消收藏</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="jsvascript:;" id="favtopic">加入收藏</a></li>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${not empty requestScope.thank}"><%--在servlet判断是否收感谢过，有就返回一个感谢对象thank，href是跳转，不需要跳转，解决办法，javascript:;让js发送异步请求收藏过就返回会一个对象fav--%>
                            <li><a href="javascript:;" id="thankTopic">取消感谢</a></li><%--//收藏感谢都是异步请求，不跳转同步数据需要在js修改--%>
                        </c:when>
                        <c:otherwise>
                            <li><a href="javascript:;" id="thankTopic";>感谢</a></li>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${sessionScope.curr_user.id == requestScope.topic.userid and topic.edit}"><%--并且有评论不可编辑--%>
                        <li><a href="/topicEdit?topicid=${param.topicid}">编辑</a></li><%--并且是五分钟之内可以编辑--%>
                    </c:if>
                </ul>
            </c:if>
            <ul class="unstyled inline pull-right muted">
                <li>${requestScope.topic.clicknum}次点击</li>
                <li><span id="topicFav">${requestScope.topic.favnum}</span>人收藏</li>
                <li><span id="topicThank">${requestScope.topic.thankyounum}</span>人感谢</li>
            </ul>
        </div>
    </div>
    <!--box end-->
<%--这个回复框架，没人回复时整个就不显示了--%>
    <c:if test="${not empty requestScope.replyList}">
    <div class="box" style="margin-top:20px;">
        <div class="talk-item muted" style="font-size: 12px">
            <%--${topic.replynum}或者--%> ${fn:length(replyList)}回复 | 直到 <span id="lastreplytime">${requestScope.topic.lastreplytime}</span><%--最后一次回复时间lastreplytime是topic表2015年12月25日 22:23:34--%>
        </div><%--9个回复，可以直接${topic.replynum},或者${fn:length(replyList)}返回集合长度需要查reply表对应topicid的多少个，都要返回还要把集合返回显示出来，所以直接返回一个集合，通过fn标签对集合操作--%>
        <%--把replyList集合输出到界面--%>
        <c:forEach items="${requestScope.replyList}" var="reply" varStatus="vs">
            <div class="talk-item">
                <a name="${vs.count}"></a>
                <table class="talk-table">
                    <tr>
                        <td width="50">
                            <img class="avatar" src="${reply.user.avatar}?imageView2/1/w/40/h/40" alt=""><%--头像在封装时就把域名封装了--%>
                        </td>
                        <td width="auto">
                            <a href="" style="font-size: 12px">${reply.user.username}</a> <span style="font-size: 12px"  class="reply">${reply.createtime}</span>
                            <br>
                            <p style="font-size: 14px">${reply.content}</p>
                        </td>
                        <td width="70" align="right" style="font-size: 12px">
                                <%--给一个隐藏属性值，用来在js的事件委托$(this)时知道是哪个，--%>
                            <a class="replyLink" rel="${vs.count}" href="javascript:;" title="回复"><i class="fa fa-reply"></i></a>&nbsp;
                            <span class="badge">${vs.count}</span><%--正序显示，楼层数，用迭代数vs.count就行--%>
                        </td>
                    </tr>
                </table>
            </div>
        </c:forEach>
        </c:if>
    <div class="box" style="margin:20px 0px;">
        <c:choose>
            <c:when test="${not empty sessionScope.curr_user}">

                <div class="talk-item muted" style="font-size: 12px"><a name="reply"></a><i class="fa fa-plus"></i> 添加一条新回复</div>
                <form action="/post" method="post" style="padding: 15px;margin-bottom:0px;" id="post_form">
                    <input type="hidden" name="topicid" value="${param.topicid}"><%--这里为什么要传值topicid,因为添加一条回复数据库需要值topicid，userid可以在服务端获取，就是当时用户发布的--%>
                    <textarea name="content" id="editor"></textarea>
                </form>
                <div class="talk-item muted" style="text-align: right;font-size: 12px">
                    <span class="pull-left">请尽量让自己的回复能够对别人有帮助回复</span>
                    <button type="button" id="post_btn" class="btn btn-primary">发布</button>
                </div>
            </c:when>
            <c:otherwise>
                <div class="talk-item muted" style="font-size: 20px">请<a href="/login?redirect=/post?topicid=${requestScope.topic.id}#reply">登录</a>后回复</div><%--只要经过/login传值，会传到jsp地址栏，login,jsp是通过截取地址栏readirct获取这个url，并且跳转的--%>
            </c:otherwise><%--这个/post页面是需要传值的，是通过传topicid，不穿直接404--%>
        </c:choose>
    </div>

</div>
</div>
<!--container end-->
<%--<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>--%>
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.10.6/moment.min.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.10.6/locale/zh-cn.js"></script>
<c:if test="${not empty sessionScope.curr_user}"><%--引入的外部js内部不能值接似用EL表达式--%>
    <script src="/static/js/user/notify.js"></script>
</c:if>

<script>
    $(function(){

        <c:if test="${not empty sessionScope.curr_user}">/*这里为什么要判断是否登录，因为上面用登录过滤textarea: $('#editor'),这块元素了，如果没有登陆，找不到这个就报错，所以也用登录判断一次*/
            var editor = new Simditor({
                textarea: $('#editor'),
                toolbar:false
                //optional options
            });

            $(".replyLink").click(function () {//setValue和getValue
            //事件委托$(this)
            var count = $(this).attr("rel");
            var html = "<a href=\"#" + count + "\">#" + count + "</a>";
            editor.setValue(html + editor.getValue());
            window.location.href="#reply";  //点击玩跳转到毛标记reply就是文本编辑器
            });

         </c:if>
        $("#post_btn").click(function () {
            $("#post_form").submit();
        });
        //$("#topictime").text(moment("${requestScope.topic.createtime}").fromNow());
        $("#topictime").text(moment($("#topictime").text()).fromNow());
        $("#lastreplytime").text(moment("${requestScope.topic.lastreplytime}").format("YYYY年MM月DD日 HH:mm:ss"));

        $(".talk-item .reply").text(function () {//用class来替换，会有很多class。会错误 事件委托，this  $("#lastreplytime").text()
            var time = $(this).text();
            return moment(time).fromNow();
        });


        $("#post_form").validate({
            errorElement:"span",
            errorClass:"text-error",
            rules:{
                content:{
                    required:true
                }
            },
            messages:{
                content:{
                    required:"请输入内容"
                }
            }
            //这里不用异步请求，表单pist提交就行因为可能发布一条回复之后，其他人也回付，需要跳转刷新页面，显示全部

        });

        $("#favtopic").click(function(){
            //判断点击的是加入收藏还是取消收藏
            var $this = $(this);//当前控件在post请求里面不能数值解使用$(this),先给他指向一个元素，在使用
            var action;
            if($this.text() == "加入收藏"){
                action = "fav";
            } else if($this.text() == "取消收藏"){
                action = "unfav";
            }
            //加入收藏和取消收藏需要知道收藏哪个topicid
            $.get("/topicFav",{"topicid":${topic.id},"action":action})

                    .done(function (json) {
                        if(json.state == "success"){
                            if(action == "fav"){

                                $this.text("取消收藏");
                            } else {
                                $this.text("加入收藏");
                            }
                            //加入收藏或者取消收藏之后，应该更新到多少人收藏topicFav，因为界面没有刷新，所以需要在这里更新
                            $("#topicFav").text(json.data);//成功后传值作为到少人收藏
                        } else {
                            alert(data.message);
                        }
                    })

                    .error(function () {
                        alert("服务器异常");
                    });


        });

        //点击时发送异步请求，需要知道感谢的主题topicid，是感谢还是取消感谢和用户在服务端可获取
        $("#thankTopic").click(function () {
            var action = "";
            if($("#thankTopic").text()=="感谢"){
                action = "thank";
            } else if($("#thankTopic").text()=="取消感谢"){
                action = "unthank";
            }
            $.get("/topicThank",{"topicid":${topic.id},"action":action})
                    .done(function (json) {
                        if(json.state == "success"){
                            $("#thankTopic").text(action == "thank" ? "取消感谢" : "感谢");
                            //并且后面的感谢数量需要从数据库同步，需要查数据库，不能简单加1这样不准确
                            $("#topicThank").text(json.data);//data为返回感谢数量，查数据库的
                        } else {
                            alert(json.message);
                        }

                    })
                    .error(function () {
                        alert("服务器异常");
                    });



        });



    });
</script>

</body>
</html>
