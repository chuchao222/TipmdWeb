package com.tipmd.webapp.vo;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.tipmd.webapp.entity.Student;
import com.tipmd.webapp.entity.Student.Sex;

public class StudentVo extends BaseVo
{
	private static final long serialVersionUID = 3078506310206300078L;
	private Integer id;
	private String name;
	private String pwd;
	private Date birthday;
	private Sex sex;
	private List<ScoreVo> scores;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public StudentVo setName(String name) {
		this.name = name;
		return this;
	}
	public String getPwd() {
		return pwd;
	}
	public StudentVo setPwd(String pwd) {
		this.pwd = pwd;
		return this;
	}
	public Date getBirthday() {
		return birthday;
	}
	public StudentVo setBirthday(Date birthday) {
		this.birthday = birthday;
		return this;
	}
	
	public Sex getSex() {
		return sex;
	}
	public StudentVo setSex(Sex sex) {
		this.sex = sex;
		return this;
	}
	
	public static StudentVo from(Student student) {
		StudentVo target = new StudentVo();
		BeanUtils.copyProperties(student, target);
		return target;
	}
	
	public Student convertToEntity() {
		Student target = new Student();
		BeanUtils.copyProperties(this, target);
		return target;
	}
	public List<ScoreVo> getScores() {
		return scores;
	}
	public void setScores(List<ScoreVo> scores) {
		this.scores = scores;
	}
	@Override
	public String toString() {
		return "StudentVo [id=" + id + ", name=" + name + ", pwd=" + pwd
				+ ", birthday=" + birthday + ", sex=" + sex + ", scores="
				+ scores + "]";
	}
}
