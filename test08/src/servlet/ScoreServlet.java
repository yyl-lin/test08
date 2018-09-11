package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DepartmentDao;
import dao.ProjectDao;
import dao.ScoreDao;
import entity.Score;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import entity.Department;
import entity.Employee;
import entity.Project;
import util.Constant;
import util.Pagination;

public class ScoreServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// ��ֹ��������
			request.setCharacterEncoding("utf-8");
			// ��ֹ��Ӧ����
			response.setContentType("text/html;charset=utf-8");

			String type = request.getParameter("type");
			if (type == null) {
				search(request, response);
			} else if ("save".equals(type)) {
				save(request, response);
			} else if ("manage".equals(type)) {
				manage(request, response);
			} else if ("input".equals(type)) {
				input(request, response);
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void input(HttpServletRequest request, HttpServletResponse response) {
		try {

			PrintWriter out = response.getWriter();

			// ���������ü�Ч��id��value
			int id = Integer.parseInt(request.getParameter("id"));
			int value = Integer.parseInt(request.getParameter("value"));

			// ����һ������������
			Score score = new Score();
			score.setValue(value);
			// ��dao��ȥ���浽���ݿ���
			ScoreDao scDao = new ScoreDao();
			boolean flag = false;

			// �ж���update����add
			if (id == 0) {
				// ������Ҫ��������õ�����id
				int empId = Integer.parseInt(request.getParameter("empId"));
				int proId = Integer.parseInt(request.getParameter("proId"));

				// ��������������
				Employee emp = new Employee();
				emp.setId(empId);
				Project pro = new Project();
				pro.setId(proId);
				score.setEmp(emp);
				score.setPro(pro);

				// ��dao��ȥ���浽���ݿ���
				id = scDao.add(score);
				// ����֮�����޸�ʱ��Ҫ�ȵ����ݿ����õ��ϴ��������ݵ�id
				if (id > 0) {
					flag = true;
				}
				// ��id�ŵ�������
				score.setId(id);
			} else {

				// ��id�ŵ�������
				score.setId(id);
				// ��dao��ȥ���浽���ݿ���
				flag = scDao.update(score);
			}

			// ���²�һ�Σ���grade�ȣ�
			Score sc = scDao.search(id);

			// �ж�update��add�Ƿ�ɹ�
			if (flag) {

				// ת����json���͵��ַ��������ص�jspҳ��
				JSON json = JSONObject.fromObject(sc);
				out.print(json);
			} else {

				out.print(false);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void basic(HttpServletRequest request, HttpServletResponse response) {

		// ��������õ�����
		String eName = request.getParameter("eName");
		String dName = request.getParameter("dName");
		String pName = request.getParameter("pName");
		String grade = request.getParameter("grade");
		// ����ʱ���ж������Ƿ�Ϊ��
		int value = -1;// ��Ϊ�յ�ʱ����������
		if (request.getParameter("value") != null && !"".equals(request.getParameter("value"))) {
			value = Integer.parseInt(request.getParameter("value"));
		}

		// ��װ��һ������
		Employee emp = new Employee();
		emp.setName(eName);
		Department dep = new Department();
		dep.setName(dName);
		emp.setDep(dep);
		Project pro = new Project();
		pro.setName(pName);

		Score condition = new Score();
		condition.setGrade(grade);
		condition.setValue(value);
		condition.setEmp(emp);
		condition.setPro(pro);

		// ��dao�����
		ScoreDao scDao = new ScoreDao();
		DepartmentDao depDao = new DepartmentDao();
		ProjectDao proDao = new ProjectDao();

		int ye = 1;
		if (request.getParameter("ye") != null) {
			ye = Integer.parseInt(request.getParameter("ye"));
		}

		// �õ�����
		int count = scDao.searchCount(condition);

		// ye��ǰҳ��count����¼����2һҳ��ʾ�������ݣ�numOfPageһҳ��ʾ����ҳ��
		Pagination p = new Pagination(ye, count, Constant.EMP_NUM_IN_PAGE, Constant.EMP_NUM_OF_PAGE);

		// �õ���ҳ������; �鵽�����ݷŵ�list��
		List<Score> list = scDao.search(condition, p.getBegin(), Constant.EMP_NUM_IN_PAGE);
		List<Department> depList = depDao.search();
		List<Project> proList = proDao.search();

		// ��jsp���͵�����
		request.setAttribute("p", p);

		request.setAttribute("c", condition);

		// ��list���͵�emps.jspҳ��
		request.setAttribute("depList", depList);
		request.setAttribute("proList", proList);
		request.setAttribute("list", list);
	}

	private void search(HttpServletRequest request, HttpServletResponse response) {
		try {

			basic(request, response);

			request.getRequestDispatcher("WEB-INF/score/sc.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void manage(HttpServletRequest request, HttpServletResponse response) {
		try {

			basic(request, response);

			request.getRequestDispatcher("WEB-INF/score/sc2.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void save(HttpServletRequest request, HttpServletResponse response) {
		try {
			// ��������õ�һ��json�ַ���
			String sc = request.getParameter("sc");
			// JSON-lib��json�ַ���ת��Ϊjava����
			// JSONArray.fromObject(emps)��empsת��ΪjsonArray����
			JSONArray jsonArray = JSONArray.fromObject(sc);
			// ͨ��toCollection����ȥJSONArray.fromObject(emps)ת����Employee.classԱ���ļ���
			List<Score> list = (List<Score>) JSONArray.toCollection(JSONArray.fromObject(sc), Score.class);

			ScoreDao scDao = new ScoreDao();
			boolean flag = scDao.save(list);
			response.sendRedirect("sc");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		doGet(request, response);
	}
}
