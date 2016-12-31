
$(function () {

    $("#newnode_btn").click(function () {
        $("#newnode_form").submit();
    });
    $("#newnode_form").validate({
        errorElement:"span",
        errorClass:"text-error",
        rules:{
            nodename:{
                required:true,
                remote:"/admin/validateNodeName"   //remote就是远程验证，自动发送此input的值发送get异步请求，只接受true和false字符串返回值。
            }
        },
        messages:{
            nodename:{
                required:"请输入节点名称",
                remote:"此节点名称已经存在"
            }

        }
        //不需要异步提交，只要表单提交就行了.异步的话就修改成功，收到返回。才去跳转到node
    });


});
