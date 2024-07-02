package com.hydroyura.springboot.kafka.msg.wrapper.producer.configs;

import com.hydroyura.springboot.kafka.msg.wrapper.producer.services.MsgWrapperProducer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

@AutoConfiguration
@Import(value = ProducerProperties.class)
public class ProducerConfig {

    @Autowired
    private ProducerProperties producerProperties;

    @Bean
    ProducerFactory<String, byte[]> producerFactory() {
        return new DefaultKafkaProducerFactory<String, byte[]>(buildProps());
    }

    @Bean
    KafkaTemplate<String, byte[]> kafkaTemplate(ProducerFactory<String, byte[]> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    Map<String, Object> buildProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(BOOTSTRAP_SERVERS_CONFIG, List.of(producerProperties.getUrl()));
        props.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        return props;
    }

    @Bean
    MsgWrapperProducer msgWrapperProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        return new MsgWrapperProducer(kafkaTemplate);
    }

}
