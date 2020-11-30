package com.cao.youth.lib;

import com.cao.youth.exception.http.ServerErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author 曹学习
 * @description CaoWxNotify 用于Wx支付接口返回
 * @date 2020/9/9 23:14
 */
public class CaoWxNotify {
    public static String readNotify(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();

        String line;

        try {
            //按行读取
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new ServerErrorException(9999);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new ServerErrorException(9999);
            }
        }
        return builder.toString();
    }

    public static String fail() {
        return "false";
    }

    public static String success() {
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }
}
