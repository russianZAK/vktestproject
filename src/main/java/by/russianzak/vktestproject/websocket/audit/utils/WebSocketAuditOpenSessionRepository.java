package by.russianzak.vktestproject.websocket.audit.utils;

import by.russianzak.vktestproject.websocket.audit.WebSocketAudit;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class WebSocketAuditOpenSessionRepository {
  private ConcurrentHashMap<String, WebSocketAudit> audits = new ConcurrentHashMap<>();

  public void addNewAudit(WebSocketAudit audit) {
    audits.put(audit.getSessionId(), audit);
  }

  public void addMessageToAudit(String session, String message) {
    audits.get(session).addNewMessage(message);
  }
  public void addResponseToAudit(String session, String response, String time) {
    audits.get(session).addNewResponse(response, time);
  }

  public void setClosedTime(String session) {
    audits.get(session).setCloseTime(LocalDateTime.now().format(
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
  }

  public WebSocketAudit deleteAudit(String session) {
    WebSocketAudit audit = audits.get(session);
    audits.remove(session);
    return audit;
  }

}
