
$(function(){

    $("#foundPassword_btn").click(function(){
        $("#foundPassword_form").submit();
    });
    /*根据复选框选择去改写方式,change()函数，select复选框的选择*/
    $("#type").change(function(){//用$(this)确定选择的，等于事件委托机制
        var type = $(this).val();
        if(type == "email"){
            $("#name_type").text("电子邮件");
        } else if(type == "phone"){
            $("#name_type").text("手机号码");
        }

    });
    /*表单验证,必须submit提交，修改submit的提交方式，改成异步ajax*/
    $("#foundPassword_form").validate({

        errorElement:"span",
        errorClass:"text-error",
        rules:{
            value:{
                required:true
            }
        },
        messages:{
            value:"该项必填"
        },
        submitHandler:function (form) {//这里的form参数，就是表单元素，需要表单就可以$(form).servalize();
            /*提交表单，ajax异步,post方式，get方式已经用来重定向jsp了*/
            $.ajax({
                url:"/foundPassword",
                type:"post",
                data:$("#foundPassword_form").serialize(),    //获取所有表单元素的值，name作为键，value作为值，所有的
                beforeSend:function(){//请求开始时
                    $("#foundPassword_btn").text("提交中...").attr("disabled","disabled");
                },
                success:function(date){
                    if(date.state == "success"){
                        if($("#type").val() == "email"){
                            alert("请查收邮件进行操作");
                        } else {
                            //电话操作，先不写
                        }
                    } else if(date.state == "error"){
                        alert(date.message);
                    }
                },
                error:function(){//响应失败
                    alert("服务器异常");
                },
                complete:function(){
                    $("#foundPassword_btn").text("提交").removeAttr("disabled");
                }
            });

        }





    });

});