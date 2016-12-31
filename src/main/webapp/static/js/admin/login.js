$(function () {

    //直接点击login进来的没有传值，经过过滤器，会传值redirect上个页面的url包括参数
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

    $("#login_btn").click(function () {
        $("#login_form").submit();
    });
    $("#login_form").validate({
        errorElement:"span",
        errorClass:"text-error",
        rules:{
            username:{
                required:true,
                minlength:3
                //remote可以发送请求验证是否已经存在，登录不需要验证而已
            },
            password:{
                required:true,
                rangelength:[6,18]
            }
        },
        messages:{
            username:{
                required:"请输入帐号",
                minlength:"最少三个字符"
            },
            password:{
                required:"请输入密码",
                rangelength:"请输入6-18位密码"
            }},
        submitHandler:function (form) {
            //提交是发送异步请求
            $.ajax({
                url:"/admin/login",
                type:"post",
                data:{"username":$("#username").val(),"password":$("#password").val()},//或者$(form).servalize()默认获取表单中所有含有name属性的值
                beforeSend:function () {
                    $("#login_btn").text("登录中").attr("disabled","disabled");
                },
                success:function (json) {
                    if(json.state=="success"){
                        //登录成功后，获取redirect
                        var url = getParameterByName("redirect");
                        if(url){
                            //传值的话，要区分一下有没有毛标记。不用区分参数，跳转可以直接带参数过去，但是得区分毛标记
                            var hash = location.hash;//获取当前地址栏里的毛标记。。因为这个跳转url毛标记传不过去，加上
                            if(hash){
                                window.location.href = url + hash;
                            } else {
                                window.location.href = url;
                            }

                        } else {
                            //没有传值，去home
                            window.location.href = "/admin/home";
                        }



                    } else if(json.state == "error"){
                        alert(json.message);
                    }
                },
                error:function () {
                    alert("服务器异常");
                },
                complete:function () {
                    $("#login_btn").text("登录").removeAttr("disabled");
                }

            });
        }


    });



});
