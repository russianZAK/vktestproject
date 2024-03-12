package by.russianzak.vktestproject.websocket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@RequiredArgsConstructor
public class EchoWebSocketHandler implements WebSocketHandler {

  private final WebSocketSession clientSession;

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    String responseMessage = message.getPayload().toString();
    if (!responseMessage.startsWith("Request served by")) {
      clientSession.sendMessage(new TextMessage(responseMessage));
      clientSession.getAttributes().put("response", responseMessage);
      clientSession.getAttributes().put("responseDate", LocalDateTime.now().format(
          DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
  }

  @Override
  public boolean supportsPartialMessages() {
    return false;
  }
}
