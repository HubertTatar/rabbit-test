package io.huta.rabbittest.scs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class QueueTestProcessor {
//https://cloud.spring.io/spring-cloud-static/spring-cloud-stream-binder-rabbit/2.1.0.RC3/single/spring-cloud-stream-binder-rabbit.html
    private final Logger logger = LoggerFactory.getLogger(QueueTestProcessor.class);

    private final ProcessorChannels processorChannels;
    private final DestinationResolver<MessageChannel> binderAwareChannelResolver;

    public QueueTestProcessor(ProcessorChannels processorChannels,
                              DestinationResolver<MessageChannel> binderAwareChannelResolver) {
        this.processorChannels = processorChannels;
        this.binderAwareChannelResolver = binderAwareChannelResolver;
    }

    @StreamListener(ProcessorChannels.input)
    void routeValuesToAnOutput(String message) {
        logger.info(message);
    }

    public void sendMsg2(String msg) {
        logger.info("{} before sending", Thread.currentThread().getName());
        boolean isSent = processorChannels.queueTestOutput()
                .send(
                        MessageBuilder
                                .withPayload(msg)
                                .build(),
                        2000
                );
        logger.info("{} isSent: {}", Thread.currentThread().getName(), isSent);
    }

    public void sendMsg(String msg) {
        logger.info("{} before sending", Thread.currentThread().getName());
        MessageChannel messageChannel = binderAwareChannelResolver.resolveDestination(ProcessorChannels.output);
        boolean isSent = messageChannel
                .send(
                        MessageBuilder
                                .withPayload(msg)
                                .build()
                );
        logger.info("{} isSent: {}", Thread.currentThread().getName(), isSent);
    }
}
