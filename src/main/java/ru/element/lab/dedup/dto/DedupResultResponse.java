package ru.element.lab.dedup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Ответ на запрос загрузки эталонного документа.
 */
@Data
@AllArgsConstructor
public class DedupResultResponse {
    private final String processId;
}
