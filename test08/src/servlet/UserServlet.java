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

			// 设置图片不缓存
			// 下面3条记录是关闭客户端浏览器的缓冲区
			// 这3条语句都可以关闭浏览器的缓冲区，但是由于浏览器的版本不同，对这3条语句的支持也不同
			// 因此，为了保险起见，同时使用这3条语句来关闭浏览器的缓冲区
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "No-cache");
			response.setHeader("Expires", "0");

			// 需要得到验证码（ValidateCode）对象
			// 引用RandomNumber类中的generateImage()方法得到生成的图片和数字
			ValidateCode vc = new ValidateCode();
			RandomNumber rn = new RandomNumber();
			vc = rn.generateImage();

			// response.getOutputStream()拿到输出流,利用输出流的方式
			ServletOutputStream outStream = response.getOutputStream();

			// 输出图像到页面：值（图片），格式，什么方式
			ImageIO.write(vc.getImage(), "JPEG", outStream);
			outStream.close();

			// 将生成的数字放到session中发到浏览器
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
			// 登录时利用cookie显示上次使用的账号
			String name = "";
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				// 不为空的时候遍历拿到需要的cookie值
				for (int i = 0; i < cookies.length; i++) {
					if ("username".equals(cookies[i].getName())) {
						name = cookies[i].getValue();
					}
				}
			}
			// 将拿到的值发送到jsp页面
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
			// sessionId打开浏览器第一次访问时就建立了
			// 去sessions集合里找这个会话的的sessionId的map
			HttpSession session = request.getSession();

			// 先判断验证码是否正确
			String random = request.getParameter("random");
			if (random.equals(session.getAttribute("rand"))) {

				// 从浏览器拿到数据
				String username = request.getParameter("username");
				String password = request.getParameter("password");

				// 创建对象保存数据
				User user = new User();
				user.setUsername(username);
				user.setPassword(password);
				// 去数据库中查询
				UserDao useDao = new UserDao();
				boolean flag = useDao.search(user);
				// 判断user是否存在
				if (flag) {

					// 对拿到的map进行set（赋）值
					session.setAttribute("user", username);

					// cookie不是内置对象, 引用时需要先new
					Cookie cookie = new Cookie("username", username);
					// 设置生命周期,单位是秒
					cookie.setMaxAge(30);
					// cookie需要放到response中
					response.addCookie(cookie);

					response.sendRedirect("index");
				} else {

					// request.setAttribute("error", "账号或密码错误");
					// response.sendRedirect("user?type=showLogin");
					request.getRequestDispatcher("user?type=showLogin&error=yes").forward(request, response);
				}
			} else {
				// request.setAttribute("mes", "请输入验证码");
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
			// sessionId打开浏览器第一次访问时就建立了
			// 去sessions集合里找这个会话的的sessionId的map
			HttpSession session = request.getSession();

			// 从浏览器拿到数据
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirmPassword");

			
			
			
			
			if (password.equals(confirmPassword)) {
				// 创建对象保存数据
				User user = new User();
				user.setUsername(username);

				// MD5加密
				user.setPassword(CreateMD5.getMD5(password + "加盐"));

				// 去数据库中增加
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