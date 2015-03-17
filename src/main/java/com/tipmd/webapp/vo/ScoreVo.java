package com.tipmd.webapp.vo;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.BeanUtils;

import com.tipmd.webapp.entity.Score;

public class ScoreVo {
	private int id;
	private int points;
	@JsonIgnore
	private StudentVo student;
	@JsonIgnore
	private CourseVo course;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public StudentVo getStudent() {
		return student;
	}
	public void setStudent(StudentVo student) {
		this.student = student;
	}
	public CourseVo getCourse() {
		return course;
	}
	public void setCourse(CourseVo course) {
		this.course = course;
	}
	public static ScoreVo from(Score score) {
		ScoreVo vo = new ScoreVo();
		BeanUtils.copyProperties(score, vo);
		if(score.getStudent() != null) {
			vo.setStudent(new StudentVo());
			BeanUtils.copyProperties(score.getStudent(), vo.getStudent());
		}
		if(score.getCourse() != null) {
			vo.setCourse(new CourseVo());
			BeanUtils.copyProperties(score.getCourse(), vo.getCourse());
		}
		return vo;
	}
	@Override
	public String toString() {
		return "ScoreVo [id=" + id + ", points=" + points + ", student.name="
				+ student.getName() + ", course.name=" + course.getName() + "]";
	}
}
