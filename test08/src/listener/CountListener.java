package listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import util.MyWebSocket;

/*
 * 记录网站访问人数
 */
public class CountListener implements HttpSessionListener {
	// 监听session 监听application：类型是ServletContext
	@Override
	public void sessionCreated(HttpSessionEvent event) {

		System.out.println("session建立");

		// 生成seesion
		HttpSession session = event.getSession();

		// 生成application
		ServletContext application = event.getSession().getServletContext();

		int num = 0;
		// 从application中拿
		if (application.getAttribute("num") != null) {
			num = (Integer) application.getAttribute("num");
		}
		num++;
		application.setAttribute("num", num);
		
		//监听服务器端向客户端发送的消息
		MyWebSocket.sendMessage(String.valueOf(num));

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {

		System.out.println("session失效");

		// 当前有多少人在线：session销毁时减去

		// 生成seesion
		HttpSession session = event.getSession();

		// 生成application
		ServletContext application = event.getSession().getServletContext();

		int num = 0;
		// 从application中拿
		if (application.getAttribute("num") != null) {
			num = (Integer) application.getAttribute("num");
		}
		num--;
		if (num <= 0) {
			num = 0;
		}
		application.setAttribute("num", num);

		//监听服务器端向客户端发送的消息
		MyWebSocket.sendMessage(String.valueOf(num));
		
		
	}

}
