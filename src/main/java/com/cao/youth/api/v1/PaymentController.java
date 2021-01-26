package com.cao.youth.api.v1;

import com.cao.youth.core.interceptors.ScopeLevel;
import com.cao.youth.lib.CaoWxNotify;
import com.cao.youth.service.PaymentNotifyService;
import com.cao.youth.service.WxPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 曹学习
 * @description PaymentController
 * @date 2020/9/6 17:32
 */
@RequestMapping("payment")
@RestController
@Validated
public class PaymentController {
    @Autowired
    private WxPaymentService wxPaymentService;

    @Autowired
    private PaymentNotifyService paymentNotifyService;


//    start 当有微信支付的时候的逻辑
//    @PostMapping("/pay/order/{id}")
//    @ScopeLevel
//    public Map<String,String> preWxOrder(@PathVariable(name = "id") @Positive Long oid){
//        Map<String,String> miniPayParams=this.wxPaymentService.preOrder(oid);
//        return miniPayParams;
//    }

    //start 在没有微信支付的时候使用
    @PostMapping("/pay/order/{id}")
    @ScopeLevel
    public String preWxOrder(@PathVariable(name = "id") @Positive Long oid){
        try{
            this.paymentNotifyService.processPayNotifyNoWx(oid);
        }catch (Exception e){
            return CaoWxNotify.fail();
        }
        return CaoWxNotify.success();
    }

    //提供给微信支付的回调接口,因为是微信调用，所以我们需要使用原生的request和reponse来接收(微信支付)
    @RequestMapping("wx/notify")
    public String payCallback(HttpServletRequest request,
                              HttpServletResponse response){
        InputStream inputStream;
        try {
            inputStream=request.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return CaoWxNotify.fail();
        }
        //TODO 读取微信支付返回给我们
        String xml=CaoWxNotify.readNotify(inputStream);
        try{
            this.paymentNotifyService.processPayNotify(xml);
        }catch (Exception e){
            return CaoWxNotify.fail();
        }
        return CaoWxNotify.success();
    }
}
