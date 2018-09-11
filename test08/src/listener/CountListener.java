package listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import util.MyWebSocket;

/*
 * ��¼��վ��������
 */
public class CountListener implements HttpSessionListener {
	// ����session ����application��������ServletContext
	@Override
	public void sessionCreated(HttpSessionEvent event) {

		System.out.println("session����");

		// ����seesion
		HttpSession session = event.getSession();

		// ����application
		ServletContext application = event.getSession().getServletContext();

		int num = 0;
		// ��application����
		if (application.getAttribute("num") != null) {
			num = (Integer) application.getAttribute("num");
		}
		num++;
		application.setAttribute("num", num);
		
		//��������������ͻ��˷��͵���Ϣ
		MyWebSocket.sendMessage(String.valueOf(num));

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {

		System.out.println("sessionʧЧ");

		// ��ǰ�ж��������ߣ�session����ʱ��ȥ

		// ����seesion
		HttpSession session = event.getSession();

		// ����application
		ServletContext application = event.getSession().getServletContext();

		int num = 0;
		// ��application����
		if (application.getAttribute("num") != null) {
			num = (Integer) application.getAttribute("num");
		}
		num--;
		if (num <= 0) {
			num = 0;
		}
		application.setAttribute("num", num);

		//��������������ͻ��˷��͵���Ϣ
		MyWebSocket.sendMessage(String.valueOf(num));
		
		
	}

}
