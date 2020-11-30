package com.cao.youth.exception.http;

/**
 * @author 曹学习
 * @description NotFoundException
 * @date 2020/8/16 13:10
 */
public class NotFoundException extends HttpException {
    public NotFoundException(int code){
        this.httpStatusCode=404;
        this.code=code;
    }

}
