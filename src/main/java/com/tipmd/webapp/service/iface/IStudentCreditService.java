package com.tipmd.webapp.service.iface;

import java.util.List;

import com.tipmd.webapp.vo.ScoreVo;
import com.tipmd.webapp.vo.StudentVo;

public interface IStudentCreditService {
	int addStudent(StudentVo stu);
	void updateStudent(StudentVo stu);
	void deleteStudent(int id);
	StudentVo getStudentVo(int id);
	List<StudentVo> getStudents(StudentVo cond);
	int getCountOfStudent(StudentVo cond);
	
	StudentVo getStudentAndScores(int id);
	List<ScoreVo> getStudentScores(int studentId);
}
