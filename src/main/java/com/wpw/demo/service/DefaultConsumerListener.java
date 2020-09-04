package com.wpw.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @auther wupeiwen
 * @description
 * @date 2020/9/3 0003
 */


@Service
@RocketMQMessageListener(
        topic = "topic123",//指定toipc
        consumerGroup = "lvJieTestGroup"//指定消费者组
)
@Slf4j
public class DefaultConsumerListener implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt messageExt) {
        String tags = messageExt.getTags();
        String topic = messageExt.getTopic();

        log.info("topic->" + topic + "__tags->" + tags + ",received message: {}", new String(messageExt.getBody()));
    }
}