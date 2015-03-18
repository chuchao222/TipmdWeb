package com.tipmd.webapp.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 缺省的命名规范为： Class name of T + Mapper, 比如ScoreMapper, StudentMapper
 * 
 * @author bowee2010
 *
 * @param <T> - 实体
 * @param <PK> － 主键
 */

@SuppressWarnings("unchecked")
@Repository
@Transactional
public abstract class GenericDaoImpl<T extends Serializable, PK extends Serializable> implements IGenericDao<T, PK> {

	@Autowired protected SqlSessionTemplate sqlSessionTemplate;  
	protected Logger log = Logger.getLogger(getClass());
	protected GenericDaoConfiguration defaultConfiguration;
	private GenericDaoConfiguration configuration;
	
	public GenericDaoImpl() {
		Type type = getClass().getGenericSuperclass();
		 Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
		 Class<T> entityClass  = (Class<T>) trueType;
		 defaultConfiguration = GenericDaoConfiguration.buildConfiguration(entityClass.getSimpleName()+"Mapper");
	}
	
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
	
	/*
	 * 如果子类想用自己的GenericDaoConfiguration代替缺省的，那么请继承此方法.
	 */
	abstract protected GenericDaoConfiguration buildConfiguration();
	
	private String getStatement(String mapperId) {
		String namespace = getConfiguration().getMapperNamespace();
		return new StringBuilder(namespace).append(".").append(mapperId).toString();
	}
	
	private GenericDaoConfiguration getConfiguration() {
		if(configuration != null) return configuration;
		
		if(buildConfiguration() != null) {
			this.configuration = buildConfiguration();
		} else {
			this.configuration = defaultConfiguration;
		}
		
		return configuration;
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

			setMapperNamespace(mapperNamespace);
			setSaveMapperId(saveMapperId);
			setUpdateMapperId(updateMapperId);
			setDeleteMapperId(deleteMapperId);
			setFindAllMapperId(findAllMapperId);
			setFindCountOfAllMapperId(findCountOfAllMapperId);
			setFindByIdMapperId(findByIdMapperId);
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
			if(StringUtils.isEmpty(saveMapperId)) 
				this.saveMapperId = DEFAULT_SAVE_MAPPER_ID;
			else
				this.saveMapperId = saveMapperId;
			
			return this;
		}
		
		public String getUpdateMapperId() {
			return updateMapperId;
		}
		public GenericDaoConfiguration setUpdateMapperId(String updateMapperId) {
			if(StringUtils.isEmpty(updateMapperId)) 
				this.updateMapperId = DEFAULT_UPDATE_MAPPER_ID;
			else
				this.updateMapperId = updateMapperId;
			return this;
		}
		
		public String getDeleteMapperId() {
			return deleteMapperId;
		}
		public GenericDaoConfiguration setDeleteMapperId(String deleteMapperId) {
			if(StringUtils.isEmpty(deleteMapperId)) 
				this.deleteMapperId = DEFAULT_DELETE_MAPPER_ID;
			else
				this.deleteMapperId = deleteMapperId;
			
			return this;
		}
		public String getFindAllMapperId() {
			return findAllMapperId;
		}
		public GenericDaoConfiguration setFindAllMapperId(String findAllMapperId) {
			if(StringUtils.isEmpty(findAllMapperId)) 
				this.findAllMapperId = DEFAULT_FIND_ALL_MAPPER_ID;
			else
				this.findAllMapperId = findAllMapperId;
			
			return this;
		}
		public String getFindByIdMapperId() {
			return findByIdMapperId;
		}
		public GenericDaoConfiguration setFindByIdMapperId(String findByIdMapperId) {
			if(StringUtils.isEmpty(findByIdMapperId)) 
				this.findByIdMapperId = DEFAULT_FIND_BY_ID_MAPPER_ID;
			else
				this.findByIdMapperId = findByIdMapperId;
			
			return this;
		}

		public String getFindCountOfAllMapperId() {
			return findCountOfAllMapperId;
		}

		public GenericDaoConfiguration setFindCountOfAllMapperId(String findCountOfAllMapperId) {
			if(StringUtils.isEmpty(findCountOfAllMapperId)) 
				this.findCountOfAllMapperId = DEFAULT_FIND_COUNT_OF_ALL_MAPPER_ID;
			else
				this.findCountOfAllMapperId = findCountOfAllMapperId;
			
			return this;
		}

		@Override
		public String toString() {
			return "GenericDaoConfiguration [mapperNamespace="
					+ mapperNamespace + ", saveMapperId=" + saveMapperId
					+ ", updateMapperId=" + updateMapperId
					+ ", deleteMapperId=" + deleteMapperId
					+ ", findAllMapperId=" + findAllMapperId
					+ ", findCountOfAllMapperId=" + findCountOfAllMapperId
					+ ", findByIdMapperId=" + findByIdMapperId + "]";
		}
	}
}
