package com.kaishengit.web.filter;

import com.kaishengit.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by 刘忠伟 on 2016/12/16.
 * encoding编码，这个过滤器解决编码问题，过滤器需要配置
 */
public class EncodingFilter extends AbstractFilter {

    private String encoding = "UTF-8";//默认UTF-8zaixml中可以改
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //获取filter配置中的值，init-param
        String encoding = filterConfig.getInitParameter("encoding");
        Logger logger = LoggerFactory.getLogger(EncodingFilter.class);
        logger.debug("{}",encoding);
        if(StringUtils.isNotEmpty(encoding)){
            this.encoding = encoding;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        request.setCharacterEncoding(encoding);//UTF-8不能写为硬编码
        response.setCharacterEncoding(encoding);//近来何处去都要过滤，字符编码
        chain.doFilter(request,response);
    }
}
