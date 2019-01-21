package com.qishui.commontoolslibrary.core;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhou on 2018/12/22.
 * 解析工具
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


    /**
     * desc 将json字符串返回对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseString(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解析json数组
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> fromJsonArray(String json, Class<T> clazz) {
        try {
            List<T> list = new ArrayList<T>();
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(new Gson().fromJson(elem, clazz));
            }
            return list;
        } catch (JsonSyntaxException e) {
            return null;
        }
    }


    /**
     * 对象转为json字符串
     *
     * @param target
     * @return
     */
    public static String toJson(Object target) {
        return new Gson().toJson(target);
    }


    /**
     * desc 获取json串中某个字段的值，注意，只能获取同一层级的value
     *
     * @param json
     * @param key
     * @return
     */
    public static String getFieldValue(String json, String key) {
        String v_null = "";
        if (TextUtils.isEmpty(json)) {
            return v_null;
        }
        if (!json.contains(key)) {
            return v_null;
        }
        try {
            return new JSONObject(json).getString(key);
        } catch (JSONException e) {
            return v_null;
        }
    }

    /**
     * desc 判断是否是json
     *
     * @param json
     * @return
     */
    public static Boolean isJson(String json) {

        try {
            new JSONObject(json);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }
}
