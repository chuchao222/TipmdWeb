package com.tipmd.webapp.dao.pager.dialect;

import com.tipmd.webapp.dao.pager.Pager;


public class MysqlDialect extends Dialect {

	/*
	 * (non-Javadoc)
	 * @see com.tipmd.webapp.dao.pager.dialect.Dialect#buildPaginationString(java.lang.String, com.tipmd.webapp.dao.pager.Pager)
	 * 
	 *  mysql select 分页语法 : 
	 *  select * 
	 *  from x 
	 *  order by y 
	 *  limit offset, limit
	 *  
	 *  select ...from... where.... group by... having... order by...limit 
	 */
	@Override
	public String buildPaginationString(final String originalSQL, Pager pager) {
		int offset = pager.getOffset();
		int limit = pager.getLimit();

		StringBuilder buffer = new StringBuilder(originalSQL);
		buffer.append(" LIMIT ");
		buffer.append(offset);
		buffer.append(",");
		buffer.append(limit);
		
		return buffer.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tipmd.webapp.dao.pager.dialect.Dialect#buildCountString(java.lang.String)
	 * 
	 * mysql求纪录总数语法
	 * select count(*) from xx
	 */
	@Override
	public String buildCountString(final String originalSQL) {
		String originalSQLInLowerCase = originalSQL;
		originalSQLInLowerCase = originalSQLInLowerCase.replaceAll("\\s+order\\s+by\\s+.+", " "); //去掉order by
		originalSQLInLowerCase = originalSQLInLowerCase.replaceAll("\\s+ORDER\\s+BY\\s+.+", " "); //去掉ORDER BY
		
		int fromIndex = originalSQLInLowerCase.toLowerCase().indexOf("from");
		return "SELECT COUNT(*) " + originalSQLInLowerCase.substring(fromIndex-1);
	}

	@Override
	protected boolean hasPaginationClause(String originalSQL) {
		return findString(originalSQL, "\\s+limit\\s+", true);
	}
	
}
