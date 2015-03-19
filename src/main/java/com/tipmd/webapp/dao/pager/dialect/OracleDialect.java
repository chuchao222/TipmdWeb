package com.tipmd.webapp.dao.pager.dialect;

import com.tipmd.webapp.dao.pager.Pager;

public class OracleDialect extends Dialect {

	@Override
	public String buildPaginationString(final String originalSQL, Pager pager) {
		throw new UnsupportedOperationException("Not implemented in this version.");
	}

	@Override
	public String buildCountString(final String originalSQL) {
		throw new UnsupportedOperationException("Not implemented in this version.");
	}

	@Override
	protected boolean hasPaginationClause(String originalSQL) {
		return false;
	}

}
