package filter;

/*
 * 过滤器
 */
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * 使用接口
 */
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		/*
		 * HttpServletRequest是ServletRequest的子类 拿出session判断map的值来设置不登录无法访问时需要强制类型转换
		 */
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		// 判断user是否为空，为空的话需要先登录
		if (session.getAttribute("user") == null) {
			
			((HttpServletResponse) response).sendRedirect("user?type=showLogin");
		
		} else {

			//如果session中已经携带了user就继续执行
			chain.doFilter(request, response);
			
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
