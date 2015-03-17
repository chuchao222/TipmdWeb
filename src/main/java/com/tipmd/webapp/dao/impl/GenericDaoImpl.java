package com.tipmd.webapp.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tipmd.webapp.dao.iface.IGenericDao;
import com.tipmd.webapp.dao.pager.Pager;

/**
 * DAO层基类
 * 默认实现了保存单个实体，更新实体，按主键删除实体，按条件查询纪录，按条件查询纪录总数，按主键查找实体方法
 * 
 * ＝＝＝＝＝＝＝＝＝Mapper xml 文件中 MapperId 命名规范＝＝＝＝＝＝＝＝＝＝
 * 说明				mapperId
 * 保存实体 			save
 * 更新实体			update
 * 俺主键删除实体		deleteById
 * 按条件查找纪录		findAll
 * 按条件查找纪录总数	findCountOfAll
 * 按主键查找实体		findById
 * 
 * 
 * ＝＝＝＝＝＝＝＝＝Mapper xml文件中 mapper的namespace命名规范＝＝＝＝＝＝＝＝＝＝
 * 缺省的命名规范为： packagename.classname+Mapper, 比如com.tipweb.webapp.entity.ScoreMapper
 * 
 * @author bowee2010
 *
 * @param <T> - 实体
 * @param <PK> － 主键
 */

@SuppressWarnings("unchecked")
@Repository
@Transactional
//@Scope(value="prototype")
public abstract class GenericDaoImpl<T extends Serializable, PK extends Serializable> implements IGenericDao<T, PK> {

	@Autowired protected SqlSessionTemplate sqlSessionTemplate;  
	protected Logger log = Logger.getLogger(getClass());
	//private GenericDaoConfiguration configuration;
	
	@Override
	public void save(T entity) {
		sqlSessionTemplate.insert(getStatement(getConfiguration().getSaveMapperId()), entity);
	}

	@Override
	public void update(T entity) {
		sqlSessionTemplate.update(getStatement(getConfiguration().getUpdateMapperId()), entity);
	}

	@Override
	public void deleteById(PK id) {
		sqlSessionTemplate.delete(getStatement(getConfiguration().getDeleteMapperId()), id);
	}

	@Override
	public List<T> findAll(T entity, Pager pager) {
		if(pager == null)
			return sqlSessionTemplate.selectList(getStatement(getConfiguration().getFindAllMapperId()), entity); 
		
		return sqlSessionTemplate.selectList(getStatement(getConfiguration().getFindAllMapperId()), entity, pager);
	}

	@Override
	public Integer findCountOfAll(T entity) {
		return sqlSessionTemplate.selectOne(getStatement(getConfiguration().getFindCountOfAllMapperId()), entity);
	}
	
	@Override
	public T findById(PK id) {
		return sqlSessionTemplate.selectOne(getStatement(getConfiguration().getFindByIdMapperId()), id);
	}
	
	public static class GenericDaoConfiguration {
		private String mapperNamespace;
		private String saveMapperId;
		private String updateMapperId;
		private String deleteMapperId;
		private String findAllMapperId;
		private String findCountOfAllMapperId;
		private String findByIdMapperId;
		
		private final static String DEFAULT_SAVE_MAPPER_ID = "save";
		private final static String DEFAULT_UPDATE_MAPPER_ID = "update";
		private final static String DEFAULT_DELETE_MAPPER_ID = "deleteById";
		private final static String DEFAULT_FIND_ALL_MAPPER_ID = "findAll";
		private final static String DEFAULT_FIND_COUNT_OF_ALL_MAPPER_ID = "findCountOfAll";
		private final static String DEFAULT_FIND_BY_ID_MAPPER_ID = "findById";
		
		private GenericDaoConfiguration() {}
		
		private GenericDaoConfiguration(String mapperNamespace) {
			this(mapperNamespace, 
					DEFAULT_SAVE_MAPPER_ID, 
					DEFAULT_UPDATE_MAPPER_ID, 
					DEFAULT_DELETE_MAPPER_ID, 
					DEFAULT_FIND_ALL_MAPPER_ID, 
					DEFAULT_FIND_COUNT_OF_ALL_MAPPER_ID, 
					DEFAULT_FIND_BY_ID_MAPPER_ID);
		}

		private GenericDaoConfiguration(String mapperNamespace, 
				String saveMapperId, 
				String updateMapperId,
				String deleteMapperId,
				String findAllMapperId,
				String findCountOfAllMapperId,
				String findByIdMapperId) 
		{

			if(StringUtils.isEmpty(mapperNamespace)) 
				throw new IllegalArgumentException("You should specify mapper namespace.");
			this.mapperNamespace = mapperNamespace;
			this.saveMapperId = saveMapperId;
			this.updateMapperId = updateMapperId;
			this.deleteMapperId = deleteMapperId;
			this.findAllMapperId = findAllMapperId;
			this.findCountOfAllMapperId = findCountOfAllMapperId;
			this.findByIdMapperId = findByIdMapperId;
		}
		
		public static GenericDaoConfiguration buildConfiguration(String mapperNamespace) {
			return new GenericDaoConfiguration(mapperNamespace);
		}
		
		public static GenericDaoConfiguration buildConfiguration(
				String mapperNamespace, 
				String saveMapperId, 
				String updateMapperId,
				String deleteMapperId,
				String findAllMapperId,
				String findCountOfAllMapperId,
				String findByIdMapperId) {
			return new GenericDaoConfiguration(mapperNamespace, 
					saveMapperId, 
					updateMapperId,
					deleteMapperId,
					findAllMapperId,
					findCountOfAllMapperId,
					findByIdMapperId);
		}
		
		
		public String getMapperNamespace() {
			return mapperNamespace;
		}
		public GenericDaoConfiguration setMapperNamespace(String mapperNamespace) {
			this.mapperNamespace = mapperNamespace;
			return this;
		}
		public String getSaveMapperId() {
			return saveMapperId;
		}
		public GenericDaoConfiguration setSaveMapperId(String saveMapperId) {
			this.saveMapperId = saveMapperId;
			return this;
		}
		public String getUpdateMapperId() {
			return updateMapperId;
		}
		public GenericDaoConfiguration setUpdateMapperId(String updateMapperId) {
			this.updateMapperId = updateMapperId;
			return this;
		}
		public String getDeleteMapperId() {
			return deleteMapperId;
		}
		public GenericDaoConfiguration setDeleteMapperId(String deleteMapperId) {
			this.deleteMapperId = deleteMapperId;
			return this;
		}
		public String getFindAllMapperId() {
			return findAllMapperId;
		}
		public GenericDaoConfiguration setFindAllMapperId(String findAllMapperId) {
			this.findAllMapperId = findAllMapperId;
			return this;
		}
		public String getFindByIdMapperId() {
			return findByIdMapperId;
		}
		public GenericDaoConfiguration setFindByIdMapperId(String findByIdMapperId) {
			this.findByIdMapperId = findByIdMapperId;
			return this;
		}

		public String getFindCountOfAllMapperId() {
			return findCountOfAllMapperId;
		}

		public GenericDaoConfiguration setFindCountOfAllMapperId(String findCountOfAllMapperId) {
			this.findCountOfAllMapperId = findCountOfAllMapperId;
			return this;
		}
	}
	
	/*
	 * 如果子类想用自己的GenericDaoConfiguration代替缺省的，那么请继承此方法.
	 */
	abstract protected GenericDaoConfiguration buildConfiguration();
	
	private String getStatement(String mapperId) {
		String namespace = getConfiguration().getMapperNamespace();
		return new StringBuilder(namespace).append(".").append(mapperId).toString();
	}
	
	private GenericDaoConfiguration getConfiguration() {
//		if(configuration != null) return configuration;
//		
//		if(buildConfiguration() != null) {
//			this.configuration = buildConfiguration();
//		} else {
//			ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();  
//			Class <T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
//	        String defaultNamespace = new StringBuffer(clazz.getSimpleName()).append("Mapper").toString();
//	        log.info("No mapper namespace specified, use default namespace :" + defaultNamespace);
//			this.configuration = GenericDaoConfiguration.buildConfiguration(defaultNamespace);
//		}
//		
//		return configuration;
		return buildConfiguration();
	}
}
