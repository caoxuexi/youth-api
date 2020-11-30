
package com.cao.youth.exception;

import com.cao.youth.exception.http.HttpException;
/**
 * @author 曹学习
 * @description ServerErrorException
 * @date 2020/8/30 11:34
 */
public class UpdateSuccess extends HttpException {
    public UpdateSuccess(int code){
        this.httpStatusCode = 200;
        this.code = code;
    }
//    201 202 204
}
