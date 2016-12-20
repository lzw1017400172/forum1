package com.kaishengit.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
                //获取url的就是/sas这种的
                resp.sendRedirect("/login?redirect=" + url);
            } else {
                chain.doFilter(req,resp);
            }
        } else {
            chain.doFilter(req,resp);
        }
    }
}
