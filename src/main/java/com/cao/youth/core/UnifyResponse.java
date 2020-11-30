package com.cao.youth.core;

import com.cao.youth.exception.CreateSuccess;
import lombok.Data;

/**
 * @author 曹学习
 * @description UnifyResponse
 * @date 2020/8/16 22:54
 */
@Data
public class UnifyResponse {
    private int code;
    private String message;
    private String request;

    public UnifyResponse(int code,String message,String request) {
        this.code = code;
        this.message = message;
        this.request = request;
    }

    public static void createSuccess(int code) {
        throw new CreateSuccess(code);
    }

}
