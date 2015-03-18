package com.tipmd.webapp.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tipmd.webapp.dao.iface.ICourseDao;
import com.tipmd.webapp.entity.Course;

@Repository("iCourseDao")
@Transactional
public class CourseDaoImpl extends GenericDaoImpl<Course, Integer> implements ICourseDao {

	@Override
	protected GenericDaoConfiguration buildConfiguration() {
		//可以返回null因为mapper id符合规范,这里做测试所以返回了非null
		//return null;
		return GenericDaoConfiguration.buildConfiguration("CourseMapper");
	}
	
	@Override
	public int createCourse(Course course) {
		sqlSessionTemplate.insert("CourseMapper.createCourse", course);
		return course.getId();
	}
	
}
