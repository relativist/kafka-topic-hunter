package ru.element.lab.dedup.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Model для ответа таблиц и колонок.
 */
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TableWithColumn implements Serializable {
    /**
     * Имя таблицы.
     */
    private String odsTableName;
    /**
     * Имя колонки.
     */
    private String odsColumnName;
}
