package com.tipmd.webapp.dao.impl;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDao {
	@Autowired  
    protected SqlSessionTemplate sqlSessionTemplate; 
	protected Logger log = Logger.getLogger(getClass());
}
