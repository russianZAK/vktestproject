package by.russianzak.vktestproject.websocket;

import by.russianzak.vktestproject.websocket.audit.WebSocketAuditService;
import by.russianzak.vktestproject.websocket.audit.utils.WebSocketAuditOpenSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
  private final WebSocketAuditOpenSessionRepository webSocketAuditOpenSessionRepository;
  private final WebSocketAuditService webSocketAuditService;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(new CustomWebSocketHandler(webSocketAuditOpenSessionRepository, webSocketAuditService), "/ws").setAllowedOrigins("*");
  }


}
