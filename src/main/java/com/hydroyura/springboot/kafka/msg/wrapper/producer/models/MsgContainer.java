package com.hydroyura.springboot.kafka.msg.wrapper.producer.models;

public class MsgContainer {
    private Object body;
    private String type, className;


    public MsgContainer() {}


    public Object getBody() {
        return body;
    }

    public MsgContainer setBody(Object body) {
        this.body = body;
        return this;
    }

    public String getType() {
        return type;
    }

    public MsgContainer setType(String type) {
        this.type = type;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public MsgContainer setClassName(String className) {
        this.className = className;
        return this;
    }
}
