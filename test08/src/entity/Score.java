package entity;

/**
 * @author yueyulei
 * @version 创建时间：2018年8月8日 下午7:17:17
 * @Class 具体说明：
 */
public class Score {
	private int id;
	private Integer value;
	private String grade;
	private Employee emp;
	private Project pro;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Employee getEmp() {
		return emp;
	}
	public void setEmp(Employee emp) {
		this.emp = emp;
	}
	public Project getPro() {
		return pro;
	}
	public void setPro(Project pro) {
		this.pro = pro;
	}

	
	
	
}
