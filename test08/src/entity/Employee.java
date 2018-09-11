package entity;

import java.io.Serializable;

/**
* @author 岳玉磊
* @version 创建时间：2018年7月25日 下午7:33:42
* 类说明：员工信息管理系统
*/
public class Employee{
	private int id;
	private String name;
	private String sex;
	private int age;
	private String photo;
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	private Department dep;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
			this.age = age;
	}
	public Department getDep() {
		return dep;
	}
	public void setDep(Department dep) {
		this.dep = dep;
	}	
	
	
}
