package com.wpw.demo.controller;


import com.wpw.demo.service.ProducerService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * quarzcontroller
 *
 * @author limengyang
 * create 2020-02-13
 **/
@RestController
@RequestMapping("/mq")
public class RocketMQController {


    @Autowired
    private ProducerService producerService;

    /**
     * 发送消息
     */
    @GetMapping("send")
    public String send(String topic,String msg) throws Exception {
        producerService.sendSimpleMsg(msg);
        return "success";
    }

    /**
     * 创建topic
     */
    @GetMapping("createTopic")
    public String detail(String topic) throws MQClientException {
        producerService.createTopic(topic);
        return "success";
    }
}
