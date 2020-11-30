package com.cao.youth.manager.redis;

import com.cao.youth.bo.OrderMessageBO;
import com.cao.youth.service.CouponBackService;
import com.cao.youth.service.OrderCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * @author 曹学习
 * @description TopicMessageListener
 * @date 2020/9/16 0:03
 */
public class TopicMessageListener implements MessageListener {
    @Autowired
    private OrderCancelService orderCancelService;

    @Autowired
    private CouponBackService couponBackService;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body =message.getBody();
        byte[] channel= message.getChannel();
        String expiredKey =new String(body);
        String topic=new String(channel);
        OrderMessageBO messageBO=new OrderMessageBO(expiredKey);
        orderCancelService.cancel(messageBO);
        couponBackService.returnBack(messageBO);
    }
}
