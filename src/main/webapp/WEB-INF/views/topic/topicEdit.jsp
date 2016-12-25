<%--
  Created by IntelliJ IDEA.
  User: 刘忠伟
  Date: 2016/12/23
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>编辑主题</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
    <link rel="stylesheet" href="/static/css/simditor-emoji.css">
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-edit"></i> 编辑主题</span>
        </div>

        <form action="" id="topicForm" style="padding: 20px">
            <label class="control-label">主题标题</label>
            <input type="text" name="title" id="title" value="${requestScope.topic.title}" style="width: 100%;box-sizing: border-box;height: 30px">
            <input type="hidden" name="topicid" value="${param.topicid}"><%--请求转发过来的地址栏还有topicid--%>
            <label class="control-label">正文</label>
            <textarea name="content" id="editor">${requestScope.topic.content}</textarea>

            <select name="nodeid" id="nodeid"style="margin-top:15px;">
                <option value="">请选择节点</option>
                <c:forEach items="${requestScope.nodeList}" var="node" >
                    <option  ${node.id == topic.nodeid?"selected":""}  value="${node.id}">${node.nodename}</option>
                </c:forEach>
            </select>
        </form>
        <div class="form-actions" style="text-align: right">
            <button type="button"  id="sendBtn"class="btn btn-primary">发布主题</button>
        </div>


    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>

<script src="/static/js/simditor-emoji.js"></script><%--表情js--%>
<script src="/static/js/jquery.validate.min.js"></script>
<script>

    $(function () {

        var editor = new Simditor({/*前面的需要判断是否登录，是因为textarea是登陆之后才出现的，没有登录就报错，现在这个是要放入过滤器，只能登录才进来*/
            textarea:$("#editor"),
            toolbar:['title','bold','italic','underline','strikethrough',
                'fontScale','color','ol' ,'ul', 'blockquote','code',
                'table', 'image','emoji'],
            emoji:{
                imagePath:'/static/img/emoji'
            },
            /*update:{
                url:"http://up-z1.qiniu.com/",
                params:{"token":"$token"},
                fileKey:"file"
            }*/
        });

        $("#sendBtn").click(function () {
            $("#topicForm").submit();
        });
        $("#topicForm").validate({
            errorElement: "span",
            errorClass: "text-error",
            rules: {
                title: {
                    required: true,
                },
                nodeid: {
                    required: true,
                }
            },
            messages: {
                title: {
                    required: "请输入标题",
                },
                nodeid: {
                    required: "请选择节点",
                }
            },
            submitHandler:function(form){
                $.ajax({
                    url:"/topicEdit",
                    type:"post",
                    data:$(form).serialize(),
                    beforeSend:function () {
                        $("#sendBtn").text("发布中").attr("disabled","disabled");
                    },
                    success:function (json) {
                        if(json.state == "success"){
                            //topic修改成功后跳转到展示页面/post,需要topicid不然会报错，
                            window.location.href = "/post?topicid=${topic.id}";//+ json.data;
                            //让json返回topicid。。。或者是请求转发过来的地址栏有topicid属性
                        } else {
                            alert(json.message);
                        }
                    },
                    error:function () {
                        alert("服务器异常");
                    },
                    complete:function () {
                        $("#sendBtn").text("发布主题").removeAttr("disabled");
                    }
                });
            }

        });

    });


</script>



