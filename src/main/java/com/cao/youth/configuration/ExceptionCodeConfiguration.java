package com.cao.youth.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 曹学习
 * @description ExceptionCodeConfiguration
 * @date 2020/8/17 12:02
 */
@ConfigurationProperties(prefix = "cao")
@PropertySource(value = "classpath:config/exception-code.properties")
@Component
public class ExceptionCodeConfiguration {
    private Map<Integer,String> codes=new HashMap<>();

    public String getMessage(int code){
        String message=codes.get(code);
        return message;
    }

    public void setCodes(Map<Integer, String> codes) {
        this.codes = codes;
    }

    public Map<Integer, String> getCodes() {
        return codes;
    }
}
