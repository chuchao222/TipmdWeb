package com.tipmd.webapp.dao.pager;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

public class Pager extends RowBounds implements Serializable 
{
	private static final long serialVersionUID = 5800202536045985179L;
	protected int page; //页码
	protected int limit; //每页记录数
	private int totalCount; //记录总数
	private List<Order> orderList; //排序信息
	private boolean needGetTotalCount; //是否需要查询记录总数
	
	public Pager() {
		this.needGetTotalCount = false;
	}
	
	public Pager(int limit) {
		
	}
	
	public Pager(int page, int limit) {
		
	}
	
	public Pager(int page, int limit, boolean needGetTotalCount, List<Order> orderList) {
		this.page = page;
		this.limit = limit;
		this.needGetTotalCount = needGetTotalCount;
		this.orderList = orderList;
	}
	
	
}
