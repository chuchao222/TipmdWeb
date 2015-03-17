package com.tipmd.webapp.entity;

import java.io.Serializable;

public class Course implements Serializable
{
	private static final long serialVersionUID = -6304589163597683721L;
	
	private int id; //课程主键
	private String name; //课程名
	private int credit; //学分
	
	public static Course emptyCourse() {
		return new Course();
	}
	
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
		return "Course [id=" + id + ", name=" + name + ", credit=" + credit
				+ "]";
	}
}
