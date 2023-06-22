package ru.element.lab.dedup.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.element.lab.big.jpa.BigJpaAutoConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Конфигурация kafka. Берём из big-kafka.
 */
@Configuration
@Import(BigJpaAutoConfiguration.class)
public class KafkaConfig {
    private final ObjectMapper objectMapper;
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.request.size.max:209717600}")
    private Integer requestMaxSize;

    public KafkaConfig(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<UUID, Object> odsEmdNodeKafkaSafeConsumerFactory(ConsumerFactory<UUID, Object> odsEmdNodeKafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<UUID, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(odsEmdNodeKafkaConsumerFactory);
        factory.setBatchListener(true);
        //factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        //factory.getContainerProperties().setIdleBetweenPolls(30_000);

        return factory;
    }

    @Bean
    public ConsumerFactory<UUID, Object> odsEmdNodeKafkaConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, requestMaxSize);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        //config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "ru.element.lab.*");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100000");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "ods-emd-node-batch");
        config.put("spring.json.value.default.type", "ru.element.lab.emd.streaming.model.OdsEmdNode");
        final DefaultKafkaConsumerFactory<UUID, Object> factory = new DefaultKafkaConsumerFactory<>(config);
        factory.setValueDeserializer(new JsonDeserializer<>(objectMapper));

        return factory;
    }
}
