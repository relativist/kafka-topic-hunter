package ru.element.lab.dedup;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.element.lab.big.jpa.BigJpaAutoConfiguration;
import ru.element.lab.dedup.dto.EmdType;
import ru.element.lab.dedup.dto.ProcessedTables;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class SendboxTest {

    @Test
    @SneakyThrows
    public void one() {
        final List<String> list = Arrays.asList("t1", "t2");
        ProcessedTables t = new ProcessedTables(
                list,
                OffsetDateTime.now(ZoneOffset.UTC),
                "schema_2",
                EmdType.OMS);
        final ObjectMapper m = new BigJpaAutoConfiguration().objectMapper();

        final String str = m.writeValueAsString(t);
        log.info("{}", str);
        final ProcessedTables p = m.readValue(str, ProcessedTables.class);
        log.info("{}", p);
    }

    @Test
    public void two() {
        String s = "2023-06-02T13:59:15.797+00:00";
        final OffsetDateTime parse = OffsetDateTime.parse(s);
        System.out.println(parse);
    }

    @Test
    @SneakyThrows
    public void three() {
        byte[] combined = (UUID.randomUUID() + "Document_id").getBytes();
        UUID uuid = UUID.nameUUIDFromBytes(combined);

        System.out.println(uuid);
    }
}
