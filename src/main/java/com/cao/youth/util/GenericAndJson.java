package com.cao.youth.util;

import com.cao.youth.exception.http.ServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 曹学习
 * @description GenericAndJson
 * @date 2020/8/24 12:42
 */
@Component
public class GenericAndJson {
    //设置为static，是的static方法内能调用,但是直接@Autowired是不合理的-这个是用于实例注入的
    private static ObjectMapper mapper;

    //使用一个setter巧妙地实现静态变量的一个依赖注入
    @Autowired
    public void setMapper(ObjectMapper mapper){
        GenericAndJson.mapper=mapper;
    }

    public static<T> String objectToJson(T o){
        try {
            return GenericAndJson.mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //抛出RuntimeException的话外面在调用时是不需要处理异常的
            throw new ServerErrorException(999);
        }
    }

    public static<T> T jsonToObject(String s,TypeReference<T> tr){
        if(s==null){
            return null;
        }
        try {
            T o=GenericAndJson.mapper.readValue(s,tr);
            return o;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(999);
        }
    }

    public static<T> T jsonToList(String s, TypeReference<T> tr){
        if(s==null){
            return null;
        }
        try {
            T list=GenericAndJson.mapper.readValue(s,tr);
            return list;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(999);
        }
    }

    /**
     * jsonToList,将list<>里面的作为泛型  这个方法在多重的时候有问题
     */
    public static <T> List<T> jsonToList(String s){
        if(s==null){
            return null;
        }
        try {
            List<T> list=GenericAndJson.mapper.readValue(s, new TypeReference<List<T>>(){});
            return list;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(999);
        }
    }
}
