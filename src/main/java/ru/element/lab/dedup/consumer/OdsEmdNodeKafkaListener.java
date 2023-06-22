package ru.element.lab.dedup.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.element.lab.dedup.repository.OdsEmdNodeRepository;
import ru.element.lab.emd.streaming.model.OdsEmdNode;

import java.util.List;

/**
 * Listener для приёма данных Нод!.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OdsEmdNodeKafkaListener {
    private final OdsEmdNodeRepository repository;

    @KafkaListener(topics = "oms-SL_USL", containerFactory = "odsEmdNodeKafkaSafeConsumerFactory", groupId = "oms-hunter-service-2")
    public void listen(@Payload List<OdsEmdNode> nodes) {
        log.info("Receive count: {}", nodes.size());
        repository.batchInsertNodes(nodes);
    }
}
