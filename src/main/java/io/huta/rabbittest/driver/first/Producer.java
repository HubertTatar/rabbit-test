package io.huta.rabbittest.driver.first;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

public class Producer {
    private final Channel channel;
    private final ExecutorService executorService;
    private final String exchange;

    public Producer(Channel channel, ExecutorService executorService, String exchange) {
        this.channel = channel;
        this.executorService = executorService;
        this.exchange = exchange;
    }

    void start() {
        executorService.submit(() -> {
            try {
                channel.exchangeDeclare(exchange, BuiltinExchangeType.TOPIC);
                while(true) {
                    System.out.println("Producer publish");
                    TimeUnit.SECONDS.sleep(1);
                    channel.basicPublish(exchange, "", null, "TEST".getBytes("UTF-8"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
