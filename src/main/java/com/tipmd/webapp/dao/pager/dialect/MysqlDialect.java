package com.tipmd.webapp.dao.pager.dialect;

import com.tipmd.webapp.dao.pager.Orders;


public class MysqlDialect extends Dialect {

	// mysql分页语法 : select * from x order by y limit offset, limit
	@Override
	public String buildLimitString(String originalSQL, Orders orders, int offset,
			int limit) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildCountString(String originalSQL) {

		//1. 剥离出from(含from)及其之后的语句
		//2. 剥离掉order by 和 limit语句
		//用select count(1) + 剥离出来的语句 凑成新的语句，并返回
		return null;
	}

	
}
