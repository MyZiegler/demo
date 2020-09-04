package com.wpw.demo.service;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

public interface ProducerService {

    void createTopic(String topic) throws MQClientException;

    void sendMsg(String topic, String msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException, Exception;

    void sendSimpleMsg(String msg);
}
