package com.tipmd.webapp.presentation.json;

/**
 * 
 * @author bowee2010
 *
 * 返回含有单个对象的VO
 */
public class JsonObject extends BaseJsonObject 
{
	public static JsonObject generateSuccessJsonObject(String msg, Object obj) {
		JsonObject jsonObj = new JsonObject(msg, obj);
		return jsonObj;
	}
	
	public static JsonObject generateFailedJsonObject(String msg, Object obj) {
		JsonObject jsonObj = new JsonObject(msg, obj);
		jsonObj.setSuccess(false);
		return jsonObj;
	}
	
	public static JsonObject generateFailedJsonObject(String msg) {
		return generateFailedJsonObject(msg, null);
	}
	
	public static JsonObject generateAccessDeniedJsonObject(String msg) {
		JsonObject jsonObj = new JsonObject(msg, null);
		jsonObj.setSuccess(false);
		jsonObj.setAccessible(false);
		return jsonObj;
	}
	
	private Object obj;
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	private JsonObject(String msg, Object data) {
		super(msg);
		this.obj = data;
	}
}

