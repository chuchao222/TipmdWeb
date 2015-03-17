package com.tipmd.webapp.presentation.json;

import java.util.List;

/**
 * 
 * @author bowee2010
 *
 * 返回给客户端的是集合对象JSON，使用此类（含分页信息）
 */

@SuppressWarnings("rawtypes")
public class JsonCollection extends BaseJsonObject 
{
	//rows represents the data collection send to client
	private List rows;
	//page number
	private long page;
	//total items
	private long total;
	
	//查询列表成功调用此方法组装成功json对象
	public static JsonCollection generateSuccessJsonCollection(List list, long page, long total) {
		JsonCollection jsonObj = new JsonCollection(list, page, total);
		return jsonObj;
	}
	
	//查询列表失败,调用此方法组装失败json对象
	public static JsonCollection generateFailedJsonCollection(List list, long page, long total) {
		JsonCollection jsonObj = new JsonCollection(list, page, total);
		jsonObj.setSuccess(false);
		return jsonObj;
	}
	
	//查询列表失败,调用此方法组装失败json对象
	public static JsonCollection generateFailedJsonCollection(String msg, long page) {
		JsonCollection jsonObj = new JsonCollection(null, page, 0);
		jsonObj.setSuccess(false);
		jsonObj.setMessage(msg);
		return jsonObj;
	} 

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
	private JsonCollection(List list, long page, long total) {
		super();
		this.rows = list;
		this.page = page;
		this.total = total;
	}
}
