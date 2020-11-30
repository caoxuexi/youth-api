package com.cao.youth.exception.http;

import lombok.Getter;

/**
 * @author 曹学习
 * @description HttpException
 * @date 2020/8/16 13:08
 */
@Getter
public class HttpException extends RuntimeException{
    protected Integer code;
    protected Integer httpStatusCode=500;
}
