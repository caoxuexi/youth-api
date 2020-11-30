package com.cao.youth.service.impl;

import com.cao.youth.core.CaoWxPayConfig;
import com.cao.youth.core.LocalUser;
import com.cao.youth.exception.http.ForbiddenException;
import com.cao.youth.exception.http.NotFoundException;
import com.cao.youth.exception.http.ParameterException;
import com.cao.youth.exception.http.ServerErrorException;
import com.cao.youth.model.Order;
import com.cao.youth.repository.OrderRepository;
import com.cao.youth.service.OrderService;
import com.cao.youth.service.WxPaymentService;
import com.cao.youth.util.CommonUtil;
import com.cao.youth.util.HttpRequestProxy;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 曹学习
 * @description WxPaymentService
 * @date 2020/9/6 23:11
 */
@Service
public class WxPaymentServiceImpl implements WxPaymentService {
    @Value("${youth.order.pay-callback-host}")
    private String payCallbackHost;

    @Value("${youth.order.pay-callback-path}")
    private String payCallbackPath;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    private static CaoWxPayConfig caoWxPayConfig=new CaoWxPayConfig();


    public Map<String,String> preOrder(Long oid){
        Long uid= LocalUser.getUser().getId();
        Optional<Order> optionalOrder=this.orderRepository.findFirstByUserIdAndId(uid,oid);
        Order order=optionalOrder.orElseThrow(()->new NotFoundException(50009));
        if(order.needCancel()){
            throw new ForbiddenException(50010);
        }
        WXPay wxPay=this.assembleWxPayConfig();
        Map<String,String> params=this.makePreOrderParams(order.getFinalTotalPrice(),order.getOrderNo());
        Map<String,String> wxOrder;//用来接收返回的微信服务器的订单信息
        try {
            //prepay_id(微信返回的) 保存到数据库中，防止重复生成订单
            wxOrder = wxPay.unifiedOrder(params);
        } catch (Exception e) {
            throw new ServerErrorException(9999);
        }
        //判断请求是否发送成功
        if(this.unifiedOrderSuccess(wxOrder)){
            this.orderService.updateOrderPrepayId(order.getId(),wxOrder.get("prepay_id"));
        }
        //生成一组小程序需要的参数返回到小程序去
        return this.makePaySignature(wxOrder);
    }

    private boolean unifiedOrderSuccess(Map<String,String> wxOrder){
        if(!wxOrder.get("return_code").equals("SUCCESS")
                ||!wxOrder.get("result_code").equals("SUCCESS")){
            throw new ParameterException(10007);
        }
        return true;
    }

    private Map<String,String> makePreOrderParams(BigDecimal serverFinalPrice,String orderNo){
        Map<String,String> data=new HashMap<>();
        data.put("body","Youth Goods");//商品标题
        data.put("out_trade_no",orderNo);//订单号
        data.put("device_info","Youth");
        data.put("fee_type","CNY");//设置为人民币
        data.put("trade_type","JSAPI");//交易方式
        //微信的价格是以分为单位的
        data.put("total_fee", CommonUtil.yuanToFenPlainString(serverFinalPrice));
        data.put("openid",LocalUser.getUser().getOpenid());//需要使用微信下的openId
        //需要获取用户的ip地址
        data.put("spbill_create_ip",HttpRequestProxy.getRemoteRealIp());
        String payCallbackUrl=this.payCallbackHost+this.payCallbackPath;
        data.put("notify_url",payCallbackUrl);
        return data;
    }
    
    private Map<String,String> makePaySignature(Map<String,String> wxOrder){
        //在小程序的开发文档里看需要的参数wx.requestPayment
        String packages= "prepay_id="+wxOrder.get("prepay_id");
        Map<String,String> wxPayMap=new HashMap<>();
        wxPayMap.put("appId",WxPaymentServiceImpl.caoWxPayConfig.getAppID());
        wxPayMap.put("timeStamp",CommonUtil.timestamp10());
        wxPayMap.put("nonceStr", RandomStringUtils.randomAlphanumeric(32));
        wxPayMap.put("package",packages);
        wxPayMap.put("signType","HMAC-SHA256");
        String sign;
        try {
            sign=WXPayUtil.generateSignature(wxPayMap,WxPaymentServiceImpl.caoWxPayConfig.getKey(),
                    WXPayConstants.SignType.HMACSHA256);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
        Map<String,String> miniPayParams=new HashMap<>();
        miniPayParams.put("paySign",sign);
        miniPayParams.putAll(wxPayMap);
        miniPayParams.remove("appId");
        return miniPayParams;
    }

    public WXPay assembleWxPayConfig(){
        WXPay wxPay;
        try {
            wxPay=new WXPay(WxPaymentServiceImpl.caoWxPayConfig);
        }catch (Exception e){
            throw new ServerErrorException(9999);
        }
        return wxPay;
    }
}
