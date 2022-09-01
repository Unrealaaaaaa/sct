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

/*在拦截所有请求的同时，给所有的请求加上basePath（项目的全路径）*/
@WebFilter(urlPatterns= {"/*"})
public class PathFilter implements Filter {

	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		httpServletRequest.setAttribute("basePath", PathUtils.getBasePath(httpServletRequest));
		chain.doFilter(httpServletRequest, httpServletResponse);
	}
	
	@Override
	public void destroy() {

	}

}
