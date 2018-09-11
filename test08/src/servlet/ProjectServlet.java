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
import dao.ProjectDao;
import entity.Department;
import entity.Project;
import util.Constant;
import util.Pagination;

public class ProjectServlet extends HttpServlet {

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

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));

			ProjectDao proDao = new ProjectDao();
			boolean flag = proDao.delete(id);
			// show(request, response);
			response.sendRedirect("pro");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			Project pro = new Project();
			pro.setId(id);
			pro.setName(name);
			ProjectDao depDao = new ProjectDao();
			boolean flag = depDao.update(pro);
			// show(request, response);
			response.sendRedirect("pro");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showUpdate(HttpServletRequest request, HttpServletResponse response) {
		try {

			int id = Integer.parseInt(request.getParameter("id"));
			ProjectDao proDao = new ProjectDao();
			Project pro = proDao.search(id);
			request.setAttribute("pro", pro);
			request.getRequestDispatcher("WEB-INF/project/update.jsp").forward(request, response);
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
			Project pro = new Project();
			pro.setName(name);
			ProjectDao proDao = new ProjectDao();
			boolean flag = proDao.add(pro);
			// show(request, response);

			response.sendRedirect("pro");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void showAdd(HttpServletRequest request, HttpServletResponse response) {
		try {

			request.getRequestDispatcher("WEB-INF/project/add.jsp").forward(request, response);
		} catch (ServletException e) {
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

			// ��װ��һ������
			Project condition = new Project();
			condition.setName(name);

			// ��dao�����
			ProjectDao proDao = new ProjectDao();

			int ye = 1;
			if (request.getParameter("ye") != null) {
				ye = Integer.parseInt(request.getParameter("ye"));
			}

			// �õ�����
			int count = proDao.searchCount(condition);

			// ye��ǰҳ��count����¼����2һҳ��ʾ�������ݣ�numOfPageһҳ��ʾ����ҳ��
			Pagination p = new Pagination(ye, count, Constant.EMP_NUM_IN_PAGE, Constant.EMP_NUM_OF_PAGE);

			// �õ���ҳ������; �鵽�����ݷŵ�list��
			List<Project> list = proDao.search(condition, p.getBegin(), Constant.EMP_NUM_IN_PAGE);
			List<Project> proList = proDao.search();
			// ��jsp���͵�����
			request.setAttribute("p", p);

			request.setAttribute("c", condition);

			// ��list���͵�emps.jspҳ��
			request.setAttribute("proList", proList);
			request.setAttribute("list", list);
			request.getRequestDispatcher("WEB-INF/project/pro.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		doGet(request, response);
	}
}
