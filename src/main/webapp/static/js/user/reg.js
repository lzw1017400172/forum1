/**
 * Created by 刘忠伟 on 2016/12/15.
 */
$(function () {

    $("#reg-btn").click(function(){

        $("#reg-form").submit();

    });

    /*表单验证*/
    $("#reg-form").validate({
        errorElement:"span",
        errorClass:"text-error",
        rules:{
            username:{
                required:true,
                minlength:3,
                remote:"/validateusername" + $("#username").val()//remote验证规则，像客户端发送请求传参可以，验证账户是否存在，只接受"false""true"字符串返回值
            },
            password:{
                required:true,
                rangelength:[6,18]
            },
            toopassword:{
                required:true,
                rangelength:[6,18],
                equalTo:"#password"
            },
            email:{
                required:true,
                email:true,
                remote:"/validateemail" + $("#email").val()  //属于url传值，get异步提交
            },
            phone:{
                required:true,
                rangelength:[11,11]
            }
        },
        messages:{
            username:{
                required:"请输入帐号",
                minlength:"长度不低于三",
                remote:"用户名已经存在！"
            },
            password:{
                required:"请输入密码",
                rangelength:"密码长度必须是6-18个字符"
            },
            toopassword:{
                required:"请输入重复密码",
                rangelength:"密码长度必须是6-18个字符",
                equalTo:"#两次输入不一致"
            },
            email:{
                required:"请输入邮箱",
                email:"邮箱格式错误",
                remote:"邮箱已经被注册"
            },
            phone:{
                required:"请输入手机号",
                rangelength:"手机号码长度必须是11位"
            }
        },
        submitHandler:function(){
            //发送ajax异步请求，提交数据，不用跳转，登陆成功在跳转
            $.ajax({
                url:"/reg",
                type:"post",
                data:$("#reg-form").serialize(),   //serialize()会返回所有的表单input的那么属性值对应value值的键值对,&连起来，属于url传值
                success:function(data){
                    if(data.state=="success"){
                        alert("注册成功");
                        window.location.href = "/login";
                    } else {
                        alert(data.message);
                    }

                },
                error:function(){
                    alert("服务器异常");
                },
                beforeSend:function(){
                    $("#reg-btn").text("注册中...").attr("disabled","disabled");
                },
                complete:function(){
                    $("#reg-btn").text("注册").removeAttr("disabled");
                }



            });

        }






    });




});