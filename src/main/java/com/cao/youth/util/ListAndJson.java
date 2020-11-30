package com.cao.youth.util;

import com.cao.youth.exception.http.ServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

/**
 * @author 曹学习
 * @description ListAndJson
 * @date 2020/8/24 11:58
 */
@Converter
public class ListAndJson implements AttributeConverter<List<Object>,String> {
    @Autowired
    private ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(List<Object> objectList) {
        try {
            return mapper.writeValueAsString(objectList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //抛出RuntimeException的话外面在调用时是不需要处理异常的
            throw new ServerErrorException(999);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object> convertToEntityAttribute(String s) {
        try {
            if(s==null){
                return null;
            }
            List<Object> t=mapper.readValue(s,List.class);
            return t;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(999);
        }
    }
}
