package top.dongxibao.erp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jackson库
 * @ClassName JsonUtils
 * @description Json转换工具
 * @author Dongxibao
 * @date 2019/12/9
 * @Version 1.0
 */
public class JsonUtils {

    public static final ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 对象转json
     * @param obj
     * @return
     */
    @Nullable
    public static String obj2Json(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("json序列化出错：" + obj, e);
            return null;
        }
    }

    /**
     * json转对象
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    @Nullable
    public static <T> T json2Obj(String json, Class<T> tClass) {
        if (json == null || json == "") {
            return null;
        }
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    /**
     * json转集合
     * @param json
     * @param eClass
     * @param <E>
     * @return
     */
    @Nullable
    public static <E> List<E> json2List(String json, Class<E> eClass) {
        if (json == null || json == "") {
            return null;
        }
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    /**
     * json转Map
     * @param json
     * @param kClass
     * @param vClass
     * @param <K>
     * @param <V>
     * @return
     */
    @Nullable
    public static <K, V> Map<K, V> json2Map(String json, Class<K> kClass, Class<V> vClass) {
        if (json == null || json == "") {
            return null;
        }
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    /**
     * 复杂json转换
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    @Nullable
    public static <T> T nativeRead(String json, TypeReference<T> type) {
        if (json == null || json == "") {
            return null;
        }
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    public static void main(String[] args) {
        // 测试obj2Json()
        Map<String,String> map = new HashMap<>();
        map.put("name","张三");
        map.put("age","22");
        String obj2Json = obj2Json(map);
        System.out.println(obj2Json);
        // 测试json2Obj()
        Map json2Obj = json2Obj(obj2Json, Map.class);
        System.out.println(json2Obj);
        // 测试json2List()
        String json = obj2Json(Arrays.asList("a", "b"));
        List<String> lists = json2List(json, String.class);
        System.out.println(lists);
        // 测试json2Map()
        Map<String, String> json2Map = json2Map(obj2Json, String.class, String.class);
        System.out.println(json2Map);
        // 测试nativeRead()  复杂类型的json转换
        Map<String,List<Map<String,String>>> nativeMap = new HashMap<>();
        nativeMap.put("native", Arrays.asList(map, map));
        String nativeJson = obj2Json(nativeMap);
        Map<String, List<Map<String, String>>> nativeRead = nativeRead(nativeJson, new TypeReference<Map<String,
                List<Map<String, String>>>>() {
        });
        System.out.println(nativeRead);
    }
}
