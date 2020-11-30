package com.cao.youth.manager.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author 曹学习
 * @description ConsumerSchedule
 * @date 2020/9/21 14:49
 */
@Component
public class ConsumerSchedule implements CommandLineRunner {
    @Value("${rocketmq.producer.producer-group}")
    private String consumerGroup;

    @Value("${rocketmq.namesrv-addr}")
    private String namesrvAddr;

    public void messageListener() throws MQClientException {
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.subscribe("TopicTest","*");
        consumer.setConsumeMessageBatchMaxSize(1);//每次消费几条消息
        consumer.registerMessageListener((MessageListenerConcurrently) (messages, context)->{
            for(Message message:messages){
                System.out.println("消息:"+new String(message.getBody()));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }); //注册了一个回调函数,message为实际收到的message
        consumer.start();
    }

    @Override
    public void run(String... args) throws Exception {
        this.messageListener();
    }
}
