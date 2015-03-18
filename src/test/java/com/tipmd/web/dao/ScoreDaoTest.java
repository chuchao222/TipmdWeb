package com.tipmd.web.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.tipmd.webapp.dao.iface.IGenericDao;
import com.tipmd.webapp.dao.iface.IScoreDao;
import com.tipmd.webapp.entity.Course;
import com.tipmd.webapp.entity.Score;
import com.tipmd.webapp.entity.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-config.xml")
public class ScoreDaoTest 
{
	@Autowired
	@Qualifier("iScoreDao") 
	private IGenericDao<Score, Integer> scoreDao;
	
	//注意@Qualifier
	@Autowired
	@Qualifier("iStudentDao") 
	private IGenericDao<Student, Integer> studentDao;
	
	//注意@Qualifier
	@Autowired
	@Qualifier("iCourseDao") 
	private IGenericDao<Course, Integer> courseDao;
	
	private Logger log = Logger.getLogger(getClass());
	
	@Test
	@Transactional
	@Rollback(true)
	public void testInsertScores() 
	{
		List<Student> studentList = studentDao.findAll(Student.emptyStudent(), null);
		assertNotNull(studentList);
		assertTrue(studentList.size() > 0);
		
		List<Course> courses = courseDao.findAll(Course.emptyCourse(), null); 
		assertNotNull(courses);
		assertTrue(courses.size() > 1);
		
		Course course = courses.get(0); //only one course
		
		for(Student s:studentList) {
			Score score = new Score();
			score.setCourse(course);
			score.setStudent(s);
			score.setPoints(100);
			scoreDao.save(score);
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetScores() 
	{
		List<Student> studentList = studentDao.findAll(Student.emptyStudent(), null);
		assertNotNull(studentList);
		assertTrue(studentList.size() > 0);
		
		for(Student s:studentList) {
			List<Score> scores = ((IScoreDao)scoreDao).getStudentScores(s.getId());
			
			if(scores != null && scores.size() > 0) {
				for(Score score:scores) {
					log.info("----------------------------------");
					log.info(score);
					log.info("----------------------------------");
				}
			}
		}
	}
}
