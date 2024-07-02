package com.hydroyura.springboot.kafka.msg.wrapper.producer.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka-msg-wrapper.producer")
public class ProducerProperties {

    private String url;


    public ProducerProperties() {
        initDefaultValues();
    }


    private void initDefaultValues() {
        this.url = "localhost:9092";
    }

    public String getUrl() {
        return url;
    }

    public ProducerProperties setUrl(String url) {
        this.url = url;
        return this;
    }
}

