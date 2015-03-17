package com.tipmd.webapp.vo;

public class CourseVo {
	private int id; //课程主键
	private String name; //课程名
	private int credit; //学分
	
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
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	@Override
	public String toString() {
		return "CourseVo [id=" + id + ", name=" + name + ", credit=" + credit
				+ "]";
	}
}
