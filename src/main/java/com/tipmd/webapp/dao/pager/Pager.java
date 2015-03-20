package com.tipmd.webapp.dao.pager;

import java.io.Serializable;

import org.apache.ibatis.session.RowBounds;

public class Pager extends RowBounds implements Serializable 
{
	private static final long serialVersionUID = 5800202536045985179L;
	protected int page; //页码(service层传给dao层的一般是页码，但是分页的时候需要传给sql的是offset)
	protected int limit; //每页记录数
	
	private int totalCount; //记录总数
	private Orders orders; //排序信息
	private boolean isGetTotalCount; //是否需要查询记录总数
	
	private int offset; //便宜量，这个才是实际传给sql的数值 

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
	 * @param isGetTotalCount 是否需要查询纪录总数
	 */
	public Pager(int page, int limit, boolean isGetTotalCount) {
		this(page, limit, isGetTotalCount, Orders.buildEmptyOrders());
	}
	
	public Pager(int page, int limit, boolean isGetTotalCount, Orders orders) {
		this.limit = limit;
		
		if(NO_ROW_LIMIT != limit) {
			this.page = page < 1 ? 1 : page;
			this.offset = (page - 1) * limit;
		}
	
		this.isGetTotalCount = isGetTotalCount;
		this.orders = orders;

	}

	public int getLimit() {
		return limit;
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

	public Pager addOrder(String property, Ordering ordering) {
		getOrders().addOrder(new Orders.Order(property, Ordering.valueOf(ordering)));
		return this;
	}
	
	public boolean isGetTotalCount() {
		return isGetTotalCount;
	}
	
	public int getOffset() {
		return offset;
	}

	//是否使用分页
	public boolean isUsePaginate() {
		return NO_ROW_LIMIT != this.limit;
	}
	
	//此pager对象是否包含有排序信息
	public boolean isUseOrderBy() {
		return (orders != null && orders.isContainAnyOrder());
	}
	
	//忽略order by
	public void ignoreOrderBy() {
		orders.getOrderList().clear();
		orders = null;
	}
	
	public String getOrderString() {
		return orders == null ? null : orders.convertToSQL();
	}
	
	public static enum Ordering {
		ASC, DESC;
		public static String valueOf(Ordering ordering) {
			if(ordering == null || ordering == ASC) 
				return "ASC";
			
			return "DESC";
		}
	}
}
