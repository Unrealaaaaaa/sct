package com.wyn.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.wyn.entity.Sc;
import com.wyn.entity.Student;
import com.wyn.utils.PropertiesUtils;

public class ScDao {
	
	public int[] add(List<Integer> cIdArray,Integer stuId) throws SQLException {
		/*方法一跟方法二的功能都是为了给sc表里面的数据清空，以确保每次选课列表
		的选课勾选情况是正确的，而方法二区别于方法一是采用了事务的方法，通过
		Connection.setAutoCommit() 方法设置数据库连接的提交模式，将默认的自动提交模式
		(如果是自动模式，执行所有的 SQL 语句，并作为单个事务被提交)更改为false,
		执行的sql语句会被聚集到事务当中，直到调用commit方法或rollback方法才会提交,而这么
		做的目的是为了防止类似于方法一那样的写法中，一但后面的代码出现错误，那么后面的代码
		将不会执行，那么就意味着只执行了清空语句，而后面的添加语句则不会执行，那么就会出现
		sc表被清空的情况，而方法二则防止了这种现象的出现*/
		/*方法一*/
		/*QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String _sql = "delete from sc where stuId = ?";
		queryRunner.update(_sql,stuId);*/
		/*方法二*/
		DataSource dataSource = PropertiesUtils.getDataSource();
		Connection connection = dataSource.getConnection();
		connection.setAutoCommit(false);
		QueryRunner queryRunner = new QueryRunner(dataSource);
		String _sql = "delete from sc where stuId = ?";
		queryRunner.update(connection,_sql, stuId);
//		System.out.println(1/0);
		Object[][] object = new Object[cIdArray.size()][2];
		//将cIdArray和stuId的值存入二维数组
		for (int i = 0; i < cIdArray.size(); i++) {
			object[i][0] = stuId;
			object[i][1] = cIdArray.get(i);
		}
		String sql = "insert into sc(stuId,cid) values(?,?)";
		int[] arr = queryRunner.batch(connection,sql, object);
		
		connection.commit();//方法二
		return arr;
	}

	public void delete(Integer scid) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "delete from sc where id = ?";
		queryRunner.update(sql, scid);
	}

public void update(String[] stuIdArr,String[] scoreArr,Integer cId) throws SQLException {
		
		DataSource dataSource = PropertiesUtils.getDataSource();
		Connection connection = dataSource.getConnection();
		connection.setAutoCommit(false);
		QueryRunner queryRunner = new QueryRunner(dataSource);
		Object[][] objects = new Object[stuIdArr.length][3];
		for(int i=0;i<stuIdArr.length;i++) {
			/*objects[i][0] = Integer.parseInt(scoreArr[i]==null?"0":scoreArr[i]) ;*/
			objects[i][0] = Integer.parseInt(scoreArr[i]);
			objects[i][1] = cId;
			objects[i][2] = Integer.parseInt(stuIdArr[i]);
		}
		String sql = "update sc set score = ? where cId = ? and stuId = ?";
		queryRunner.batch(sql,objects);
		connection.commit();
	}
	
	public List<Sc> listBystuId(Integer stuId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from sc where stuId = ?";
		List<Sc> list = queryRunner.query(sql, new BeanListHandler<>(Sc.class),stuId);
		return list;
	}
	
	public List<Student> listStudentBycId(Integer cId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select student.*,score from sc,student where sc.stuId = student.stuId and cId = ?";
		List<Student> list = queryRunner.query(sql, new BeanListHandler<>(Student.class),cId);
		return list;
	}
	
	public List<Sc> list(Sc sc) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from sc";
		List<Sc> list = queryRunner.query(sql, new BeanListHandler<>(Sc.class));
		return list;
	}

	public Sc findById(Integer scId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from sc where scId = ?";
		Sc sc = queryRunner.query(sql,new BeanHandler<>(Sc.class),scId);
		return sc;
	}
	
	//管理员成绩区间查询
	public List<Map<String, Object>> query_range() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select course.cid,cname,ifnull(bad,0) bad,ifnull(common,0) common,ifnull(good,0) good,ifnull(best,0) best" + 
				" from course" + 
				" left join (" + 
				" select cid,count(*) bad from sc where score<60 group by cid" + 
				" ) A on course.cid = A.cid" + 
				" left join (" + 
				" select cid,count(*) common from sc where score>=60 and score<=70 group by cid" + 
				" ) B on  course.cid = B.cid" + 
				" left join(" + 
				" select cid,count(*) good from sc where score>70 and score<=85 group by cid" + 
				" ) C on course.cid = C.cid" + 
				" left join (" + 
				" select cid,count(*) best from sc where score>85 and score<=100 group by cid" + 
				" ) D on course.cid =D.cid ";
		List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler());	
		return list;
	}
	
	//管理员及格率查询
	public List<Map<String, Object>> query_jgl() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select A.cid,(" + 
				" select cname from course where A.cid = course.cid " + 
				" ) cname,jgnum,allnum,round(jgnum/allnum,2)*100 jgl from (" + 
				" select cid, count(*) jgnum from sc where score>=60 group by cid " + 
				" ) A,(" + 
				" select cid, count(*) allnum from sc group by cid " + 
				" ) B where A.cid = B.cid ";
		List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler());
		return list;
		
	}
	
	//老师成绩区间查询
	public List<Map<String, Object>> query_rangeBytId(Integer tId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select course.cid,cname,ifnull(bad,0) bad,ifnull(common,0) common,ifnull(good,0) good,ifnull(best,0) best" + 
				" from course" + 
				" left join (" + 
				" select cid,count(*) bad from sc where score<60 group by cid" + 
				" ) A on course.cid = A.cid" + 
				" left join (" + 
				" select cid,count(*) common from sc where score>=60 and score<=70 group by cid" + 
				" ) B on  course.cid = B.cid" + 
				" left join(" + 
				" select cid,count(*) good from sc where score>70 and score<=85 group by cid" + 
				" ) C on course.cid = C.cid" + 
				" left join (" + 
				" select cid,count(*) best from sc where score>85 and score<=100 group by cid" + 
				" ) D on course.cid =D.cid where tid = ?";
		
		List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(),tId);
		return list;
	}
	
	//老师及格率查询
	public List<Map<String, Object>> query_jglBytId(Integer tId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from course,(" + 
				" select A.cid,jgnum,allnum,round(jgnum/allnum,2)*100 jgl from (" + 
				" select cid, count(*) jgnum from sc where score>=60 group by cid " + 
				" ) A,(" + 
				" select cid, count(*) allnum from sc group by cid " + 
				" ) B where A.cid = B.cid" + 
				" ) C where course.cid = C.cid " + 
				" and tid = ?";
		List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(),tId);
		return list;
	}
	
	
}
