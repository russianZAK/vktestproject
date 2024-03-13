package by.russianzak.vktestproject.websocket.audit;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(indexName = "websoket-audits")
public class WebSocketAudit {

  @Id
  private Long id;

  @Field(type = FieldType.Keyword, name = "session_id")
  private String sessionId;

  @Field(type = FieldType.Keyword, name = "open_time", format = DateFormat.date_time)
  private String openTime;

  @Field(type = FieldType.Keyword, name = "close_time", format = DateFormat.date_time)
  private String closeTime;

  @Field(type = FieldType.Keyword, name = "username")
  private String username;

  @Builder.Default
  private Set<WebSocketMessage> messages = new HashSet<>();

  @Builder.Default
  private Set<WebSocketMessage> responses = new HashSet<>();

  public void addNewMessage(String message) {
    WebSocketMessage webSocketMessage = new WebSocketMessage();
    webSocketMessage.setMessage(message);
    webSocketMessage.setSendTime(LocalDateTime.now().format(
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    messages.add(webSocketMessage);
  }

  public void addNewResponse(String response, String time) {
    WebSocketMessage webSocketMessage = new WebSocketMessage();
    webSocketMessage.setMessage(response);
    webSocketMessage.setSendTime(time);
    responses.add(webSocketMessage);
  }
}
