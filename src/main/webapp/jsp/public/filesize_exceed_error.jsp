<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.tipmd.webapp.presentation.json.BaseJsonObject"%>
<%@ page import="org.codehaus.jackson.map.ObjectMapper"%>
<%
	BaseJsonObject json = BaseJsonObject.generateFailedJsonObject("文件尺寸超过限制，请先压缩后再上传");
	ObjectMapper mapper = new ObjectMapper();
	String value = mapper.writeValueAsString(json);
	out.println(value);
%>