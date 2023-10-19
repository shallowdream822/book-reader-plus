package com.tyf.bookreaderplus.common.component;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/4 18:41
 */
public class LongValueSerializer implements ObjectDeserializer {
    @Override
    public Long deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {
        String value = defaultJSONParser.parseObject(String.class);
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Long.parseLong(value);
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
