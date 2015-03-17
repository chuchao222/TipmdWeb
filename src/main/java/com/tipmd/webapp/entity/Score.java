package com.tipmd.webapp.entity;

import java.io.Serializable;

public class Score implements Serializable
{
	private static final long serialVersionUID = -665154588737119928L;
	private int id;
	private int points;
	private Student student;
	private Course course;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPoints() {
		return points;
	}
	public Score setPoints(int points) {
		this.points = points;
		return this;
	}
	public Student getStudent() {
		return student;
	}
	public Score setStudent(Student student) {
		this.student = student;
		return this;
	}
	public Course getCourse() {
		return course;
	}
	public Score setCourse(Course course) {
		this.course = course;
		return this;
	}
	@Override
	public String toString() {
		return "Score [id=" + id + ", points=" + points + ", course=" + course
				+ "]";
	}
}

