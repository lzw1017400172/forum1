/*通知到铃铛需要进行轮询，有新的未读通知的话就显示，几乎所有的页面都需要加入这个轮询*/
$(function () {

    //1解决EL表达式布恩那个直接在外部js使用，就给html添加一个默认属性当作E来表达式的获取值写在navbar.jsp里面不用每次导入一个就在写一遍，这里可以获取到属性值
    //2因为这个js不登陆时不导入就行，所以在内部用EL表达式，判断是否导入此JS就可


    var loadnotify = function () {
        $.post("/notify",function(json){//不需要传值，只需要获取值（未读信息数量）就行
            if(json.state == "success" && json.data > 0){//传回来一个未读通知数量,=0时不显示
                $("#unreadnum").text(json.data);
            } else if(json.state == "error"){
                alert(json.message);
            }
        });
    }
    //间歇调用有延时性，所以先执行一次
    loadnotify();
    //间歇调用,并且只有登录状态去发送请求，不再登录状态就不执行，需要判断session
    setInterval(loadnotify,5*1000);
/*    if($("#loadnotify").attr("rel")){
        //登录时才执行发送请求函数
        setInterval(loadnotify,5*1000);
    }*/

});
