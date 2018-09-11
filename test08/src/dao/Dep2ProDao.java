package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import entity.Project;

/**
 * @author yueyulei
 * @version ����ʱ�䣺2018��8��8�� ����3:15:35
 * @Class ����˵����
 */
public class Dep2ProDao extends BaseDao {

	public List<Project> searchByDepartment(int depId) {
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;

		// �����ݿ����õ�����
		List<Project> list = new ArrayList<Project>();

		// 2 ���÷��䣬�������ݿ�����
		try {
			conn = getConnection();
			// 4 ����statement sql���ִ����
			Statement stat = conn.createStatement();
			// 5 ִ��sql��䲢�õ����

			rs = stat.executeQuery("select * from v_dep_pro where d_id=" + depId);
			// 6 �Խ�������д���
			while (rs.next()) {
				Project pro = new Project();
				pro.setId(rs.getInt("p_id"));
				pro.setName(rs.getString("p_name"));
				list.add(pro);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, rs);
		}

		return list;
	}

	public List<Project> searchByNotDepartment(int depId) {
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;

		// �����ݿ����õ�����
		List<Project> list = new ArrayList<Project>();

		// 2 ���÷��䣬�������ݿ�����
		try {
			conn = getConnection();
			// 4 ����statement sql���ִ����
			Statement stat = conn.createStatement();
			// 5 ִ��sql��䲢�õ����
			String sql = "select * from project where id not in (select p_id from v_dep_pro where d_id=" + depId + ")";
			rs = stat.executeQuery(sql);
			// 6 �Խ�������д���
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
			closeAll(conn, pstat, rs);
		}

		return list;
	}

	public boolean add(int depId, int proId) {

		Connection conn = null;
		PreparedStatement pstat = null;
		int rs = 0;
		// �����ݿ����õ�����
		List<Project> list = new ArrayList<Project>();

		// 2 ���÷��䣬�������ݿ�����
		try {
			conn = getConnection();
			// 4 ����statement sql���ִ����
			Statement stat = conn.createStatement();
			// 5 ִ��sql��䲢�õ����
			String sql = "insert into r_dep_pro(d_id, p_id) values(" + depId + "," + proId + ") ";
			rs = stat.executeUpdate(sql);
			// 6 �Խ�������д���

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}

		return rs > 0;// ����boolean
	}

	public boolean add(Connection conn, int depId, int proId) {

		PreparedStatement pstat = null;
		int rs = 0;
		// �����ݿ����õ�����
		List<Project> list = new ArrayList<Project>();

		// 2 ���÷��䣬�������ݿ�����
		try {
			// 4 ����statement sql���ִ����
			Statement stat = conn.createStatement();
			// 5 ִ��sql��䲢�õ����
			String sql = "insert into r_dep_pro(d_id, p_id) values(" + depId + "," + proId + ") ";
			rs = stat.executeUpdate(sql);
			// 6 �Խ�������д���

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(null, pstat, null);
		}

		return rs > 0;// ����boolean
	}

	public boolean addBatch(int depId, String[] proIds) {
		//����
		Connection conn = null;
		int rs = 1;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			for (int i = 0; i < proIds.length; i++) {
				add(conn, depId, Integer.parseInt(proIds[i]));
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rs = 0;
		} finally {
			closeAll(conn, null, null);
		}

		return rs > 0;// ����boolean
	}
	
	
	public boolean deleteBatch(int depId, String[] proIds) {
		//����
		Connection conn = null;
		int rs = 1;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			for (int i = 0; i < proIds.length; i++) {
				delete(conn, depId, Integer.parseInt(proIds[i]));
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rs = 0;
		} finally {
			closeAll(conn, null, null);
		}

		return rs > 0;// ����boolean
	}
	
	
	public boolean delete(Connection conn, int depId, int proId) {

		Statement stat = null;
		int rs = 0;
		// �����ݿ����õ�����
		List<Project> list = new ArrayList<Project>();

		// 2 ���÷��䣬�������ݿ�����
		try {
			// 4 ����statement sql���ִ����
			stat = conn.createStatement();
			// 5 ִ��sql��䲢�õ����
			String sql = "delete from r_dep_pro where d_id = " + depId + " and p_id= " + proId + " ";
			rs = stat.executeUpdate(sql);
			// 6 �Խ�������д���

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(null, stat, null);
		}

		return rs > 0;// ����boolean
	}


	public boolean delete(int depId, int proId) {

		Connection conn = null;
		PreparedStatement pstat = null;
		int rs = 0;
		// �����ݿ����õ�����
		List<Project> list = new ArrayList<Project>();

		// 2 ���÷��䣬�������ݿ�����
		try {
			conn = getConnection();
			// 4 ����statement sql���ִ����
			Statement stat = conn.createStatement();

			// 5 ִ��sql��䲢�õ����
			String sql = "delete from r_dep_pro where d_id = " + depId + " and p_id= " + proId + " ";
			rs = stat.executeUpdate(sql);
			// 6 �Խ�������д���

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}

		return rs > 0;// ����boolean
	}


}
