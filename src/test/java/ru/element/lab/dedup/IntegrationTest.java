package ru.element.lab.dedup;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.element.lab.dedup.dto.ProcessedTables;

import java.time.OffsetDateTime;
import java.util.Arrays;

/**
 * Для отправки тестового сообщения на стенд запустите тест.
 */
@SpringBootTest
@Slf4j
@Disabled("Интеграционный тест выключен для CI")
public class IntegrationTest {

    @Autowired
    KafkaTablesProducer kafkaProducer;

    @Test
    @SneakyThrows
    public void sendRawOmsEmdToKafka() {
        kafkaProducer.send(ProcessedTables.builder()
                .tables(Arrays.asList("one", "two"))
                //.time("time")
                        .time(OffsetDateTime.now())
                .build()).get();

        Thread.sleep(10_000);
    }
}
