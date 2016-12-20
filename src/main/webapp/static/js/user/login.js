

$(function () {

    /*获取当前地址栏url并且截取name后面值*/
    function getParameterByName(name, url) {
        if (!url) {
            url = window.location.href;
        }
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    $("#btn").click(function(){

        $("#login_form").submit();

    });

    /*表单验证*/
    $("#login_form").validate({

        errorElement:"span",
        errorClass:"text-error",
        rules:{
            username:{
                required:true
            },
            password:{
                required:true
            }
        },
        messages:{
            username:{
                required:"请输入帐号"
            },
            password:{
                required:"请输入密码"
            }
        },
        submitHandler:function(form){/*这个参数就是表单*/
            $.ajax({
                url:"/login",
                type:"post",
                data:$(form).serialize(),//或者$("#login_form").serialize(),此方法获取所有的input的name和value作为键值，相当于提交表单
                beforeSend:function(){//请求开始时
                    $("#btn").text("登陆中").attr("disabled","disabled");//attr添加属性，不能修改
                },
                success:function(date){//响应成功，接受值为json
                    if(date.state == "success"){//登录成功，跳转location
                        alert("登录成功");
                        var url = getParameterByName("redirect");
                        if(url){//地址栏的值能修改，所有不一定能获取到
                            window.location.href = url;//登录成功应该跳转到登录前的界
                        } else {
                            window.location.href = "/home";//登录成功应该跳转到登录前的界面
                        }

                    } else {//登录失败
                        alert("登录失败，" + date.message);
                    }
                },
                error:function(){
                    alert("服务器异常");
                },
                complete:function () {
                    $("#btn").text("登录").removeAttr("disabled");//disabled属性为bootstrap的禁止点击属性
                }


            });
        }



    });


});
