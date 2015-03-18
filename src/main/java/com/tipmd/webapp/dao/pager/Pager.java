package com.tipmd.webapp.dao.pager;

import java.io.Serializable;

import org.apache.ibatis.session.RowBounds;

public class Pager extends RowBounds implements Serializable 
{
	private static final long serialVersionUID = 5800202536045985179L;
	protected int page; //页码
	protected int limit; //每页记录数
	private int totalCount; //记录总数
	private Orders orders; //排序信息
	private boolean isGetTotalCount; //是否需要查询记录总数
	
	/**
	 * 缺省构造函数会构造一个包含所有纪录的查询
	 */
	public Pager() {
		this(NO_ROW_OFFSET, NO_ROW_LIMIT, false, Orders.buildEmptyOrders());
	}
	
	/**
	 * 查询top limit条纪录，不分页，缺省不返回纪录总数。（通常会有排序）
	 * @param limit
	 */
	public Pager(int limit, Orders orders) {
		this(1, limit, false, orders);
	}
	
	/**
	 * 查询第page页纪录，每页有limit条纪录，缺省会返回纪录总数，无排序
	 * @param page
	 * @param limit
	 */
	public Pager(int page, int limit) {
		this(page, limit, true, Orders.buildEmptyOrders());
	}
	
	/**
	 * 查询第page页纪录，每页有limit条纪录，缺省会返回纪录总数，无排序
	 * @param page
	 * @param limit
	 */
	public Pager(int page, int limit, boolean isGetTotalCount) {
		this(page, limit, isGetTotalCount, Orders.buildEmptyOrders());
	}
	
	public Pager(int page, int limit, boolean isGetTotalCount, Orders orders) {
		this.page = page;
		this.limit = limit;
		this.isGetTotalCount = isGetTotalCount;
		this.orders = orders;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public boolean isGetTotalCount() {
		return isGetTotalCount;
	}

	public void setGetTotalCount(boolean isGetTotalCount) {
		this.isGetTotalCount = isGetTotalCount;
	}
}
