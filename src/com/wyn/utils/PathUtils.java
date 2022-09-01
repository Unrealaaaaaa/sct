package com.wyn.utils;

import javax.servlet.http.HttpServletRequest;

public class PathUtils {
	/*定义一个可供所有文件调用的工具类（获取项目的全路径）*/
	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() +"://" + request.getServerName() +":"+request.getServerPort()+path+"/";
		return basePath;
	}

}
