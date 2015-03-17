package com.tipmd.webapp.presentation.json;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 
 * @author bowee2010
 *
 * 返回给客户端JSON对象的父类
 */
public class BaseJsonObject 
{
	//permission denied
	private boolean accessible;
	//indicate operation is success 
	private boolean success;
	//session timeout or invalid
	private boolean sessionValid;
	//the message which is going to send to client
	private String message;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	private String url;

	//用户操作成功时使用此对象返回给客户端
	public static BaseJsonObject generateSuccessJsonObject(String msg) {
		BaseJsonObject obj = new BaseJsonObject(msg);
		return obj;
	}
	
	//用户操作失败时使用此对象返回给客户端
	public static BaseJsonObject generateFailedJsonObject(String msg) {
		BaseJsonObject obj = new BaseJsonObject(true, true, false, msg);
		return obj;
	}
		
	//用户登录失败时使用此对象返回给客户端
	public static BaseJsonObject generateLoginFailedJsonObject(String msg) {
		BaseJsonObject obj = new BaseJsonObject(true, true, false, msg);
		return obj;
	}
	
	//用户session无效时使用此对象返回给客户端
	public static BaseJsonObject generateSessionInvalidJsonObject(String msg) {
		BaseJsonObject obj = new BaseJsonObject(true, false, true, msg);
		return obj;
	}
	
	//用户无权限执行操作时使用此对象返回给客户端
	public static BaseJsonObject generateAccessDeniedJsonObject(String msg) {
		BaseJsonObject obj = new BaseJsonObject(false, true, true, msg);
		return obj;
	}
	
	protected BaseJsonObject(boolean accessible, 
			boolean sessionValid, boolean success, String msg) {
		this.accessible = accessible;
		this.sessionValid = sessionValid;
		this.success = success;
		this.message = msg;
	}
	
	protected BaseJsonObject(String msg) {
		this(true, true, true, msg);
	}
	
	protected BaseJsonObject() {
		this(true, true, true, null);
	}
	public boolean isAccessible() {
		return accessible;
	}

	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isSessionValid() {
		return sessionValid;
	}

	public void setSessionValid(boolean sessionValid) {
		this.sessionValid = sessionValid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
