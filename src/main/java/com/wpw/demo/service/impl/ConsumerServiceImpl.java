package com.wpw.demo.service.impl;

import com.wpw.demo.service.ConsumerService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * @auther wupeiwen
 * @description
 * @date 2020/9/3 0003
 */

@Service
public class ConsumerServiceImpl implements ConsumerService {

    // 主动拉取型
    // private DefaultMQPullConsumer defaultMQPullConsumer

    /**
     * 被动推送型
     */
    private DefaultMQPushConsumer defaultMQPushConsumer;

    /**
     * 初始化消费者
     */
    @PostConstruct
    public void initConsumer() {
        defaultMQPushConsumer = new DefaultMQPushConsumer();
        // 设置消费组 用于把多个Consumer 组织到一起
        defaultMQPushConsumer.setConsumerGroup("LvTestGroup");
        // 设置nameService地址
        defaultMQPushConsumer.setNamesrvAddr("127.0.0.1:9876");
        // 设置消费模式
        // 默认集群模式：同一个组里所有的Consumer消费的内容合起来才是所订阅Topic 内容的整体
        // 广播模式： 同一个组里每个Consumer都能消费到所订阅Topic的全部消息
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        try {
            // * 标签通配符，表示所有标签，也可以直接写某个标签的名称
            defaultMQPushConsumer.subscribe("lv_topic", "*");
            // MessageListenerOrderly型消费监听器，保证并发消费时，同一个Consumer Queue不会被并发消费
            defaultMQPushConsumer.registerMessageListener(new MessageListenerOrderly() {
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                    System.out.println(context.getMessageQueue().getQueueId());
                    for (MessageExt msg : msgs) {
                        System.out.println("消费队列queueId=" + msg.getQueueId() + "的消息：" + new String(msg.getBody()));
                        /**
                         * 业务逻辑
                         */
                    }
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });

            defaultMQPushConsumer.start();
            System.out.println("lv_topic 消费者启动成功...");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭消费者
     */
    @PreDestroy
    public void destroyConsumer() {
        if (defaultMQPushConsumer != null) {
            defaultMQPushConsumer.shutdown();
        }
    }
}
