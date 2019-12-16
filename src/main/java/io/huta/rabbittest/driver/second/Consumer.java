package io.huta.rabbittest.driver.second;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class Consumer {

    private final Channel channel;
    private final ExecutorService executorService;
    private final String topic;
    private final String exchange;


    public Consumer(Channel channel, ExecutorService executorService, String topic, String exchange) {
        this.channel = channel;
        this.executorService = executorService;
        this.topic = topic;
        this.exchange = exchange;
    }

    void start() {
        executorService.submit(() -> {
            try {
                channel.exchangeDeclare(exchange, BuiltinExchangeType.TOPIC);
                channel.queueDeclare(topic, false, false, true, new HashMap<>(0));
                channel.queueBind(topic, exchange, "");
                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), "UTF-8");
                    System.out.println(LocalDateTime.now() + " Consume: " + message);
                };
                channel.basicConsume(topic, true, deliverCallback, consumerTag -> { });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
