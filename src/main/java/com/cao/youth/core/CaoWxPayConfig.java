package com.cao.youth.core;


import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author 曹学习
 * @description CaoWxPayConfig
 * @date 2020/9/7 12:44
 */
public class CaoWxPayConfig extends WXPayConfig {
    public InputStream getCertStream() {
        return null;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    public IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }

    //填写自己的id
    public String getAppID(){
        return "wx72074e95366c5e42";
    }

    //从小程序商户平台获取的
    public String getMchID(){
        return "1473426802";
    }

    //从小程序商户平台获取的
    public String getKey(){
        return "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb";
    }



}
