package com.example.demo.utils;

import com.alibaba.fastjson.JSON;

public class JsonUtils {
    public static String objectToJson(Object o) {
        return JSON.toJSONString(o);
    }

    public static Object jsonToObject(String json, Class clazz) {
        return JSON.parseObject(json, clazz);
    }
}
