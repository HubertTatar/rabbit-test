package io.huta.rabbittest.driver.first;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class DefaultTest {

    public static void main(String[] args) throws IOException, TimeoutException {
        DefaultTest t = new DefaultTest();
        ConnectionFactory cf = t.cf();
        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();
//        channel.confirmSelect();

        String exchange = "test_ex";
        String topic = "test";
        Producer p = new Producer(channel, Executors.newFixedThreadPool(1), exchange);
        Consumer c = new Consumer(channel, Executors.newFixedThreadPool(1), topic, exchange);

        p.start();
        c.start();
    }


    ConnectionFactory cf () {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("localhost");
        cf.setUsername("guest");
        cf.setPassword("guest");

        return cf;
    }

}
