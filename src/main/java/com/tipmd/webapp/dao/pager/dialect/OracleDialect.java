package com.tipmd.webapp.dao.pager.dialect;

import com.tipmd.webapp.dao.pager.Orders;

public class OracleDialect extends Dialect {

	@Override
	public String buildLimitString(String originalSQL, Orders orders, int offset,
			int limit) {
		throw new UnsupportedOperationException("You must implement this method before use it.");
	}

	@Override
	public String buildCountString(String originalSQL) {
		throw new UnsupportedOperationException("You must implement this method before use it.");
	}

}
