package io.huta.rabbittest.template;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ConfigurationForTemplate {

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.afterPropertiesSet();
        return rabbitAdmin;
    }

    @Bean
    public Queue queue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue("template-queue");
        queue.setAdminsThatShouldDeclare(rabbitAdmin);
        return queue;
    }

    @Bean
    public Exchange exchange(RabbitAdmin rabbitAdmin) {
        DirectExchange exchange = new DirectExchange("template-exchange");
        exchange.setAdminsThatShouldDeclare(rabbitAdmin);
        return exchange;
    }

    @Bean
    public Binding binding(Exchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = new Binding("foo", Binding.DestinationType.QUEUE, exchange.getName(), "", null);
        binding.setAdminsThatShouldDeclare(rabbitAdmin);
        return binding;
    }
}
