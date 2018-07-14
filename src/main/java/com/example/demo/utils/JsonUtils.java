package com.example.demo.utils;

import com.alibaba.fastjson.JSON;

public class JsonUtils {
    public static String objectToJson(Object o) {
        return JSON.toJSONString(o);
    }
}
