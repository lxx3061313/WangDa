
package com.wangda.alarm.service.util.json.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.wangda.alarm.service.util.pojo.ShortTime;
import java.io.IOException;

/**
 * @author yushen.ma
 * @version 2016-04-08
 */
public class ShortTimeJsonSerializer extends JsonSerializer<ShortTime> {

    @Override
    public void serialize(ShortTime shortTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        jsonGenerator.writeString(shortTime != null ? shortTime.toString() : "null");
    }

}
