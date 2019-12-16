package io.huta.rabbittest.scs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class Producer {

    private final QueueTestProcessor processor;
    private final ExecutorService executor;

    public Producer(QueueTestProcessor processor) {
        this.processor = processor;
        this.executor = Executors.newFixedThreadPool(10);
    }



    @Scheduled(fixedDelay = 1000)
    void test() throws InterruptedException {
        executor.submit(
                () -> processor.sendMsg("test:" + LocalDateTime.now().getSecond())
        );
    }

}
