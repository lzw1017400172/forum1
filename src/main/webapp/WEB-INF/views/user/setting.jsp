<%--
  Created by IntelliJ IDEA.
  User: 刘忠伟
  Date: 2016/12/19
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/webuploader-0.1.5/webuploader.css">
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-cog"></i> 基本设置</span>
        </div>

        <form action="" class="form-horizontal" id="email_form">
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input type="text" name="username" id="username" readonly value="${sessionScope.curr_user.username}"><%--修改邮箱的时候，一定处于登录状态，session里面存的curr_user对应的user对象，所以可以直接从session取现在的帐号和邮箱，session可以在客户端获取，也可以在服务端获取--%>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">电子邮件</label>
                <div class="controls">
                    <input type="text" name="email" id="email" value="${sessionScope.curr_user.email}">
                </div>
            </div>
            <div class="form-actions">
                <button type="button" id="email_btn" class="btn btn-primary">保存</button>
            </div>

        </form>

    </div>
    <!--box end-->
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-key"></i> 密码设置</span>
            <span class="pull-right muted" style="font-size: 12px">如果你不打算更改密码，请留空以下区域</span>
        </div>

        <form action="" class="form-horizontal" id="password_form">
            <div class="control-group">
                <label class="control-label">原始密码</label>
                <div class="controls">
                    <input type="password" name="oldpassword" id="oldpassword">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                    <input type="password" name="newpassword" id="newpassword">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">重复密码</label>
                <div class="controls">
                    <input type="password" name="repassword" id="repassword">
                </div>
            </div>
            <div class="form-actions">
                <button type="button" class="btn btn-primary" id="password_btn">保存</button>
            </div>

        </form>

    </div>
    <!--box end-->

    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-user"></i> 头像设置</span>
        </div>

        <form action="" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">当前头像</label>
                <div class="controls">
                    <img id="avatar" src="http://oi04kst4a.bkt.clouddn.com/${sessionScope.curr_user.avatar}?imageView2/1/w/40/h/40" class="img-circle" alt="">
                </div>
            </div>
            <hr>
            <p style="padding-left: 20px">关于头像的规则</p>
            <ul>
                <li>禁止使用任何低俗或者敏感图片作为头像</li>
                <li>如果你是男的，请不要用女人的照片作为头像，这样可能会对其他会员产生误导</li>
            </ul>
            <div class="form-actions">
                <div id="picker">上传新头像</div><%--webupload的文件上传框架这里不是一个按钮，是一个div在swf文件里做的改变成选择文件--%>
            </div>


        </form>

    </div>
    <!--box end-->

</div>
<!--container end-->
<script src="../../../static/js/jquery-1.11.3.min.js"></script>
<script src="../../../static/js/jquery.validate.min.js"></script>
<script src="../../../static/js/webuploader-0.1.5/webuploader.min.js"></script>
<script src="../../../static/js/user/setting.js"></script>
<c:if test="${not empty sessionScope.curr_user}"><%--引入的外部js内部不能值接似用EL表达式--%>
    <script src="/static/js/user/notify.js"></script>
</c:if>

<%--将文件上传到七牛是需要凭证的token，但是获取凭证必须用EL表达式，EL表达式不能在外部js写，所以必须写这里--%>
<script>

    var uploader = WebUploader.create({
        swf:"/static/js/webuploader-0.1.5/Uploader.swf",
        server:"http://up-z1.qiniu.com/",//文件接受的服务端，上传到七牛就写七牛
        pick:"#picker",//确定哪个是选择文件按钮
        auto:true,//自动上传
        fileVal:"file",//恩件上传域的name属性值，必须是file
        formData:{"token":"${requestScope.token}"},//必须字符串裹起来//额外上传给七牛的参数，token凭证就要放里面，七牛会返回hash和key（文件名）
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        }//只允许选择图片
    });
    //webuploader上传是通过事件触发来实现操作
    uploader.on( 'uploadSuccess', function( file,data ) {
        //上传成功，同步到数据库
        var filekey = data.key;
        $.post("/setting?action=avatar",{"filekey":filekey})
                .success(function(data){
                    if(data.state == "success") {
                        var url = "http://oi04kst4a.bkt.clouddn.com/" + filekey ;
                        $("#avatar").attr("src",url+"?imageView2/1/w/40/h/40");
                        //上传完成后，导航的头像也需要改变，是通过session获取的，session和数据库指向同一片内存空间，数据库改变，session就改变，只需要重新获取一下session就会同步数据
                        //为了直接显示导航头像，就在这里直接改了
                        $("#navbar_img").attr("src",url+"?imageView2/1/w/20/h/20");
                    }
                })
                .error(function () {
                    alert("服务器异常");
                });
    });

    uploader.on( 'uploadError', function( file ) {
        //上传失败
        alert("上传失败稍后再试！");
    });



</script>
</body>
</html>
