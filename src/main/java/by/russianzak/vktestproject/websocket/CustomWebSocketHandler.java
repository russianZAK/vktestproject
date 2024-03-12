package by.russianzak.vktestproject.websocket;

import by.russianzak.vktestproject.websocket.audit.WebSocketAudit;
import by.russianzak.vktestproject.websocket.audit.WebSocketAuditService;
import by.russianzak.vktestproject.websocket.audit.utils.WebSocketAuditOpenSessionRepository;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.client.WebSocketClient;

@Component
@RequiredArgsConstructor
public class CustomWebSocketHandler implements WebSocketHandler {

  private final WebSocketAuditOpenSessionRepository webSocketAuditOpenSessionRepository;
  private final WebSocketAuditService webSocketAuditService;

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    Principal principal = session.getPrincipal();
    String username;
    if (principal != null) {
      username = principal.getName();
    } else {
      username = "Anonymous";
    }
    WebSocketAudit audit = new WebSocketAudit();
    audit.setUsername(username);
    audit.setOpenTime(LocalDateTime.now().format(
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    audit.setSessionId(session.getId());
    webSocketAuditOpenSessionRepository.addNewAudit(audit);
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    String messageContent = message.getPayload().toString();
    String echoServerURL = "wss://echo.websocket.org";
    WebSocketClient client = new StandardWebSocketClient();
    webSocketAuditOpenSessionRepository.addMessageToAudit(session.getId(), messageContent);
    WebSocketSession echoSession = client.doHandshake(new EchoWebSocketHandler(session), echoServerURL).get();
    echoSession.sendMessage(new TextMessage(messageContent));
    webSocketAuditOpenSessionRepository.addResponseToAudit(session.getId(),(String) session.getAttributes().get("response"), (String) session.getAttributes().get("responseDate"));
    echoSession.close();
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    webSocketAuditOpenSessionRepository.addResponseToAudit(session.getId(),(String) session.getAttributes().get("response"), (String) session.getAttributes().get("responseDate"));
    webSocketAuditOpenSessionRepository.setClosedTime(session.getId());
    webSocketAuditService.saveWebSocketAuditLog(webSocketAuditOpenSessionRepository.deleteAudit(session.getId()));
  }

  @Override
  public boolean supportsPartialMessages() {
    return false;
  }

}
