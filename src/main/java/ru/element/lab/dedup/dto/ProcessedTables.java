package ru.element.lab.dedup.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTO для обработанных таблиц.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessedTables implements Serializable {
    private List<String> tables;
    private OffsetDateTime time;
    private String schema;
    private EmdType type;
}
