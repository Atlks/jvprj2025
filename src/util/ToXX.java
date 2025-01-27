package util;

import com.alibaba.fastjson2.JSON;

import java.util.Map;

public class ToXX {

    /**
     * 将map转换为给定的实体类。。如果需要使用json，使用fastjson2
     * @param map
     * @param Class1
     * @return
     * @param <t>
     */
    public static <t> t toObjFrmMap(Map<String, String> map, Class<t> Class1) {

        // 将 Map 转换为 JSON 字符串
        String jsonString = JSON.toJSONString(map);

        // 将 JSON 字符串解析为目标对象
        return JSON.parseObject(jsonString, Class1);
    }
}
