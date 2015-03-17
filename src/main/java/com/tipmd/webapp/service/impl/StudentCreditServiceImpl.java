package com.tipmd.webapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tipmd.webapp.dao.iface.IGenericDao;
import com.tipmd.webapp.dao.iface.IScoreDao;
import com.tipmd.webapp.dao.iface.IStudentDao;
import com.tipmd.webapp.entity.Score;
import com.tipmd.webapp.entity.Student;
import com.tipmd.webapp.service.iface.IStudentCreditService;
import com.tipmd.webapp.vo.ScoreVo;
import com.tipmd.webapp.vo.StudentVo;

@SuppressWarnings("unchecked")
@Service
@Transactional
public class StudentCreditServiceImpl implements IStudentCreditService {

	@Autowired
	private IStudentDao studentDao;
	@Autowired
	private IScoreDao scoreDao;
	
	@Override
	public int addStudent(StudentVo stu) {
		Student student = stu.convertToEntity();
		return studentDao.createStudent(student);
	}

	@Override
	public void updateStudent(StudentVo stu) {
		Student student = stu.convertToEntity();
		//studentDao.updateStudent(student);
		((IGenericDao<Student, Integer>)studentDao).update(student);
	}

	@Override
	public void deleteStudent(int id) {
		//studentDao.deleteStudentById(id);
		((IGenericDao<Student, Integer>)studentDao).deleteById(id);
	}

	@Override
	public StudentVo getStudentVo(int id) {
		//Student student = studentDao.getStudentById(id);
		Student student = ((IGenericDao<Student, Integer>)studentDao).findById(id);
		return StudentVo.from(student);
	}

	@Override
	public List<StudentVo> getStudents(StudentVo cond) {
		Student student = cond.convertToEntity();
		//List<Student> entityList = studentDao.getStudents(student, cond.getPage(), cond.getPageSize());
		List<Student> entityList = ((IGenericDao<Student, Integer>)studentDao).findAll(student, null);
		if(entityList == null || entityList.size() == 0) return null;
		
		List<StudentVo> retList = new ArrayList<StudentVo>(entityList.size());
		for(Student s:entityList) {
			retList.add(StudentVo.from(s));
		}
		return retList;
	}

	@Override
	public int getCountOfStudent(StudentVo cond) {
		Student student = cond.convertToEntity();
		//return studentDao.getCountOfStudents(student);
		return ((IGenericDao<Student, Integer>)studentDao).findCountOfAll(student);
	}
	
	@Override
	public StudentVo getStudentAndScores(int id) {
		Student student = studentDao.getStudentWithScores(id);
		StudentVo vo = StudentVo.from(student);
		if(student.getScores() != null) {
			BeanUtils.copyProperties(student.getScores(), vo.getScores());
		}
		return vo;
	}

	@Override
	public List<ScoreVo> getStudentScores(int studentId) {
		List<Score> scores =  scoreDao.getStudentScores(studentId);
		if(scores == null || scores.size() == 0) return null;
		
		List<ScoreVo> retList = new ArrayList<>(scores.size());
		for(Score s:scores) {
			retList.add(ScoreVo.from(s));
		}
		return retList;
	}
}
