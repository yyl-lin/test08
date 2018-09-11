package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DepartmentDao;
import dao.EmployeeDao;
import entity.Department;
import entity.Employee;
import util.Constant;
import util.Pagination;

public class DepartmentServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");

			String type = request.getParameter("type");
			if (type == null) {
				search(request, response);
			} else if ("showAdd".equals(type)) {
				showAdd(request, response);
			} else if ("add".equals(type)) {
				add(request, response);
			} else if ("showUpdate".equals(type)) {
				showUpdate(request, response);
			} else if ("update".equals(type)) {
				update(request, response);
			} else if ("delete".equals(type)) {
				delete(request, response);
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void search(HttpServletRequest request, HttpServletResponse response) {
		try {
			// ��������õ�����
			String name = request.getParameter("name");

			// ����ʱ���ж������Ƿ�Ϊ��
			int empCount = -1;// ��Ϊ�յ�ʱ����������
			if (request.getParameter("empCount") != null && !"".equals(request.getParameter("empCount"))) {
				empCount = Integer.parseInt(request.getParameter("empCount"));
			}

			// ��װ��һ������
			Department condition = new Department();
			condition.setName(name);
			condition.setEmpCount(empCount);

			// ��dao�����
			DepartmentDao empDao = new DepartmentDao();

			int ye = 1;
			if (request.getParameter("ye") != null) {
				ye = Integer.parseInt(request.getParameter("ye"));
			}

			// �õ�����
			int count = empDao.searchCount(condition);

			// ye��ǰҳ��count����¼����2һҳ��ʾ�������ݣ�numOfPageһҳ��ʾ����ҳ��
			Pagination p = new Pagination(ye, count, Constant.EMP_NUM_IN_PAGE, Constant.EMP_NUM_OF_PAGE);

			// �õ���ҳ������; �鵽�����ݷŵ�list��
			List<Department> list = empDao.search(condition, p.getBegin(), Constant.EMP_NUM_IN_PAGE);
			// ��jsp���͵�����
			request.setAttribute("p", p);

			request.setAttribute("c", condition);

			// ��list���͵�emps.jspҳ��
			request.setAttribute("list", list);
			request.getRequestDispatcher("WEB-INF/department/deps.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			Department dep = new Department();
			dep.setId(id);
			dep.setName(name);
			DepartmentDao depDao = new DepartmentDao();
			boolean flag = depDao.update(dep);
			// show(request, response);
			response.sendRedirect("dep");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));

			DepartmentDao depDao = new DepartmentDao();
			boolean flag = depDao.delete(id);
			// show(request, response);
			response.sendRedirect("dep");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showUpdate(HttpServletRequest request, HttpServletResponse response) {
		try {

			int id = Integer.parseInt(request.getParameter("id"));
			DepartmentDao depDao = new DepartmentDao();
			Department dep = depDao.search(id);
			request.setAttribute("dep", dep);
			request.getRequestDispatcher("WEB-INF/department/update.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void show(HttpServletRequest request, HttpServletResponse response) {

		try {
			DepartmentDao empDao = new DepartmentDao();
			List<Department> list = empDao.search();
			request.setAttribute("list", list);

			request.getRequestDispatcher("WEB-INF/department/deps.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void showAdd(HttpServletRequest request, HttpServletResponse response) {
		try {

			request.getRequestDispatcher("WEB-INF/department/add.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		try {
			String name = request.getParameter("name");
			Department dep = new Department();
			dep.setName(name);
			DepartmentDao depDao = new DepartmentDao();
			boolean flag = depDao.add(dep);
			// show(request, response);

			response.sendRedirect("dep");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		doGet(request, response);
	}
}
