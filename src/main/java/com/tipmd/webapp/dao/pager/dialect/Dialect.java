package com.tipmd.webapp.dao.pager.dialect;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.tipmd.webapp.dao.pager.InvalidSelectSqlException;
import com.tipmd.webapp.dao.pager.Pager;

public abstract class Dialect 
{
	public static final String SELECT_IN_LOWER_CASE = "select";
	
	/**
	 * 将原始SQL语句（不含分页语句）组装成含有排序以及分页信息的SQL语句
	 * @param originalSQL 原始SQL
	 * @param pager 分页对象(含有分页和排序信息)
	 * @return 含有分页语句的SQL语句
	 */
	public String getPaginationSQL(String originalSQL, Pager pager) {
		validateQuerySQL(originalSQL, pager); //check to see if originalSQL is legal
		StringBuilder buffer = new StringBuilder(originalSQL);
		
		if(pager == null) return buffer.toString(); //no pager found, just return it.
		
		//append order by clause if necessary
		if(pager.isUseOrderBy()) {
			//StringBuilder buffer = new StringBuilder(originalSQL);
			buffer.append(" ");
			buffer.append(pager.getOrderString());
			buffer.append(" ");
			//originalSQL += pager.getOrderString();
		}
		
		if(!pager.isUsePaginate()) return buffer.toString(); //Do not need Pagination, just return.
		
		return buildPaginationString(buffer.toString(), pager);
	}
	
	/**
	 * 根据原始SQL语句(select * from .. where .....)转化成求纪录总数的语句(select count(*) from ... where ....)
	 * @param originalSQL
	 * @return 查询纪录总数的SQL
	 */
	public String getCountSQL(String originalSQL) {
		validateQuerySQL(originalSQL, null);
		return buildCountString(originalSQL);
	}
	
	/**
	 * 生成包含分页语句的SQL String
	 * @param originalSQL 原始sql（不含分页信息）
	 * @param pager 分页类
	 * @return 含有分页SQL的String
	 * 
	 * 
	 */
	protected abstract String buildPaginationString(final String originalSQL, Pager pager);

	/**
	 * 根据原始SQL语句(select * from .. where .....)转化成求纪录总数的语句(select count(*) from ... where ....)
	 * @param originalSQL
	 * @return 查询纪录总数的SQL
	 */
	protected abstract String buildCountString(final String originalSQL);

	/**
	 * 检查originalSQL中是否已经包含分页语句
	 * @param originalSQL
	 * @return true 如果originalSQL包含有分页语句
	 */
	protected abstract boolean hasPaginationClause(String originalSQL);
	
	/**
	 * 检查originalSQL是否含有order by从句
	 * @param originalSQL
	 * @return true 如果 originalSQL 中含有order by从句
	 */
	protected boolean hasOrderBy(String originalSQL) {
		return findString(originalSQL, "\\s+order\\s+by\\s+", true);
	}
	
	
	/**
	 * 查找testString是否包含regExp所制定的字符串
	 * @param testString - 需要检查的字符串
	 * @param regExp － 正则表达式
	 * @param useLowercase - 使用小写字母来匹配
	 * @return true testString中包含regExp模式的字符串
	 */
	protected boolean findString(String testString, String regExp, boolean useLowercase) {
		if(StringUtils.isEmpty(testString)) return false;
		
		if(useLowercase) 
			testString = testString.toLowerCase();
		
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(testString);
		return matcher.find();
	}
	
	/**
	 * 检查传入的Query SQL语句是否合法
	 * @param querySQL
	 */
	private void validateQuerySQL(String querySQL, Pager pager) {
		if(StringUtils.isEmpty(querySQL) || querySQL.trim().length() == 0)
			throw new IllegalArgumentException("参数 'querySQL' 不能为空.");
		
		if(hasSelect(querySQL)) {
			throw new InvalidSelectSqlException("无效的SELECT SQL语句:" + querySQL);
		}
		
		 //如果originalSQL已经包含分页子句，抛出异常
		if(hasPaginationClause(querySQL)) {
			throw new InvalidSelectSqlException("传入的SQL语句中已经包含分页语句: " + querySQL);
		}
		
		if(pager != null) {
			if(pager.isUseOrderBy() && hasOrderBy(querySQL))
				pager.ignoreOrderBy(); //明确告诉pager忽略自身的order by
		}
	}

	private boolean hasSelect(String originalSQL) {
		return findString(originalSQL, "\\s+select\\s+", true);
	}
}


