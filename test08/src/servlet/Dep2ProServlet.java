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

import dao.Dep2ProDao;
import dao.DepartmentDao;
import dao.ProjectDao;
import entity.Department;
import entity.Project;
import util.Constant;
import util.Pagination;

public class Dep2ProServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");

			String type = request.getParameter("type");
			if (type == null) {
				search(request, response);
			} else if ("add".equals(type)) {
				add(request, response);
			} else if ("delete".equals(type)) {
				delete(request, response);
			} else if ("add2".equals(type)) {
				add2(request, response);
			} else if ("delete2".equals(type)) {
				delete2(request, response);
			} else if ("addBatch".equals(type)) {
				addBatch(request, response);
			} else if ("deleteBatch".equals(type)) {
				deleteBatch(request, response);
			} else if ("m2".equals(type)) {
				search2(request, response);
			} else if ("m3".equals(type)) {
				search3(request, response);
			} else if ("m4".equals(type)) {
				search4(request, response);
			} else if ("m5".equals(type)) {
				search5(request, response);
			} else if ("m6".equals(type)) {
				search6(request, response);
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
			// 从浏览器得到两个id
			int depId = Integer.parseInt(request.getParameter("depId"));
			int proId = Integer.parseInt(request.getParameter("proId"));
			// 查出要增加的项目封装到一个对象
			Dep2ProDao dpDao = new Dep2ProDao();
			dpDao.delete(depId, proId);
			response.sendRedirect("d2p?depId=" + depId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 从浏览器得到两个id
			int depId = Integer.parseInt(request.getParameter("depId"));
			int proId = Integer.parseInt(request.getParameter("proId"));

			// 查出要增加的项目封装到一个对象
			Dep2ProDao dpDao = new Dep2ProDao();
			dpDao.add(depId, proId);
			response.sendRedirect("d2p?depId=" + depId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void search(HttpServletRequest request, HttpServletResponse response) {
		try {

			int depId = Integer.parseInt(request.getParameter("depId"));

			// 根据id查出部门名封装到一个对象
			DepartmentDao depDao = new DepartmentDao();
			Department dep = depDao.search(depId);

			// 查出部门所有的项目封装到list中
			Dep2ProDao dpDao = new Dep2ProDao();
			List<Project> list = dpDao.searchByDepartment(depId);

			// 查出部门没有的项目封装到noList中
			List<Project> noList = dpDao.searchByNotDepartment(depId);

			// 将查到的数据发到jsp页面
			request.setAttribute("dep", dep);
			request.setAttribute("list", list);
			request.setAttribute("noList", noList);

			request.getRequestDispatcher("WEB-INF/dep2pro/dep2pro.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void search2(HttpServletRequest request, HttpServletResponse response) {
		try {

			int depId = Integer.parseInt(request.getParameter("depId"));

			// 根据id查出部门名封装到一个对象
			DepartmentDao depDao = new DepartmentDao();
			Department dep = depDao.search(depId);

			// 查出部门所有的项目封装到list中
			Dep2ProDao dpDao = new Dep2ProDao();
			List<Project> list = dpDao.searchByDepartment(depId);

			// 查出部门没有的项目封装到noList中
			List<Project> noList = dpDao.searchByNotDepartment(depId);

			// 将查到的数据发到jsp页面
			request.setAttribute("dep", dep);
			request.setAttribute("list", list);
			request.setAttribute("noList", noList);

			request.getRequestDispatcher("WEB-INF/dep2pro/dep2pro2.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void search3(HttpServletRequest request, HttpServletResponse response) {
		try {

			int depId = Integer.parseInt(request.getParameter("depId"));

			// 根据id查出部门名封装到一个对象
			DepartmentDao depDao = new DepartmentDao();
			Department dep = depDao.search(depId);

			// 查出部门所有的项目封装到list中
			Dep2ProDao dpDao = new Dep2ProDao();
			List<Project> list = dpDao.searchByDepartment(depId);

			// 查出部门没有的项目封装到noList中
			List<Project> noList = dpDao.searchByNotDepartment(depId);

			// 将查到的数据发到jsp页面
			request.setAttribute("dep", dep);
			request.setAttribute("list", list);
			request.setAttribute("noList", noList);

			request.getRequestDispatcher("WEB-INF/dep2pro/dep2pro3.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void search4(HttpServletRequest request, HttpServletResponse response) {
		try {

			int depId = Integer.parseInt(request.getParameter("depId"));

			// 根据id查出部门名封装到一个对象
			DepartmentDao depDao = new DepartmentDao();
			Department dep = depDao.search(depId);

			// 查出部门所有的项目封装到list中
			Dep2ProDao dpDao = new Dep2ProDao();
			List<Project> list = dpDao.searchByDepartment(depId);

			// 查出部门没有的项目封装到noList中
			List<Project> noList = dpDao.searchByNotDepartment(depId);

			// 将查到的数据发到jsp页面
			request.setAttribute("dep", dep);
			request.setAttribute("list", list);
			request.setAttribute("noList", noList);

			request.getRequestDispatcher("WEB-INF/dep2pro/dep2pro4.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void search5(HttpServletRequest request, HttpServletResponse response) {
		try {

			int depId = Integer.parseInt(request.getParameter("depId"));

			// 根据id查出部门名封装到一个对象
			DepartmentDao depDao = new DepartmentDao();
			Department dep = depDao.search(depId);

			// 查出部门所有的项目封装到list中
			Dep2ProDao dpDao = new Dep2ProDao();
			List<Project> list = dpDao.searchByDepartment(depId);

			// 查出部门没有的项目封装到noList中
			List<Project> noList = dpDao.searchByNotDepartment(depId);

			// 将查到的数据发到jsp页面
			request.setAttribute("dep", dep);
			request.setAttribute("list", list);
			request.setAttribute("noList", noList);

			request.getRequestDispatcher("WEB-INF/dep2pro/dep2pro5.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void search6(HttpServletRequest request, HttpServletResponse response) {
		try {

			int depId = Integer.parseInt(request.getParameter("depId"));

			// 根据id查出部门名封装到一个对象
			DepartmentDao depDao = new DepartmentDao();
			Department dep = depDao.search(depId);

			// 查出部门所有的项目封装到list中
			Dep2ProDao dpDao = new Dep2ProDao();
			List<Project> list = dpDao.searchByDepartment(depId);

			// 查出部门没有的项目封装到noList中
			List<Project> noList = dpDao.searchByNotDepartment(depId);

			// 将查到的数据发到jsp页面
			request.setAttribute("dep", dep);
			request.setAttribute("list", list);
			request.setAttribute("noList", noList);

			request.getRequestDispatcher("WEB-INF/dep2pro/dep2pro6.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void add2(HttpServletRequest request, HttpServletResponse response) {

		try {

			// 从浏览器得到一个depId和一个字符串
			int depId = Integer.parseInt(request.getParameter("depId"));
			int proId = Integer.parseInt(request.getParameter("proId"));

			// 查出要增加的项目封装到一个对象
			Dep2ProDao dpDao = new Dep2ProDao();
			boolean flag = dpDao.add(depId, proId);

			// 将boolean类型的数据返回到jsp
			PrintWriter out = response.getWriter();
			out.print(flag);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addBatch(HttpServletRequest request, HttpServletResponse response) {

		try {

			// 从浏览器得到一个depId和一个字符串
			int depId = Integer.parseInt(request.getParameter("depId"));
			String[] proIds = request.getParameter("proId").split(",");

			// 查出要增加的项目封装到一个对象
			Dep2ProDao dpDao = new Dep2ProDao();
			boolean flag = dpDao.addBatch(depId, proIds);

			// 将boolean类型的数据返回到jsp
			PrintWriter out = response.getWriter();
			out.print(flag);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void delete2(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 从浏览器得到两个id
			int depId = Integer.parseInt(request.getParameter("depId"));
			int proId = Integer.parseInt(request.getParameter("proId"));

			// 查出要增加的项目封装到一个对象
			Dep2ProDao dpDao = new Dep2ProDao();
			boolean flag = dpDao.delete(depId, proId);

			// 将boolean类型的数据返回到jsp
			PrintWriter out = response.getWriter();
			out.print(flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteBatch(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 从浏览器得到两个id
			int depId = Integer.parseInt(request.getParameter("depId"));
			String[] proIds = request.getParameter("proId").split(",");

			// 查出要增加的项目封装到一个对象
			Dep2ProDao dpDao = new Dep2ProDao();
			boolean flag = dpDao.deleteBatch(depId, proIds);

			// 将boolean类型的数据返回到jsp
			PrintWriter out = response.getWriter();
			out.print(flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		doGet(request, response);
	}
}
