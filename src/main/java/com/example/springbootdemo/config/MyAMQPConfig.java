package com.example.springbootdemo.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 这是一个配置类，配置消息转换器，使用json转换器代替默认tomcat的简单转换类型
 * 这样可以在rabbitmq服务的15672端口看到发送的json数据原样，而不是序列化后的数据
 */
@Configuration
public class MyAMQPConfig {

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
