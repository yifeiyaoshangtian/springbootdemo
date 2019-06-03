package com.example.springbootdemo.service;

import com.example.springbootdemo.param.RabbitMQParam;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息到rabbitmq服务器上
     */
    public void send(){
        //Message需要自己构造一个；定义消息体内容和消息头
        //rabbitTemplate.send(exchange,routingKey,message);

        //object默认当成消息体,只需要传入要发送的对象，自动序列化发送给rabbitmq
        //rabbitTemplate.convertAndSend(exchange,routingkey,object);
        Map<String,Object> map = new HashMap<>();
        map.put("msg","这是第一个消息");
        map.put("data", Arrays.asList("helloworld",123,true));
        //对象被默认序列化以后发送出去
        rabbitTemplate.convertAndSend("exchange.direct","atguigu.news",new RabbitMQParam("王一飞",24));//map
        //还可以选择广播模式fanout(不管路由键，所有绑定队列都将收到消息),匹配模式topic(路由键中，#代表0个或多个单词,*代表1个单词)
        /*
        每种交换机exchange可以指定一种类型，常用的有direct,fanout,topic类型
        direct:点对点模式，路由键必须完全相同的队列才能接受到消息
        fanout:广播模式，不管路由键是什么，所有绑定该交换机的队列都将接受到消息
        topic:匹配模式，只有匹配成功的才能接受到消息，如路由键为atguigu.#能匹配到atguigu.news和atguigu.emps队列等
         */
    }


    /**
     * 从rabbitmq服务器上接受消息
     * 执行一次，消费一个数据，自行上网怎么做才能有就消费？？?
     * 如果队列无数据，获取对象将为null，注意抛出异常或者非空处理后再时候
     *
     */
    public void receive(){
        //监听队列，消费数据
        Object object = rabbitTemplate.receiveAndConvert("atguigu.news");
        if (object != null){
            System.out.println(object.getClass());//打印数据类型，class java.util.HashMap
            System.out.println(object);//打印数据，{msg=这是第一个消息, data=[helloworld, 123, true]}
        }
    }

    /**在test上对service层方法的测试
     *  @Autowired
     *     private RabbitMQService rabbitMQService;
     *
     *     @Test
     *     public void contextLoads() throws SQLException {
     *         rabbitMQService.send();
     * //        rabbitMQService.receive();
     *     }
     */
}

/**官方基础实现
 * private final static String QUEUE_NAME = "hello";
 *
 *     @Test
 *     public void send() throws Exception {
 *         ConnectionFactory connectionFactory = new ConnectionFactory();
 *         connectionFactory.setHost("129.204.36.221");
 *         try (Connection connection = connectionFactory.newConnection();
 *              Channel channel = connection.createChannel()){
 *             channel.queueDeclare(QUEUE_NAME,false,false,false,null);
 *             String message = "Hello World!";
 *             channel.basicPublish("",QUEUE_NAME,null,message.getBytes("UTF-8"));
 *             System.out.println(" [x] Sent '" + message + "'");
 *         }
 *     }
 *
 *     @Test
 *     public void recv() throws Exception {
 *         ConnectionFactory connectionFactory = new ConnectionFactory();
 *         connectionFactory.setHost("129.204.36.221");
 *         Connection connection = connectionFactory.newConnection();
 *         Channel channel = connection.createChannel();
 *         channel.queueDeclare(QUEUE_NAME,false,false,false,null);
 *         System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
 *         DeliverCallback deliverCallback = (consumerTag, delivery) -> {
 *             String message = new String(delivery.getBody(), "UTF-8");
 *             System.out.println(" [x] Received '" + message + "'");
 *         };
 *         channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
 *     }
 */
