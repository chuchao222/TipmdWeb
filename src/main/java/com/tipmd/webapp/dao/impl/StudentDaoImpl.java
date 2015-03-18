package com.tipmd.webapp.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tipmd.webapp.dao.iface.IStudentDao;
import com.tipmd.webapp.entity.Student;

@Repository("iStudentDao")
@Transactional
public class StudentDaoImpl extends GenericDaoImpl<Student, Integer> implements IStudentDao 
{
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
		return GenericDaoConfiguration.buildConfiguration("StudentMapper")
				.setSaveMapperId("createStudent")
				.setUpdateMapperId("updateStudent")
				.setDeleteMapperId("deleteStudent")
				.setFindAllMapperId("getStudents")
				.setFindCountOfAllMapperId("getCountOfStudents")
				.setFindByIdMapperId("getStudentById");
	}
}
