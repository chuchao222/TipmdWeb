package com.tipmd.webapp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Student implements Serializable
{
	private static final long serialVersionUID = -923135402830365734L;
	public enum Sex {
		Male, Female, X
	}
	
	private Integer id;
	private String name;
	private String pwd;
	private Date birthday;
	private Sex sex;
	private List<Score> scores;
	
	public static Student emptyStudent() {
		return new Student();
	}
	
	public Integer getId() {
		return id;
	}
	public Student setId(Integer id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public Student setName(String name) {
		this.name = name;
		return this;
	}
	public String getPwd() {
		return pwd;
	}
	public Student setPwd(String pwd) {
		this.pwd = pwd;
		return this;
	}
	public Date getBirthday() {
		return birthday;
	}
	public Student setBirthday(Date birthday) {
		this.birthday = birthday;
		return this;
	}
	
	public Sex getSex() {
		return sex;
	}
	public Student setSex(Sex sex) {
		this.sex = sex;
		return this;
	}
	public List<Score> getScores() {
		return scores;
	}
	public void setScores(List<Score> scores) {
		this.scores = scores;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", pwd=" + pwd
				+ ", birthday=" + birthday + ", sex=" + sex + ", scores="
				+ scores + "]";
	}

}
