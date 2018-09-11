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

		// �����ݿ����õ�����
		List<Department> list = new ArrayList<Department>();

		// 2 ���÷��䣬�������ݿ�����
		try {
			conn = getConnection();
			// 4 ����statement sql���ִ����
			stat = conn.createStatement();
			// 5 ִ��sql��䲢�õ����
			String sql = "select * from department";
			rs = stat.executeQuery(sql);
			// 6 �Խ�������д���
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
	
	
	// ����ҳ�ķ�ҳ����
		public int searchCount(Department condition) {
			Connection conn = null;
			Statement stat = null;
			ResultSet rs = null;
			int count = 0;

			// �����ݿ����õ�����

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
					where += "and name='" + condition.getName() + "'";
				}
				if (condition.getEmpCount() != -1) {
					where += "and emp_Count='" + condition.getEmpCount() + "'";
				}

				// 5 ִ��sql��䲢�õ����
				String sql = "select count(*) from department" + where;
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
	
	
	// ��ѯ������
		public int searchCount() {
			Connection conn = null;
			PreparedStatement pstat = null;
			ResultSet rs = null;
			int count = 0;

			// �����ݿ����õ�����

			// 2 ���÷��䣬�������ݿ�����
			try {
				conn = getConnection();
				// 4 ����statement sql���ִ����
				Statement stat = conn.createStatement();
				// 5 ִ��sql��䲢�õ����
				String sql = "select count(*) from department";
				rs = stat.executeQuery(sql);
				// 6 �Խ�������д���
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

		// ����
		public List<Department> search(Department condition, int begin, int size) {
			Connection conn = null;
			Statement stat = null;
			ResultSet rs = null;

			// �����ݿ����õ�����
			List<Department> list = new ArrayList<Department>();

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
					where += "and name='" + condition.getName() + "'";
				}
				if (condition.getEmpCount() != -1) {
					where += "and emp_Count='" + condition.getEmpCount() + "'";
				}

				// 5 ִ��sql��䲢�õ����
				String sql = "select * from department " + where + "limit " + begin + "," + size;
				rs = stat.executeQuery(sql);
				// 6 �Խ�������д���
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
		// 2 ���÷��䣬�������ݿ�����
		try {

			conn = getConnection();
			// 4 ����statement sql���ִ����

			// 5 ִ��sql��䲢�õ����
			// �õ��ַ���ƴ��
			String sql = "insert into department(name) values(?)";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dep.getName());

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
	
	
	public Department search(int id) {
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;

		// �����ݿ����õ�����
		Department dep = new Department();

		// 2 ���÷��䣬�������ݿ�����
		try {
			conn = getConnection();
			// 4 ����statement sql���ִ����
			Statement stat = conn.createStatement();
			// 5 ִ��sql��䲢�õ����
			String sql = "select * from department where id =" + id;
			rs = stat.executeQuery(sql);
			// 6 �Խ�������д���
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
		// 2 ���÷��䣬�������ݿ�����
		try {

			conn = getConnection();
			// 4 ����statement sql���ִ����
			String sql = "delete from department where id = ?";
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

	public boolean update(Department dep) {
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
			String sql = "update department set name = ? where id = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dep.getName());
			pstat.setInt(2, dep.getId());
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
	
}
