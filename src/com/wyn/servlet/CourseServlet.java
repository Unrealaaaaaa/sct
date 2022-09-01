package com.wyn.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.wyn.dao.DaoFactory;
import com.wyn.entity.Course;
import com.wyn.entity.Teacher;
import com.wyn.utils.MD5;
import com.wyn.utils.PageInfo;
import com.wyn.utils.PathUtils;

@WebServlet("/course")
public class CourseServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		//如果list等于method
		if("list".equals(method)) {
			this.list(request, response);
		}else if("add".equals(method)) {
			this.add(request, response);
		}else if("v_add".equals(method)) {
			this.v_add(request, response);
		}else if("edit".equals(method)) {
			this.findById(request, response);
		}else if("editsubmit".equals(method)) {
			this.editsubmit(request, response);
		}else if("delete".equals(method)) {
			this.delete(request, response);
		}
	}
	
	private void editsubmit(HttpServletRequest request, HttpServletResponse response) {		
		String cName = request.getParameter("cName");
		Integer cId = getIntParameter(request,"cId");
		//老师Id
		Integer tId = Integer.parseInt(request.getParameter("tId"));
		Course course = new Course();
		course.setcId(cId);
		course.setcName(cName);
		//创建一个Teacher对象，然后设置Id，在用course对象的setTeacher关联一下
		Teacher teacher = new Teacher();
		teacher.settId(tId);
		course.setTeacher(teacher);
		try {
			DaoFactory.getInstance().getCourseDao().update(course);
			//直接重定向到列表页面
			response.sendRedirect(PathUtils.getBasePath(request)+"course?method=list");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		try {
			DaoFactory.getInstance().getCourseDao().delete(Integer.parseInt(id));
			response.sendRedirect(PathUtils.getBasePath(request) + "course?method=list");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void findById(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		PageInfo<Teacher> pageInfo = new PageInfo<>(1);
		pageInfo.setPageSize(1000);
		try {
			Course course = DaoFactory.getInstance().getCourseDao().findById(Integer.parseInt(id));
			pageInfo = DaoFactory.getInstance().getTeacherDao().list(null, pageInfo);
			request.setAttribute("course",course);
			request.setAttribute("teachers", pageInfo.getList());
			request.getRequestDispatcher("page/course/update.jsp").forward(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) {
		String cName = request.getParameter("cName");
		//老师Id
		Integer tId = Integer.parseInt(request.getParameter("tId"));
		Course course = new Course();
		course.setcName(cName);
		//创建一个Teacher对象，然后设置Id，在用course对象的setTeacher关联一下
		Teacher teacher = new Teacher();
		teacher.settId(tId);
		course.setTeacher(teacher);
		try {
			DaoFactory.getInstance().getCourseDao().add(course);
			/*添加完毕后直接重定向到列表页面*/
			response.sendRedirect(PathUtils.getBasePath(request) + "course?method=list");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void v_add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PageInfo<Teacher> pageInfo = new PageInfo<>(1);
		pageInfo.setPageSize(1000);
		try {
			 pageInfo = DaoFactory.getInstance().getTeacherDao().list(null, pageInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("teachers", pageInfo.getList());
		request.getRequestDispatcher("page/course/add.jsp").forward(request, response);
	}
	
	private void list(HttpServletRequest request, HttpServletResponse response) {
		//当前页码
		Integer pageNo = getIntParameter(request, "pageNo");
		//查询条件
		Integer cId = getIntParameter(request, "cId");
		String cName = request.getParameter("cName");
		String tName = request.getParameter("tName");
		String userName = request.getParameter("userName");
		
		Course course = new Course();
		course.setcId(cId);
		course.setcName(cName);
		Teacher teacher = new Teacher();
		teacher.settName(tName);
		teacher.setUserName(userName);
		course.setTeacher(teacher);
		//构造一个pageInfo对象
		PageInfo<Course> pageInfo = new PageInfo<>(pageNo);
		try {
			pageInfo =  DaoFactory.getInstance().getCourseDao().list(course,pageInfo);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		 
		try {
			request.setAttribute("pageInfo", pageInfo);
			request.setAttribute("course", course);
			request.getRequestDispatcher("page/course/list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
	}
   }
		
	//对getParameter进行一个简单的封装，判断是否为空
	public Integer getIntParameter(HttpServletRequest request, String name) {
		if (StringUtils.isNotBlank(request.getParameter(name))) {
			return Integer.parseInt(request.getParameter(name));
		}else {
			return null;
		}
	}
}


