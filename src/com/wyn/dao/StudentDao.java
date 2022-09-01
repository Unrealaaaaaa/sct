package com.wyn.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import com.wyn.entity.Student;
import com.wyn.utils.PageInfo;
import com.wyn.utils.PropertiesUtils;

public class StudentDao {
	
	public void add(Student student) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "insert into student(stuName,stuNo,stuPwd) values(?,?,?)";
		queryRunner.update(sql, student.getStuName(), student.getStuNo(), student.getStuPwd());
	}

	public void delete(Integer stuId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "delete from student where stuId = ?";
		queryRunner.update(sql, stuId);
	}
	//更改所有的学生信息
	public void update(Student student) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "update student set stuName = ?,stuNo = ? where stuId = ?";
		queryRunner.update(sql, student.getStuName(), student.getStuNo(), student.getStuId());
	}
	//更改密码
	public void update(String pwd,Integer stuId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "update  student set stuPwd = ? where stuId = ? ";
		queryRunner.update(sql, pwd,stuId);
	}
	

	/*public List<Student> list(Student student, Integer pageNo, Integer pageSize) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from student limit "+(pageNo - 1)*pageSize+","+pageSize;
		List<Student> list = queryRunner.query(sql, new BeanListHandler<>(Student.class));
		return list;
	}*/
	
	//分页dao改造
	public PageInfo<Student> list(Student student, PageInfo<Student> pageInfo) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		/*判断条件组合查询里面是否包含stuId、stuName、stuNo这三个条件*/
		String _sql = "";
		List<Object> _list = new ArrayList<Object>();
		if(student.getStuId() != null) {
			_sql += " and STUID = ?";
			_list.add(student.getStuId());
		}
		if(StringUtils.isNoneBlank(student.getStuName())) {
			_sql += " and STUNAME like ?";
			_list.add("%"+student.getStuName()+"%");
		}
		if(StringUtils.isNoneBlank(student.getStuNo())) {
			_sql += " and STUNO like ?";
			_list.add("%"+student.getStuNo()+"%");
		}
		//list转数组
		Object[] arr = new Object[_list.size()];
		for(int i=0; i<_list.size(); i++) {
			arr[i] = _list.get(i);
		}
		/*由于无法确定_sql中的条件组合查询包含那几个，因此为了确保查询语句不出现语法错误，
		在_sql每个查询条件前都加上and,同时在where后面加上一个 1=1恒等式，确保加入_sql
		条件后不会因为每个条件查询前都有and出现语句错误*/
		String sql = "select * from student where 1=1 "+_sql+" limit "+(pageInfo.getPageNo() - 1)*pageInfo.getPageSize()+","+pageInfo.getPageSize();
		List<Student> list = queryRunner.query(sql, new BeanListHandler<>(Student.class),arr);
		pageInfo.setList(list);
		pageInfo.setTotalCount(this.count(student));
		return pageInfo;
	}
	//组合列表查询结果的总数查询
	public Long count(Student student) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String _sql = "";
		List<Object> _list = new ArrayList<>();
		if(student.getStuId() != null) {
			_sql += " and STUID = ?";
			_list.add(student.getStuId());
		}
		if(StringUtils.isNotBlank(student.getStuName())) {
			_sql += " and STUNAME like ?";
			_list.add("%"+student.getStuName()+"%");
		}
		if(StringUtils.isNotBlank(student.getStuNo())) {
			_sql += " and STUNO like ?";
			_list.add("%"+student.getStuNo()+"%");
		}
		//list转数组
		Object[] arr = new Object[_list.size()];
		for(int i=0; i<_list.size(); i++) {
			arr[i] = _list.get(i);
		}
		String sql = "select count(*) from student where 1=1" + _sql;
		Long count = (Long)queryRunner.query(sql,new ScalarHandler(),arr);
		return count;
	}
	
	public Student findById(Integer sId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from student where STUID = ?";
		Student student = queryRunner.query(sql,new BeanHandler<>(Student.class),sId);
		return student;
	}
	
	public Student login(String stuNo, String stuPwd) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from student where stuNo = ? and stuPwd = ?";
		Student student = queryRunner.query(sql,new BeanHandler<>(Student.class),stuNo,stuPwd);
		return student;
	}

}
