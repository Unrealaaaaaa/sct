package com.wyn.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.wyn.entity.Admin;
import com.wyn.utils.PropertiesUtils;

/**
 * 会使用DBUtils工具类
 * 
 * @author Administrator
 *
 */
public class AdminDao {

	
	//增加
	public void add(Admin admin) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "insert into admin(userName,pwd,name) values(?,?,?)";
		queryRunner.update(sql, admin.getUserName(), admin.getPwd(), admin.getName());
	}
	
	//删除
	public void delete(Integer id) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "delete from admin where id = ?";
		queryRunner.update(sql, id);
	}
	
	//更新所有信息
	public void update(Admin admin) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "update admin set userName = ?,pwd = ?,name = ? where id = ?";
		queryRunner.update(sql, admin.getUserName(), admin.getPwd(), admin.getName(), admin.getId());
	}
	
	//按Id更改密码
	public void update(String pwd,Integer id) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "update  admin set pwd = ? where id = ? ";
		queryRunner.update(sql, pwd,id);
	}
	
	//列表
	public List<Admin> list(Admin admin) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from admin";
		List<Admin> list = queryRunner.query(sql, new BeanListHandler<>(Admin.class));
		return list;
	}
	
	//按Id来展示列表
	public Admin findById(Integer id) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from admin where id = ?";
		Admin admin = queryRunner.query(sql,new BeanHandler<>(Admin.class),id);
		return admin;
	}
	
	//登录
	public Admin login(Admin admin) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from admin where username = ? and pwd = ?";
		Admin entity = queryRunner.query(sql, new BeanHandler<>(Admin.class),admin.getUserName(),admin.getPwd());
		return entity;
	}
	
}
