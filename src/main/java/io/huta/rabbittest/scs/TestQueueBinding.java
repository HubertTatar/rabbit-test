package io.huta.rabbittest.scs;

import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(ProcessorChannels.class)
public class TestQueueBinding {
}
