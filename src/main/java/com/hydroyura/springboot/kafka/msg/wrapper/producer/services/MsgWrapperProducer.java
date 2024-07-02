package com.hydroyura.springboot.kafka.msg.wrapper.producer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hydroyura.springboot.kafka.msg.wrapper.producer.models.MsgContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.SendResult;

import java.util.function.BiConsumer;


public class MsgWrapperProducer {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    private ObjectMapper objectMapper = JacksonUtils.enhancedObjectMapper();

    private BiConsumer<SendResult<String, byte[]>, Throwable> resultHandler;

    public MsgWrapperProducer(KafkaTemplate<String,byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(String topic, Object msg, String eventType) {
        try {
            MsgContainer msgContainer = new MsgContainer()
                    .setBody(msg)
                    .setType(eventType)
                    .setClassName(msg.getClass().getName());

            BiConsumer<SendResult<String, byte[]>, Throwable> handler = (resultHandler != null)
                ? resultHandler
                : (res, ex) -> {
                    if (ex == null) {
                        LOG.info("Msg was sent to topic = [{}] successful, offset = [{}]", topic, res.getRecordMetadata().offset());
                    } else {
                        LOG.error("Msg did not send to topic = [{}]", topic);
                    }
                };

            kafkaTemplate
                    .send(topic, objectMapper.writeValueAsBytes(msgContainer))
                    .whenComplete(handler);
        } catch (JsonProcessingException e) {
            LOG.error("Cannot serialize msg to byte[]");
            throw new RuntimeException(e);
        }
    }
}