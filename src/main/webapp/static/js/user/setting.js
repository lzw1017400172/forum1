$(function(){

    //修改邮箱
    $("#email_btn").click(function () {
        $("#email_form").submit();
    });

    $("#email_form").validate({
        errorElement:"span",
        errorClass:"text-error",
        rules:{
            email:{
                required:true,
                email:true,
                remote:"/validateemail?type=1"   //这里remote验证邮箱是否存在，是修改邮箱，若果我把原邮箱输入仍然会提示邮箱已存在，所以在验证的时候需要区分是否是登录帐号的邮箱
            }
        },
        messages:{
            email:{
                required:"请输入邮箱",
                email:"邮箱格式不正确",
                remote:"您输入的邮箱已经存在！"
            }
        },
        submitHandler:function(form){
            $.ajax({
                url:"/setting?action=email",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#email_btn").text("保存中").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state == "success"){
                        alert("邮箱修改成功");
                    }
                },
                error:function () {
                    alert("服务器异常！");
                },
                complete:function(){
                    $("#email_btn").text("保存").removeAttr("disabled");
                }


            });
        }


    });


    //修改密码
    $("#password_btn").click(function(){
        $("#password_form").submit();
    });

    $("#password_form").validate({

        errorElement: "span",
        errorClass: "text-error",
        rules:{
            oldpassword:{ //原始密码验证是否正确去servlet处理
                required:true,
                rangelength:[6,18]
            },
            newpassword:{
                required:true,
                rangelength:[6,18]
            },
            repassword:{
                required:true,
                rangelength:[6,18],
                equalTo:"#newpassword"
            }
        },
        messages:{
            oldpassword:{ //原始密码验证是否正确去servlet处理
                required:"请输入原始密码",
                rangelength:"密码长度为6-18个字符"
            },
            newpassword:{
                required:"请输入新密码",
                rangelength:"面码长度为6-18个字符"
            },
            repassword:{
                required:"请输入确定密码",
                rangelength:"密码长度为6-18个字符",
                equalTo:"两次输入密码不相同"
            }
        },
        submitHandler:function(form){

            $.ajax({
                url:"/setting?action=password",
                type:"post",
                data:$(form).serialize(),
                beforeSend: function () {
                    $("#password_btn").text("保存中").attr("disabled","disabled");
                },
                success: function (data) {
                    if(data.state == "success"){
                        alert(data.data);
                    } else if(data.state == "error"){
                        alert(data.message);
                    }
                },
                error: function () {
                    alert("服务器异常！");
                },
                complete:function () {
                    $("#password_btn").text("保存").removeAttr("disabled");
                }
            });
        }
    });

    /*头像上传使用webupload上传到七牛*/

});
