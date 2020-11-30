package com.cao.youth.util;

import com.cao.youth.exception.http.ServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 曹学习
 * @description MapAndJson
 * @date 2020/8/23 18:46
 */
//第一个参数是Entity当前字段的类型，第二个是在数据库中数据类型(json在java中等于String)
@Converter
public class MapAndJson implements AttributeConverter<Map<String,Object>,String> {
    //把spring默认的序列化类jackson注入进来
    @Autowired
    private ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        try {
            return mapper.writeValueAsString(stringObjectMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //抛出RuntimeException的话外面在调用时是不需要处理异常的
            throw new ServerErrorException(999);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> convertToEntityAttribute(String s) {
        try {
            if(s==null){
                return null;
            }
            Map<String,Object> t=mapper.readValue(s, HashMap.class);
            return t;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(999);
        }
    }
}
