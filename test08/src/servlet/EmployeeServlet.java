package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.DepartmentDao;
import dao.EmployeeDao;
import entity.Department;
import entity.Employee;
import net.sf.json.JSONArray;
import util.Constant;
import util.Pagination;

public class EmployeeServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 防止Post方法传参中文乱码
			request.setCharacterEncoding("utf-8");

			String type = request.getParameter("type");
			if (type == null) {
				search(request, response);
			} else if ("showAdd".equals(type)) {
				showAdd(request, response);
			} else if ("showAdd2".equals(type)) {
				showAdd2(request, response);
			} else if ("add".equals(type)) {
				add(request, response);
			} else if ("upload".equals(type)) {
				upload(request, response);
			} else if ("add2".equals(type)) {
				add2(request, response);
			} else if ("showUpdate".equals(type)) {
				showUpdate(request, response);
			} else if ("update".equals(type)) {
				update(request, response);
			} else if ("delete".equals(type)) {
				delete(request, response);
			} else if ("deleteBatch".equals(type)) {
				deleteBatch(request, response);
			} else if ("showUpdateBatch1".equals(type)) {
				showUpdateBatch1(request, response);
			} else if ("updateBatch1".equals(type)) {
				updateBatch1(request, response);
			} else if ("showUpdateBatch2".equals(type)) {
				showUpdateBatch2(request, response);
			} else if ("updateBatch2".equals(type)) {
				updateBatch2(request, response);
			} else if ("updateBatch3".equals(type)) {
				updateBatch3(request, response);
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
			// 从浏览器拿到数据
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");

			// 搜索时先判断年龄是否为空
			int age = -1;// 不为空的时候再拿数据
			if (request.getParameter("age") != null && !"".equals(request.getParameter("age"))) {
				age = Integer.parseInt(request.getParameter("age"));
			}
			// 搜索时先判断部门id是否为空
			int depId = -1;// 不为空的时候再拿数据
			if (request.getParameter("depId") != null && !"".equals(request.getParameter("depId"))) {
				depId = Integer.parseInt(request.getParameter("depId"));
			}

			// 封装到一个对象
			Employee condition = new Employee();
			condition.setName(name);
			condition.setSex(sex);
			condition.setAge(age);
			Department dep = new Department();
			dep.setId(depId);
			condition.setDep(dep);

			// 到dao层查找
			EmployeeDao empDao = new EmployeeDao();
			DepartmentDao depDao = new DepartmentDao();

			int ye = 1;
			if (request.getParameter("ye") != null) {
				ye = Integer.parseInt(request.getParameter("ye"));
			}

			// 得到总数
			int count = empDao.searchCount(condition);

			// ye当前页，count最大记录数，2一页显示多少数据，numOfPage一页显示多少页码
			Pagination p = new Pagination(ye, count, Constant.EMP_NUM_IN_PAGE, Constant.EMP_NUM_OF_PAGE);
			// 查到的数据放到list中(第一个list得到分页的限制)
			List<Employee> list = empDao.search(condition, p.getBegin(), Constant.EMP_NUM_IN_PAGE);
			List<Department> depList = depDao.search();

			// 向jsp发送的数据
			request.setAttribute("p", p);
			request.setAttribute("c", condition);
			request.setAttribute("list", list);
			request.setAttribute("depList", depList);
			request.getRequestDispatcher("WEB-INF/employee/emps.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public void show(HttpServletRequest request, HttpServletResponse response) {
	// try {
	//
	// EmployeeDao empDao = new EmployeeDao();
	// // 设初始值 判断是否为空
	// int ye = 1;
	// if (request.getParameter("ye") != null) {
	// ye = Integer.parseInt(request.getParameter("ye"));
	// }
	//
	// // 得到总数
	// int count = empDao.searchCount();
	//
	// // ye当前页，count最大记录数，2一页显示多少数据，numOfPage一页显示多少页码
	// Pagination p = new Pagination(ye, count, Constant.EMP_NUM_IN_PAGE,
	// Constant.EMP_NUM_OF_PAGE);
	//
	// // 得到分页的限制
	// List<Employee> list = empDao.search(p.getBegin(), Constant.EMP_NUM_IN_PAGE);
	// // 向jsp发送的数据
	// request.setAttribute("p", p);
	// request.setAttribute("list", list);
	// request.getRequestDispatcher("WEB-INF/employee/emps.jsp").forward(request,
	// response);
	// } catch (ServletException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	private void showAdd(HttpServletRequest request, HttpServletResponse response) {
		try {
			DepartmentDao depDao = new DepartmentDao();
			List<Department> depList = depDao.search();
			request.setAttribute("depList", depList);
			request.getRequestDispatcher("WEB-INF/employee/add.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showAdd2(HttpServletRequest request, HttpServletResponse response) {
		try {
			DepartmentDao depDao = new DepartmentDao();
			List<Department> depList = depDao.search();
			request.setAttribute("depList", depList);
			request.getRequestDispatcher("WEB-INF/employee/add2.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void add(HttpServletRequest request, HttpServletResponse response) {
		try {

			// 从浏览器拿到数据
			String name = "";
			String sex = "";
			String age = "";
			String depId = "";
			String photoName = "";

			// 绝对路径
			String path = "F:/test/pic";

			// 表单中的数据经过这三行代码全部放到了list中 。
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);

			// 循环拿到list中的文件
			for (int i = 0; i < items.size(); i++) {
				// 拿到第i个文件
				FileItem item = items.get(i);
				if (item.getFieldName().equals("photo")) {
					// UUID通用唯一识别码
					UUID uuid = UUID.randomUUID();
					// 得到后缀
					String houzhui = item.getName().substring(item.getName().lastIndexOf("."));
					// 对photo文件处理：地址，文件名。在该位置生成一个文件
					photoName = uuid.toString() + houzhui;
					File savedFile = new File(path, photoName);
					item.write(savedFile);

				} else if (item.getFieldName().equals("name")) {
					// 拿到name文本框的内容
					// 防止中文乱码：先将item.getString()转换为以ISO-8859-1为格式的字节，再重新生成utf-8格式的字符串
					name = new String(item.getString().getBytes("ISO-8859-1"), "utf-8");

				} else if (item.getFieldName().equals("sex")) {
					// 拿到name文本框的内容
					// 防止中文乱码：先将item.getString()转换为以ISO-8859-1为格式的字节，再重新生成utf-8格式的字符串
					sex = new String(item.getString().getBytes("ISO-8859-1"), "utf-8");

				} else if (item.getFieldName().equals("age")) {
					age = new String(item.getString());

				} else if (item.getFieldName().equals("depId")) {
					depId = new String(item.getString());

				}
			}

			// 创建对象，将数据存到对象中
			Employee emp = new Employee();
			Department dep = new Department();
			emp.setName(name);
			emp.setSex(sex);
			emp.setAge(Integer.parseInt(age));
			emp.setPhoto(photoName);
			// 判断增加时是否填写部门
			if (!"".equals(depId)) {
				// 上面拿到的都是String类型需要转换为int类型
				dep.setId(Integer.parseInt(depId));
			}
			emp.setDep(dep);

			// 去dao层将数据增加到数据库中
			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.add(emp);

			response.sendRedirect("emp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void upload(HttpServletRequest request, HttpServletResponse response) {
		try {

			String photoName = "";
			// 绝对路径
			String path = "F:/test/pic";

			// 表单中的数据经过这三行代码全部放到了list中 。
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);

			// 循环拿到list中的文件
			for (int i = 0; i < items.size(); i++) {
				// 拿到第i个文件
				FileItem item = items.get(i);
				if (item.getFieldName().equals("photo")) {
					// UUID通用唯一识别码
					UUID uuid = UUID.randomUUID();
					// 得到后缀
					String houzhui = item.getName().substring(item.getName().lastIndexOf("."));
					// 对photo文件处理：地址，文件名。在该位置生成一个文件
					photoName = uuid.toString() + houzhui;
					File savedFile = new File(path, photoName);
					item.write(savedFile);

				}
			}

			// 把photoName传回去
			PrintWriter out = response.getWriter();
			out.print(photoName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void add2(HttpServletRequest request, HttpServletResponse response) {
		try {
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			int age = Integer.parseInt(request.getParameter("age"));
			String photo = request.getParameter("picture");
			// 增加的时候可以不填部门
			Integer depId = null;
			if (!"".equals(request.getParameter("depId"))) {
				depId = Integer.parseInt(request.getParameter("depId"));
			}

			Employee emp = new Employee();
			emp.setName(name);
			emp.setSex(sex);
			emp.setAge(age);
			emp.setPhoto(photo);
			Department dep = new Department();
			dep.setId(depId);
			emp.setDep(dep);

			EmployeeDao empDao = new EmployeeDao();

			boolean flag = empDao.add(emp);
			// show(request, response);
			response.sendRedirect("emp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showUpdate(HttpServletRequest request, HttpServletResponse response) {
		try {

			int id = Integer.parseInt(request.getParameter("id"));
			EmployeeDao empDao = new EmployeeDao();
			Employee emp = empDao.search(id);

			DepartmentDao depDao = new DepartmentDao();
			List<Department> depList = depDao.search();
			request.setAttribute("depList", depList);

			request.setAttribute("emp", emp);
			request.getRequestDispatcher("WEB-INF/employee/update.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			int age = Integer.parseInt(request.getParameter("age"));

			// 修改的时候可以不填部门
			Integer depId = null;
			if (!"".equals(request.getParameter("depId"))) {
				depId = Integer.parseInt(request.getParameter("depId"));
			}
			Employee emp = new Employee();
			emp.setId(id);
			emp.setName(name);
			emp.setSex(sex);
			emp.setAge(age);

			Department dep = new Department();
			dep.setId(depId);
			emp.setDep(dep);

			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.update(emp);
			// show(request, response);
			response.sendRedirect("emp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));

			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.delete(id);
			// show(request, response);
			response.sendRedirect("emp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteBatch(HttpServletRequest request, HttpServletResponse response) {
		try {
			String ids = request.getParameter("ids");
			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.deleteBatch(ids);
			// show(request, response);
			response.sendRedirect("emp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showUpdateBatch1(HttpServletRequest request, HttpServletResponse response) {
		try {

			String ids = request.getParameter("ids");
			EmployeeDao empDao = new EmployeeDao();
			List<Employee> list = empDao.search(ids);
			request.setAttribute("emp", list.get(0));
			request.setAttribute("ids", ids);
			request.getRequestDispatcher("WEB-INF/employee/updateBatch1.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateBatch1(HttpServletRequest request, HttpServletResponse response) {
		try {
			String ids = request.getParameter("ids");
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			int age = Integer.parseInt(request.getParameter("age"));
			Employee emp = new Employee();
			emp.setName(name);
			emp.setSex(sex);
			emp.setAge(age);
			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.updateBatch1(ids, emp);
			// show(request, response);
			response.sendRedirect("emp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateBatch2(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 从浏览器得到一个字符串
			String emps = request.getParameter("emps");
			// 以分号为界拆开字符串为一条数据
			String[] array = emps.split(";");

			List<Employee> list = new ArrayList<Employee>();
			for (int i = 0; i < array.length; i++) {
				String[] temp = array[i].split(",");
				Employee emp = new Employee();
				emp.setId(Integer.parseInt(temp[0]));
				emp.setName(temp[1]);
				emp.setSex(temp[2]);
				emp.setAge(Integer.parseInt(temp[3]));

				list.add(emp);

			}
			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.updateBatch2(list);
			response.sendRedirect("emp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public void updateBatch3(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 从浏览器得到一个json字符串
			String emps = request.getParameter("emps");
			// JSON-lib将json字符串转换为java对象
			// JSONArray.fromObject(emps)将emps转换为jsonArray对象
			JSONArray jsonArray = JSONArray.fromObject(emps);
			// 通过toCollection传过去JSONArray.fromObject(emps)转换成Employee.class员工的集合
			List<Employee> list = (List<Employee>) JSONArray.toCollection(JSONArray.fromObject(emps), Employee.class);

			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.updateBatch2(list);
			response.sendRedirect("emp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showUpdateBatch2(HttpServletRequest request, HttpServletResponse response) {
		try {

			String ids = request.getParameter("ids");
			EmployeeDao empDao = new EmployeeDao();
			List<Employee> list = empDao.search(ids);
			request.setAttribute("list", list);
			request.getRequestDispatcher("WEB-INF/employee/updateBatch2.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		doGet(request, response);
	}
}
