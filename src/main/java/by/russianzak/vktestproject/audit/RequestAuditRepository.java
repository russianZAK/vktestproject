package by.russianzak.vktestproject.audit;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestAuditRepository extends ElasticsearchRepository<RequestAudit, String> {
}