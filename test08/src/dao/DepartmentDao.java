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

public class DepartmentDao extends BaseDao {

	public List<Department> search() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;

		// 从数据库中拿到数据
		List<Department> list = new ArrayList<Department>();

		// 2 利用反射，加载数据库驱动
		try {
			conn = getConnection();
			// 4 建立statement sql语句执行器
			stat = conn.createStatement();
			// 5 执行sql语句并得到结果
			String sql = "select * from department";
			rs = stat.executeQuery(sql);
			// 6 对结果集进行处理
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmpCount(rs.getInt("emp_Count"));
				list.add(dep);
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
		public int searchCount(Department condition) {
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
				if (condition.getEmpCount() != -1) {
					where += "and emp_Count='" + condition.getEmpCount() + "'";
				}

				// 5 执行sql语句并得到结果
				String sql = "select count(*) from department" + where;
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
				String sql = "select count(*) from department";
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
		public List<Department> search(Department condition, int begin, int size) {
			Connection conn = null;
			Statement stat = null;
			ResultSet rs = null;

			// 从数据库中拿到数据
			List<Department> list = new ArrayList<Department>();

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
				if (condition.getEmpCount() != -1) {
					where += "and emp_Count='" + condition.getEmpCount() + "'";
				}

				// 5 执行sql语句并得到结果
				String sql = "select * from department " + where + "limit " + begin + "," + size;
				rs = stat.executeQuery(sql);
				// 6 对结果集进行处理
				while (rs.next()) {

					Department dep = new Department();
					dep.setId(rs.getInt("id"));
					dep.setName(rs.getString("name"));
					dep.setEmpCount(rs.getInt("emp_Count"));

					list.add(dep);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeAll(conn, stat, rs);
			}

			return list;
		}

	public boolean add(Department dep) {
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
			String sql = "insert into department(name) values(?)";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dep.getName());

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
	
	
	public Department search(int id) {
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;

		// 从数据库中拿到数据
		Department dep = new Department();

		// 2 利用反射，加载数据库驱动
		try {
			conn = getConnection();
			// 4 建立statement sql语句执行器
			Statement stat = conn.createStatement();
			// 5 执行sql语句并得到结果
			String sql = "select * from department where id =" + id;
			rs = stat.executeQuery(sql);
			// 6 对结果集进行处理
			while (rs.next()) {

				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmpCount(rs.getInt("emp_Count"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, rs);
		}

		return dep;
	}

	public boolean delete(int id) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;
		// 2 利用反射，加载数据库驱动
		try {

			conn = getConnection();
			// 4 建立statement sql语句执行器
			String sql = "delete from department where id = ?";
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

	public boolean update(Department dep) {
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
			String sql = "update department set name = ? where id = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dep.getName());
			pstat.setInt(2, dep.getId());
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
