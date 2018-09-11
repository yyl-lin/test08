package util;

public class Pagination {
	private int ye;
	private int maxYe;
	private int beginYe;
	private int endYe;
	private int begin;

	// ye��ǰҳ��count����¼��
	// numInPageÿҳ��ʾ��������numOfPageһҳ��ʾ����ҳ��
	public Pagination(int ye, int count, int numInPage, int numOfPage) {
		this.ye = ye;
		// ��ȫ��Ԥ����ַ���������������
		if (this.ye <= 1) {
			this.ye = 1;
		}
		// �����ҳ��
		// int maxYe = 0;
		// if (count % numInPage == 0) {
		// maxYe = count / numInPage;
		// } else {
		// maxYe = count / numInPage + 1;
		// }

		// ��Ŀ�����A?B:C �﷨��:��Ԫѭ��Ҳ��
		maxYe = count % numInPage == 0 ? count / numInPage : count / numInPage + 1;

		// ��ȫ��Ԥ����ַ���������������
		if (this.ye >= maxYe) {
			this.ye = maxYe;
		}

		// ���ÿ�ʼҳ�ͽ���ҳ(jspѭ����ʱ����)
		beginYe = this.ye - numOfPage / 2;
		if (beginYe <= 1) {
			beginYe = 1;
		}
		endYe = beginYe + numOfPage - 1;
		if (endYe >= maxYe) {
			endYe = maxYe;
		}
		
		// ���õ�ҳ������ʵ�ʵ�ҳ��ʱ
		if (endYe >= maxYe) {
			beginYe = endYe - numOfPage + 1;
		}
		// ���õ�ҳ��С��ʵ�ʵ�ҳ��ʱ
		if (beginYe <= 1) {
			beginYe = 1;
		}
		// �õ�beginֵ
		begin = (this.ye - 1) * numInPage;
	}

	public int getBegin() {
		return begin;
	}

	public int getYe() {
		return ye;
	}

	public int getMaxYe() {
		return maxYe;
	}

	public int getBeginYe() {
		return beginYe;
	}

	public int getEndYe() {
		return endYe;
	}

}
