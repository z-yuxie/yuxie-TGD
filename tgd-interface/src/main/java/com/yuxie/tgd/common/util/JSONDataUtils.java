package com.yuxie.tgd.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class JSONDataUtils {

    /**
     * 判断一个JSON是否包含某个key
     * @param key 键
     * @param jsonObject JSON对象
     * @return true包含 false不包含
     */
    public static boolean checkJsonContainsKey(String key , JSONObject jsonObject) {
        boolean containsKey = false;
        if (jsonObject != null && !jsonObject.isEmpty() && jsonObject.containsKey(key)) {
            containsKey = true;
        }
        return containsKey;
    }

    /**
     * 判断一个JSON是否包含某个key且数据是非空的字符串
     * @param key 键
     * @param jsonObject JSON对象
     * @return true包含 false不包含
     */
    public static boolean checkJsonContainsStringData(String key , JSONObject jsonObject) {
        boolean containsData = false;
        if (jsonObject != null && !jsonObject.isEmpty() && jsonObject.containsKey(key) && StringUtils.isNotBlank(jsonObject.getString(key))) {
            containsData = true;
        }
        return containsData;
    }

    /**
     * 判断一个JSON是否包含某个key且数据是非空的
     * @param key 键
     * @param jsonObject JSON对象
     * @return true包含 false不包含
     */
    public static boolean checkJsonContainsObjectData(String key , JSONObject jsonObject) {
        boolean containsData = false;
        if (jsonObject != null && !jsonObject.isEmpty() && jsonObject.containsKey(key) && jsonObject.get(key) != null) {
            containsData = true;
        }
        return containsData;
    }

    /**
     * 根据传入的key集合,判断传入的json缺少哪些数据
     * @param keySet 完整的数据应该具有的所有的键的集合
     * @param jsonObject 待检测数据
     * @param keyCanBeBlank 是否允许key为空
     * @return 缺失的数据的key的集合
     */
    public static Set<String> getNotCompleteKeysSet(Set<String> keySet , JSONObject jsonObject , boolean keyCanBeBlank) {
        Set<String> notCompleteKeysSet = new HashSet<>();
        if (jsonObject != null && !jsonObject.isEmpty()) {
            for (String key : keySet) {
                if ((keyCanBeBlank || StringUtils.isNotBlank(key)) && !jsonObject.containsKey(key)) {
                    notCompleteKeysSet.add(key);
                }
            }
        }
        return notCompleteKeysSet;
    }

    /**
     * 根据key从json中取出其类型为String的value值,若没取到则返回空字符串
     * @param key 键
     * @param jsonObject json对象
     * @return key对应的值
     */
    public static String getStringValueFromJson(String key , JSONObject jsonObject) {
        String value = null;
        if (jsonObject != null && !jsonObject.isEmpty() && jsonObject.containsKey(key)) {
            value = jsonObject.getString(key);
        }
        if (StringUtils.isBlank(value)) {
            value = "";
        }
        return value;
    }

    /**
     * 根据key从json中取出其类型为Object的value值,若没取到则返回空字符串
     * @param key 键
     * @param jsonObject json对象
     * @return key对应的值
     */
    public static Object getObjectValueFromJson(String key , JSONObject jsonObject) {
        Object value = null;
        if (jsonObject != null && !jsonObject.isEmpty() && jsonObject.containsKey(key)) {
            value = jsonObject.get(key);
        }
        return value;
    }
}
