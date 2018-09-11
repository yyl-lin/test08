package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.Department;
import entity.Employee;
import entity.User;

public class UserDao extends BaseDao {

	public boolean search(User user) {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		boolean flag = false;
		try {

			conn = getConnection();

			String sql = "select * from user where username=? and password=?";
			// 4 建立PreparedStatement sql语句执行器
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getUsername());
			pstat.setString(2, user.getPassword());

			// 5 执行sql语句并得到结果

			rs = pstat.executeQuery();

			// 6 对结果集进行处理
			if (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, rs);
		}

		return flag;
	}
	
	

	

	public boolean add(User user) {
		Connection conn = null;
		Statement stat = null;
		int rs = 0;
		try {

			conn = getConnection();

			String sql = "insert into user(username,password) values(?,?)";
			// 4 建立PreparedStatement sql语句执行器
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getUsername());
			pstat.setString(2, user.getPassword());

			// 5 执行sql语句并得到结果

			rs = pstat.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, null);
		}

		return rs > 0;
	}


}
