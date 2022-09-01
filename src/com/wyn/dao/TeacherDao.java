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
import com.wyn.entity.Teacher;
import com.wyn.utils.PageInfo;
import com.wyn.utils.PropertiesUtils;

public class TeacherDao {
	
	public void add(Teacher teacher) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "insert into teacher(tName,userName,pwd) values(?,?,?)";
		queryRunner.update(sql, teacher.gettName(), teacher.getUserName(), teacher.getPwd());
	}

	public void delete(Integer tId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "delete from teacher where tId = ?";
		queryRunner.update(sql, tId);
	}

	public void update(Teacher teacher) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "update teacher set tName = ?,USERNAME = ? where tId = ?";
		queryRunner.update(sql, teacher.gettName(), teacher.getUserName(),teacher.gettId());
	}
	
	public void update(String pwd,Integer tId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "update  teacher set PWD = ? where tId = ? ";
		queryRunner.update(sql, pwd,tId);
	}
	

	public PageInfo<Teacher> list(Teacher teacher,PageInfo<Teacher> pageInfo) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		/*判断条件组合查询里面是否包含stuId、stuName、stuNo这三个条件*/
		String _sql = "";
		List<Object> _list = new ArrayList<Object>();
		if(teacher != null && teacher.gettId() != null) {
			_sql += " and TID = ?";
			_list.add(teacher.gettId());
		}
		if(teacher != null &&StringUtils.isNotBlank(teacher.gettName())) {
			_sql += " and TNAME like ?";
			_list.add("%"+teacher.gettName()+"%");
		}
		if(teacher != null &&StringUtils.isNotBlank(teacher.getUserName())) {
			_sql += " and USERNAME like ?";
			_list.add("%"+teacher.getUserName()+"%");
		}
		//list转数组
		Object[] arr = new Object[_list.size()];
		for(int i=0; i<_list.size(); i++) {
			arr[i] = _list.get(i);
		}
		/*由于无法确定_sql中的条件组合查询包含那几个，因此为了确保查询语句不出现语法错误，
		在_sql每个查询条件前都加上and,同时在where后面加上一个 1=1恒等式，确保加入_sql
		条件后不会因为每个条件查询前都有and出现语句错误*/
		String sql = "select * from teacher where 1=1 "+_sql+" limit "+(pageInfo.getPageNo() - 1)*pageInfo.getPageSize()+","+pageInfo.getPageSize();
		List<Teacher> list = queryRunner.query(sql, new BeanListHandler<>(Teacher.class),arr);
		pageInfo.setList(list);
		pageInfo.setTotalCount(this.count(teacher));
		return pageInfo;
	}
	//组合列表查询 
	public Long count(Teacher teacher) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String _sql = "";
		List<Object> _list = new ArrayList<Object>();
		if(teacher != null &&teacher.gettId() != null) {
			_sql += " and TID = ?";
			_list.add(teacher.gettId());
		}
		if(teacher != null &&StringUtils.isNoneBlank(teacher.gettName())) {
			_sql += " and TNAME like ?";
			_list.add("%"+teacher.gettName()+"%");
		}
		if(teacher != null &&StringUtils.isNoneBlank(teacher.getUserName())) {
			_sql += " and USERNAME like ?";
			_list.add("%"+teacher.getUserName()+"%");
		}
		//list转数组
		Object[] arr = new Object[_list.size()];
		for(int i=0; i<_list.size(); i++) {
			arr[i] = _list.get(i);
		}
		String sql = "select count(*) from teacher where 1=1" + _sql;
		Long count = (Long)queryRunner.query(sql,new ScalarHandler(),arr);
		return count;
	}

	public Teacher findById(Integer tId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from teacher where tId = ?";
		Teacher teacher = queryRunner.query(sql,new BeanHandler<>(Teacher.class),tId);
		return teacher;
	}
	
	public Teacher login(String userName, String pwd) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from teacher where userName = ? and pwd = ?";
		Teacher teacher = queryRunner.query(sql,new BeanHandler<>(Teacher.class),userName,pwd);
		return teacher;
	}

}
