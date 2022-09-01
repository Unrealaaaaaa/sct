package com.wyn.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*编码过滤器，用于将所有的字符都定义为utf-8,防止出现乱码问题*/
@WebFilter(urlPatterns= {"/*"})
public class CharacterEncodingFilter implements Filter {

	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		httpServletRequest.setCharacterEncoding("utf-8");
		chain.doFilter(httpServletRequest, httpServletResponse);
		httpServletResponse.setContentType("text/html;charset=utf-8");
	}
	
	

	@Override
	public void destroy() {

	}

}
