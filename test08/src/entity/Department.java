package entity;
/**
* @author yueyulei
* @version 创建时间：2018年8月6日 下午7:32:08
* @Class   具体说明：
*/
public class Department {
	private Integer id;
	private String name;
	private int empCount;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getEmpCount() {
		return empCount;
	}
	public void setEmpCount(int empCount) {
		this.empCount = empCount;
	}
	
}
