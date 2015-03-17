package com.tipmd.webapp.dao.iface;

import java.util.List;

import com.tipmd.webapp.dao.pager.Pager;

/**
 * @author bowee2010
 * 
 * 泛化接口, 包含一般DAO层通用接口！
 */
public interface IGenericDao<T, PK> 
{
	/**
	 * 保存实体
	 * @param entity
	 */
	void save(T entity);

	/**
	 * 更新实体
	 * 
	 * @param entity - 要更新的实体
	 */
	void update(T entity);

	/**
	 * 删除实体
	 * 
	 * @param id - 实体主键
	 */
	void deleteById(PK id);

	/**
	 * 按条件分页查询实体
	 * 
	 * @param entity - 查询条件
	 * @param pager - 分业类
	 * @return 分页查询结果集
	 */
	List<T> findAll(T entity, Pager pager);
	
	/**
	 * 按条件查询实体总记录条数
	 * 
	 * @param entity - 查询条件
	 * @return 实体总数
	 */
	Integer findCountOfAll(T entity);
	
	/**
	 * 按主键查询实体
	 * 
	 * @param id - 主键
	 * @return
	 */
	T findById(PK id);

}
