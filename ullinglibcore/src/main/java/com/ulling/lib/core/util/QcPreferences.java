/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.lib.core.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.reflect.TypeToken;
import com.ulling.lib.core.common.QcDefine;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : KILHO
 * @프로그램 ㄴ 프리퍼런스 헬퍼 클래스
 * @변경이력
 */
public class QcPreferences {
    /**
     * Log - class name
     */
//    private static String TAG = "SharedPreferencesHelper";
    private static QcPreferences prefsInstances = null;
    /**
     * preference
     */
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;
    public static final boolean PREFER_LOG_FLAG = QcDefine.DEBUG_FLAG;
    private static Gson GSON;
//	Type typeOfObject = new TypeToken<Object>(){}.getType();

    public static synchronized void init(Context context, String APP_NAME) {
        prefsInstances = getComplexPreferences(context, APP_NAME);
        QcLog.e("ComplexPreferences init complete !");
    }

    private QcPreferences(Context context, String APP_NAME) {
        prefs = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
        GsonBuilder gsonGsonBuilder = new GsonBuilder();
        gsonGsonBuilder.setDateFormat(QcDefine.UTC_DATE_FORMAT);
        gsonGsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        gsonGsonBuilder.setPrettyPrinting();
        GSON = gsonGsonBuilder.create();
    }

    public static QcPreferences getComplexPreferences(Context context, String APP_NAME) {
        if (prefsInstances == null) {
            prefsInstances = new QcPreferences(context, APP_NAME);
        }
        return prefsInstances;
    }

    public void put(String key, String value) {
        editor.putString(key, value);
        editor.commit();
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + value);
    }

    public void putSet(String key, ArrayList<String> valueList) {
        valueList = new ArrayList<String>();
        Set<String> value = new HashSet<String>(valueList);
        editor.putStringSet(key, value);
        editor.commit();
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + value);
    }

    public void put(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + value);
    }

    public void put(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + value);
    }

    public void put(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + value);
    }

    public void put(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + value);
    }

    public String get(String key, String defValue) {
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + prefs.getString(key, defValue));
        return prefs.getString(key, defValue);
    }

    public int get(String key, int defValue) {
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + prefs.getInt(key, defValue));
        return prefs.getInt(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + prefs.getBoolean(key, defValue));
        return prefs.getBoolean(key, defValue);
    }

    public long getLong(String key, long defValue) {
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + prefs.getLong(key, defValue));
        return prefs.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + prefs.getFloat(key, defValue));
        return prefs.getFloat(key, defValue);
    }

    public Object putObject(String key, Object object) {
        if (object == null) {
            QcLog.e("Object is null");
            return null;
        }
        if (key.equals("") || key == null) {
            QcLog.e("Key is empty or null");
            return null;
        }
        if (object.equals("")) {
            editor.putString(key, "");
        } else {
            editor.putString(key, GSON.toJson(object));
        }
        return editor.commit();
    }

    public <T> T getObject(String key, Class<T> a) {
        String gson = null;
        try {
            gson = prefs.getString(key, null);
        } catch (Exception e) {
        }
        if (gson == null) {
            return null;
        } else {
            try {
                return GSON.fromJson(gson, a);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public <T> List<Object> getObjectSet(String key, Class<T> a) {
        List<Object> gsonList = new ArrayList<Object>();
        String gson = null;
        try {
            gson = prefs.getString(key, null);
        } catch (Exception e) {
        }
        if (gson == null) {
            return null;
        }
        Type type = new TypeToken<List<Object>>() {
        }.getType();
        gsonList = GSON.fromJson(gson, type);
        return gsonList;
    }

    public QcPreferences remove(String key) {
        editor.remove(key);
        editor.commit();
        return this;
    }
}