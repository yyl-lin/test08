package util;

public class Pagination {
	private int ye;
	private int maxYe;
	private int beginYe;
	private int endYe;
	private int begin;

	// ye当前页，count最大记录数
	// numInPage每页显示多少数据numOfPage一页显示多少页码
	public Pagination(int ye, int count, int numInPage, int numOfPage) {
		this.ye = ye;
		// 安全：预防地址栏输入非正常数字
		if (this.ye <= 1) {
			this.ye = 1;
		}
		// 算出总页数
		// int maxYe = 0;
		// if (count % numInPage == 0) {
		// maxYe = count / numInPage;
		// } else {
		// maxYe = count / numInPage + 1;
		// }

		// 三目运算符A?B:C 语法糖:逐元循环也是
		maxYe = count % numInPage == 0 ? count / numInPage : count / numInPage + 1;

		// 安全：预防地址栏输入非正常数字
		if (this.ye >= maxYe) {
			this.ye = maxYe;
		}

		// 设置开始页和结束页(jsp循环的时候用)
		beginYe = this.ye - numOfPage / 2;
		if (beginYe <= 1) {
			beginYe = 1;
		}
		endYe = beginYe + numOfPage - 1;
		if (endYe >= maxYe) {
			endYe = maxYe;
		}
		
		// 设置的页数大于实际的页数时
		if (endYe >= maxYe) {
			beginYe = endYe - numOfPage + 1;
		}
		// 设置的页数小于实际的页数时
		if (beginYe <= 1) {
			beginYe = 1;
		}
		// 得到begin值
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
