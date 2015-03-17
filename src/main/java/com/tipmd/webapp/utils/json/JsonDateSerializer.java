package com.tipmd.webapp.utils.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

import com.tipmd.webapp.utils.Constants;

/**
 * 
 * @author bowee2010
 * 序列化输出到客户端的日期格式，客户端传过来的日期格式必须是
 * Constants.DEFAULT_DATE_FORMAT(yyyy-MM-dd)
 */
@Component 
public class JsonDateSerializer extends JsonSerializer<Date>{ 
	
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT); 
    @Override 
    public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) 
            throws IOException, JsonProcessingException { 

        String formattedDate = dateFormat.format(date); 
        gen.writeString(formattedDate); 
    }
}