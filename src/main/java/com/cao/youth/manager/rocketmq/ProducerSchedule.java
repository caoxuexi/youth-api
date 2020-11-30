package com.cao.youth.manager.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author 曹学习
 * @description Producer
 * @date 2020/9/20 12:05
 */
@Component
public class ProducerSchedule {
    private DefaultMQProducer producer;

    @Value("${rocketmq.producer.producer-group}")
    private  String producerGroup;

    @Value("${rocketmq.namesrv-addr}")
    private String namesrvAddr;

    public ProducerSchedule() {
    }

    /*
      用于解决构造函数内无法读取配置问题
      @PostConstruct在类初始化使用的时候，希望使用被spring托管的对象或值得时候要使用这个注解
      Constructor – Autowired - PostConstruct
     */
    @PostConstruct
    public void defaultMQProducer(){
        if(this.producer==null){
            this.producer=new DefaultMQProducer(this.producerGroup);
            this.producer.setNamesrvAddr(this.namesrvAddr);
        }
        try {
            this.producer.start();
            System.out.println("-----producer start------");
        }catch (MQClientException e){
            e.printStackTrace();
        }
    }

    //发送消息
    public String send(String topic,String messageText) throws Exception{
        Message message=new Message(topic,messageText.getBytes());
        //设置的是级别，4-对应30s
        message.setDelayTimeLevel(4);
        SendResult result=this.producer.send(message);
        System.out.println(result.getMsgId());
        System.out.println(result.getSendStatus());
        return result.getMsgId();
    }
}
