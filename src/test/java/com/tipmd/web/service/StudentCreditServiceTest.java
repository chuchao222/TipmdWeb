package com.tipmd.web.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.tipmd.webapp.entity.Student.Sex;
import com.tipmd.webapp.service.iface.IStudentCreditService;
import com.tipmd.webapp.vo.ScoreVo;
import com.tipmd.webapp.vo.StudentVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-config.xml")
public class StudentCreditServiceTest {
	
	@Autowired
	private IStudentCreditService service;
	private Logger log = Logger.getLogger(getClass());

	@Test
	@Transactional
	@Rollback(true)
	public void testAddStudent() {
		StudentVo student = new StudentVo();
		student.setBirthday(new Date()).setName("汤东").setPwd("pwdpwd").setSex(Sex.Male);
		long id = service.addStudent(student);
		log.debug("id="+id);
		assertTrue(id > 0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetStudentAndScores() {
		List<StudentVo> studentList = service.getStudents(new StudentVo());
		assertNotNull(studentList);
		assertTrue(studentList.size() > 0);
		for(StudentVo vo:studentList) {
			StudentVo stu = service.getStudentAndScores(vo.getId());
			log.info(stu);
			log.info("-----------------------------------------");
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetStudentScores() {
		List<StudentVo> studentList = service.getStudents(new StudentVo());
		assertNotNull(studentList);
		assertTrue(studentList.size() > 0);
		for(StudentVo vo:studentList) {
			List<ScoreVo> scores = service.getStudentScores(vo.getId());
			if(scores != null && scores.size() > 0) {
				for(ScoreVo svo:scores) {
					log.info(svo);
					log.info("=================================================");
				}
			}
		}
	}
}
