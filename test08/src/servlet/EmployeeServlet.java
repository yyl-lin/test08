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
			// ��ֹPost����������������
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
			// ��������õ�����
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");

			// ����ʱ���ж������Ƿ�Ϊ��
			int age = -1;// ��Ϊ�յ�ʱ����������
			if (request.getParameter("age") != null && !"".equals(request.getParameter("age"))) {
				age = Integer.parseInt(request.getParameter("age"));
			}
			// ����ʱ���жϲ���id�Ƿ�Ϊ��
			int depId = -1;// ��Ϊ�յ�ʱ����������
			if (request.getParameter("depId") != null && !"".equals(request.getParameter("depId"))) {
				depId = Integer.parseInt(request.getParameter("depId"));
			}

			// ��װ��һ������
			Employee condition = new Employee();
			condition.setName(name);
			condition.setSex(sex);
			condition.setAge(age);
			Department dep = new Department();
			dep.setId(depId);
			condition.setDep(dep);

			// ��dao�����
			EmployeeDao empDao = new EmployeeDao();
			DepartmentDao depDao = new DepartmentDao();

			int ye = 1;
			if (request.getParameter("ye") != null) {
				ye = Integer.parseInt(request.getParameter("ye"));
			}

			// �õ�����
			int count = empDao.searchCount(condition);

			// ye��ǰҳ��count����¼����2һҳ��ʾ�������ݣ�numOfPageһҳ��ʾ����ҳ��
			Pagination p = new Pagination(ye, count, Constant.EMP_NUM_IN_PAGE, Constant.EMP_NUM_OF_PAGE);
			// �鵽�����ݷŵ�list��(��һ��list�õ���ҳ������)
			List<Employee> list = empDao.search(condition, p.getBegin(), Constant.EMP_NUM_IN_PAGE);
			List<Department> depList = depDao.search();

			// ��jsp���͵�����
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
	// // ���ʼֵ �ж��Ƿ�Ϊ��
	// int ye = 1;
	// if (request.getParameter("ye") != null) {
	// ye = Integer.parseInt(request.getParameter("ye"));
	// }
	//
	// // �õ�����
	// int count = empDao.searchCount();
	//
	// // ye��ǰҳ��count����¼����2һҳ��ʾ�������ݣ�numOfPageһҳ��ʾ����ҳ��
	// Pagination p = new Pagination(ye, count, Constant.EMP_NUM_IN_PAGE,
	// Constant.EMP_NUM_OF_PAGE);
	//
	// // �õ���ҳ������
	// List<Employee> list = empDao.search(p.getBegin(), Constant.EMP_NUM_IN_PAGE);
	// // ��jsp���͵�����
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

			// ��������õ�����
			String name = "";
			String sex = "";
			String age = "";
			String depId = "";
			String photoName = "";

			// ����·��
			String path = "F:/test/pic";

			// ���е����ݾ��������д���ȫ���ŵ���list�� ��
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);

			// ѭ���õ�list�е��ļ�
			for (int i = 0; i < items.size(); i++) {
				// �õ���i���ļ�
				FileItem item = items.get(i);
				if (item.getFieldName().equals("photo")) {
					// UUIDͨ��Ψһʶ����
					UUID uuid = UUID.randomUUID();
					// �õ���׺
					String houzhui = item.getName().substring(item.getName().lastIndexOf("."));
					// ��photo�ļ�������ַ���ļ������ڸ�λ������һ���ļ�
					photoName = uuid.toString() + houzhui;
					File savedFile = new File(path, photoName);
					item.write(savedFile);

				} else if (item.getFieldName().equals("name")) {
					// �õ�name�ı��������
					// ��ֹ�������룺�Ƚ�item.getString()ת��Ϊ��ISO-8859-1Ϊ��ʽ���ֽڣ�����������utf-8��ʽ���ַ���
					name = new String(item.getString().getBytes("ISO-8859-1"), "utf-8");

				} else if (item.getFieldName().equals("sex")) {
					// �õ�name�ı��������
					// ��ֹ�������룺�Ƚ�item.getString()ת��Ϊ��ISO-8859-1Ϊ��ʽ���ֽڣ�����������utf-8��ʽ���ַ���
					sex = new String(item.getString().getBytes("ISO-8859-1"), "utf-8");

				} else if (item.getFieldName().equals("age")) {
					age = new String(item.getString());

				} else if (item.getFieldName().equals("depId")) {
					depId = new String(item.getString());

				}
			}

			// �������󣬽����ݴ浽������
			Employee emp = new Employee();
			Department dep = new Department();
			emp.setName(name);
			emp.setSex(sex);
			emp.setAge(Integer.parseInt(age));
			emp.setPhoto(photoName);
			// �ж�����ʱ�Ƿ���д����
			if (!"".equals(depId)) {
				// �����õ��Ķ���String������Ҫת��Ϊint����
				dep.setId(Integer.parseInt(depId));
			}
			emp.setDep(dep);

			// ȥdao�㽫�������ӵ����ݿ���
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
			// ����·��
			String path = "F:/test/pic";

			// ���е����ݾ��������д���ȫ���ŵ���list�� ��
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);

			// ѭ���õ�list�е��ļ�
			for (int i = 0; i < items.size(); i++) {
				// �õ���i���ļ�
				FileItem item = items.get(i);
				if (item.getFieldName().equals("photo")) {
					// UUIDͨ��Ψһʶ����
					UUID uuid = UUID.randomUUID();
					// �õ���׺
					String houzhui = item.getName().substring(item.getName().lastIndexOf("."));
					// ��photo�ļ�������ַ���ļ������ڸ�λ������һ���ļ�
					photoName = uuid.toString() + houzhui;
					File savedFile = new File(path, photoName);
					item.write(savedFile);

				}
			}

			// ��photoName����ȥ
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
			// ���ӵ�ʱ����Բ����
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

			// �޸ĵ�ʱ����Բ����
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
			// ��������õ�һ���ַ���
			String emps = request.getParameter("emps");
			// �Էֺ�Ϊ����ַ���Ϊһ������
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
			// ��������õ�һ��json�ַ���
			String emps = request.getParameter("emps");
			// JSON-lib��json�ַ���ת��Ϊjava����
			// JSONArray.fromObject(emps)��empsת��ΪjsonArray����
			JSONArray jsonArray = JSONArray.fromObject(emps);
			// ͨ��toCollection����ȥJSONArray.fromObject(emps)ת����Employee.classԱ���ļ���
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
