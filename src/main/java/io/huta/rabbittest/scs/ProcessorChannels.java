package io.huta.rabbittest.scs;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface ProcessorChannels {

    String input = "queue-test-input";
    String output = "queue-test-output";

    @Input(input)
    SubscribableChannel queueTestInput();

    @Output(output)
    MessageChannel queueTestOutput();

}
