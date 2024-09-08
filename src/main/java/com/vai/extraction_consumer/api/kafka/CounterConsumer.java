package com.vai.extraction_consumer.api.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class CounterConsumer {
    @KafkaListener(
            topics = "${kafka.inbound-topic}",
            concurrency = "10"
    )
    public void listen(final @Payload String value,
                       final @Header(KafkaHeaders.OFFSET) Integer offset,
                       final @Header(KafkaHeaders.RECEIVED_KEY) String key,
                       final @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                       final @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       final @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        System.out.printf("Receive message ::%s in partition :: %d%n", value, partition);
    }
}
