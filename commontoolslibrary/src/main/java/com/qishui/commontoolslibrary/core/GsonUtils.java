package com.qishui.commontoolslibrary.core;

import com.google.gson.JsonObject;

import java.util.Map;

/**
 * Created by zhou on 2018/12/22.
 */

public class GsonUtils {

    /**
     * 构建json对象
     *
     * @param map
     * @return
     */
    public static String getJsonString(Map<String, Object> map) {

        if (map == null) {
            return "{}";
        }
        JsonObject object = new JsonObject();
        for (String key : map.keySet()) {
            //如果要为当前的JSON对象添加另一个JSON对象，使用add()方法
            //如果要为当前的JSON对象添加属性值（键值对），使用addProperty()方法
            Object o = map.get(key);
            if (o instanceof Number) {
                object.addProperty(key, (Number) o);
            } else if (o instanceof Boolean) {
                object.addProperty(key, (Boolean) o);
            } else if (o instanceof String) {
                object.addProperty(key, (String) o);
            } else if (o instanceof Character) {
                object.addProperty(key, (Character) o);
            }
        }
        return object.toString();
    }
}
