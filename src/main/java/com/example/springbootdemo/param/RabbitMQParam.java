package com.example.springbootdemo.param;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 这是一个测试参数类
 */
public class RabbitMQParam {

    String name;

    Integer age;

    public RabbitMQParam(){}

    public RabbitMQParam(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
