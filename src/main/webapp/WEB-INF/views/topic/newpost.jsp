<%--
  Created by IntelliJ IDEA.
  User: 刘忠伟
  Date: 2016/12/20
  Time: 19:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>发布新主题</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-plus"></i> 发布新主题</span>
        </div>

        <form action="" style="padding: 20px" id="newpost_form">
            <label class="control-label">主题标题</label>
            <input name="newpost_title" type="text" style="width: 100%;box-sizing: border-box;height: 30px" placeholder="请输入主题标题，如果标题能够表达完整内容，则正文可以为空">
            <label class="control-label">正文</label>
            <textarea name="content" id="editor"></textarea>

            <select name="nodeid" id="nodeid" style="margin-top:15px;">
                <option value="">请选择节点</option>
                <c:forEach items="${requestScope.nodeList}" var="item">
                    <option value="${item.id}">${item.nodename}</option>
                </c:forEach>
            </select>

        </form>
        <div class="form-actions" style="text-align: right">
            <button type="button" id="newpost_btn" class="btn btn-primary">发布主题</button>
        </div>


    </div>
    <!--box end-->
</div>
<!--container end-->

<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="../../../static/js/editer/scripts/simditor.min.js"></script><%--文本编辑器--%>
<script src="/static/js/jquery.validate.min.js"></script>
<script>
    $(function(){
        //文本编辑器
        var editor = new Simditor({
            textarea: $('#editor')
            //optional options
        });

        $("#newpost_btn").click(function () {
            $("#newpost_form").submit();
        });

        $("#newpost_form").validate({
            errorElement:"span",
            errorClass:"text-error",
            rules:{
                newpost_title:{
                    required:true
                },
                nodeid:{
                    required:true
                }
                //文本不用必填，如果标题就能表示全部意思就不用写文本
            },
            messages:{
                newpost_title:{
                    required:"请输入标题"
                },
                nodeid:{
                    required:"请选择节点"
                }
            },
            submitHandler:function (form) {

                $.ajax({
                    url:"",
                    type:"post",
                    data:$(form).serialize(),
                    beforeSend:function () {
                        $("#newpost_btn").text("发布主题中").attr("disabled","disabled");
                    },
                    success:function (json) {
                        if(json.state == "success"){
                            alert("主题已发布，请去查看");
                            window.location.href = "/post";
                        } else {
                            alert("新增主题异常");
                        }
                    },
                    error:function () {
                        alert("服务器异常");
                    },
                    complete:function () {
                        $("#newpost_btn").text("发布主题").removeAttr("disabled");
                    }


                });
            }




        });


    });
</script>

</body>
</html>
