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

/**
 * @author yueyulei
 * @version 创建时间：2018年7月30日 下午2:00:41
 * @Class 具体说明：
 */
public class EmployeeDao extends BaseDao {

	public List<Employee> search() {
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;

		// 1 导入对应的数据库jar包
		List<Employee> list = new ArrayList<Employee>();

		// 2 利用反射，加载数据库驱动
		try {
			conn = getConnection();
			// 4 建立statement sql语句执行器
			Statement stat = conn.createStatement();
			// 5 执行sql语句并得到结果
			String sql = "select e.*,d.name as dName,emp_count from employee as e left join department as d on e.d_id=d.id";
			rs = stat.executeQuery(sql);
			// 6 对结果集进行处理
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("dName"));
				dep.setEmpCount(rs.getInt("emp_count"));
				emp.setDep(dep);
				list.add(emp);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, rs);
		}

		return list;
	}

	// 分页查询
	public List<Employee> search(int begin, int size) {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;

		// 1 导入对应的数据库jar包
		List<Employee> list = new ArrayList<Employee>();

		// 2 利用反射，加载数据库驱动
		try {
			conn = getConnection();
			// 4 建立statement sql语句执行器
			stat = conn.createStatement();
			// 5 执行sql语句并得到结果
			String sql = "select e.*,d.name as dName,emp_count from employee as e left join department as d on e.d_id=d.id limit "
					+ begin + "," + size;
			rs = stat.executeQuery(sql);
			// 6 对结果集进行处理
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("dName"));
				dep.setEmpCount(rs.getInt("emp_count"));
				emp.setDep(dep);
				list.add(emp);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, rs);
		}

		return list;
	}

	// 查询总数据
	public int searchCount() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		int count = 0;

		// 1 导入对应的数据库jar包

		// 2 利用反射，加载数据库驱动
		try {
			conn = getConnection();
			// 4 建立statement sql语句执行器
			stat = conn.createStatement();
			// 5 执行sql语句并得到结果
			String sql = "select count(*) from employee";
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

	// 搜索
	public List<Employee> search(Employee condition, int begin, int size) {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;

		// 1 导入对应的数据库jar包
		List<Employee> list = new ArrayList<Employee>();

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
				where += "and e.name='" + condition.getName() + "'";
			}
			if (condition.getSex() != null && !condition.getSex().equals("")) {
				where += "and sex='" + condition.getSex() + "'";
			}
			if (condition.getAge() != -1 && condition.getAge() != -1) {
				where += "and age='" + condition.getAge() + "'";
			}
			if (condition.getDep() != null && condition.getDep().getId() != -1) {
				where += "and d_id='" + condition.getDep().getId() + "'";
			}

			// 5 执行sql语句并得到结果
			String sql = "select e.*,d.name as depName from employee as e left join department as d on e.d_id=d.id "
					+ where + "limit " + begin + "," + size;
			rs = stat.executeQuery(sql);
			// 6 对结果集进行处理
			while (rs.next()) {

				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				emp.setPhoto(rs.getString("photo"));

				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("depName"));
				emp.setDep(dep);
				list.add(emp);
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
	public int searchCount(Employee condition) {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		int count = 0;

		// 1 导入对应的数据库jar包

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
				where += "and e.name='" + condition.getName() + "'";
			}
			if (condition.getSex() != null && !condition.getSex().equals("")) {
				where += "and sex='" + condition.getSex() + "'";
			}
			if (condition.getAge() != -1) {
				where += "and age='" + condition.getAge() + "'";
			}
			if (condition.getDep() != null && condition.getDep().getId() != -1) {
				where += "and d_id='" + condition.getDep().getId() + "'";
			}

			// 5 执行sql语句并得到结果
			String sql = "select count(*) from employee as e left join department as d on e.d_id=d.id " + where;
			// System.out.println(sql);
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

	public Employee search(int id) {
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;

		// 1 导入对应的数据库jar包
		Employee emp = new Employee();

		// 2 利用反射，加载数据库驱动
		try {
			conn = getConnection();
			// 4 建立statement sql语句执行器
			Statement stat = conn.createStatement();
			// 5 执行sql语句并得到结果
			String sql = "select e.*, d.name as depName from employee as e left join department as d on e.d_id=d.id where e.id ="
					+ id;
			rs = stat.executeQuery(sql);
			// 6 对结果集进行处理
			while (rs.next()) {

				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));

				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("depName"));
				emp.setDep(dep);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, rs);
		}

		return emp;
	}

	public List<Employee> search(String ids) {
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;

		// 1 导入对应的数据库jar包
		List<Employee> list = new ArrayList<>();

		// 2 利用反射，加载数据库驱动
		try {
			conn = getConnection();
			// 4 建立statement sql语句执行器
			Statement stat = conn.createStatement();
			// 5 执行sql语句并得到结果
			String sql = "select * from employee where id in(" + ids + ")";
			rs = stat.executeQuery(sql);
			// 6 对结果集进行处理
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				list.add(emp);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, rs);
		}

		return list;
	}

	public boolean add(Employee emp) {
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
			String sql = "insert into employee(name, sex, age, d_id, photo) values(?,?,?,?,?)";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, emp.getName());
			pstat.setString(2, emp.getSex());
			pstat.setInt(3, emp.getAge());
			// Object解决空指针异常Integet的父类
			pstat.setObject(4, emp.getDep().getId());
			pstat.setString(5, emp.getPhoto());

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

	public boolean delete(int id) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;
		// 2 利用反射，加载数据库驱动
		try {

			conn = getConnection();
			// 4 建立statement sql语句执行器
			String sql = "delete from employee where id = ?";
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

	public boolean update(Employee emp) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;
		int rs = 0;
		
		try {
			// 2 利用反射，加载数据库驱动
			// 3 建立连接
			conn = getConnection();
			// 4 建立statement sql语句执行器

			// 5 执行sql语句并得到结果
			// 用到字符串拼接
			String sql = "update employee set name = ?, sex = ?, age = ?, d_id = ? where id = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, emp.getName());
			pstat.setString(2, emp.getSex());
			pstat.setInt(3, emp.getAge());
			pstat.setObject(4, emp.getDep().getId());
			pstat.setInt(5, emp.getId());
			
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

	public boolean deleteBatch(String ids) {
		boolean flag = false;
		Connection conn = null;
		Statement stat = null;
		// 2 利用反射，加载数据库驱动
		try {

			conn = getConnection();
			// 4 建立statement sql语句执行器
			stat = conn.createStatement();
			String sql = "delete from employee where id in(" + ids + ")";
			stat = conn.createStatement();
			// 5 执行sql语句并得到结果
			int rs = stat.executeUpdate(sql);

			// 6 对结果集进行处理
			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, null);
		}
		return flag;

	}

	public boolean updateBatch1(String ids, Employee emp) {
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
			String sql = "update employee set name = ?, sex = ?, age = ? where id in(" + ids + ")";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, emp.getName());
			pstat.setString(2, emp.getSex());
			pstat.setInt(3, emp.getAge());
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

	public boolean updateBatch2(List<Employee> list) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;
		int rs = 0;
		// 2 利用反射，加载数据库驱动
		try {

			conn = getConnection();

			for (int i = 0; i < list.size(); i++) {

				Employee emp = list.get(i);
				String sql = "update employee set name = ?, sex = ?, age = ? where id = ?";
				pstat = conn.prepareStatement(sql);
				pstat.setString(1, emp.getName());
				pstat.setString(2, emp.getSex());
				pstat.setInt(3, emp.getAge());
				pstat.setInt(4, emp.getId());
				rs = pstat.executeUpdate();
				// 6 对结果集进行处理
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return rs > 0;
	}

}
