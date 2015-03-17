package com.tipmd.webapp.vo;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

public class BaseVo implements Serializable
{
	private static final long serialVersionUID = -5516580118045976859L;
	@JsonIgnore
	private int page;
	@JsonIgnore
	private int pageSize;
	@JsonIgnore
	private String keyword;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
