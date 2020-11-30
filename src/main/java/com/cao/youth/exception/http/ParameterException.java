package com.cao.youth.exception.http;

/**
 * @author 曹学习
 * @description NotFoundException
 * @date 2020/8/26 18:23
 */
public class ParameterException extends HttpException {
    public ParameterException(int code){
        this.code = code;
        this.httpStatusCode = 400;
    }
}
