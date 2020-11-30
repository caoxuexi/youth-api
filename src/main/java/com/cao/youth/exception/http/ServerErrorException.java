package com.cao.youth.exception.http;

/**
 * @author 曹学习
 * @description ServerErrorException
 * @date 2020/8/23 23:36
 */
public class ServerErrorException extends HttpException {
    public ServerErrorException(int code){
        this.code=code;
        this.httpStatusCode=500;
    }
}
