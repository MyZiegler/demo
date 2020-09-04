package com.wpw.demo.service.impl;

import com.wpw.demo.service.ProducerService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @auther wupeiwen
 * @description
 * @date 2020/9/3 0003
 */

@Service
public class ProducerServiceImpl implements ProducerService {

    private DefaultMQProducer defaultMQProducer;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Override
    public void createTopic(String topic) throws MQClientException {
        // key: 系统已经存在的一个topic的名称
        defaultMQProducer.createTopic("TopicTest",topic,8);
    }

    @Override
    public void sendMsg(String topic, String msg) {

//        try {
//            Message message = new Message(topic,msg.getBytes());
//            SendResult send = defaultMQProducer.send(message);
//            System.out.println(send.getMsgId());
//        } catch (MQClientException e) {
//            e.printStackTrace();
//        } catch (RemotingException e) {
//            e.printStackTrace();
//        } catch (MQBrokerException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void sendSimpleMsg(String msg) {
        //封装消息体

        try {
            Message<String> message = MessageBuilder
                    .withPayload(msg)
                    .setHeader(RocketMQHeaders.TAGS,"自定义TAGS")
                    .setHeader(RocketMQHeaders.KEYS,"自定义KEY")
                    .setHeader(RocketMQHeaders.FLAG,"自定义FLAG")
                    .build();
            rocketMQTemplate.syncSend("topic123", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化生产者
     */
//    @PostConstruct
    public void initProducer(){
        defaultMQProducer = new DefaultMQProducer();
        // 设置生产组
        defaultMQProducer.setProducerGroup("LvTestGroup1");
        // 设置
        defaultMQProducer.setInstanceName("instance1");
        // 设置nameService地址，多个用';'隔开
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        // 设置当产出消息失败时，重试的次数
        defaultMQProducer.setRetryTimesWhenSendFailed(3);
        try {
            defaultMQProducer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 摧毁生产者
     */
//    @PreDestroy
    public void destroyProducer(){
        if(defaultMQProducer == null){
            defaultMQProducer.shutdown();
        }
    }

}
