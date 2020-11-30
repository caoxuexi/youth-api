package com.cao.youth.core.hack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * @author 曹学习
 * @description AutoPrefixUrlMapping
 * @date 2020/8/17 12:36
 */
public class AutoPrefixUrlMapping extends RequestMappingHandlerMapping {
    @Value("${youth.api-package}")
    private String apiPackagePath;

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo mappingInfo= super.getMappingForMethod(method, handlerType);
        if(mappingInfo!=null){
            String prefix=this.getPrefix(handlerType);
            //即我们增加的路由前缀，加上我们Controller上定义的路由前缀
            return RequestMappingInfo.paths(prefix).build().combine(mappingInfo);
        }
        return mappingInfo;
    }

    private String getPrefix(Class<?> handlerType){
        String packageName=handlerType.getPackageName();
        //去掉包名多余的前缀
        String dotPath=packageName.replaceAll(this.apiPackagePath,"");
        return dotPath.replace(".","/");
    }
}
