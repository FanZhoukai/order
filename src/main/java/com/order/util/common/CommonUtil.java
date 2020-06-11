package com.order.util.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class CommonUtil {

    /**
     * @author fuqiang.gou
     * @date 20200109
     * @description 解析字符串，将字符串转化为json串
     *
     * @param jsonStr：json字符串
     * @return
     * @throws
     * */
    public static Map<String, Object> parseJson(String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        Map<String, Object> jsonDataMap = (Map<String, Object>) JSONObject.parseObject(jsonObject.toJSONString());
        return jsonDataMap;
    }
}
