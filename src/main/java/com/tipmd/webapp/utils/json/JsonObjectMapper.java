package com.tipmd.webapp.utils.json;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;

import com.tipmd.webapp.utils.Constants;

/**
 * @author bowee2010
 * 处理各种格式
 */
public class JsonObjectMapper extends ObjectMapper
{
	public JsonObjectMapper() {
        super();
        //允许单引号
        //this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 字段和值都加引号
        //this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 数字也加引号
        //this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
        //this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
        // 空值处理为空串
        this.getSerializerProvider().setNullValueSerializer(new NullSerializer());
        this.setDateFormat(new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT));
    }
	
	//null的JSON序列  
	private class NullSerializer extends JsonSerializer<Object> {  
	    public void serialize(Object value, JsonGenerator jgen,  
	            SerializerProvider provider) throws IOException,  
	            JsonProcessingException {  
	        jgen.writeString("");  
	    } 
	}  
}

	