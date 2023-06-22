package ru.element.lab.dedup.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.element.lab.emd.streaming.model.OdsEmdNode;

import java.util.List;

/**
 * Репозиторий для персистентности запросов ЭМД.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class OdsEmdNodeRepositoryImpl implements OdsEmdNodeRepository {

    @Qualifier("ods-emd-node-template")
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void batchInsertNodes(List<OdsEmdNode> nodes) {
        try {
            jdbcTemplate.batchUpdate("insert into emd_node_hunter (ods_id, document_id, value, ods_table_name, ods_column_name, region_id) values (?,?,?,?,?,?)",
                    nodes, 1000,
                    (ps, node) -> {
                        ps.setString(1, node.getOdsId());
                        ps.setString(2, node.getDocumentId());
                        ps.setString(3, node.getValue());
                        ps.setString(4, node.getOdsTableName());
                        ps.setString(5, node.getOdsColumnName());
                        ps.setString(6, node.getRegionId());
                    }
            );
        } catch (Exception e) {
            log.error("On save: ", e);
        }

        log.info("Stored {}", nodes.size());
    }
}
