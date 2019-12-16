package io.huta.rabbittest.scs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class ErrorsProcessor {

    private final Logger logger = LoggerFactory.getLogger(ErrorsProcessor.class);

    @StreamListener("errorChannel")
    void errorChannel(Message message) {
        logger.info(message.toString());
    }
}
