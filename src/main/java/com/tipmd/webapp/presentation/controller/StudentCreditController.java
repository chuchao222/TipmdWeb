package com.tipmd.webapp.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tipmd.webapp.presentation.json.JsonCollection;
import com.tipmd.webapp.presentation.json.JsonObject;
import com.tipmd.webapp.service.iface.IStudentCreditService;
import com.tipmd.webapp.vo.ScoreVo;
import com.tipmd.webapp.vo.StudentVo;

@RestController
public class StudentCreditController extends BaseController
{
	@Autowired
	private IStudentCreditService service;
	
	@RequestMapping(value = "/studentHomePage.do", method = RequestMethod.GET)
	public ModelAndView gotoStudentHomePage(@ModelAttribute("studentVo") StudentVo studentVo) {
		//Don't add ".jsp" suffix, see 'resourceViewResolver' bean definition in spring-web.xml for detail.
		ModelAndView mv = new ModelAndView("student/home"); 
		if(studentVo == null) studentVo = new StudentVo();
		List<StudentVo> list = service.getStudents(studentVo);
		mv.addObject("list", list);
		return mv;
	}
	
	
	@RequestMapping(value = "/getStudentScores.do", method = RequestMethod.GET)
	public ModelAndView getStudentScores(@ModelAttribute("id") int id) {
		ModelAndView mv = new ModelAndView("student/student_scores");
		List<ScoreVo> list = service.getStudentScores(id);
		mv.addObject("scoreList", list);
		
		StudentVo studentVo = service.getStudentVo(id);
		mv.addObject("student", studentVo);
		return mv;
	}
	
	//============示范restful风格，返回json格式================//
	
	@RequestMapping(value = "/students", method = RequestMethod.GET) 
	public JsonCollection getStudents(@ModelAttribute("studentVo") StudentVo studentVo) {
		if(studentVo == null) studentVo = new StudentVo();
		List<StudentVo> list = service.getStudents(studentVo);
		return JsonCollection.generateSuccessJsonCollection(list, 1, 10);
	}
	
	//使用 GET /students/{id} 访问
	@RequestMapping(value = "/students/{id}", method = RequestMethod.GET) 
	public JsonObject getStudentById(@PathVariable int id) {
		StudentVo student = service.getStudentAndScores(id);
		if(student != null)
			return JsonObject.generateSuccessJsonObject("", student);
		
		return JsonObject.generateFailedJsonObject("Failed to get student for id:" + id);
	}
}
