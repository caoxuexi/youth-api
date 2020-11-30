package com.cao.youth.core.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.cao.youth.core.LocalUser;
import com.cao.youth.exception.http.ForbiddenException;
import com.cao.youth.exception.http.UnAuthenticatedException;
import com.cao.youth.model.User;
import com.cao.youth.service.UserService;
import com.cao.youth.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/**
 * @author 曹学习
 * @description PermissionInterceptor
 * @date 2020/8/28 11:13
 */
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;

    public PermissionInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<ScopeLevel> scopeLevel=this.getScopeLevel(handler);
        //如果方法题没有打上 @ScopeLevel即公共API,就直接放行
        if(!scopeLevel.isPresent()){
            return true;
        }
        String token=getRealToken(request);
        //校验token
        Optional<Map<String, Claim>> optionalMap = JwtToken.getClaims(token);
        Map<String,Claim> map=optionalMap.orElseThrow(()-> new UnAuthenticatedException(10004));
        boolean valid=this.hasPermission(scopeLevel.get(),map);
        if(valid){
            this.setToThreadLocal(map);
        }
        return valid;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LocalUser.clear();
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 获取ScopeLevel这个注解
     * @param handler 方法体
     * @return optional包装的ScopeLevel
     */
    private Optional<ScopeLevel> getScopeLevel(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ScopeLevel scopeLevel=handlerMethod.getMethodAnnotation(ScopeLevel.class);
            if(scopeLevel==null){
                return Optional.empty();
            }
            return Optional.of(scopeLevel);
        }
        return Optional.empty();
    }

    private String getRealToken(HttpServletRequest request){
        //请求头里格式是Authorization: Bearer <token>
        String bearerToken=request.getHeader("Authorization");
        if(StringUtils.isEmpty(bearerToken)){
            throw new UnAuthenticatedException(10004);
        }
        if(!bearerToken.startsWith("Bearer")){
            throw new UnAuthenticatedException(10004);
        }
        String tokens[]=bearerToken.split(" ");
        //没有token的情况，只有一个Bearer
        if(!(tokens.length==2)){
            throw new UnAuthenticatedException(10004);
        }
        String token=tokens[1];
        return token;
    }

    /**
     * 判断是否有权限
     * @param scopeLevel 注解
     * @param map token中取出来的参数map
     * @return  是否有权限
     */
    private boolean hasPermission(ScopeLevel scopeLevel,Map<String,Claim> map){
        Integer level=scopeLevel.value();
        Integer scope=map.get("scope").asInt();
        if(level>scope){
            throw new ForbiddenException(10005);
        }
        return true;
    }

    /**
     * 写入localUser类
     */
    private void setToThreadLocal(Map<String,Claim> map){
        Long uid=map.get("uid").asLong();
        Integer scope=map.get("scope").asInt();
        User user=userService.getUserById(uid);
        LocalUser.set(user,scope);
    }
}
