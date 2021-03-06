
package com.wangda.alarm.service.common.util.json.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.wangda.alarm.service.common.util.pojo.ShortTime;
import java.io.IOException;

/**
 * @author yushen.ma
 * @version 1.0 2016-04-08
 */
public class ShortTimeJsonDeserializer extends JsonDeserializer<ShortTime> {

    @Override
    public ShortTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        String time = jsonParser.getText();
        if (time != null && !time.isEmpty()) {
            return ShortTime.create(time);
        }
        return null;
    }

}
