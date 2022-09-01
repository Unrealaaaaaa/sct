/*package com.wyn.utils;

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

//过滤掉不符合以下信息的访问请求
@WebFilter(urlPatterns= {"/*"},initParams = {
		@WebInitParam(name = "exclude", value = "/login.jsp,/login,/noprivilige.jsp,.css,.png,.jpg,.js")
})
public class PermissionFilter implements Filter {
	//定义一个全局变量以获取值
	public static String excludeString ;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		excludeString = filterConfig.getInitParameter("exclude");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		
		Object user = httpServletRequest.getSession().getAttribute("user");
		String uri = httpServletRequest.getRequestURI();
		if(isExist(uri) || uri.equals(httpServletRequest.getContextPath()+"/")) {
			//获取登录信息
			chain.doFilter(httpServletRequest, httpServletResponse);
		}else {
		//如果不为空，则放行 ，空 则跳转到错误页面
			if(user != null) {
				chain.doFilter(httpServletRequest, httpServletResponse);
			}else {
				httpServletResponse.sendRedirect("noprivilige.jsp");
			}
		}
	}
	
	public static boolean isExist(String uri) {
		//最后URI的结尾与exclude匹配
		//将exclude转化为数组，方便匹配
		String[] arr = excludeString.split(",");
		boolean flag = false;
		for (String string : arr) {
			if(uri.endsWith(string)) {
				flag = true;
			}
		}
		return flag;
	}
	

	@Override
	public void destroy() {

	}

}
*/