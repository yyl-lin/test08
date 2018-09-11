package util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

//��ע������ָ��һ��url
@ServerEndpoint("/websocket")
public class MyWebSocket {

	// Set���������ÿ���ͻ��˶�Ӧ��MyWebSocket������Ҫʵ�ַ�����뵥һ�ͻ���ͨ�ŵĻ�������ʹ��Map����ţ�����Key����Ϊ�û���ʶ
	public static Set<MyWebSocket> set = new HashSet<MyWebSocket>();

	// ��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
	private Session session;

	/**
	 * ���ӽ����ɹ����õķ���
	 * 
	 * @param session
	 *            ��ѡ�Ĳ�����sessionΪ��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
	 */
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		set.add(this); // ����set��
		System.out.println("�������Ӽ��룡");
	}

	/**
	 * ���ӹرյ��õķ���
	 */
	@OnClose
	public void onClose() {
		set.remove(this); // ��set��ɾ��
		System.out.println("��һ���ӹرգ�");
	}

	/**
	 * �յ��ͻ�����Ϣ����õķ���
	 * 
	 * @param message
	 *            �ͻ��˷��͹�������Ϣ
	 * @param session
	 *            ��ѡ�Ĳ���
	 */
	// @OnMessage
	// public void onMessage(String message, Session session) {
	// System.out.println("���Կͻ��˵���Ϣ:" + message);
	//
	// // Ⱥ����Ϣ
	// for (MyWebSocket item : set) {
	// try {
	// item.sendMessage(message);
	// } catch (IOException e) {
	// e.printStackTrace();
	// continue;
	// }
	// }
	// }

	/**
	 * ��������ʱ����
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("��������");
		error.printStackTrace();
	}

	/**
	 * ����������ͻ��˷���Ϣ
	 * 
	 * @param session
	 * @param sendMessage
	 */
	// ����������ͻ��˷���Ϣ
	public static void sendMessage(String message) {
		//����set����������session�����ϣ��鿴��ǰ��������
		for (MyWebSocket item : set) {
			try {
				// ��ָ���û�����Ϣ�������session���Ǹ��û���session
				item.session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
