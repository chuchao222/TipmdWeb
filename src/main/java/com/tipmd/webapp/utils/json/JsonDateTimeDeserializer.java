package com.tipmd.webapp.utils.json;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.stereotype.Component;

import com.tipmd.webapp.utils.Constants;

/**
 * 
 * @author bowee2010
 * 反序列化客户端传过来的日期时间数据，客户端传过来的日期时间格式必须是
 * Constants.DEFAULT_DATE_TIME_FORMAT(yyyy-MM-dd HH:mm:ss)
 */
@Component
public class JsonDateTimeDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		String str = jp.getText();
		if(str == null || str.trim().length() == 0) {
    		throw new IllegalArgumentException("Date string is null");
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat(Constants.DEFAULT_DATE_TIME_FORMAT);
    	try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
