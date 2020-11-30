package com.cao.youth.api.v1;

import com.cao.youth.manager.rocketmq.ProducerSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 曹学习
 * @description TestController
 * @date 2020/9/20 12:04
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private ProducerSchedule producerSchedule;

    @GetMapping("/push")
    public void pushMessageToMq() throws Exception {
        producerSchedule.send("TopicTest","test");
    }
}
