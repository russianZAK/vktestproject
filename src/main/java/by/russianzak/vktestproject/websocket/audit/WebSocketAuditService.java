package by.russianzak.vktestproject.websocket.audit;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class WebSocketAuditService {

  private final WebSocketAuditRepository auditRepository;

  public void saveWebSocketAuditLog(final WebSocketAudit log) {
    auditRepository.save(log);
  }
}
