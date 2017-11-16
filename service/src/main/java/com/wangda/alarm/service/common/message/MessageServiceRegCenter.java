package com.wangda.alarm.service.common.message;

import com.google.common.base.Strings;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 * @author wangshuo
 * @version 2017-10-28
 */
@Service
public class MessageServiceRegCenter {
    private ConcurrentHashMap<Class, MessageProccessor> proMap = new ConcurrentHashMap<>();

    public void reg(Class msgClass, MessageProccessor proccessor) {
        proMap.put(msgClass, proccessor);
    }

    public MessageProccessor getProcessor(Class msgClass) {
        if (msgClass == null) {
            return null;
        }
        return proMap.get(msgClass);
    }
}
