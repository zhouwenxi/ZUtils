package com.qishui.commontoolslibrary.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.qishui.commontoolslibrary.base.BaseQiShuiApplication;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhou on 2018/12/22.
 * 存储工具
 */
public class SpUtils {

    //线程安全hashmap
    private static final Map<String, SpUtils> MAP = new ConcurrentHashMap<>();
    private SharedPreferences sp;
    //默认文件名字sp.xml
    private static String name = "sp";

    public static SpUtils with() {
        return with("");
    }

    public static SpUtils with(String spName) {

        if (isSpace(spName)) {
            spName = name;
        }
        SpUtils instance = MAP.get(spName);
        if (instance == null) {
            instance = new SpUtils(spName);
            MAP.put(spName, instance);
        }
        return instance;
    }


    private SpUtils(final String spName) {
        sp = BaseQiShuiApplication.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    public SpUtils put(@NonNull final String key, final String value) {
        sp.edit().putString(key, value).apply();
        return this;
    }

    public String getString(@NonNull final String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public SpUtils put(@NonNull final String key, final int value) {
        sp.edit().putInt(key, value).apply();
        return this;
    }

    public int getInt(@NonNull final String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }


    public SpUtils put(@NonNull final String key, final long value) {
        sp.edit().putLong(key, value).apply();
        return this;
    }


    public long getLong(@NonNull final String key, final long defaultValue) {
        return sp.getLong(key, defaultValue);
    }


    public SpUtils put(@NonNull final String key, final float value) {
        sp.edit().putFloat(key, value).apply();
        return this;
    }

    public float getFloat(@NonNull final String key, final float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public SpUtils put(@NonNull final String key, final boolean value) {
        sp.edit().putBoolean(key, value).apply();
        return this;
    }

    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }


    public SpUtils put(@NonNull final String key, final Set<String> value) {
        sp.edit().putStringSet(key, value).apply();
        return this;
    }

    public Set<String> getStringSet(@NonNull final String key, final Set<String> defaultValue) {
        return sp.getStringSet(key, defaultValue);
    }

    /**
     * 存储一系列数据
     *
     * @param key
     * @param value
     */
    public SpUtils put(@NonNull final String[] key, final Object[] value) {
        SharedPreferences.Editor editor = sp.edit();
        for (int i = 0; i < value.length; i++) {
            Object obj = value[i];
            if (obj instanceof String) {
                editor.putString(key[i], (String) value[i]);
            } else if (obj instanceof Integer) {
                editor.putInt(key[i], (Integer) value[i]);
            } else if (obj instanceof Float) {
                editor.putFloat(key[i], (Float) value[i]);
            } else if (obj instanceof Long) {
                editor.putLong(key[i], (Long) value[i]);
            } else if (obj instanceof Boolean) {
                editor.putBoolean(key[i], (Boolean) value[i]);
            } else {
                editor.putString(key[i], value[i].toString());
            }
        }
        return this;
    }

    /**
     * 存放对象
     *
     * @param key
     * @param object
     */
    public SpUtils putObject(@NonNull final String key, Object object) {
        String json = GsonUtils.toJson(object);
        String value = EncDecUtils.str2HexStr(json);
        sp.edit().putString(key, value).apply();
        return this;
    }

    /**
     * 获取一个对象
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getObject(@NonNull final String key, Class<T> clazz) {
        String json = sp.getString(key, "");
        String value = EncDecUtils.hexStr2Str(json);
        return GsonUtils.parseString(value, clazz);
    }


    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    public boolean contains(@NonNull final String key) {
        return sp.contains(key);
    }

    public void remove(@NonNull final String key) {
        sp.edit().remove(key).apply();
    }

    public void clear() {
        sp.edit().clear().apply();
    }


    /**
     * 判断空字符串
     *
     * @param s
     * @return
     */
    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
