package com.kaishengit.web.filter;

import com.kaishengit.utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by 刘忠伟 on 2016/12/19.
 */
public class LoginFilter extends AbstractFilter {

    List<String> urlList = null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String url = filterConfig.getInitParameter("url");
        urlList = Arrays.asList(url.split(","));//把数组转成一个集合
    }

    /**
     * 登录过滤器，在login时已经上传过session，所以每次请求都要进过滤器判断是否处于登录状态，需要判断的servlet不写死，用init获取
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //需要求访问的url就需要HttpServletRequest强转，向下转型
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        //访问的url
        String url = req.getRequestURI();

        //先判断请求的网址是否是需要登录的,在xml中获取的需要过滤的可能为空,空就全部通过
        if(urlList != null && urlList.contains(url)){
            //需要过滤,只有登录状态才能通过
            if(req.getSession().getAttribute("curr_user") == null){
                //重定向跳转到登录，并且登录完成后，回到被拦截前的页面url，把登陆前url的servlet当参数传过去就好
                //获取url的就是/sas这种的      login.jsp写的是登录成功跳转到之前页面，第一次登录如果指定redirect的值，也会登陆后跳转，登陆后回帖，就这样设置，登陆玩跳转/post

                //如果只获取网址，有些需要传参数，没有参数就报错，所以还需要传参数。先获取参数
                Map map = req.getParameterMap();//胡群殴所以偶参数的map集合
                //迭代map集合，需要装到set集合
                Set paramSet = map.entrySet();
                Iterator it = paramSet.iterator();
                //迭代，先判断有没有参数，没有就不跌待了，有献给url后面加一个?连接参数
                if(it.hasNext()){
                    url += "?";
                    while(it.hasNext()){
                        Map.Entry me = (Map.Entry)it.next();
                        Object key = me.getKey();
                        Object value = me.getValue();
                        //value是一个数组需要转
                        String param = "";
                        String valString[] = (String[])value;
                        for(int i =0;i<valString.length;i++){
                            param += key + "=" + valString[i] + "&";//拼接参数，结尾&用于继续循环相加
                        }
                        url += param;
                    }
                    //去掉多余的&
                    url = url.substring(0,url.length()-1);

                }


                resp.sendRedirect("/login?redirect=" + url);//jsp截取的是redirect的值，全部所以截取到的值是地址加参数
            } else {
                chain.doFilter(req,resp);
            }
        } else {
            chain.doFilter(req,resp);
        }
    }
}
