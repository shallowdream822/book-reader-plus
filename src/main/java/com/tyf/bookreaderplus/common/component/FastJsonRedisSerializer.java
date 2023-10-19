package com.tyf.bookreaderplus.common.component;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/5/2 16:43
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private Class<T> clazz;

    public FastJsonRedisSerializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    public byte[] serialize(T t) throws SerializationException {
        return t == null ? new byte[0] : JSON.toJSONString(t, new SerializerFeature[]{SerializerFeature.WriteClassName}).getBytes(DEFAULT_CHARSET);
    }

    public T deserialize(byte[] bytes) throws SerializationException {

//        if (bytes != null && bytes.length > 0) {
//            String str = new String(bytes, DEFAULT_CHARSET);
//            return JSON.parseObject(str, this.clazz);
//        } else {
//            return null;
//        }
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        String json = new String(bytes, StandardCharsets.UTF_8);
        if (clazz == List.class) {
            return (T) JSON.parseArray(json, Object.class);
        } else {
            return JSON.parseObject(json, clazz);
        }
    }

    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }

    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }
}

