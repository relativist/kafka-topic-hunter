package ru.element.lab.dedup;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import ru.element.lab.dedup.dto.ProcessedTables;

import java.util.UUID;

@Slf4j
@Component
public class KafkaTablesProducer {

    private final static String TOPIC = "ods-stored-tables";

    @Autowired
    private KafkaTemplate<UUID, ProcessedTables> kafkaTemplate;

    public ListenableFuture<SendResult<UUID, ProcessedTables>> send(ProcessedTables tables) {
        ProducerRecord<UUID, ProcessedTables> record = new ProducerRecord<>(TOPIC, UUID.randomUUID(), tables);
        record.headers().add("X-Correlation-ID", UUID.randomUUID().toString().getBytes());
        return kafkaTemplate.send(record);
    }

}
