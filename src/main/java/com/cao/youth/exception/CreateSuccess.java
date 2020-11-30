package com.cao.youth.exception;


import com.cao.youth.exception.http.HttpException;

/**
 * @author 曹学习
 * @description ServerErrorException
 * @date 2020/8/23 23:36
 */
public class CreateSuccess extends HttpException {
    public CreateSuccess(int code){
        this.httpStatusCode = 201;
        this.code = code;
    }
//    201 202 204
}
