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
import com.wyn.entity.Sc;
import com.wyn.entity.Student;
import com.wyn.entity.Course;
import com.wyn.utils.MD5;
import com.wyn.utils.PageInfo;
import com.wyn.utils.PathUtils;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
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
		}else if("edit".equals(method)) {
			this.findById(request, response);
		}else if("editsubmit".equals(method)) {
			this.editsubmit(request, response);
		}else if("delete".equals(method)) {
			this.delete(request, response);
		}else if("detail".equals(method)) {
			this.detail(request, response);
		}
	}
	
	private void detail(HttpServletRequest request, HttpServletResponse response) {
		Student entity = (Student)request.getSession().getAttribute("user");
		try {
			Student student = DaoFactory.getInstance().getStudentDao().findById(entity.getStuId());
			List<Sc> list = DaoFactory.getInstance().getScDao().listBystuId(entity.getStuId());
			request.setAttribute("student", student);
			for(Sc sc:list) {
				Course c  = DaoFactory.getInstance().getCourseDao().findById(sc.getcId());
				sc.setcName(c.getcName());
			}
			request.setAttribute("list", list);
			request.getRequestDispatcher("page/student/detail.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private void editsubmit(HttpServletRequest request, HttpServletResponse response) {
		Integer stuId = Integer.parseInt(request.getParameter("stuId"));
		String stuNo = request.getParameter("stuNo");
		String stuName = request.getParameter("stuName");
		Student student = new Student();
		student.setStuName(stuName);
		student.setStuNo(stuNo);
		student.setStuId(stuId);
		try {
			DaoFactory.getInstance().getStudentDao().update(student);
			//直接重定向到列表页面
			response.sendRedirect(PathUtils.getBasePath(request)+"student?method=list");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		try {
			DaoFactory.getInstance().getStudentDao().delete(Integer.parseInt(id));
			response.sendRedirect(PathUtils.getBasePath(request) + "student?method=list");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void findById(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		try {
			Student student = DaoFactory.getInstance().getStudentDao().findById(Integer.parseInt(id));
			request.setAttribute("student",student);
			request.getRequestDispatcher("page/student/update.jsp").forward(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) {
		String stuNo = request.getParameter("stuNo");
		String stuName = request.getParameter("stuName");
		String stuPwd = request.getParameter("stuPwd");
		Student student = new Student();
		student.setStuNo(stuNo);
		student.setStuName(stuName);
		student.setStuPwd(MD5.encrypByMd5(MD5.encrypByMd5(stuPwd)));
		try {
			DaoFactory.getInstance().getStudentDao().add(student);
			/*添加完毕后直接重定向到列表页面*/
			response.sendRedirect(PathUtils.getBasePath(request) + "student?method=list");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void list(HttpServletRequest request, HttpServletResponse response) {
		//当前页码
		Integer pageNo = getIntParameter(request, "pageNo");
		
		Integer stuId = getIntParameter(request, "stuId");
		String stuName = request.getParameter("stuName");
		String stuNo = request.getParameter("stuNo");
		
		Student student = new Student();
		student.setStuId(stuId);
		student.setStuName(stuName);
		student.setStuNo(stuNo);
		
		//构造一个pageInfo对象
		PageInfo<Student> pageInfo = new PageInfo<>(pageNo);
		try {
			pageInfo =  DaoFactory.getInstance().getStudentDao().list(student,pageInfo);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		 
		try {
			request.setAttribute("pageInfo", pageInfo);
			request.setAttribute("student", student);
			request.getRequestDispatcher("page/student/list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
	}
		//不使用封装类的写法
		/*//当前页码
		Integer pageNo = getIntParameter(request, "pageNo");
		//每页显示最大记录条数
		int pageSize = 10;
		//当pageNo参数为空时，默认为第一页
		if(pageNo == null) {
			pageNo = 1;
		}
		//总记录条数
		Long totalCount = 0L;
		try {
			totalCount = DaoFactory.getInstance().getStudentDao().count(null);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//总页数
		Long totalPage = totalCount/pageSize;
		if (totalCount%pageSize != 0 || totalPage == 0) {
			totalPage++;
		}
		//下一页
		Integer nextPage = 0;
		if (nextPage < totalPage) {
			nextPage = pageNo + 1;
		}else {
			nextPage = pageNo; 
		}
		//上一页
		Integer prePage = 0;
		if (pageNo > 1 ) {
			prePage = pageNo - 1;
		}else {
			prePage = pageNo; 
		}
		
		boolean isFirstPage = false;//用来判断是否是第一页
		if(pageNo > 1) {
			isFirstPage = false;
		}else {
			isFirstPage = true;
		}
		
		boolean isLastPage = false;//用来判断是否是最后一页
		if(pageNo < totalPage) {
			isLastPage = false;
		}else {
			isLastPage = true;
		}
		
		try {
			List<Student> list = DaoFactory.getInstance().getStudentDao().list(null,pageNo,pageSize);
			request.setAttribute("pageNo", pageNo);
			request.setAttribute("isFirstPage", isFirstPage);
			request.setAttribute("isLastPage", isLastPage);
			request.setAttribute("totalCount", totalCount);
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("nextPage", nextPage);
			request.setAttribute("prePage", prePage);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("list", list);
			request.getRequestDispatcher("page/student/list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
	}*/
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


