package filter;

/*
 * ������
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
 * ʹ�ýӿ�
 */
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		/*
		 * HttpServletRequest��ServletRequest������ �ó�session�ж�map��ֵ�����ò���¼�޷�����ʱ��Ҫǿ������ת��
		 */
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		// �ж�user�Ƿ�Ϊ�գ�Ϊ�յĻ���Ҫ�ȵ�¼
		if (session.getAttribute("user") == null) {
			
			((HttpServletResponse) response).sendRedirect("user?type=showLogin");
		
		} else {

			//���session���Ѿ�Я����user�ͼ���ִ��
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
