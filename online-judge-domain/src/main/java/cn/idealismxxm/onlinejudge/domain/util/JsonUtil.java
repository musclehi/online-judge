package cn.idealismxxm.onlinejudge.domain.util;

import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json互转工具类
 *
 * @author idealism
 * @date 2018/3/26
 */
public class JsonUtil {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将对象转成 json串
     *
     * @param object 待序列化的对象
     * @return 序列化后的串
     */
    public static String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.JSON_WRITE_VALUE_ERROR);
        }
    }

    /**
     * 将 json串 转成指定对象
     *
     * @param json       json串
     * @param valueClass 对象类型
     * @return 反序列化后的对象
     */
    public static <T> T jsonToObject(String json, Class<T> valueClass) {
        if(StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, valueClass);
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.JSON_READ_VALUE_ERROR);
        }
    }

    /**
     * 将 json串 转成指定 map对象
     *
     * @param json       json串
     * @param keyClass   键类型
     * @param valueClass 值类型
     * @return 反序列化后的 map对象
     */
    public static <K, V> Map<K, V> jsonToMap(String json, Class<K> keyClass, Class<V> valueClass) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructMapType(HashMap.class, keyClass, valueClass);
            return objectMapper.readValue(json, javaType);
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.JSON_READ_VALUE_ERROR);
        }
    }

    /**
     * 将 json串 转成指定 list对象
     *
     * @param json       json串
     * @param valueClass 值类型
     * @return 反序列化后的 list对象
     */
    public static <T> List<T> jsonToList(String json, Class<T> valueClass) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructArrayType(valueClass);
            return objectMapper.readValue(json, javaType);
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.JSON_READ_VALUE_ERROR);
        }
    }
}
