package servlet;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import entity.User;
import util.CreateMD5;
import util.RandomNumber;
import util.ValidateCode;

public class UserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		if (type == null) {
			showRegister(request, response);
		} else if ("showLogin".equals(type)) {
			showLogin(request, response);
		} else if ("doLogin".equals(type)) {
			doLogin(request, response);
		} else if ("randomImage".equals(type)) {
			randomImage(request, response);
		} else if ("doRegister".equals(type)) {
			doRegister(request, response);
		}

	}

	private void randomImage(HttpServletRequest request, HttpServletResponse response) {

		try {

			// ����ͼƬ������
			// ����3����¼�ǹرտͻ���������Ļ�����
			// ��3����䶼���Թر�������Ļ���������������������İ汾��ͬ������3������֧��Ҳ��ͬ
			// ��ˣ�Ϊ�˱��������ͬʱʹ����3��������ر�������Ļ�����
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "No-cache");
			response.setHeader("Expires", "0");

			// ��Ҫ�õ���֤�루ValidateCode������
			// ����RandomNumber���е�generateImage()�����õ����ɵ�ͼƬ������
			ValidateCode vc = new ValidateCode();
			RandomNumber rn = new RandomNumber();
			vc = rn.generateImage();

			// response.getOutputStream()�õ������,����������ķ�ʽ
			ServletOutputStream outStream = response.getOutputStream();

			// ���ͼ��ҳ�棺ֵ��ͼƬ������ʽ��ʲô��ʽ
			ImageIO.write(vc.getImage(), "JPEG", outStream);
			outStream.close();

			// �����ɵ����ַŵ�session�з��������
			request.getSession().setAttribute("rand", vc.getRand());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void showRegister(HttpServletRequest request, HttpServletResponse response) {
		try {

			request.getRequestDispatcher("WEB-INF/user/register.jsp").forward(request, response);

		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			// ��¼ʱ����cookie��ʾ�ϴ�ʹ�õ��˺�
			String name = "";
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				// ��Ϊ�յ�ʱ������õ���Ҫ��cookieֵ
				for (int i = 0; i < cookies.length; i++) {
					if ("username".equals(cookies[i].getName())) {
						name = cookies[i].getValue();
					}
				}
			}
			// ���õ���ֵ���͵�jspҳ��
			request.setAttribute("name", name);

			request.getRequestDispatcher("WEB-INF/user/login.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			// sessionId���������һ�η���ʱ�ͽ�����
			// ȥsessions������������Ự�ĵ�sessionId��map
			HttpSession session = request.getSession();

			// ���ж���֤���Ƿ���ȷ
			String random = request.getParameter("random");
			if (random.equals(session.getAttribute("rand"))) {

				// ��������õ�����
				String username = request.getParameter("username");
				String password = request.getParameter("password");

				// �������󱣴�����
				User user = new User();
				user.setUsername(username);
				user.setPassword(password);
				// ȥ���ݿ��в�ѯ
				UserDao useDao = new UserDao();
				boolean flag = useDao.search(user);
				// �ж�user�Ƿ����
				if (flag) {

					// ���õ���map����set������ֵ
					session.setAttribute("user", username);

					// cookie�������ö���, ����ʱ��Ҫ��new
					Cookie cookie = new Cookie("username", username);
					// ������������,��λ����
					cookie.setMaxAge(30);
					// cookie��Ҫ�ŵ�response��
					response.addCookie(cookie);

					response.sendRedirect("index");
				} else {

					// request.setAttribute("error", "�˺Ż��������");
					// response.sendRedirect("user?type=showLogin");
					request.getRequestDispatcher("user?type=showLogin&error=yes").forward(request, response);
				}
			} else {
				// request.setAttribute("mes", "��������֤��");
				request.getRequestDispatcher("user?type=showLogin&mes=yes").forward(request, response);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doRegister(HttpServletRequest request, HttpServletResponse response) {
		try {
			// sessionId���������һ�η���ʱ�ͽ�����
			// ȥsessions������������Ự�ĵ�sessionId��map
			HttpSession session = request.getSession();

			// ��������õ�����
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirmPassword");

			
			
			
			
			if (password.equals(confirmPassword)) {
				// �������󱣴�����
				User user = new User();
				user.setUsername(username);

				// MD5����
				user.setPassword(CreateMD5.getMD5(password + "����"));

				// ȥ���ݿ�������
				UserDao useDao = new UserDao();
				boolean flag = useDao.add(user);

				if (flag) {
					response.sendRedirect("user?type=showLogin");
				} else {
					response.sendRedirect("user");
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		doGet(request, response);
	}

}