package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import java.sql.PreparedStatement;

import entity.Department;
import entity.Employee;
import entity.Project;
import entity.Score;

/**
 * @author yueyulei
 * @version ����ʱ�䣺2018��7��30�� ����2:00:41
 * @Class ����˵����
 */
public class ScoreDao extends BaseDao {

	boolean flag = false;
	Connection conn = null;
	PreparedStatement pstat = null;
	ResultSet rs = null;

	public List<Score> search() {
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;

		// �����ݿ����õ�����
		List<Score> list = new ArrayList<Score>();

		// 2 ���÷��䣬�������ݿ�����
		try {
			conn = getConnection();
			// 4 ����statement sql���ִ����
			Statement stat = conn.createStatement();
			// 5 ִ��sql��䲢�õ����
			String sql = "select * from v_dep_sc"; 
			rs = stat.executeQuery(sql);
			// 6 �Խ�������д���
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("e_id"));
				emp.setName(rs.getString("e_name"));

				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));

				Project pro = new Project();
				pro.setId(rs.getInt("p_id"));
				pro.setName(rs.getString("p_name"));

				Score sc = new Score();
				sc.setId(rs.getInt("sc_id"));
				// ����ʱΪ�ն�����0
				sc.setValue((Integer) rs.getObject("value"));
				sc.setGrade(rs.getString("grade"));

				emp.setDep(dep);
				sc.setEmp(emp);
				sc.setPro(pro);
				list.add(sc);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, rs);
		}

		return list;
	}
	
	
	public Score search(int id) {
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;

		// �����ݿ����õ�����
		Score sc = new Score();

		// 2 ���÷��䣬�������ݿ�����
		try {
			conn = getConnection();
			// 4 ����statement sql���ִ����
			Statement stat = conn.createStatement();
			// 5 ִ��sql��䲢�õ����
			String sql = "select * from v_dep_sc where sc_id=" + id;
			rs = stat.executeQuery(sql);
			// 6 �Խ�������д���
			while (rs.next()) {

				Employee emp = new Employee();
				emp.setId(rs.getInt("e_id"));
				emp.setName(rs.getString("e_name"));

				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));

				Project pro = new Project();
				pro.setId(rs.getInt("p_id"));
				pro.setName(rs.getString("p_name"));

				
				sc.setId(rs.getInt("sc_id"));
				// ����ʱΪ�ն�����0
				sc.setValue((Integer) rs.getObject("value"));
				sc.setGrade(rs.getString("grade"));

				emp.setDep(dep);
				sc.setEmp(emp);
				sc.setPro(pro);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, rs);
		}

		return sc;
	}
	
	public boolean save(List<Score> list) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;
		int rs = 0;
		// 2 ���÷��䣬�������ݿ�����
		try {

			conn = getConnection();

			for (int i = 0; i < list.size(); i++) {

				Score sc = list.get(i);
				String sql = "update score set value=? where id = ?";
				pstat = conn.prepareStatement(sql);
				pstat.setInt(1, sc.getValue());
				pstat.setInt(2, sc.getId());
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
	
	
	
	public int add(Score sc) {
		
		int id =0;
		int rs = 0;
		Connection conn = null;
		PreparedStatement pstat = null;

		// 2 ���÷��䣬�������ݿ�����
		try {
			conn = getConnection();

			// 5 ִ��sql��䲢�õ����
			String sql = "insert into score(e_id,p_id,value) values(?,?,?)";
			// 4 ����statement sql���ִ����
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, sc.getEmp().getId());
			pstat.setInt(2, sc.getPro().getId());
			pstat.setInt(3, sc.getValue());
			rs = pstat.executeUpdate();
			
			//last_insert_id()�鵽�ϴ����������ݵ�id��������ͬһ��������
			pstat.close();
			sql = "select last_insert_id() ";
			pstat = conn.prepareStatement(sql);
			ResultSet r = pstat.executeQuery();

			if(r.next()) {
				id = r.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}

		return id;
	}
	
	
	public boolean update(Score sc) {
		int rs = 0;
		Connection conn = null;
		PreparedStatement pstat = null;

		// 2 ���÷��䣬�������ݿ�����
		try {
			conn = getConnection();

			// 5 ִ��sql��䲢�õ����
			String sql = "update score set value = ? where id = ?";
			// 4 ����statement sql���ִ����
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, sc.getValue());
			pstat.setInt(2, sc.getId());
			rs = pstat.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}

		return rs > 0;
	}
	
	
	// ��ҳ��ѯ
		public List<Score> search(int begin, int size) {
			Connection conn = null;
			PreparedStatement pstat = null;
			ResultSet rs = null;

			// �����ݿ����õ�����
			List<Score> list = new ArrayList<Score>();

			// 2 ���÷��䣬�������ݿ�����
			try {
				conn = getConnection();
				// 4 ����statement sql���ִ����
				Statement stat = conn.createStatement();
				// 5 ִ��sql��䲢�õ����
				String sql = "select * from v_dep_sc limit " + begin + "," + size;
				rs = stat.executeQuery(sql);
				// 6 �Խ�������д���
				while (rs.next()) {
					Employee emp = new Employee();
					emp.setId(rs.getInt("e_id"));
					emp.setName(rs.getString("e_name"));

					Department dep = new Department();
					dep.setId(rs.getInt("d_id"));
					dep.setName(rs.getString("d_name"));

					Project pro = new Project();
					pro.setId(rs.getInt("p_id"));
					pro.setName(rs.getString("p_name"));

					Score sc = new Score();
					sc.setId(rs.getInt("sc_id"));
					// ����ʱΪ�ն�����0
					sc.setValue((Integer) rs.getObject("value"));
					sc.setGrade(rs.getString("grade"));

					emp.setDep(dep);
					sc.setEmp(emp);
					sc.setPro(pro);
					list.add(sc);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeAll(conn, pstat, rs);
			}

			return list;
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
				String sql = "select count(*) from v_dep_sc";
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
		public List<Score> search(Score condition, int begin, int size) {
			Connection conn = null;
			Statement stat = null;
			ResultSet rs = null;

			// �����ݿ����õ�����
			List<Score> list = new ArrayList<Score>();

			// 2 ���÷��䣬�������ݿ�����
			try {
				conn = getConnection();
				// 4 ����statement sql���ִ����
				stat = conn.createStatement();

				/*
				 * �ж�����������������������
				 */
				String where = " where 1 = 1 ";
				if (condition.getEmp().getName() != null && !condition.getEmp().getName().equals("")) {
					where += "and e_name='" + condition.getEmp().getName() + "'";
				}
				if (condition.getEmp().getDep().getName() != null && !condition.getEmp().getDep().getName().equals("")) {
					where += "and d_name='" + condition.getEmp().getDep().getName() + "'";
				}

				if ( condition.getPro().getName()!=null && !condition.getPro().getName().equals("")) {
					where += "and p_name='" + condition.getPro().getName() + "'";
				}
				if (condition.getValue() != -1) {
					where += "and value='" + condition.getValue() + "'";
				}
				if (condition.getGrade()!=null && !condition.getGrade().equals("")) {
					where += "and grade='" + condition.getGrade() + "'";
				}


				// 5 ִ��sql��䲢�õ����
				String sql = "select e_id, e_name, d_id, d_name,p_id, p_name, value, grade, sc_id as scId from v_dep_sc "
						+ where + "limit " + begin + "," + size;
				rs = stat.executeQuery(sql);
				// 6 �Խ�������д���
				while (rs.next()) {

					Employee emp = new Employee();
					emp.setId(rs.getInt("e_id"));
					emp.setName(rs.getString("e_name"));
					Department dep = new Department();
					dep.setId(rs.getInt("d_id"));
					dep.setName(rs.getString("d_name"));
					Project pro = new Project();
					pro.setId(rs.getInt("p_id"));
					pro.setName(rs.getString("p_name"));
					Score sc = new Score();
					sc.setId(rs.getInt("scId"));
					sc.setValue(rs.getInt("value"));
					sc.setGrade(rs.getString("grade"));

					emp.setDep(dep);
					sc.setEmp(emp);
					sc.setPro(pro);
					list.add(sc);
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
		public int searchCount(Score condition) {
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
				if (condition.getEmp().getName() != null && !condition.getEmp().getName().equals("")) {
					where += "and e_name='" + condition.getEmp().getName() + "'";
				}
				if (condition.getEmp().getDep().getName() != null && !condition.getEmp().getDep().getName().equals("")) {
					where += "and d_name='" + condition.getEmp().getDep().getName() + "'";
				}

				if ( condition.getPro().getName()!=null && !condition.getPro().getName().equals("")) {
					where += "and p_name='" + condition.getPro().getName() + "'";
				}
				if (condition.getValue() != -1) {
					where += "and value='" + condition.getValue() + "'";
				}
				if (condition.getGrade()!=null && !condition.getGrade().equals("")) {
					where += "and grade='" + condition.getGrade() + "'";
				}

				// 5 ִ��sql��䲢�õ����
				String sql = "select count(*) from v_dep_sc " + where;
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



	public void save(Set<Score> set) {
		// ��Ԫѭ��
		for (Score sc : set) {
			if (sc.getId() == 0) {
				add(sc);
			} else {
				update(sc);
			}

		}
	}

}
