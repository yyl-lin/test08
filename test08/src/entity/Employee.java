package entity;

import java.io.Serializable;

/**
* @author ������
* @version ����ʱ�䣺2018��7��25�� ����7:33:42
* ��˵����Ա����Ϣ����ϵͳ
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
