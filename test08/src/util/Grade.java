package util;

public enum Grade {
	
	a("优秀"),b("良好"),c("一般"),d("及格"),e("不及格");

	private String value;
	private Grade(String value) {
		this.value = value;
	}
	
	public String val() {
		return value;
	}
	
}
