package com.cao.youth.manager.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

/**
 * @author 曹学习
 * @description MessageListenerConfiguration
 * @date 2020/9/16 0:05
 */
@Configuration
public class MessageListenerConfiguration {
    @Value("${spring.redis.listen-pattern}")
    public String pattern;

    @Bean
    public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory redisConnection){
        //这个Container可以理解为和redis的一个连接类
        RedisMessageListenerContainer container=new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnection);
        Topic topic=new PatternTopic(pattern);
        //第一个参数是监听器，第二个是监听主题
        container.addMessageListener(new TopicMessageListener(),topic);
        return container;
    }
}
