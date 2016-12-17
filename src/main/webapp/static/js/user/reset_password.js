$(function(){
    $("#reset_btn").click(function(){
        $("#reset_form").submit();
    });

    $("#reset_form").validate({

        errorElement:"span",
        errorClass:"text-error",
        rules:{
            password:{
                required:true,
                rangelength:[6,18]
            },
            repetition_password:{
                required:true,
                rangelength:[6,18],
                equalTo:"#password"
            }

        },
        messages:{
            password:{
                required:"请输入新密码",
                rangelength:"请输入6-18位密码"
            },
            repetition_password:{
                required:"请输入新密码",
                rangelength:"请输入6-18位密码",
                equalTo:"请输入相同密码"
            }
        },
        submitHandler:function(form){
            $.ajax({
                url:"/foundpassword/newpassword",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#reset_btn").text("保存中").attr("disabled","disabled");
                },
                success:function (date) {
                    if(date.state == "success"){
                        alert("密码修改成功，请登录！");
                        window.location.href = "/login";//在jsp跳转
                    } else if(date.state == "error"){
                        alert(date.message);
                    }

                },
                error:function () {
                    alert("服务器异常");
                },
                complete:function () {
                    $("#reset_btn").text("保存").removeAttr("disabled");
                }


            });


        }



    });


});
