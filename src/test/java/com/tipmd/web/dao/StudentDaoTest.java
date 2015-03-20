package com.tipmd.web.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
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
import com.tipmd.webapp.dao.iface.IStudentDao;
import com.tipmd.webapp.dao.pager.Pager;
import com.tipmd.webapp.entity.Student;
import com.tipmd.webapp.entity.Student.Sex;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-config.xml")
public class StudentDaoTest 
{
	//@Autowired
	//private IStudentDao dao;
	
	@Autowired
	@Qualifier("iStudentDao") 
	private IGenericDao<Student, Integer> studentDao;
	
	private Logger log = Logger.getLogger(getClass());
	
	//缺省@Rollback为true，如果改成false，里边的方法执行将影响到真实数据。建议不写，或者为true
	@Test
	@Transactional
	@Rollback(true)
	public void testInsertAndDeleteStudent() {
		
		Student student = new Student();
		student.setBirthday(com.tipmd.webapp.utils.DateUtil.string2Date("1988-06-01", "yyy-MM-dd"))
				.setName("汤东").setPwd("123456").setSex(Sex.Male);
		int id = ((IStudentDao)studentDao).createStudent(student);
		log.debug("id="+id);
		assertTrue(id > 0);
		
		studentDao.deleteById(id);
		Student stu = studentDao.findById(id);
		assertNull(stu);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetStudent() {
		Student cond = new Student();
		Pager pager = new Pager(2, 6); 
		pager.addOrder("id", null).addOrder("birthday", Pager.Ordering.DESC);
		List<Student> list = studentDao.findAll(cond, pager);
		assertNotNull(list);
		
		//Student studentWithScores = null;
		for(Student s:list) {
			log.info("-------------------------");
			log.info(s);
			log.info("-------------------------");
			//studentWithScores = ((IStudentDao)studentDao).getStudentWithScores(s.getId());
			//log.info(studentWithScores);
		}
		log.debug("total items :" + pager.getTotalCount());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateStudent() {
		Student cond = new Student();
		List<Student> list = studentDao.findAll(cond, null);
		assertNotNull(list);
		assertTrue(list.size() > 0);
		
		Date newBirthday = new Date();
		String newPassword = "123456";
		
		for(Student s:list) {
			log.debug("before update....");
			log.debug(s);
			s.setBirthday(newBirthday);
			s.setPwd(newPassword);
			
			studentDao.update(s);
			int id = s.getId();
			
			Student updatedStudent = studentDao.findById(id);
			log.debug("after update student's info");
			log.debug(updatedStudent);
			
		}
	}
	
	
}
