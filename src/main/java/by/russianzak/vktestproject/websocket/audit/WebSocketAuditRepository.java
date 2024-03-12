package by.russianzak.vktestproject.websocket.audit;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebSocketAuditRepository extends ElasticsearchRepository<WebSocketAudit, String> {
}
