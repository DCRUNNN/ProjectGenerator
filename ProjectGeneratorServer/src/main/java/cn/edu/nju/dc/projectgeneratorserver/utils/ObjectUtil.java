package cn.edu.nju.dc.projectgeneratorserver.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dc
 * @date 2019/12/17 23:08
 *
 * 对象转换工具类
 */
@Slf4j
public class ObjectUtil
{

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private ObjectUtil()
    {

    }

    static
    {
        // 对象中没有属性时不抛出异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 忽略未知属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 允许JSON字符串包含非引号控制字符
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static <S, T> T deepCloneByJson(S source, Class<T> targetType)
    {
        Preconditions.checkNotNull(source, "source object must be not null");
        Preconditions.checkNotNull(targetType, "target type must be not null");
        try
        {
            return objectMapper.readValue(objectMapper.writeValueAsString(source), targetType);
        }
        catch (Exception e)
        {
            log.error("error occurs when deep cloning object");
        }
        return null;
    }
}
