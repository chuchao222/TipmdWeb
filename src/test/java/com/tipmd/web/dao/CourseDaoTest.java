package com.tipmd.web.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

import com.tipmd.webapp.dao.iface.ICourseDao;
import com.tipmd.webapp.dao.iface.IGenericDao;
import com.tipmd.webapp.entity.Course;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-config.xml")
public class CourseDaoTest 
{
	@Autowired
	@Qualifier("iCourseDao") 
	private IGenericDao<Course, Integer> dao;

	private Logger log = Logger.getLogger(getClass());
	
	@Test
	@Transactional
	@Rollback(true)
	public void testInsertAndDeleteCourse() {
		Course c = new Course();
		c.setName("语文");
		c.setCredit(6);
		int cid =  ((ICourseDao)dao).createCourse(c);
		
		Course c1 = dao.findById(cid);
		assertNotNull(c1);
		assertTrue(c1.getId() > 0);
		
		
		c = new Course();
		c.setName("语文");
		c.setCredit(3);
		cid = ((ICourseDao)dao).createCourse(c);
		
		List<Course> cList = dao.findAll(null, null);
		assertTrue(cList != null);
		assertTrue(cList.size() >= 2);
		for(Course cc:cList) {
			log.debug(cc);
		}
		
		dao.deleteById(cid);
		c = dao.findById(cid);
		assertNull(c);
	}
}
