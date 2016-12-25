

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

    //鼠标点击事件
    $("#btn").click(function(){

        $("#login_form").submit();

    });
    //键盘事件
    $("#password").keydown(function () {
        if(event.keyCode == "13"){//event状态，按下13对应回车键
            $("#btn").click();
        }
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
                        alert("登录成功");//把登陆前的网址通过redirect的参数传过来了，js不能用EL表达式就截取了，截的是地址栏redirect的参数，也就是说，可以手动给redirect赋值，让其登录后跳转到指定页面，跳转/login时就传值，值会被传到jsp地址栏，然后js里面截取的就是地址栏的参数redirect的值
                        var url = getParameterByName("redirect");//每次登录完成都获取redirect的值
                        if(url){//地址栏的值能修改，所有不一定能获取到..url截取的是redirect后面所有的值包括参数，毛标记也截取了，跳转时不显示，需要hash
                            var hash = location.hash;//hash获取毛标记
                            if(hash){
                                window.location.href = url + hash;//登录成功应该跳转到登录前的界，通过这样加毛标记跳转过去才能找到毛标记
                            } else {
                                window.location.href = url;//登录成功应该跳转到登录前的界
                            }

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
