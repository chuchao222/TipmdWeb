package com.tipmd.webapp.dao.pager.dialect;

import org.springframework.util.StringUtils;

import com.tipmd.webapp.dao.pager.Orders;
import com.tipmd.webapp.dao.pager.Pager;

public abstract class Dialect 
{
	public static final String SELECT_IN_LOWER_CASE = "select";
	
	/**
	 * 将原始SQL语句（不含分页语句）组装成含有排序以及分页信息的SQL语句
	 * @param originalSQL 原始SQL
	 * @param orders 排序语句
	 * @param offset 分页纪录偏移量
	 * @param limit  分页大小（每页纪录数）
	 * @return 含有分页语句的SQL语句
	 */
	protected abstract String buildLimitString(String originalSQL, Orders orders, int offset, int limit);

	/**
	 * 根据原始SQL语句(select * from .. where .....)转化成求纪录总数的语句(select count(*) from ... where ....)
	 * @param originalSQL
	 * @return 查询纪录总数的SQL
	 */
	protected abstract String buildCountString(String originalSQL);

	/**
	 * 检查传入的Query SQL语句是否合法
	 * @param querySQL
	 */
	private void validateQuerySQL(String querySQL) {
		if(StringUtils.isEmpty(querySQL) || querySQL.trim().length() == 0)
			throw new IllegalArgumentException("Parameter 'querySQL' cannot be null.");
		
		querySQL = querySQL.toLowerCase();
		if(!querySQL.contains(SELECT_IN_LOWER_CASE)) {
			throw new IllegalArgumentException("Expected a query sql, actual sql is :" + querySQL);
		}
	}
	
	/**
	 * 将原始SQL语句（不含分页语句）组装成含有排序以及分页信息的SQL语句
	 * @param originalSQL 原始SQL
	 * @param pager 分页对象(含有分页和排序信息)
	 * @return 含有分页语句的SQL语句
	 */
	public String getLimitSQL(String originalSQL, Pager pager) {
		validateQuerySQL(originalSQL);
		return buildLimitString(originalSQL, pager.getOrders(), pager.getOffset(), pager.getLimit());
	}
	
	/**
	 * 根据原始SQL语句(select * from .. where .....)转化成求纪录总数的语句(select count(*) from ... where ....)
	 * @param originalSQL
	 * @return 查询纪录总数的SQL
	 */
	public String getCountSQL(String originalSQL) {
		validateQuerySQL(originalSQL);
		return buildCountString(originalSQL);
	}
}


