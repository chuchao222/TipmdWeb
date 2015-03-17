package com.tipmd.webapp.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tipmd.webapp.dao.iface.IStudentDao;
import com.tipmd.webapp.dao.impl.GenericDaoImpl.GenericDaoConfiguration;
import com.tipmd.webapp.entity.Student;

@Repository("iStudentDao")
@Transactional
public class StudentDaoImpl extends GenericDaoImpl<Student, Integer> implements IStudentDao 
{
	private static GenericDaoConfiguration configuration = GenericDaoConfiguration.buildConfiguration("CourseMapper")
			.setSaveMapperId("createStudent")
			.setUpdateMapperId("updateStudent")
			.setDeleteMapperId("deleteStudent")
			.setFindAllMapperId("getStudents")
			.setFindCountOfAllMapperId("getCountOfStudents")
			.setFindByIdMapperId("getStudentById");;
	
	@Override
	public int createStudent(Student student) {
		sqlSessionTemplate.insert("StudentMapper.createStudent", student);
		return student.getId();
	}
	
	@Override
	public Student getStudentWithScores(int id) {
		return sqlSessionTemplate.selectOne("StudentMapper.getStudentAndScoresById", id);
	}

	@Override
	protected GenericDaoConfiguration buildConfiguration() {
		return configuration;
	}
	
	//如果子类的mapperid 和 namespace 遵守命名规范，此方法可以直接返回null。
	//有关命名规范请参考 GenericDaoImpl 开头的注释
	/*
	@Override
	protected GenericDaoConfiguration buildConfiguration() {
		return null;
	}
	*/
}
