package ru.element.lab.dedup.repository;

import ru.element.lab.emd.streaming.model.OdsEmdNode;

import java.util.List;

/**
 * Репозиторий для персистентности запросов ЭМД.
 */
public interface OdsEmdNodeRepository {

    void batchInsertNodes(List<OdsEmdNode> nodes);

}
