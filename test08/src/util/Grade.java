package util;

public enum Grade {
	
	a("����"),b("����"),c("һ��"),d("����"),e("������");

	private String value;
	private Grade(String value) {
		this.value = value;
	}
	
	public String val() {
		return value;
	}
	
}
