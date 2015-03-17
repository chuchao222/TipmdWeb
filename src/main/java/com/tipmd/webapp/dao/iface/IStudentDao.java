package com.tipmd.webapp.dao.iface;

import com.tipmd.webapp.entity.Student;

public interface IStudentDao {
//	Student getStudentById(int id);
//	void deleteStudentById(int id);
//	List<Student> getStudents(Student cond, int page, int pageSize);
//	int getCountOfStudents(Student cond);
//	void updateStudent(Student student);
	int createStudent(Student student);
	Student getStudentWithScores(int id);
}
