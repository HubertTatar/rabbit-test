package io.huta.rabbittest.scs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class QueueTestProcessor {
//https://cloud.spring.io/spring-cloud-static/spring-cloud-stream-binder-rabbit/2.1.0.RC3/single/spring-cloud-stream-binder-rabbit.html
    private final Logger logger = LoggerFactory.getLogger(QueueTestProcessor.class);

    private final ProcessorChannels processorChannels;

    public QueueTestProcessor(ProcessorChannels processorChannels) {
        this.processorChannels = processorChannels;
    }

    @StreamListener(ProcessorChannels.input)
    void routeValuesToAnOutput(String message) {
        logger.info(message);
    }

    public void sendMsg(String msg) {
        logger.info(Thread.currentThread().getName() + "before sending");
        boolean isSent = processorChannels.queueTestOutput()
                .send(
                        MessageBuilder
                                .withPayload(msg)
                                .build(),
                        2000
                );
        logger.info(Thread.currentThread().getName() + " isSent: " + isSent);
    }
}
