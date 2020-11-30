package com.cao.youth.core;

import com.cao.youth.configuration.ExceptionCodeConfiguration;
import com.cao.youth.exception.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author 曹学习
 * @description GlobalExceptionAdvice
 * @date 2020/8/16 12:47
 */
@ControllerAdvice
public class GlobalExceptionAdvice {
    @Autowired
    private ExceptionCodeConfiguration codeConfiguration;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(code= HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleException(HttpServletRequest req,Exception e){
        String requestUrl=req.getRequestURI();
        String method=req.getMethod();
//        System.out.println(e);
        UnifyResponse message=new UnifyResponse(500,"服务器错误",method+" "+requestUrl);
        return message;
    }

    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity<UnifyResponse> handleException(HttpServletRequest req,HttpException e){
        String requestUrl=req.getRequestURI();
        String method=req.getMethod();
        UnifyResponse message=new UnifyResponse(e.getCode(),
                codeConfiguration.getMessage(e.getCode()),
                method+" "+requestUrl);
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus=HttpStatus.resolve(e.getHttpStatusCode());
        ResponseEntity<UnifyResponse> r=new ResponseEntity<UnifyResponse>(message,headers,httpStatus);
        return r;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(code=HttpStatus.BAD_REQUEST) //设置了这个注解就不用返回ResponseEntity了
    public UnifyResponse handleBeanValidation(HttpServletRequest req,MethodArgumentNotValidException e){
        String requestUrl=req.getRequestURI();
        String method=req.getMethod();
        String message=e.getMessage();
        return new UnifyResponse(10001,message,method+" "+requestUrl);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handleMissingServletRequestParameterException(HttpServletRequest req, MissingServletRequestParameterException e){
        String requestUrl=req.getRequestURI();
        String method=req.getMethod();
        String missingParam=e.getParameterName();
        String type=e.getParameterType();
        String message="需要的"+type+"参数"+ missingParam+"不存在" ;
        return new UnifyResponse(10001,message,method+" "+requestUrl);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handleConstraintException(HttpServletRequest req, ConstraintViolationException errors){
        String requestUrl=req.getRequestURI();
        StringBuilder errorMsg=new StringBuilder();
        for(ConstraintViolation error: errors.getConstraintViolations()){
            String message=error.getMessage();
            String m=error.getPropertyPath().toString();
            String name=m.split("[.]")[1];
            errorMsg.append(name).append(" ").append(message).append(" ").append(m);
        }
        String method=req.getMethod();
        return new UnifyResponse(10001,errorMsg.toString(),method+" "+requestUrl);
    }

    //用于拼接错误的信息
    private String formatAllErrorMessages(List<ObjectError> errors){
        StringBuffer errorMsg=new StringBuffer();
        for (ObjectError error : errors) {
            errorMsg.append(error.getDefaultMessage()).append(';');
        }
        return errorMsg.toString();
    }
}
