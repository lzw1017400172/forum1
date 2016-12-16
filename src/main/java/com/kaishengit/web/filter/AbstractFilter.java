package com.kaishengit.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by 刘忠伟 on 2016/12/16.
 */
public abstract class AbstractFilter implements Filter {//必须实现其方法，除非自己也写成抽象方法,有抽象方法的类必须是抽象类
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }



    public abstract void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;

    @Override
    public void destroy() {

    }




}
