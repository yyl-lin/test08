package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Department;
import entity.Employee;
import entity.Project;

public class ProjectDao extends BaseDao {

	public List<Project> search() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;

		// 从数据库中拿到数据
		List<Project> list = new ArrayList<Project>();

		// 2 利用反射，加载数据库驱动
		try {
			conn = getConnection();
			// 4 建立statement sql语句执行器
			stat = conn.createStatement();
			// 5 执行sql语句并得到结果
			String sql = "select * from project";
			rs = stat.executeQuery(sql);
			// 6 对结果集进行处理
			while (rs.next()) {
				Project pro = new Project();
				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("name"));
				list.add(pro);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, rs);
		}

		return list;
	}
	
	
	// 搜索页的分页查找
		public int searchCount(Project condition) {
			Connection conn = null;
			Statement stat = null;
			ResultSet rs = null;
			int count = 0;

			// 从数据库中拿到数据

			// 2 利用反射，加载数据库驱动
			try {
				conn = getConnection();
				// 4 建立statement sql语句执行器
				stat = conn.createStatement();
				/*
				 * 判断组合搜索的条件是那种情况
				 */
				String where = " where 1 = 1 ";
				if (condition.getName() != null && !condition.getName().equals("")) {
					where += "and name='" + condition.getName() + "'";
				}

				// 5 执行sql语句并得到结果
				String sql = "select count(*) from project" + where;
				rs = stat.executeQuery(sql);
				// 6 对结果集进行处理
				while (rs.next()) {
					count = rs.getInt(1);

				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeAll(conn, stat, rs);
			}

			return count;
		}
	
	
	// 查询总数据
		public int searchCount() {
			Connection conn = null;
			PreparedStatement pstat = null;
			ResultSet rs = null;
			int count = 0;

			// 从数据库中拿到数据

			// 2 利用反射，加载数据库驱动
			try {
				conn = getConnection();
				// 4 建立statement sql语句执行器
				Statement stat = conn.createStatement();
				// 5 执行sql语句并得到结果
				String sql = "select count(*) from project";
				rs = stat.executeQuery(sql);
				// 6 对结果集进行处理
				while (rs.next()) {
					count = rs.getInt(1);

				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeAll(conn, pstat, rs);
			}

			return count;
		}

		// 搜索
		public List<Project> search(Project condition, int begin, int size) {
			Connection conn = null;
			Statement stat = null;
			ResultSet rs = null;

			// 从数据库中拿到数据
			List<Project> list = new ArrayList<Project>();

			// 2 利用反射，加载数据库驱动
			try {
				conn = getConnection();
				// 4 建立statement sql语句执行器
				stat = conn.createStatement();

				/*
				 * 判断组合搜索的条件是那种情况
				 */
				String where = " where 1 = 1 ";
				if (condition.getName() != null && !condition.getName().equals("")) {
					where += "and name='" + condition.getName() + "'";
				}
				// 5 执行sql语句并得到结果
				String sql = "select * from project " + where + "limit " + begin + "," + size;
				rs = stat.executeQuery(sql);
				// 6 对结果集进行处理
				while (rs.next()) {

					Project pro = new Project();
					pro.setId(rs.getInt("id"));
					pro.setName(rs.getString("name"));

					list.add(pro);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeAll(conn, stat, rs);
			}

			return list;
		}

	public boolean add(Project pro) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;

		int rs = 0;
		// 2 利用反射，加载数据库驱动
		try {

			conn = getConnection();
			// 4 建立statement sql语句执行器

			// 5 执行sql语句并得到结果
			// 用到字符串拼接
			String sql = "insert into project(name) values(?)";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, pro.getName());

			rs = pstat.executeUpdate();
			// 6 对结果集进行处理

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return rs > 0;
	}
	
	
	public Project search(int id) {
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;

		// 从数据库中拿到数据
		Project pro = new Project();

		// 2 利用反射，加载数据库驱动
		try {
			conn = getConnection();
			// 4 建立statement sql语句执行器
			Statement stat = conn.createStatement();
			// 5 执行sql语句并得到结果
			String sql = "select * from project where id =" + id;
			rs = stat.executeQuery(sql);
			// 6 对结果集进行处理
			while (rs.next()) {

				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("name"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, rs);
		}

		return pro;
	}

	public boolean delete(int id) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;
		// 2 利用反射，加载数据库驱动
		try {

			conn = getConnection();
			// 4 建立statement sql语句执行器
			String sql = "delete from project where id = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			// 5 执行sql语句并得到结果
			int rs = pstat.executeUpdate();

			// 6 对结果集进行处理
			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return flag;

	}

	public boolean update(Project pro) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;
		int rs = 0;
		// 2 利用反射，加载数据库驱动
		try {

			conn = getConnection();
			// 4 建立statement sql语句执行器

			// 5 执行sql语句并得到结果
			// 用到字符串拼接
			String sql = "update project set name = ? where id = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, pro.getName());
			pstat.setInt(2, pro.getId());
			rs = pstat.executeUpdate();
			// 6 对结果集进行处理

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return rs > 0;
	}
	
}
