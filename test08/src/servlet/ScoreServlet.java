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
			// 防止请求乱码
			request.setCharacterEncoding("utf-8");
			// 防止响应乱码
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

			// 从浏览器获得绩效的id和value
			int id = Integer.parseInt(request.getParameter("id"));
			int value = Integer.parseInt(request.getParameter("value"));

			// 构建一个对象存放数据
			Score score = new Score();
			score.setValue(value);
			// 到dao层去保存到数据库中
			ScoreDao scDao = new ScoreDao();
			boolean flag = false;

			// 判断是update还是add
			if (id == 0) {
				// 增加需要从浏览器得到两个id
				int empId = Integer.parseInt(request.getParameter("empId"));
				int proId = Integer.parseInt(request.getParameter("proId"));

				// 构建对象存放数据
				Employee emp = new Employee();
				emp.setId(empId);
				Project pro = new Project();
				pro.setId(proId);
				score.setEmp(emp);
				score.setPro(pro);

				// 到dao层去保存到数据库中
				id = scDao.add(score);
				// 新增之后再修改时需要先到数据库中拿到上次新增数据的id
				if (id > 0) {
					flag = true;
				}
				// 将id放到对象中
				score.setId(id);
			} else {

				// 将id放到对象中
				score.setId(id);
				// 到dao层去保存到数据库中
				flag = scDao.update(score);
			}

			// 重新查一次（查grade等）
			Score sc = scDao.search(id);

			// 判断update或add是否成功
			if (flag) {

				// 转化成json类型的字符串，返回到jsp页面
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

		// 从浏览器拿到数据
		String eName = request.getParameter("eName");
		String dName = request.getParameter("dName");
		String pName = request.getParameter("pName");
		String grade = request.getParameter("grade");
		// 搜索时先判断年龄是否为空
		int value = -1;// 不为空的时候再拿数据
		if (request.getParameter("value") != null && !"".equals(request.getParameter("value"))) {
			value = Integer.parseInt(request.getParameter("value"));
		}

		// 封装到一个对象
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

		// 到dao层查找
		ScoreDao scDao = new ScoreDao();
		DepartmentDao depDao = new DepartmentDao();
		ProjectDao proDao = new ProjectDao();

		int ye = 1;
		if (request.getParameter("ye") != null) {
			ye = Integer.parseInt(request.getParameter("ye"));
		}

		// 得到总数
		int count = scDao.searchCount(condition);

		// ye当前页，count最大记录数，2一页显示多少数据，numOfPage一页显示多少页码
		Pagination p = new Pagination(ye, count, Constant.EMP_NUM_IN_PAGE, Constant.EMP_NUM_OF_PAGE);

		// 得到分页的限制; 查到的数据放到list中
		List<Score> list = scDao.search(condition, p.getBegin(), Constant.EMP_NUM_IN_PAGE);
		List<Department> depList = depDao.search();
		List<Project> proList = proDao.search();

		// 向jsp发送的数据
		request.setAttribute("p", p);

		request.setAttribute("c", condition);

		// 将list发送到emps.jsp页面
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
			// 从浏览器得到一个json字符串
			String sc = request.getParameter("sc");
			// JSON-lib将json字符串转换为java对象
			// JSONArray.fromObject(emps)将emps转换为jsonArray对象
			JSONArray jsonArray = JSONArray.fromObject(sc);
			// 通过toCollection传过去JSONArray.fromObject(emps)转换成Employee.class员工的集合
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
