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
 * @version ����ʱ�䣺2018��7��30�� ����2:00:41
 * @Class ����˵����
 */
public class EmployeeDao extends BaseDao {

	public List<Employee> search() {
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;

		// 1 �����Ӧ�����ݿ�jar��
		List<Employee> list = new ArrayList<Employee>();

		// 2 ���÷��䣬�������ݿ�����
		try {
			conn = getConnection();
			// 4 ����statement sql���ִ����
			Statement stat = conn.createStatement();
			// 5 ִ��sql��䲢�õ����
			String sql = "select e.*,d.name as dName,emp_count from employee as e left join department as d on e.d_id=d.id";
			rs = stat.executeQuery(sql);
			// 6 �Խ�������д���
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

	// ��ҳ��ѯ
	public List<Employee> search(int begin, int size) {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;

		// 1 �����Ӧ�����ݿ�jar��
		List<Employee> list = new ArrayList<Employee>();

		// 2 ���÷��䣬�������ݿ�����
		try {
			conn = getConnection();
			// 4 ����statement sql���ִ����
			stat = conn.createStatement();
			// 5 ִ��sql��䲢�õ����
			String sql = "select e.*,d.name as dName,emp_count from employee as e left join department as d on e.d_id=d.id limit "
					+ begin + "," + size;
			rs = stat.executeQuery(sql);
			// 6 �Խ�������д���
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

	// ��ѯ������
	public int searchCount() {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		int count = 0;

		// 1 �����Ӧ�����ݿ�jar��

		// 2 ���÷��䣬�������ݿ�����
		try {
			conn = getConnection();
			// 4 ����statement sql���ִ����
			stat = conn.createStatement();
			// 5 ִ��sql��䲢�õ����
			String sql = "select count(*) from employee";
			rs = stat.executeQuery(sql);
			// 6 �Խ�������д���
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

	// ����
	public List<Employee> search(Employee condition, int begin, int size) {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;

		// 1 �����Ӧ�����ݿ�jar��
		List<Employee> list = new ArrayList<Employee>();

		// 2 ���÷��䣬�������ݿ�����
		try {
			conn = getConnection();
			// 4 ����statement sql���ִ����
			stat = conn.createStatement();

			/*
			 * �ж�����������������������
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

			// 5 ִ��sql��䲢�õ����
			String sql = "select e.*,d.name as depName from employee as e left join department as d on e.d_id=d.id "
					+ where + "limit " + begin + "," + size;
			rs = stat.executeQuery(sql);
			// 6 �Խ�������д���
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

	// ����ҳ�ķ�ҳ����
	public int searchCount(Employee condition) {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		int count = 0;

		// 1 �����Ӧ�����ݿ�jar��

		// 2 ���÷��䣬�������ݿ�����
		try {
			conn = getConnection();
			// 4 ����statement sql���ִ����
			stat = conn.createStatement();

			/*
			 * �ж�����������������������
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

			// 5 ִ��sql��䲢�õ����
			String sql = "select count(*) from employee as e left join department as d on e.d_id=d.id " + where;
			// System.out.println(sql);
			rs = stat.executeQuery(sql);
			// 6 �Խ�������д���
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

		// 1 �����Ӧ�����ݿ�jar��
		Employee emp = new Employee();

		// 2 ���÷��䣬�������ݿ�����
		try {
			conn = getConnection();
			// 4 ����statement sql���ִ����
			Statement stat = conn.createStatement();
			// 5 ִ��sql��䲢�õ����
			String sql = "select e.*, d.name as depName from employee as e left join department as d on e.d_id=d.id where e.id ="
					+ id;
			rs = stat.executeQuery(sql);
			// 6 �Խ�������д���
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

		// 1 �����Ӧ�����ݿ�jar��
		List<Employee> list = new ArrayList<>();

		// 2 ���÷��䣬�������ݿ�����
		try {
			conn = getConnection();
			// 4 ����statement sql���ִ����
			Statement stat = conn.createStatement();
			// 5 ִ��sql��䲢�õ����
			String sql = "select * from employee where id in(" + ids + ")";
			rs = stat.executeQuery(sql);
			// 6 �Խ�������д���
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
		// 2 ���÷��䣬�������ݿ�����
		try {

			conn = getConnection();
			// 4 ����statement sql���ִ����

			// 5 ִ��sql��䲢�õ����
			// �õ��ַ���ƴ��
			String sql = "insert into employee(name, sex, age, d_id, photo) values(?,?,?,?,?)";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, emp.getName());
			pstat.setString(2, emp.getSex());
			pstat.setInt(3, emp.getAge());
			// Object�����ָ���쳣Integet�ĸ���
			pstat.setObject(4, emp.getDep().getId());
			pstat.setString(5, emp.getPhoto());

			rs = pstat.executeUpdate();
			// 6 �Խ�������д���

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
		// 2 ���÷��䣬�������ݿ�����
		try {

			conn = getConnection();
			// 4 ����statement sql���ִ����
			String sql = "delete from employee where id = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			// 5 ִ��sql��䲢�õ����
			int rs = pstat.executeUpdate();

			// 6 �Խ�������д���
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
			// 2 ���÷��䣬�������ݿ�����
			// 3 ��������
			conn = getConnection();
			// 4 ����statement sql���ִ����

			// 5 ִ��sql��䲢�õ����
			// �õ��ַ���ƴ��
			String sql = "update employee set name = ?, sex = ?, age = ?, d_id = ? where id = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, emp.getName());
			pstat.setString(2, emp.getSex());
			pstat.setInt(3, emp.getAge());
			pstat.setObject(4, emp.getDep().getId());
			pstat.setInt(5, emp.getId());
			
			rs = pstat.executeUpdate();
			// 6 �Խ�������д���

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
		// 2 ���÷��䣬�������ݿ�����
		try {

			conn = getConnection();
			// 4 ����statement sql���ִ����
			stat = conn.createStatement();
			String sql = "delete from employee where id in(" + ids + ")";
			stat = conn.createStatement();
			// 5 ִ��sql��䲢�õ����
			int rs = stat.executeUpdate(sql);

			// 6 �Խ�������д���
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
		// 2 ���÷��䣬�������ݿ�����
		try {

			conn = getConnection();
			// 4 ����statement sql���ִ����

			// 5 ִ��sql��䲢�õ����
			// �õ��ַ���ƴ��
			String sql = "update employee set name = ?, sex = ?, age = ? where id in(" + ids + ")";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, emp.getName());
			pstat.setString(2, emp.getSex());
			pstat.setInt(3, emp.getAge());
			rs = pstat.executeUpdate();
			// 6 �Խ�������д���

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
		// 2 ���÷��䣬�������ݿ�����
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
				// 6 �Խ�������д���
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
