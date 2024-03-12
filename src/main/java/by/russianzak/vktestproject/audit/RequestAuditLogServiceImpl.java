package by.russianzak.vktestproject.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestAuditLogServiceImpl {

  private final RequestAuditRepository auditRepository;

  public void saveAuditLog(final RequestAudit log) {
    auditRepository.save(log);
  }
}
