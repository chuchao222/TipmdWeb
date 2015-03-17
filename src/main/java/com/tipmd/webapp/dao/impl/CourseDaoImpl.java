package com.tipmd.webapp.dao.impl;

import org.springframework.stereotype.Repository;

import com.tipmd.webapp.dao.iface.ICourseDao;
import com.tipmd.webapp.entity.Course;
import com.tipmd.webapp.entity.Student;

@Repository("iCourseDao")
public class CourseDaoImpl extends GenericDaoImpl<Student, Integer> implements ICourseDao {

	private static GenericDaoConfiguration configuration = GenericDaoConfiguration.buildConfiguration("CourseMapper");
	
	@Override
	protected GenericDaoConfiguration buildConfiguration() {
		return configuration;
	}
	
	@Override
	public int createCourse(Course course) {
		sqlSessionTemplate.insert("CourseMapper.createCourse", course);
		return course.getId();
	}
	
}
