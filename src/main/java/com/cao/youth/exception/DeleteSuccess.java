
package com.cao.youth.exception;

import com.cao.youth.exception.http.HttpException;
/**
 * @author 曹学习
 * @description ServerErrorException
 * @date 2020/8/30 11:34
 */
public class DeleteSuccess extends HttpException {
    public DeleteSuccess(int code){
        this.httpStatusCode = 200;
        this.code = code;
    }
    // 200 201 204 200
    // 200 201 200 200

    // Create：201 资源本身
    // Get: 200
    // Put: 200
    // Delete: 200
}
