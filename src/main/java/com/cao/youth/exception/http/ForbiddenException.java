package com.cao.youth.exception.http;

/**
 * @author 曹学习
 * @description ForbiddenException
 * @date 2020/8/16 13:11
 */
public class ForbiddenException extends HttpException {
    public ForbiddenException(int code){
        this.code=code;
        this.httpStatusCode=403;
    }
}
