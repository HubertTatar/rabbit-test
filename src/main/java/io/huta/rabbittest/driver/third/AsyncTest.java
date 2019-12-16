package io.huta.rabbittest.driver.third;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class AsyncTest {

    public static void main(String[] args) throws IOException, TimeoutException {
        AsyncTest t = new AsyncTest();
        ConnectionFactory cf = t.cf();
        Connection connection1 = cf.newConnection();
        Connection connection2 = cf.newConnection();
        Channel channel1 = connection1.createChannel();
        Channel channel2 = connection2.createChannel();
        channel1.confirmSelect();
        channel1.addConfirmListener((sequenceNumber, multiple) -> {
            System.out.println("ack");
        }, (sequenceNumber, multiple) -> {
            System.out.println("nack");
        });

        String exchange = "test_ex";
        String topic = "test";
        Producer p = new Producer(channel1, Executors.newFixedThreadPool(1), exchange);
        Consumer c = new Consumer(channel2, Executors.newFixedThreadPool(1), topic, exchange);

        p.start();
        c.start();
    }


    ConnectionFactory cf () {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("localhost");
        cf.setUsername("guest");

        return cf;
    }

}
