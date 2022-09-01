package com.wyn.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.wyn.dao.DaoFactory;
import com.wyn.entity.Course;
import com.wyn.entity.Sc;
import com.wyn.entity.Student;
import com.wyn.entity.Teacher;
import com.wyn.utils.MD5;
import com.wyn.utils.PageInfo;
import com.wyn.utils.PathUtils;

@WebServlet("/scquery")
public class SCQueryServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if("query_range".equals(method)) {
			this.query_range(request,response);
		}else if("query_jgl".equals(method)) {
			this.query_jgl(request,response);
		}else if("query_teacher".equals(method)) {
			this.query_jglBytId(request,response);
		}
	}
	
	private void query_jglBytId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Teacher teacher = (Teacher)request.getSession().getAttribute("user");
		try {
			List<Map<String, Object>> list_1 = DaoFactory.getInstance().getScDao().query_rangeBytId(teacher.gettId());
			request.setAttribute("list_1", list_1);
			List<Map<String, Object>> list_2 = DaoFactory.getInstance().getScDao().query_jglBytId(teacher.gettId());
			request.setAttribute("list_2", list_2);
			request.getRequestDispatcher("page/sc/query_teacher.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void query_range(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			List<Map<String, Object>> list = DaoFactory.getInstance().getScDao().query_range();
			request.setAttribute("list", list);
			request.getRequestDispatcher("page/sc/query_range.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void query_jgl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			List<Map<String, Object>> list = DaoFactory.getInstance().getScDao().query_jgl();
			request.setAttribute("list", list);
			request.getRequestDispatcher("page/sc/query_jgl.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}


